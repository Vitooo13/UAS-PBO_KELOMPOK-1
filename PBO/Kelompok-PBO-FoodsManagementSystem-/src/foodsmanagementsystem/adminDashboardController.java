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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
public class adminDashboardController implements Initializable {

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button addProducts_addBtn;

    @FXML
    private TextField addProducts_brandName;

    @FXML
    private Button addProducts_btn;

    @FXML
    private Button addProducts_clearBtn;

    @FXML
    private TableColumn<productData, String> addProducts_col_brandName;

    @FXML
    private TableColumn<productData, String> addProducts_col_price;

    @FXML
    private TableColumn<productData, String> addProducts_col_productID;

    @FXML
    private TableColumn<productData, String> addProducts_col_productName;

    @FXML
    private TableColumn<productData, String> addProducts_col_status;

    @FXML
    private Button addProducts_deleteBtn;

    @FXML
    private AnchorPane addProducts_form;

    @FXML
    private TextField addProducts_price;

    @FXML
    private TextField addProducts_productID;

    @FXML
    private TextField addProducts_productName;

    @FXML
    private TextField addProducts_search;

    @FXML
    private ComboBox<?> addProducts_status;

    @FXML
    private TableView<productData> addProducts_tableView;

    @FXML
    private Button addProducts_updateBtn;

    @FXML
    private Button close;

    @FXML
    private Label dashboard_IncomeToday;

    @FXML
    private Label dashboard_activeEmployees;

    @FXML
    private Button dashboard_btn;

    @FXML
    private AreaChart<?, ?> dashboard_chart;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Label dashboard_totalIncome;

    @FXML
    private Button employees_btn;

    @FXML
    private Button employees_clearBtn;

    @FXML
    private TableColumn<employeeData, String> employees_col_date;

    @FXML
    private TableColumn<employeeData, String> employees_col_employeeID;

    @FXML
    private TableColumn<employeeData, String> employees_col_firstName;

    @FXML
    private TableColumn<employeeData, String> employees_col_gender;

    @FXML
    private TableColumn<employeeData, String> employees_col_lastName;

    @FXML
    private TableColumn<employeeData, String> employees_col_password;

    @FXML
    private Button employees_deleteBtn;

    @FXML
    private TextField employees_employeeID;

    @FXML
    private TextField employees_firstName;

    @FXML
    private AnchorPane employees_form;

    @FXML
    private ComboBox<?> employees_gender;

    @FXML
    private TextField employees_lastName;

    @FXML
    private TextField employees_password;

    @FXML
    private Button employees_saveBtn;

    @FXML
    private TableView<employeeData> employees_tableView;

    @FXML
    private Button employees_updateBtn;

    @FXML
    private Button logout;

    @FXML
    private Button minimize;

    @FXML
    private Label username;

    private double x = 0;
    private double y = 0;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    
    public void dashboardDisplayActiveEmployees(){
        
        String sql = "SELECT COUNT(id) FROM employee";
        
        connect = database.connectDb();
        
        int countE = 0;
        
        try{
            statement = connect.createStatement();
            result = statement.executeQuery(sql);
            
            if(result.next()){
                countE = result.getInt("COUNT(id)");
            }
            dashboard_activeEmployees.setText(String.valueOf(countE));
        }catch(Exception e){e.printStackTrace();}
    }
    
    public void dasboardDisplayIncomeToday(){
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        String sql = "SELECT SUM(total) FROM customer_receipt WHERE date = '"
                +sqlDate+"'";
        
        double sumT = 0;
        
        connect = database.connectDb();
        
        try{
            statement = connect.createStatement();
            result = statement.executeQuery(sql);
            
            if(result.next()){
                sumT = result.getDouble("SUM(total)");
            }
            dashboard_IncomeToday.setText("$"+String.valueOf(sumT));
            
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    public void dashboardTotalIncome(){
        
        String sql = "SELECT SUM(total) FROM customer_receipt";
        
        double sumTI = 0;
        
        connect = database.connectDb();
        
        try{
            statement = connect.createStatement();
            result = statement.executeQuery(sql);
            
            if(result.next()){
                sumTI = result.getDouble("SUM(total)");
            }
            dashboard_totalIncome.setText("$"+String.valueOf(sumTI));
            
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    public void dashboardChart(){
        dashboard_chart.getData().clear();
        
        String sql = "SELECT date, SUM(total) FROM customer_receipt GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 9";

        connect = database.connectDb();
        
        try{
            
            XYChart.Series chart = new XYChart.Series();
            
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }
            
            dashboard_chart.getData().add(chart);
            
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    public void addProductsAdd(){
        
        String insertProduct = "INSERT INTO product " 
                + "(product_id,brand,product_name,status,price)"
                + "VALUES(?,?,?,?,?)";
        
        connect = database.connectDb();
        
        try{
            Alert alert;
            
            if(addProducts_productID.getText().isEmpty()
                    || addProducts_brandName.getText().isEmpty()
                    || addProducts_productName.getText().isEmpty()
                    || addProducts_status.getSelectionModel().getSelectedItem() == null
                    || addProducts_price.getText().isEmpty()){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Massage");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{
                
                String check = "SELECT product_id FROM product WHERE product_id = '"
                        +addProducts_productID.getText()+"'";
                
                statement = connect.createStatement();
                result = statement.executeQuery(check);
                
                if(result.next()){
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Massage");
                    alert.setHeaderText(null);
                    alert.setContentText("Product ID: " + addProducts_productID.getText() + " was already exist!");
                    alert.showAndWait();
                }else{
                    prepare = connect.prepareStatement(insertProduct);
                    prepare.setString(1, addProducts_productID.getText());
                    prepare.setString(2, addProducts_brandName.getText());
                    prepare.setString(3, addProducts_productName.getText());
                    prepare.setString(4, (String)addProducts_status.getSelectionModel().getSelectedItem());
                    prepare.setString(5, addProducts_price.getText());
                    
                    prepare.executeUpdate();
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Massage");
                    alert.setHeaderText(null);
                    alert.setContentText("Successful Added!");
                    alert.showAndWait();
                    
                    
                    addProductsShowData();
                    addProductsClear();
                }
            }
        }catch(Exception e){e.printStackTrace();} 
    }
    
    public void addProductsUpdate(){
        String updateProduct = "UPDATE product SET brand = '"
                +addProducts_brandName.getText()+"', product_name ='"
                +addProducts_productName.getText()+"', status = '"
                +addProducts_status.getSelectionModel().getSelectedItem()+"', price = '"
                +addProducts_price.getText()+"' WHERE product_id = '"
                +addProducts_productID.getText()+"'";
        
        connect = database.connectDb();
        
        try{
            Alert alert;
            
            if(addProducts_productID.getText().isEmpty()
                    || addProducts_brandName.getText().isEmpty()
                    || addProducts_productName.getText().isEmpty()
                    || addProducts_status.getSelectionModel().getSelectedItem() == null
                    || addProducts_price.getText().isEmpty()){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Massage");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Massage");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure want to UPDATE Product ID: " + addProducts_productID.getText() + "?");
                
                Optional<ButtonType> option = alert.showAndWait();
                
                if(option.get().equals(ButtonType.OK)){
                    statement = connect.createStatement();
                    statement.executeUpdate(updateProduct);
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Massage");
                    alert.setHeaderText(null);
                    alert.setContentText("Succesfully Update!");
                    alert.showAndWait();
                    
                    addProductsShowData();
                    addProductsClear();
                    
                }else return;
            }
            
        }catch(Exception e){e.printStackTrace();}
    }
    
    public void addProductsDelete(){
        String deleteProduct = "DELETE FROM product WHERE product_id = '"
                +addProducts_productID.getText()+"'";
        
        connect = database.connectDb();
        
        try{
            Alert alert;
            
            if(addProducts_productID.getText().isEmpty()
                    || addProducts_brandName.getText().isEmpty()
                    || addProducts_productName.getText().isEmpty()
                    || addProducts_status.getSelectionModel().getSelectedItem() == null
                    || addProducts_price.getText().isEmpty()){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Massage");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Massage");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Product ID: " + addProducts_productID.getText() + "?");
                
                Optional<ButtonType> option = alert.showAndWait();
                
                if(option.get().equals(ButtonType.OK)){
                    prepare = connect.prepareStatement(deleteProduct);
                    prepare.executeUpdate();
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Massage");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();
                    
                    addProductsShowData();
                    addProductsClear();
                    
                }else {
                    return;
                }
                
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void addProductsClear(){
        addProducts_productID.setText("");
        addProducts_brandName.setText("");
        addProducts_productName.setText("");
        addProducts_status.getSelectionModel().clearSelection();
        addProducts_price.setText("");
    }
    
    private String[] statusList = {"Available", "Not Available"};
    
    public void addProductsStatusList(){
        List<String> listS = new ArrayList<>();
        
        for(String data: statusList){
            listS.add(data);
        }
        
        ObservableList statusData = FXCollections.observableArrayList(listS);
        addProducts_status.setItems(statusData);
    }
    
    public void addProductsSearch(){
        
        FilteredList<productData> filter = new FilteredList<>(addProductsList, e-> true);
        
        addProducts_search.textProperty().addListener((Observable, oldValue, newValue) ->{
        
            filter.setPredicate(predicateProductData ->{
                
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                
                String searchKey = newValue.toLowerCase();
                
                if(predicateProductData.getProductId().toLowerCase().contains(searchKey)) {
                    return true;
                }else if(predicateProductData.getBrand().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateProductData.getStatus().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateProductData.getProductName().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateProductData.getPrice().toString().contains(searchKey)){
                    return true;
                }else return false;
            });
        });
        
        SortedList<productData> sortList = new SortedList<>(filter);
        
        sortList.comparatorProperty().bind(addProducts_tableView.comparatorProperty());
        addProducts_tableView.setItems(sortList);
        
    }
    
    public ObservableList<productData> addProductsListData(){
        ObservableList<productData> prodList = FXCollections.observableArrayList();
    
        String sql = "SELECT * FROM product";
        
        connect = database.connectDb();
        
        try{
            productData prod;
            
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                prod = new productData(result.getString("product_id")
                        , result.getString("brand") 
                        , result.getString("product_name") 
                        , result.getString("status")
                        , result.getDouble("price"));
                
                prodList.add(prod);
            }
        }catch(Exception e){e.printStackTrace();}
        return prodList;
    }
    
    private ObservableList<productData> addProductsList;
    public void addProductsShowData(){
        addProductsList = addProductsListData();
        
        addProducts_col_productID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        addProducts_col_brandName.setCellValueFactory(new PropertyValueFactory<>("brand"));
        addProducts_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        addProducts_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        addProducts_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        addProducts_tableView.setItems(addProductsList);
        
    }
    
    public void addProductsSelect(){
        productData prod = addProducts_tableView.getSelectionModel().getSelectedItem();
        int num = addProducts_tableView.getSelectionModel().getSelectedIndex();
        
        if((num - 1) < -1){
            return;
        }
        
        addProducts_productID.setText(prod.getProductId());
        addProducts_brandName.setText(prod.getBrand());
        addProducts_productName.setText(prod.getProductName());
        addProducts_price.setText(String.valueOf(prod.getPrice()));
    }
    
    public void employeesSave(){
        Date date = new Date(); 
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        
        String insertEmployee = "INSERT INTO employee " 
                + "(employee_id,password,firstName,lastName,gender,date)" 
                + "VALUES(?,?,?,?,?,?)";
        
        connect = database.connectDb();
        
        try{
            
            Alert alert;
            
            if(employees_employeeID.getText().isEmpty()
                    || employees_password.getText().isEmpty()
                    || employees_firstName.getText().isEmpty()
                    || employees_lastName.getText().isEmpty()
                    || employees_gender.getSelectionModel().getSelectedItem() == null){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Massage");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{
                
                String checkExist = "SELECT employee_id FROM employee WHERE employee_id = '"
                        +employees_employeeID.getText()+"'";
                
                statement = connect.createStatement();
                result = statement.executeQuery(checkExist);
                
                if(result.next()){
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Massage");
                    alert.setHeaderText(null);
                    alert.setContentText("Employee ID: " + employees_employeeID.getText() + " was already exist!");
                    alert.showAndWait();
                }else{
                    prepare = connect.prepareStatement(insertEmployee);
                    prepare.setString(1, employees_employeeID.getText());
                    prepare.setString(2, employees_password.getText());
                    prepare.setString(3, employees_firstName.getText());
                    prepare.setString(4, employees_lastName.getText());
                    prepare.setString(5, (String)employees_gender.getSelectionModel().getSelectedItem());
                    prepare.setString(6, String.valueOf(sqlDate));

                    prepare.executeUpdate();
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Massage");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Saved!");
                    alert.showAndWait();
                    
                    employeesShowListData();
                    employeesReset();
                    
                }
            }
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    private String[] genderList = {"Male", "Female", "Others"};
    public void employeesGender(){
        List<String> genderL = new ArrayList<>();
        
        for(String data: genderList){
            genderL.add(data);
        }
        
        ObservableList listG = FXCollections.observableArrayList(genderL);
        employees_gender.setItems(listG);
    }
    
    public void employeesUpdate(){
        String updateEmployee = "UPDATE employee SET password = '"
                +employees_password.getText()+"', firstName = '"
                +employees_firstName.getText()+"', lastName = '"
                +employees_lastName.getText()+"', gender = '"
                +employees_gender.getSelectionModel().getSelectedItem()
                +"' WHERE employee_id = '"+employees_employeeID.getText()+"'";
        
        connect = database.connectDb();
        
        try{
            Alert alert;
            
            if(employees_employeeID.getText().isEmpty()
                    || employees_password.getText().isEmpty()
                    || employees_firstName.getText().isEmpty()
                    || employees_lastName.getText().isEmpty()
                    || employees_gender.getSelectionModel().getSelectedItem() == null){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Massage");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{
                
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Massage");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Employee ID: " + employees_employeeID.getText() + "?");
                
                Optional<ButtonType> option = alert.showAndWait();
                
                if(option.get().equals(ButtonType.OK)){
                    statement = connect.createStatement();
                    statement.executeUpdate(updateEmployee); 
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Massage");
                    alert.setHeaderText(null);
                    alert.setContentText("Succesfully Updated!");
                    alert.showAndWait();
                    
                    employeesShowListData();
                    employeesReset();
                    
                }else{
                    return;
                }
            }
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    public void employeesDelete(){
        
        String deleteEmployee = "DELETE FROM employee WHERE employee_id = '"
                +employees_employeeID.getText()+"'";
        
        connect = database.connectDb();
        
        try{
            Alert alert;
            if(employees_employeeID.getText().isEmpty()
                    || employees_password.getText().isEmpty()
                    || employees_firstName.getText().isEmpty()
                    || employees_lastName.getText().isEmpty()
                    || employees_gender.getSelectionModel().getSelectedItem() == null){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Massage");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Massage");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Employee ID: " + employees_employeeID.getText() + "?");
                
                Optional<ButtonType> option = alert.showAndWait();
                
                if(option.get().equals(ButtonType.OK)){
                    statement = connect.createStatement();
                    statement.executeUpdate(deleteEmployee);
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Massage");
                    alert.setHeaderText(null);
                    alert.setContentText("Succesfully Deleted!");
                    alert.showAndWait();
                    
                    employeesShowListData();
                    employeesReset();
                    
                }else return;
                
            }
        }catch(Exception e){e.printStackTrace();}
    }
    
    public void employeesReset(){
        employees_employeeID.setText("");
        employees_password.setText("");
        employees_firstName.setText("");
        employees_lastName.setText("");
        employees_gender.getSelectionModel().clearSelection();
    }
    
    public ObservableList<employeeData> employeesListData(){
        
       ObservableList<employeeData> emData = FXCollections.observableArrayList();
       
       String sql = "SELECT * FROM employee";
       
       connect = database.connectDb();
       
       try{
       
           employeeData employeeD;
           
           prepare = connect.prepareStatement(sql);
           result = prepare.executeQuery();
           
           while(result.next()){
               
               employeeD = new employeeData(result.getString("employee_id")
                       , result.getString("password")
                       , result.getString("firstName")
                       , result.getString("lastName")
                       , result.getString("gender")
                       , result.getDate("date"));
               
               emData.add(employeeD);
               
           }
           
           
       }catch(Exception e){e.printStackTrace();}
        return emData;
    }
    
    private ObservableList<employeeData> employeesList;
    public void employeesShowListData(){
        employeesList = employeesListData();
        
        employees_col_employeeID.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        employees_col_password.setCellValueFactory(new PropertyValueFactory<>("password"));
        employees_col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        employees_col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        employees_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        employees_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        
        employees_tableView.setItems(employeesList);
                
    }
    
    public void employeesSelect(){
        employeeData employeeD = employees_tableView.getSelectionModel().getSelectedItem();
        int num = employees_tableView.getSelectionModel().getSelectedIndex();
        
        if((num-1) < -1){
            return;
        }
        
        employees_employeeID.setText(employeeD.getEmployeeId());
        employees_password.setText(employeeD.getPassword());
        employees_firstName.setText(employeeD.getFirstName());
        employees_lastName.setText(employeeD.getLastName());
        
    }
    
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
    
    public void displayUsername(){
        username.setText(getData.username);
    }
    
    public void defaultNavBtn(){
        dashboard_btn.setStyle("-fx-background-color: linear-gradient(to top right, #896b34, #CFBFAB);");
    }

    public void switchForm(ActionEvent event) {

        if (event.getSource() == dashboard_btn) {
            dashboard_form.setVisible(true);
            addProducts_form.setVisible(false);
            employees_form.setVisible(false);

            dashboard_btn.setStyle("-fx-background-color: linear-gradient(to top right, #896b34, #CFBFAB);");
            addProducts_btn.setStyle("-fx-background-color: transparent;");
            employees_btn.setStyle("-fx-background-color: transparent;");
            
            dashboardDisplayActiveEmployees();
            dasboardDisplayIncomeToday();
            dashboardTotalIncome();
            dashboardChart();

        } else if (event.getSource() == addProducts_btn) {
            dashboard_form.setVisible(false);
            addProducts_form.setVisible(true);
            employees_form.setVisible(false);

            addProducts_btn.setStyle("-fx-background-color: linear-gradient(to top right, #896b34, #CFBFAB);");
            dashboard_btn.setStyle("-fx-background-color: transparent;");
            employees_btn.setStyle("-fx-background-color: transparent;");

            addProductsShowData();
            addProductsStatusList();
            addProductsSearch();
            
        } else if (event.getSource() == employees_btn) {
            dashboard_form.setVisible(false);
            addProducts_form.setVisible(false);
            employees_form.setVisible(true);

            employees_btn.setStyle("-fx-background-color: linear-gradient(to top right, #896b34, #CFBFAB);");
            addProducts_btn.setStyle("-fx-background-color: transparent;");
            dashboard_btn.setStyle("-fx-background-color: transparent;");

            employeesShowListData();
            
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
        displayUsername();
        defaultNavBtn();
        
        dashboardDisplayActiveEmployees();
        dasboardDisplayIncomeToday();
        dashboardTotalIncome();
        dashboardChart();
        
        addProductsShowData();
        addProductsStatusList();
        
        employeesShowListData();
        employeesGender();
    }

}
