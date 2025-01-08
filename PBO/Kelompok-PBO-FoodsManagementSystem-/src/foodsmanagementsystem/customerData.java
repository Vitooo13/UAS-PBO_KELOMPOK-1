
package foodsmanagementsystem;

import java.util.Date;

public class customerData {
    
    private Integer customerId;
    private String brand;
    private String productName;
    private Integer quantity;
    private double price;
    private Date date;
    
    public customerData(Integer customerId, String brand, String productName, Integer quantity, Double price, Date date){
        this.customerId = customerId;
        this.brand = brand;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
    }
    public Integer getCustomerId(){
        return customerId;
    }
    
    public String getBrand(){
        return brand;
    }
    public String getProductName(){
        return productName;
    }
    public Integer getQuantity(){
        return quantity;
    }
    public Double getPrice(){
        return price;
    }
    public Date getDate(){
        return date;
    }
    
}
