package Control;

import Entity.Facility;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ManageFacility 
{
    public int checkFacilityAccount(String email, String password)
    {
        int fid = 0;
        boolean found = false;
        //Loading data form Database
        try 
        {
            //DB Connection
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/SmartMallMapDB", "smartmallmap", "smartmallmap");
            //Retrieve map          
            PreparedStatement ps = con.prepareStatement("SELECT * FROM SMARTMALLMAP.FACILITY WHERE FAC_EMAIL = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {    
                fid = rs.getInt("FACILITY_ID");
                ps = con.prepareStatement("SELECT * FROM SMARTMALLMAP.ACCOUNT WHERE EMAIL = ?");
                ps.setString(1, email);
                rs = ps.executeQuery();
                if(rs.next())
                {
                    if(!rs.getString("PASSWORD").equals(password))
                        fid = 0;
                }
            }
            //Close Connection
            ps.close();
            rs.close();
            con.close();
        } catch (SQLException e) { System.out.println("Loading DB Connection Failed! : " + e); } 
        
        return fid;
    }
    
    public Facility getFacilityByID(int id)
    {
        Facility f = null;
        //Loading data form Database
        try 
        {
            //DB Connection
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/SmartMallMapDB", "smartmallmap", "smartmallmap");
            //Retrieve map          
            PreparedStatement ps = con.prepareStatement("SELECT * FROM SMARTMALLMAP.FACILITY WHERE FACILITY_ID = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                f = new Facility(rs.getInt("FACILITY_ID"), rs.getString("FAC_NAME"), rs.getString("FAC_EMAIL"), "", rs.getString("LOCATION"), rs.getString("FAC_CATEGORY"), rs.getString("PHONE_NUMBER"), rs.getString("WORK_FROM"), rs.getString("WORK_TO"), rs.getString("DESCRIPSTION"));
            }
            //Close Connection
            ps.close();
            rs.close();
            con.close();
        } catch (SQLException e) { System.out.println("Loading DB Connection Failed! : " + e); } 
        
        return f;
    }
    
    public boolean RequestFacilityUpdate(int FacID, String FacName, String email, String password, String location, 
            String Category, String PhoneNo, String fromH, String toH, String Description)
    {
        Facility f = new Facility(0, "", "", "", "", "", "", "", "", "");
        //Loading data form Database
        try 
        {
            //DB Connection
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/SmartMallMapDB", "smartmallmap", "smartmallmap");
            //Retrieve map          
            PreparedStatement ps = con.prepareStatement("SELECT * FROM SMARTMALLMAP.ACCOUNT WHERE EMAIL = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                password = rs.getString("PASSWORD");
            
            //Close Connection
            ps.close();
            rs.close();
            con.close();
        } catch (SQLException e) { System.out.println("Loading DB Connection Failed! : " + e); } 
        
        f.updateFacility(FacID, FacName, email, password, location, Category, PhoneNo, fromH, toH, Description);
        return true;
    }
}
