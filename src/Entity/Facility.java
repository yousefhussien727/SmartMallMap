package Entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Facility 
{   
    private int facID;
    private String facName;
    private Account facAccount;
    private String location;
    private String category; //Amenities, 
    private String phoneNo;      
    private String[] workingHours; //from # to # in hours "24"
    private String description;

    
    //Constructor
    public Facility(int FacID, String FacName, String email, String password, String location, 
            String Category, String PhoneNo, String fromH, String toH, String Description) 
    {
        this.facID = FacID;
        this.facName = FacName;
        this.facAccount = new Account(email, password);
        this.location = location;
        this.category = Category;
        this.phoneNo = PhoneNo;
        this.workingHours = new String[] {fromH, toH};
        this.description = Description;
    }
    
    //Setters and Getters
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoneNo() {
        return phoneNo;
    }
    public void setPhoneNo(String PhoneNo) {
        this.phoneNo = PhoneNo;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String Description) {
        this.description = Description;
    }

    public String getFacName() {
        return facName;
    }
    public void setFacName(String FacName) {
        this.facName = FacName;
    }

    public int getFacID() {
        return facID;
    }
    public void setFacID(int FacID) {
        this.facID = FacID;
    }

    public String[] getWorkingHours() {
        return workingHours;
    }
    public void setWorkingHours(String fromH, String toH) {
        this.workingHours[0] = fromH;
        this.workingHours[1] = toH;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String Category) {
        this.category = Category;
    }

    public Account getFacAccount() {
        return facAccount;
    }
    public void setFacAccount(String email, String password) {
        this.facAccount.setEmail(email);
        this.facAccount.setPassword(password);
    }
   
    //Database
    public void addFacility(int FacID, String FacName, String email, String password, String location, 
            String Category, String PhoneNo, String fromH, String toH, String Description) 
    {
        try
        {
            //DB Connection
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/SmartMallMapDB", "smartmallmap", "smartmallmap");
            //Add new facility & info to object
            this.facID = FacID;
            this.facName = FacName;
            this.facAccount.addAccount(email, password);
            this.location = location;
            this.category = Category;
            this.phoneNo = PhoneNo;
            this.workingHours = new String[] {fromH, toH};
            this.description = Description;
            //Add new facility
            PreparedStatement ps = con.prepareStatement("INSERT INTO SMARTMALLMAP.FACILITY (FACILITY_ID, LOCATION, PHONE_NUMBER, WORK_FROM, WORK_TO, DESCRIPSTION, FAC_EMAIL, FAC_CATEGORY, MAP_ID, FAC_NAME) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, FacID);
            ps.setString(2, location);
            ps.setString(3, PhoneNo);
            ps.setString(4, fromH);
            ps.setString(5, toH);
            ps.setString(6, Description);
            ps.setString(7, email);
            ps.setString(8, Category);
            ps.setInt(9, 11);            
            ps.setString(10, FacName);
            ps.executeUpdate();
            //Close Connection
            ps.close();
            con.close();
	}catch(SQLException e){ System.out.println("Facility DB Connection Failed! : " + e); }
    }
        
    public void deleteFacility(int id)
    {
        try 
        {
            //DB Connection
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/SmartMallMapDB", "smartmallmap", "smartmallmap");
            //Delete statement
            PreparedStatement ps = con.prepareStatement("DELETE FROM SMARTMALLMAP.FACILITY WHERE FACILITY_ID = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            //Close Connection
            ps.close();
            con.close();
        } catch (SQLException e) { System.out.println("Facility DB Connection Failed! : " + e);} 
    
    }
    
    public void updateFacility(int FacID, String FacName, String email, String password, String location, 
            String Category, String PhoneNo, String fromH, String toH, String Description) 
    {
        try
        {
            //DB Connection
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/SmartMallMapDB", "smartmallmap", "smartmallmap");
            //Update facility
            PreparedStatement ps = con.prepareStatement("UPDATE SMARTMALLMAP.FACILITY SET FAC_NAME = ? AND LOCATION = ? ADN PHONE_NUMBER = ? AND WORK_FROM = ? AND WORK_TO = ? AND DESCRIPSTION = ? AND FAC_CATEGORY = ? WHERE FACILITY_ID = ?");           
            ps.setString(1, FacName);
            ps.setString(2, location);
            ps.setString(3, PhoneNo);
            ps.setString(4, fromH);
            ps.setString(5, toH);
            ps.setString(6, Description);
            ps.setString(7, Category);
            ps.setInt(8, FacID);
            ps.executeUpdate();
            this.facAccount.updateAccount(email, password);
            //Close Connection
            ps.close();
            con.close();
	}catch(SQLException e){System.out.println("Facility DB Connection Failed! : " + e);}
    }
    
}
