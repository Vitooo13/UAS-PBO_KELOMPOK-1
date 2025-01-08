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
public class employeeData {
    
    private String employeeId;
    private String password;
    private String firstName;
    private String lastName;
    private String gender;
    private Date date;
    
    public employeeData(String employeeId, String password, String firstName, String lastName, String gender, Date date){
        this.employeeId = employeeId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.date = date;
    }
    
    public String getEmployeeId(){
        return employeeId;
    }
    public String getPassword(){
        return password;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getGender(){
        return gender;
    }
    public Date getDate(){
        return date;
    }

}
