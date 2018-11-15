
import com.mysql.jdbc.Driver;

import java.sql.*;

public class ConnectionFactory {

    public static final String URL = "jdbc:mysql://localhost/cs370project4?useSSL=false";
    public static final String USER = "root";
    public static final String PASS = "love96";

    public static Connection getConnection()
    {
        try {
            DriverManager.registerDriver(new Driver());
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException ex) {
            throw new RuntimeException("Error connecting to the database", ex);
        }
    }

    public static boolean movieExistsAlready(String movieTitle)
    {
        try
        {
            Connection conn = ConnectionFactory.getConnection();

            String query = "Select * from movies where Title = ?";

            PreparedStatement prp = conn.prepareStatement(query);

            prp.setString(1, movieTitle);

            ResultSet rs = prp.executeQuery();

            if (rs.next())
            {
                conn.close();
                return true;
            } else {
                conn.close();
                return false;
            }




        } catch (Exception e)
        {

            e.printStackTrace();
            return true;
        }

    }

    public static boolean cinemaExistsAlready(String cinemaTitle, int x, int y)
    {
        try
        {
            Connection conn = ConnectionFactory.getConnection();

            String query = "Select * from cinemas where CinemaName = ? OR (addressX = ? AND addressY = ?)";

            PreparedStatement prp = conn.prepareStatement(query);

            prp.setString(1, cinemaTitle);
            prp.setInt(2, x);
            prp.setInt(3, y);

            ResultSet rs = prp.executeQuery();

            if (rs.next())
            {
                conn.close();
                return true;
            } else {
                conn.close();
                return false;
            }




        } catch (Exception e)
        {

            e.printStackTrace();
            return true;
        }

    }

    public static boolean showTimeExistsAlready(){
        return true;
    }



}
