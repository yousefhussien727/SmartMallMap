package Control;

import Entity.Advertiser;
import Entity.Facility;
import Entity.Inquiry;
import Entity.MallMap;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MapManager 
{   
    public MallMap map;
    public Inquiry inq;
    
    public MapManager()
    {        
        //Loading data form Database
        try 
        {
            //DB Connection
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/SmartMallMapDB", "smartmallmap", "smartmallmap");
            
            //Retrieve map          
            PreparedStatement ps = con.prepareStatement("SELECT * FROM SMARTMALLMAP.MALL_MAP WHERE MAP_ID = ?");
            ps.setInt(1, 11);
            ResultSet rs = ps.executeQuery();            
            if(rs.next())
                map = new MallMap(rs.getInt("MAP_ID"), rs.getString("LOCATION"), rs.getInt("NO_OF_SLOTS"));
            
            //Retrieve the adveriser of the map
            int advId = rs.getInt("ADVERTISER_ID");
            ps = con.prepareStatement("SELECT * FROM SMARTMALLMAP.ADVERTISER WHERE ADVERTISER_ID = ?");
            ps.setInt(1, advId);
            rs = ps.executeQuery();
            if(rs.next())
                map.setAdvertiser(new Advertiser(rs.getInt("ADVERTISER_ID"), rs.getString("ADVERTISER_NAME"), "", "", map));            
            
            //Retrieve all facilities in map            
            ps = con.prepareStatement("SELECT * FROM SMARTMALLMAP.FACILITY WHERE MAP_ID = ?");
            ps.setInt(1, 11);
            rs = ps.executeQuery();
            while(rs.next())
            {
                map.addFacilityToMap(new Facility(rs.getInt("FACILITY_ID"), rs.getString("FAC_NAME"), rs.getString("FAC_EMAIL"), "", rs.getString("LOCATION"), rs.getString("FAC_CATEGORY"), rs.getString("PHONE_NUMBER"), rs.getString("WORK_FROM"), rs.getString("WORK_TO"), rs.getString("DESCRIPSTION")));
            }
            
            //Close Connection
            ps.close();
            rs.close();
            con.close();
        } catch (SQLException e) { System.out.println("Loading DB Connection Failed! : " + e); } 
    }
    
    public String getFacilityName(String location)
    {        
        for(int i = 0; i < map.getNoOfFacilities(); i++)
        {
            if(map.getFacilityAt(i).getLocation().equals(location))
                return map.getFacilityAt(i).getFacName();
        }       
        return "X";
    }
    
    public Facility getFacilityByName(String name)
    {
        for(int i = 0; i < map.getNoOfFacilities(); i++)
        {
            if(map.getFacilityAt(i).getFacName().equals(name))
                return map.getFacilityAt(i);
        } 
        return null;
    }
    
    public void sendInquiry(String type, String desc)
    {
        //get the current date and time of inquiry
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate date = LocalDate.now();
        DateTimeFormatter tf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime time = LocalTime.now();
        
        //get inquiries in DB to set id
        int inqId = 0;
        try 
        {
            //DB Connection
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/SmartMallMapDB", "smartmallmap", "smartmallmap");
            
            //Retrieve from DB         
            PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM SMARTMALLMAP.INQUIRY");
            ResultSet rs = ps.executeQuery();           
            if(rs.next())
                inqId = rs.getInt(1) + 1001; //inquiry id sequence is 1001, 1002, ..           
            
            //Close Connection
            ps.close();
            rs.close();
            con.close();
        } catch (SQLException e) { System.out.println("Inquiry DB Connection Failed! : " + e);} 
        
        inq = new Inquiry(inqId, type, date.format(df), time.format(tf), null, "Pending", desc);
        inq.addInquiry(inqId, type, date.format(df), time.format(tf), null, "Pending", desc);  
    }
    
    public int[][] getDirection(String fn)
    {
        //2D-array of direction steps points X,Y
        int[][] d = new int[20][2];
        //get facility location
        String fl = getFacilityByName(fn).getLocation();
        
        d[0][0] = 0;   //number of steps
        d[1][0] = 243; //start point X         
        d[1][1] = 365; //start point Y
        
        if (fl.equals("F1"))
        {
            d[0][0] = 1;

            d[2][0] = d[1][0];      d[2][1] = d[1][1] + 50;

        }
        else if (fl.equals("F2"))
        {
            d[0][0] = 3;

            d[2][0] = d[1][0];      d[2][1] = d[1][1] + 30;
            d[3][0] = d[2][0]+ 100; d[3][1] = d[2][1];
            d[4][0] = d[3][0];      d[4][1] = d[3][1] + 20;
        }
        else if (fl.equals("F3"))
        {
            d[0][0] = 3;

            d[2][0] = d[1][0];      d[2][1] = d[1][1] + 30;
            d[3][0] = d[2][0]+ 170; d[3][1] = d[2][1];
            d[4][0] = d[3][0];      d[4][1] = d[3][1] + 20;
        }
        else if (fl.equals("F4"))
        {
            d[0][0] = 3;

            d[2][0] = d[1][0];      d[2][1] = d[1][1] + 30;
            d[3][0] = d[2][0]+ 240; d[3][1] = d[2][1];
            d[4][0] = d[3][0];      d[4][1] = d[3][1] + 20;
        }
        else if (fl.equals("F5"))
        {
            d[0][0] = 3;

            d[2][0] = d[1][0];      d[2][1] = d[1][1] + 30;
            d[3][0] = d[2][0]+ 340; d[3][1] = d[2][1];
            d[4][0] = d[3][0];      d[4][1] = d[3][1] + 20;
        }
        else if (fl.equals("F6"))
        {
            d[0][0] = 3;

            d[2][0] = d[1][0];      d[2][1] = d[1][1] + 30;
            d[3][0] = d[2][0]+ 425; d[3][1] = d[2][1];
            d[4][0] = d[3][0];      d[4][1] = d[3][1] + 160;
        }
        else if (fl.equals("F7"))
        {
            d[0][0] = 6;

            d[2][0] = d[1][0];      d[2][1] = d[1][1] + 30;
            d[3][0] = d[2][0]+ 425; d[3][1] = d[2][1];
            d[4][0] = d[3][0];      d[4][1] = d[3][1] + 125;
            d[5][0] = d[4][0]+ 160; d[5][1] = d[4][1];
            d[6][0] = d[5][0];      d[6][1] = d[5][1] + 105;
            d[7][0] = d[6][0]+ 36;  d[7][1] = d[6][1];
        }
        else if (fl.equals("F8"))
        {
            d[0][0] = 4;

            d[2][0] = d[1][0];      d[2][1] = d[1][1] + 30;
            d[3][0] = d[2][0]+ 425; d[3][1] = d[2][1];
            d[4][0] = d[3][0];      d[4][1] = d[3][1] + 125;
            d[5][0] = d[4][0]+ 195; d[5][1] = d[4][1];
        }
        else if (fl.equals("F9"))
        {
            d[0][0] = 5;

            d[2][0] = d[1][0];      d[2][1] = d[1][1] + 30;
            d[3][0] = d[2][0]+ 425; d[3][1] = d[2][1];
            d[4][0] = d[3][0];      d[4][1] = d[3][1] + 125;
            d[5][0] = d[4][0]+ 160; d[5][1] = d[4][1];
            d[6][0] = d[5][0];      d[6][1] = d[5][1] - 40;
        }
        else if (fl.equals("F10"))
        {
            d[0][0] = 4;

            d[2][0] = d[1][0];      d[2][1] = d[1][1] + 30;
            d[3][0] = d[2][0]+ 425; d[3][1] = d[2][1];
            d[4][0] = d[3][0];      d[4][1] = d[3][1] + 57;
            d[5][0] = d[4][0]+ 38;  d[5][1] = d[4][1];
        }
        else if (fl.equals("F11"))
        {
            d[0][0] = 3;

            d[2][0] = d[1][0]+ 15;  d[2][1] = d[1][1];
            d[3][0] = d[2][0];      d[3][1] = d[2][1] - 30;
            d[4][0] = d[3][0]+ 585; d[4][1] = d[3][1];
        }
        else if (fl.equals("F12"))
        {
            d[0][0] = 5;

            d[2][0] = d[1][0]+ 15;  d[2][1] = d[1][1];
            d[3][0] = d[2][0];      d[3][1] = d[2][1] - 30;
            d[4][0] = d[3][0]+ 500; d[4][1] = d[3][1];
            d[5][0] = d[4][0];      d[5][1] = d[4][1] - 100;
            d[6][0] = d[5][0]+ 35;  d[6][1] = d[5][1];
        }
        else if (fl.equals("F13"))
        {
            d[0][0] = 4;

            d[2][0] = d[1][0]+ 15;  d[2][1] = d[1][1];
            d[3][0] = d[2][0];      d[3][1] = d[2][1] - 30;
            d[4][0] = d[3][0]+ 500; d[4][1] = d[3][1];
            d[5][0] = d[4][0];      d[5][1] = d[4][1] - 215;          
        }
        else if (fl.equals("F14"))
        {
            d[0][0] = 6;

            d[2][0] = d[1][0]+ 15;  d[2][1] = d[1][1];
            d[3][0] = d[2][0];      d[3][1] = d[2][1] - 30;
            d[4][0] = d[3][0]+ 310; d[4][1] = d[3][1];
            d[5][0] = d[4][0];      d[5][1] = d[4][1] - 130;  
            d[6][0] = d[5][0]+ 40;  d[6][1] = d[5][1];
            d[7][0] = d[6][0];      d[7][1] = d[6][1] - 85;
        }
        else if (fl.equals("F15"))
        {
            d[0][0] = 7;

            d[2][0] = d[1][0]+ 15;  d[2][1] = d[1][1];
            d[3][0] = d[2][0];      d[3][1] = d[2][1] - 30;
            d[4][0] = d[3][0]+ 310; d[4][1] = d[3][1];
            d[5][0] = d[4][0];      d[5][1] = d[4][1] - 130;  
            d[6][0] = d[5][0]+ 40;  d[6][1] = d[5][1];
            d[7][0] = d[6][0];      d[7][1] = d[6][1] - 60;
            d[8][0] = d[7][0]- 40;  d[8][1] = d[7][1];
        }
        else if (fl.equals("F16"))
        {
            d[0][0] = 4;

            d[2][0] = d[1][0]+ 15;  d[2][1] = d[1][1];
            d[3][0] = d[2][0];      d[3][1] = d[2][1] - 30;
            d[4][0] = d[3][0]+ 220; d[4][1] = d[3][1];
            d[5][0] = d[4][0];      d[5][1] = d[4][1] - 20;
        }
        else if (fl.equals("F17"))
        {
            d[0][0] = 4;

            d[2][0] = d[1][0]+ 15;  d[2][1] = d[1][1];
            d[3][0] = d[2][0];      d[3][1] = d[2][1] - 30;
            d[4][0] = d[3][0]+ 130; d[4][1] = d[3][1];
            d[5][0] = d[4][0];      d[5][1] = d[4][1] - 20;
        }
        else if (fl.equals("F18"))
        {
            d[0][0] = 2;

            d[2][0] = d[1][0]+ 15;  d[2][1] = d[1][1];
            d[3][0] = d[2][0];      d[3][1] = d[2][1] - 50;
        }               
        return d;
    }
    
}