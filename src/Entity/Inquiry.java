
package Entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Inquiry
{
    private int inqID; //1001, 1002
    private String type;  //Complaint, Assistance, Suggestion, Other, Call
    private String date;
    private String time;
    private CustomerService customerService;
    private String status; //Pending, Canceled, Completed
    private String description;

    //Constructor
    public Inquiry(int inqID, String type, String date, String time, CustomerService customerService, String status, String description) {
        this.inqID = inqID;
        this.type = type;
        this.date = date;
        this.time = time;
        this.customerService = customerService;
        this.status = status;
        this.description = description;
    }
    
    //Setter & Getters
    public int getInqID() {
        return inqID;
    }
    public void setInqID(int inqID) {
        this.inqID = inqID;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
       
    
     //Database
    public void addInquiry(int inqID, String type, String date, String time, CustomerService customerService, String status, String description) 
    {
        try
        {
            //DB Connection
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/SmartMallMapDB", "smartmallmap", "smartmallmap");            //Add new inquiry & info to object
            this.inqID = inqID;
            this.type = type;
            this.date = date;
            this.time = time;
            this.customerService = customerService;
            this.status = status;
            this.description = description;
            //Add new inquiry
            PreparedStatement ps = con.prepareStatement("INSERT INTO SMARTMALLMAP.INQUIRY (INQUIRY_ID, TYPE, DATE, TIME, STAUTS, DESCRIPTION, CUST_ID) VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, inqID);
            ps.setString(2, type);
            ps.setString(3, date);
            ps.setString(4, time);
            ps.setString(5, status);
            ps.setString(6, description);
            if(customerService != null)
                ps.setInt(7, customerService.getCustID());
            else
                ps.setInt(7, 100);
            ps.executeUpdate();
            //Close Connection
            ps.close();
            con.close();
	}catch(SQLException e){ System.out.println("Inquiry DB Connection Failed! : " + e); }
    }
        
    public void deleteInquiry(int id)
    {
        try 
        {
            //DB Connection
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/SmartMallMapDB", "smartmallmap", "smartmallmap");
            //Delete statement
            PreparedStatement ps = con.prepareStatement("DELETE FROM SMARTMALLMAP.INQUIRY WHERE INQUIRY_ID = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            //Close Connection
            ps.close();
            con.close();
        } catch (SQLException e) { System.out.println("Inquiry DB Connection Failed! : " + e);} 
    
    }
    
    public void updateInquiry(int inqID, String type, String date, String time, CustomerService customerService, String status, String description)  
    {
        try
        {
            //DB Connection
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/SmartMallMapDB", "smartmallmap", "smartmallmap");
            //Update facility
            PreparedStatement ps = con.prepareStatement("UPDATE SMARTMALLMAP.INQUIRY SET TYPE = ? AND DATE = ? AND TIME = ? AND STAUTS = ? AND DESCRIPTION = ? AND CUST_ID = ? WHERE INQUIRY_ID = ?");            
            ps.setString(1, type);
            ps.setString(2, date);
            ps.setString(3, time);
            ps.setString(4, status);
            ps.setString(5, description);
            ps.setInt(6, customerService.getCustID());
            ps.setInt(7, inqID);
            ps.executeUpdate();
            //Close Connection
            ps.close();
            con.close();
	}catch(SQLException e){System.out.println("Inquiry DB Connection Failed! : " + e);}
    }
}
