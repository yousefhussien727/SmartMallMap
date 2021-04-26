package Entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Account 
{
    private String email;
    private String password;
    
    //Constructor
    public Account(String email, String password) 
    {
        this.email = email;
        this.password = password;      
    }
    
    //setters & getters
    public String getEmail() 
    {
        return email;
    }
    public void setEmail(String email) 
    {
        this.email = email;
    }

    public String getPassword() 
    {
        return password;
    }
    public void setPassword(String password) 
    {
        this.password = password;
    }
    
    public void addAccount(String email, String password)
    {
        try
        {
            //DB Connection
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/SmartMallMapDB", "smartmallmap", "smartmallmap");
            //Add new Account
            PreparedStatement ps = con.prepareStatement("INSERT INTO SMARTMALLMAP.ACCOUNT(EMAIL,PASSWORD) values(?,?)");
            ps.setString(1, email);
            ps.setString(2, password);
            ps.executeUpdate();
            //add info to object
            this.email = email;
            this.password = password;
            //Close Connection
            ps.close();
            con.close();
	}catch(SQLException e){System.out.println("Account DB Connection Failed! : " + e);}
    }
    
    public void deleteAccount(String email)
    {
        try 
        {
            //DB Connection
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/SmartMallMapDB", "smartmallmap", "smartmallmap");
            //Delete statement
            PreparedStatement ps = con.prepareStatement("DELETE FROM SMARTMALLMAP.ACCOUNT WHERE EMAIL = ?");
            ps.setString(1, email);
            ps.executeUpdate();
            //Close Connection
            ps.close();
            con.close();
        } catch (SQLException e) { System.out.println("Account DB Connection Failed! : " + e);} 
    
    }
    
    public void updateAccount(String email, String password)
    {
        try 
        {
            //DB Connection
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/SmartMallMapDB", "smartmallmap", "smartmallmap");
            //Delete statement
            PreparedStatement ps = con.prepareStatement("UPDATE SMARTMALLMAP.ACCOUNT SET PASSWORD = ? WHERE EMAIL = ?");
            ps.setString(1, password);
            ps.setString(2, email);
            ps.executeUpdate();
            //Close Connection
            ps.close();
            con.close();
        } catch (SQLException e) { System.out.println("Account DB Connection Failed! : " + e);} 
    
    }
}
