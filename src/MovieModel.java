import java.sql.Timestamp;

public class MovieModel {

    private String MovieID;

    private String title;

    private String rating;

    private Timestamp time;

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
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


    public String getMovieID()
    {
        return MovieID;
    }

    public void setMovieID(String movieID)
    {
        MovieID = movieID;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getRating()
    {
        return rating;
    }

    public void setRating(String rating)
    {
        this.rating = rating;
    }



}
