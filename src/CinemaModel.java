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

    private String CinemaID;
    private int addressX;
    private int addressY;

    public String getCinemaName()
    {
        return CinemaName;
    }

    public void setCinemaName(String cinemaName)
    {
        CinemaName = cinemaName;
    }

    private  String CinemaName;



}
