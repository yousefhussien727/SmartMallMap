package Entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class MallMap
{
    private int ID; //11, 22 
    private String location;
    private ArrayList<Facility> facility;
    private Advertiser advertiser;
    private int slotsNum;
        
    //Constructors
    public MallMap(int ID, String location, int slotsNum)
    {
        this.ID = ID;
        this.location = location;
        this.slotsNum = slotsNum;
        facility = new ArrayList<>();
    }

    //Setters & Getters
    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public int getSlotsNum() {
        return slotsNum;
    }
    public void setSlotsNum(int slotsNum) {
        this.slotsNum = slotsNum;
    }

    public Advertiser getAdvertiser() {
        return advertiser;
    }
    public void setAdvertiser(Advertiser advertiser) {
        this.advertiser = advertiser;
    }
    
    //managing facilities of map
    public Facility getFacilityAt(int index)
    {
        return facility.get(index);
    }
    public int getNoOfFacilities()
    {
        return facility.size();
    }   
    public void addFacilityToMap (Facility f) 
    {
        facility.add(f);
    }
    public boolean removeFacility (Facility f) 
    {
        //search for facility in map list
        int i = facility.indexOf(f);
        
        //if not found return
        if(i == -1)
            return false;
        
        //remove from list
        facility.remove(i);            
        return true;
    }


    //Database
    public void addMallMap(int ID, String location, int slotsNum)
    {
        try
        {
            //DB Connection
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/SmartMallMapDB", "smartmallmap", "smartmallmap");
            //Add info to object
            this.ID = ID;
            this.location = location;
            this.slotsNum = slotsNum;
            facility = new ArrayList<>();
            //Add Statement
            PreparedStatement ps = con.prepareStatement("INSERT INTO SMARTMALLMAP.MALL_MAP(MAP_ID, LOCATION, NO_OF_SLOTS) values(?,?,?)");
            ps.setInt(1, ID);
            ps.setString(2, location);
            ps.setInt(3, slotsNum);
            ps.executeUpdate();
            //Close Connection
            ps.close();
            con.close();
	}catch(SQLException e){System.out.println("Map DB Connection Failed! : " + e);}
    }
    
    public void deleteMallMap(int ID)
    {
        try
        {
            //DB Connection
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/SmartMallMapDB", "smartmallmap", "smartmallmap");
            //Delete statement
            PreparedStatement ps = con.prepareStatement("DELETE FROM SMARTMALLMAP.MALL_MAP WHERE MAP_ID = ?");
            ps.setInt(1, ID);
            ps.executeUpdate();
            //Close Connection
            ps.close();
            con.close();
	}catch(SQLException e){ System.out.println("Map DB Connection Failed! : " + e); }
    }
    
}
