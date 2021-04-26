package Entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerService 
{
    private int custID; //100, 200
    private String custName;
    private Account custAccount;
    private ArrayList<Inquiry> custInq;

    public CustomerService(int custID, String custName, String email, String password) 
    {
        addCustomerService(custID, custName, email, password);
    }

    public String getCustName() 
    {
        return custName;
    }
    public void setCustName(String custName) 
    {
        this.custName = custName;
    }

    public int getCustID() 
    {
        return custID;
    }
    public void setCustID(int custID) 
    {
        this.custID = custID;
    }

    public Account getCustAccount() 
    {
        return custAccount;
    }
    public void setCustAccount(String email, String password) 
    {
        this.custAccount.setEmail(email);
        this.custAccount.setPassword(password);
    }

    public void addCustomerService(int custID, String custName, String email, String password)
    {
        try
        {
            //DB Connection
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/SmartMallMapDB", "smartmallmap", "smartmallmap");
            //Add new facility & info to object
            this.custName = custName;
            this.custID = custID;
            this.custAccount = new Account(email, password);
            this.custInq = new ArrayList<>();
            //Add new facility
            PreparedStatement ps = con.prepareStatement("INSERT INTO CUSTOMER_SERVICE(CUSTOMER_ID, CUST_NAME, CUST_EMAIL) values(?,?,?)");
            ps.setInt(1, custID);
            ps.setString(2, custName);
            ps.setString(3, email);
            ps.executeUpdate();
            //Close Connection
            ps.close();
            con.close();
	}catch(SQLException e){System.out.println("DB Connection Failed!");}
    }
    
    
    //add some inquiry functions
    
    public void callMap () 
    {

    }

    public void msgAdmin () 
    {
    
    }
    
}
