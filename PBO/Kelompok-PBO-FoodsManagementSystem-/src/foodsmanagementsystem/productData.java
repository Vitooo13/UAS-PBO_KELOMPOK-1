
package foodsmanagementsystem;


public class productData {
    
    private String productId;;
    private String brand;
    private String productName;
    private String status;
    private Double price;
    
    public productData(String productId, String brand, String productName, String status, Double price){
        this.productId = productId;
        this.brand = brand;
        this.productName = productName;
        this.status = status;
        this.price = price;
    } 
    
    public String getProductId(){
        return productId;
    }
    public String getBrand(){
        return brand;
    }
    public String getProductName(){
        return productName;
    }
    public String getStatus(){
        return status;
    }
    public Double getPrice(){
        return price;
    }
            
}
