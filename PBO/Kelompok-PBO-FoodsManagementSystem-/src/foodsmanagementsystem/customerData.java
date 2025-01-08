/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodsmanagementsystem;

import java.util.Date;

/**
 *
 * @author Haikal Erlana
 */
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
