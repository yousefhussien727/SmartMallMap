package Entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;


public class Advertiser 
{
    private int advID; //10, 20
    private String advName;
    private Account advAccount;
    private ArrayList<Advertisement> ad;
    private MallMap map;

    public Advertiser(int AdvID, String AdvName, String email, String password, MallMap map) 
    {
        this.advName = AdvName;
        this.advID = AdvID;
        this.advAccount = new Account(email, password);
        this.ad = new ArrayList<>();
        this.map = map;
    }

    public String getAdvName() {
        return advName;
    }
    public void setAdvName(String AdvName) {
        this.advName = AdvName;
    }

    public int getAdvID() {
        return advID;
    }
    public void setAdvID(int AdvID) {
        this.advID = AdvID;
    }

    public Account getAdvAccount() {
        return advAccount;
    }
    public void setAdvAccount(String email, String password) {
        this.advAccount.setEmail(email);
        this.advAccount.setPassword(password);
    }

    public MallMap getMap() {
        return map;
    }
    public void setMap(MallMap map) {
        this.map = map;
    }

    public void addAdvertiser(int AdvID, String AdvName, String email, String password, MallMap map)
    {
        try
        {
            //DB Connection
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/SmartMallMapDB", "smartmallmap", "smartmallmap");
            //Add new facility & info to object
            this.advName = AdvName;
            this.advID = AdvID;
            this.advAccount = new Account(email, password);
            this.ad = new ArrayList<>();
            this.map = map;
            //Add new facility
            PreparedStatement ps = con.prepareStatement("INSERT INTO ADVERTISER(ADVERTISER_ID, ADVERTISER_NAME, ADVERTISER_EMAIL) values(?,?,?)");
            ps.setInt(1, AdvID);
            ps.setString(2, AdvName);
            ps.setString(3, email);
            ps.executeUpdate();
            //Close Connection
            ps.close();
            con.close();
	}catch(SQLException e){System.out.println("DB Connection Failed!");}
    }
    
    
    public void addAdvertisement(int ID, String type, String filePath) 
    {
        ad.add(new Advertisement(ID, type, filePath));
    }

    public void delelteAdvertisement(String AdID)
    {
        
    }

    public void updateAdvertisement(String AdID) 
    {
        
    }
}
