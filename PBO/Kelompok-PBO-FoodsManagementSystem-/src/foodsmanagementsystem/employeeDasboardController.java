/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodsmanagementsystem;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Haikal Erlana
 */
public class employeeDasboardController implements Initializable {

    @FXML
    private Button close;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button minimize;

    @FXML
    private Button purchase_addBtn;

    @FXML
    private TextField purchase_brand;

    @FXML
    private TableColumn<customerData, String> purchase_col_brand;

    @FXML
    private TableColumn<customerData, String> purchase_col_price;

    @FXML
    private TableColumn<customerData, String> purchase_col_productName;

    @FXML
    private TableColumn<customerData, String> purchase_col_quantity;

    @FXML
    private Label purchase_employeeId;

    @FXML
    private Button purchase_pay;

    @FXML
    private ComboBox<?> purchase_productName;

    @FXML
    private Spinner<Integer> purchase_quantity;

    @FXML
    private Button purchase_receiptBtn;

    @FXML
    private TableView<customerData> purchase_tableView;

    @FXML
    private Label purchase_total;
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    
    public void purchaseAdd(){
        purchaseCustomerId();
        purchaseSpinnerValue();
        purchaseGetPrice();
        
        String insertProd = "INSERT INTO customer "
                + "(customer_id, brand, productName, quantity, price, date)" 
                + "VALUES(?,?,?,?,?,?)";
        
        connect = database.connectDb();
        
        try{
            Alert alert;
            
            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            
            if(purchase_brand.getText().isEmpty()
                    || purchase_productName.getSelectionModel().getSelectedItem() == null
                    || qty == 0){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Massage");
                alert.setHeaderText(null);
                alert.setContentText("Please choose product/s first");
                alert.showAndWait();
            }else{
            
                prepare = connect.prepareStatement(insertProd);
                prepare.setString(1, String.valueOf(customerId));
                prepare.setString(2, purchase_brand.getText());
                prepare.setString(3, (String)purchase_productName.getSelectionModel().getSelectedItem());
                prepare.setString(4, String.valueOf(qty));
                
                totalPrice = (qty * price);
                
                prepare.setString(5, String.valueOf(totalPrice));
                prepare.setString(6, String.valueOf(sqlDate));

                prepare.executeUpdate();
                
                purchaseShowListData();
                
                purchaseDisplayTotal();
            }
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    public void purchaseReset(){
        purchaseCustomerId();
        String resetData = "DELETE FROM customer WHERE customer_id = '"+customerId+"'";
        
        connect = database.connectDb();
        
        try{
            
           Alert alert = new Alert(AlertType.CONFIRMATION);
           alert.setTitle("Confirmation Message");
           alert.setHeaderText(null);
           alert.setContentText("Are you sure you want to reset? The Product List also including to reset");
           
           Optional<ButtonType> option = alert.showAndWait();
           
           if(option.get().equals(ButtonType.OK)){
                statement = connect.createStatement();
                statement.executeUpdate(resetData);
                
                purchase_brand.setText("");
                purchase_productName.getSelectionModel().clearSelection();     
                purchaseSpinner();
                purchase_total.setText("$0.0");
                
           }else return;
            
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    public void purchasePay(){
        purchaseCustomerId();
        purchaseDisplayTotal();
        String sql = "INSERT INTO customer_receipt (customer_id,total,date) "
                + "VALUES(?,?,?)";
        
        connect = database.connectDb();
        
        try{
            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            Alert alert;
            if(purchase_tableView.getItems().isEmpty()){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please choose product first");
                alert.showAndWait();
            }else{
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure?");
                
                Optional<ButtonType> option = alert.showAndWait();
                
                if(option.get().equals(ButtonType.OK)){
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, String.valueOf(customerId));
                    prepare.setString(2, String.valueOf(totalP));
                    prepare.setString(3, String.valueOf(sqlDate));
                    
                    prepare.executeUpdate();
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successful!");
                    alert.showAndWait();
                    
                }else return;
                
            }
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    public void purchaseReceipt(){
        
    }
    
    private double totalP = 0;
    public void purchaseDisplayTotal(){
        purchaseCustomerId();
        String sql = "SELECT SUM(price) FROM customer WHERE customer_id = '"
                +customerId+"'";
        
        connect = database.connectDb();
        
        try{
            statement = connect.createStatement();
            result = statement.executeQuery(sql);
            
            if(result.next()){
                totalP = result.getDouble("SUM(price)");
            }
            purchase_total.setText("$"+String.valueOf(totalP));
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    private double price = 0;
    private double totalPrice = 0;
    public void purchaseGetPrice(){
    
        String gPrice = "SELECT price FROM product WHERE product_name = '"
                +purchase_productName.getSelectionModel().getSelectedItem()+"'";
        
        connect = database.connectDb();
        
        try{
            statement = connect.createStatement();
            result = statement.executeQuery(gPrice);
            
            if(result.next()){
                price = result.getDouble("price");
            }
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    public void purchaseSearchBrand() {

        String searchB = "SELECT * FROM product WHERE brand = '"
                + purchase_brand.getText() + "' and status = 'Available'";

        connect = database.connectDb();

        try {

            prepare = connect.prepareStatement(searchB);
            result = prepare.executeQuery();

            ObservableList listProduct = FXCollections.observableArrayList();


            while (result.next()) {
                listProduct.add(result.getString("product_name"));
            }

            purchase_productName.setItems(listProduct);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private SpinnerValueFactory spinner;

    public void purchaseSpinner() {
        spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 0);

        purchase_quantity.setValueFactory(spinner);

    }

    private int qty;

    public void purchaseSpinnerValue() {
        qty = purchase_quantity.getValue();

//        System.out.println(qty);
    }

    public ObservableList<customerData> purchaseListData() {
        purchaseCustomerId();

        ObservableList<customerData> customerList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM customer WHERE customer_id = '" + customerId + "'";

        connect = database.connectDb();

        try {

            customerData custD;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                custD = new customerData(result.getInt("customer_id"),
                         result.getString("brand"),
                         result.getString("productName"),
                         result.getInt("quantity"),
                         result.getDouble("price"),
                         result.getDate("date"));

                customerList.add(custD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerList;
    }

    private ObservableList<customerData> purchaseList;

    public void purchaseShowListData() {
        purchaseList = purchaseListData();

        purchase_col_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        purchase_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        purchase_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        purchase_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        purchase_tableView.setItems(purchaseList);

    }

    private int customerId;

    public void purchaseCustomerId() {

        String cID = "SELECT customer_id FROM customer";

        connect = database.connectDb();

        try {

            prepare = connect.prepareStatement(cID);
            result = prepare.executeQuery();

            while (result.next()) {
                customerId = result.getInt("customer_id");
            }

            int checkNum = 0;

            String checkCustomerId = "SELECT customer_id FROM customer_receipt";

            statement = connect.createStatement();
            result = statement.executeQuery(checkCustomerId);

            while (result.next()) {
                checkNum = result.getInt("customer_id");
            }

            if (customerId == 0) {
                customerId += 1;
            } else if (checkNum == customerId) {
                customerId += 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void displayEmployeeId() {
        purchase_employeeId.setText(getData.employeeId);
    }

    private double x = 0;
    private double y = 0;

    public void logout() {

        try {

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Massage");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                logout.getScene().getWindow().hide();

                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();

            } else {
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayEmployeeId();

        purchaseShowListData();
        purchaseSpinner();
        
        purchaseDisplayTotal();

//        purchaseSpinnerValue();
    }

}
