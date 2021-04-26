
package Entity;


public class Advertisement 
{
    private int adID; //1, 2
    private String type; //Image, Video, GIF
    private String filePath;

    public Advertisement(int adID) {
        this.adID = adID;
        this.type = "";
        this.filePath = "";
    }
    public Advertisement(int ID, String type, String filePath) {
        this.adID = ID;
        this.type = type;
        this.filePath = filePath;
    }

    public int getAdID() {
        return adID;
    }
    public void setAdID(int adID) {
        this.adID = adID;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
