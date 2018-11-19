import java.sql.Timestamp;

public class CinemaModel {
    public String getCinemaID()
    {
        return CinemaID;
    }

    public void setCinemaID(String cinemaID)
    {
        CinemaID = cinemaID;
    }

    public int getAddressX()
    {
        return addressX;
    }

    public void setAddressX(int addressX)
    {
        this.addressX = addressX;
    }

    public int getAddressY()
    {
        return addressY;
    }

    public void setAddressY(int addressY)
    {
        this.addressY = addressY;
    }


    public String getShowTimes()
    {
        return showTimes;
    }

    public void setShowTimes(String showTimes)
    {
        this.showTimes = showTimes;
    }

    private String showTimes;
    private String CinemaID;
    private int addressX;
    private int addressY;
    private  String CinemaName;
    private Timestamp time;

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getCinemaName()
    {
        return CinemaName;
    }

    public void setCinemaName(String cinemaName)
    {
        CinemaName = cinemaName;
    }








}
