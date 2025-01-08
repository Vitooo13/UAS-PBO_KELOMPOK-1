/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodsmanagementsystem;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Haikal Erlana
 */
public class database {
    
    public static Connection connectDb() {
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/food", "root", "");
            return connect;
        }catch(Exception e){e.printStackTrace();}
        return null;
    }
    
}