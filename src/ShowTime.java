public class ShowTime {
    public String getCinema()
    {
        return Cinema;
    }

    public void setCinema(String cinema)
    {
        Cinema = cinema;
    }

    public String getMovie()
    {
        return Movie;
    }

    public void setMovie(String movie)
    {
        Movie = movie;
    }

    public String getTime()
    {
        return Time;
    }

    public void setTime(String time)
    {
        Time = time;
    }
    @Override
    public String toString() {
        return this.getTime() + " at " + this.getCinema();
    }
    private String Cinema;
    private String Movie;
    private String Time;


}
