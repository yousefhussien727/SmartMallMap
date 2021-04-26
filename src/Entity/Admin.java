
package Entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Admin 
{
    private int adminID; //101, 202
    private String adminName;
    private Account adminAccount;
    private MallMap map;

    public Admin(int adminID, String adminName, String email, String password, MallMap map) {
        addAdmin(adminID, adminName, email, password, map);
    }

    public String getAdminName() {
        return adminName;
    }
    
    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public int getAdminID() {
        return adminID;
    }
    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public Account getAdminAccount() {
        return adminAccount;
    }
    public void setAdminAccount(String email, String password) {
        this.adminAccount.setEmail(email);
        this.adminAccount.setPassword(password);
    }

    public MallMap getMap() {
        return map;
    }
    public void setMap(MallMap map) {
        this.map = map;
    }

    public void addAdmin(int adminID, String adminName, String email, String password, MallMap map)
    {
        try
        {
            //DB Connection
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/SmartMallMapDB", "smartmallmap", "smartmallmap");
            //Add new Account & info to object
            this.adminName = adminName;
            this.adminID = adminID;
            this.adminAccount = new Account(email, password);
            this.map = map;
            //Add new admin
            PreparedStatement ps = con.prepareStatement("INSERT INTO ADMIN(ADMIN_ID, ADMIN_NAME, ADMIN_EMAIL, MAP_ID) values(?,?,?,?)");
            ps.setInt(1, adminID);
            ps.setString(2, adminName);
            ps.setString(3, email);
            ps.setInt(5, map.getID());
            ps.executeUpdate();
            //Close Connection
            ps.close();
            con.close();
	}catch(SQLException e){System.out.println("DB Connection Failed!");}
    }
    
    
}
