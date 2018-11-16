import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;


public class AddDeleteController {

    @FXML
    private Button btnDeleteMovie;

    @FXML
    private Button btnDeleteCinema;

    @FXML
    private Button btnDeleteShowtime;

    @FXML
    private JFXComboBox<String> cbDeleteMovie;

    @FXML
    private Label lblMsg;

    @FXML
    private Label lblShowtimeDate;

    @FXML
    private JFXTextField txtMovie;

    @FXML
    private JFXComboBox<String> cbDeleteShowtimeMovie;

    @FXML
    private JFXComboBox<ShowTime> cbDeleteShowtime;

    ObservableList<ShowTime> showtimes = FXCollections.observableArrayList();

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXComboBox<String> cbDeleteCinema;

    @FXML
    private Button btnSubmit;

    @FXML
    private JFXComboBox<String> cbRating;


    //Add Cinema Starts Here

    @FXML
    private JFXTextField txtCinema;

    @FXML
    private Button btnCSubmit;


    @FXML
    private JFXTextField txtX;

    @FXML
    private JFXTextField txtY;


    @FXML
    void btnCSubmitClicked(ActionEvent event)
    {
        String cinema = txtCinema.getText().trim();
        if (cinema.length() == 0 || txtX.getText().length() == 0 || txtY.getText().length() == 0)
        {
            lblMsg.setText("Please Enter all the Fields");
            lblMsg.setVisible(true);
            return;
        }
        int x = Integer.parseInt(txtX.getText());
        int y = Integer.parseInt(txtY.getText());
        if (x < 0 || x > 100 || y < 0 || y > 100)
        {
            lblMsg.setText("Please Enter address in the range of [0,100]");
            lblMsg.setVisible(true);
            return;
        }
        if (ConnectionFactory.cinemaExistsAlready(cinema, x, y))
        {
            showAlertWithHeaderText("Cinema already exists.");
            return;
        }

        try
        {
            Connection conn = ConnectionFactory.getConnection();

            String query = "insert into cinemas(CinemaName, addressX, addressY) values (?, ?, ?)";

            PreparedStatement prp = conn.prepareStatement(query);

            prp.setString(1, cinema);
            prp.setString(2, Integer.toString(x));
            prp.setString(3, Integer.toString(y));


            prp.execute();

            conn.close();
            lblMsg.setText(cinema + " has been added successfully!");
            lblMsg.setVisible(true);


        } catch (Exception e)
        {
            lblMsg.setText(cinema + " has failed to add! Please try again");
            lblMsg.setVisible(true);
        }

        txtCinema.setText("");
        txtY.setText("");
        txtX.setText("");


    }

    //Add Cinema Finishes Here

    //Add Time Starts

    @FXML
    private Button btnTSubmit;


    @FXML
    private JFXComboBox<String> cbCinema;

    @FXML
    private JFXComboBox<String> cbMovie;

    @FXML
    private JFXComboBox<String> cbSession;

    @FXML
    private JFXComboBox<String> cbTime;


    @FXML
    void btnTSubmitClicked(ActionEvent event)
    {
        String cinema = cbCinema.getValue();
        String movie = cbMovie.getValue();
        String session = cbSession.getValue();
        String time = cbTime.getValue();
        if (cinema==null || movie == null || session== null || time == null)
        {
            lblMsg.setText("Please Enter all the Fields");
            lblMsg.setVisible(true);
            return;
        }
        if(ConnectionFactory.showTimeExistsAlready(movie, cinema, Timestamp.valueOf(LocalDateTime.now().toLocalDate().toString() + " " + time + ":00"))){
            showAlertWithHeaderText("Showtime already exist");
        }
            try
            {
                Connection conn = ConnectionFactory.getConnection();

                String query = "SELECT MovieId FROM movies where Title = ?";
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                // execute the preparedstatement
                preparedStmt.setString(1, movie);
                ResultSet rs = preparedStmt.executeQuery();
                rs.next();
                int movieID = rs.getInt("MovieID");

                query = "SELECT CinemaID FROM cinemas where CinemaName = ?";
                preparedStmt = conn.prepareStatement(query);
                preparedStmt.setString(1, cinema);
                rs = preparedStmt.executeQuery();
                rs.next();
                int cinemaID = rs.getInt("CinemaID");

                query = "insert into showtime(CinemaID, MovieID, Session, Time) values (?, ?, ?, ?)";

                PreparedStatement prp = conn.prepareStatement(query);

                time = LocalDateTime.now().toLocalDate() + " " + time + ":00";

                prp.setInt(1, cinemaID);
                prp.setInt(2, movieID);
                prp.setString(3, session);
                prp.setString(4, time);


                prp.execute();

                conn.close();
                cbTime.setValue(null);
                cbSession.setValue(null);
                cbMovie.setValue(null);
                cbCinema.setValue(null);
                lblMsg.setText(movie + " has been added at " + cinema + " successfully!");
                lblMsg.setVisible(true);


            } catch (Exception e)
            {
                lblMsg.setText("Failed to add! Please try again");
                cbTime.setValue(null);
                cbSession.setValue(null);
                cbMovie.setValue(null);
                cbCinema.setValue(null);
                lblMsg.setVisible(true);
            }



    }

    public void initTime()
    {
        lblShowtimeDate.setText(lblShowtimeDate.getText() + "for " + LocalDateTime.now().toLocalDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
        // cbTime.getItems().add("Please choose a session first");
        cbTime.setDisable(true);
        cbMovie.setOnAction(e->{
            lblMessageChange();
        });
        cbCinema.setOnAction(e->{
            lblMessageChange();
        });
        cbTime.setOnAction(e->{
            lblMessageChange();
        });
        cbSession.setOnAction(e -> {
            lblMessageChange();
            if (cbSession.getValue() == null)
            {
                cbTime.setDisable(true);
            }
            if (cbSession.getValue() == "Afternoon")
            {
                cbTime.setDisable(false);
                cbTime.getItems().clear();
                cbTime.getItems().add("1:00");
                cbTime.getItems().add("1:30");
                cbTime.getItems().add("2:15");
                cbTime.getItems().add("2:40");
                cbTime.getItems().add("3:20");
                cbTime.getItems().add("4:45");
            }
            if (cbSession.getValue() == "Evening")
            {
                cbTime.setDisable(false);
                cbTime.getItems().clear();
                cbTime.getItems().add("5:15");
                cbTime.getItems().add("5:40");
                cbTime.getItems().add("6:10");
                cbTime.getItems().add("6:55");
                cbTime.getItems().add("7:20");
                cbTime.getItems().add("8:00");
                cbTime.getItems().add("8:40");
            }
            if (cbSession.getValue() == "Late Night")
            {
                cbTime.setDisable(false);
                cbTime.getItems().clear();
                cbTime.getItems().add("10:00");
                cbTime.getItems().add("11.30");
                cbTime.getItems().add("12:00");
            }


        });

        cbSession.getItems().add("Afternoon");
        cbSession.getItems().add("Evening");
        cbSession.getItems().add("Late Night");

        /*cbTime.getItems().add("02:15");
        cbTime.getItems().add("03:20");
        cbTime.getItems().add("04:45");
        cbTime.getItems().add("08:00");
        cbTime.getItems().add("08:40");
        cbTime.getItems().add("10:00");
        cbTime.getItems().add("11.30");

      /*  if (cbSession.getValue().length()==9){
            cbTime.getItems().add("01:00");
            cbTime.getItems().add("01:30");
            cbTime.getItems().add("02:15");
            cbTime.getItems().add("02:40");
            cbTime.getItems().add("03:20");
            cbTime.getItems().add("04:45");
        }
 /*       else if (cbSession.getValue().equals("Evening")) {
            cbTime.getItems().add("05:15");
            cbTime.getItems().add("05:40");
            cbTime.getItems().add("06:10");
            cbTime.getItems().add("06:55");
            cbTime.getItems().add("07:20");
            cbTime.getItems().add("08:00");
            cbTime.getItems().add("08:40");
        }
        else{
            cbTime.getItems().add("10:00");
            cbTime.getItems().add("11.30");
            cbTime.getItems().add("12:00");
        }

*/
        try
        {
            Connection conn = ConnectionFactory.getConnection();

            // the mysql insert statement
            String query = " SELECT * FROM cinemas";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            // execute the preparedstatement
            ResultSet rs = preparedStmt.executeQuery();

            while (rs.next())
            {
                cbCinema.getItems().add(rs.getString("CinemaName"));

            }

            conn.close();

        } catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }

        try
        {
            Connection conn = ConnectionFactory.getConnection();

            // the mysql insert statement
            String query = " SELECT * FROM movies";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            // execute the preparedstatement
            ResultSet rs = preparedStmt.executeQuery();

            while (rs.next())
            {
                cbMovie.getItems().add(rs.getString("Title"));

            }

            conn.close();

        } catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }


    }


    //Add Time Finishes

    @FXML
    void btnBackClicked(ActionEvent event)
    {

        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminScene.fxml"));
            Stage stage = (Stage) btnBack.getScene().getWindow();

            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

        } catch (Exception e)
        {

        }

    }

    @FXML
    void lblMessageChange(KeyEvent event)
    {
        lblMsg.setVisible(false);
    }

    void lblMessageChange() {
        lblMsg.setVisible(false);
    }


    @FXML
    void btnSubmitClicked(ActionEvent event)
    {
        String movie = txtMovie.getText().trim();
        String rating = cbRating.getValue();
        if (movie.length() == 0 || rating == null)
        {
            lblMsg.setText("Please Enter Both Fields");
            lblMsg.setVisible(true);
            return;
        }
        if (ConnectionFactory.movieExistsAlready(movie))
        {
            showAlertWithHeaderText("Movie already exists.");
            return;
        }

        try
        {
            Connection conn = ConnectionFactory.getConnection();

            String query = "insert into movies(Title, Rating) values (?, ?)";

            PreparedStatement prp = conn.prepareStatement(query);

            prp.setString(1, movie);
            prp.setString(2, cbRating.getValue());

            prp.execute();

            conn.close();
            lblMsg.setText(movie + " has been added successfully!");
            lblMsg.setVisible(true);


        } catch (Exception e)
        {
            e.printStackTrace();
            lblMsg.setText(movie + " has failed to add! Please try again later.");
            lblMsg.setVisible(true);
        }

        txtMovie.setText("");
        cbRating.setValue(null);


    }

    public void initAddMovie()
    {
        cbRating.getItems().add("G");
        cbRating.getItems().add("PG");
        cbRating.getItems().add("PG-13");
        cbRating.getItems().add("R");
        cbRating.getItems().add("NC-17");
        cbRating.getItems().add("NR");

    }

    private void showAlertWithHeaderText(String errorMsg)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Error");
        alert.setHeaderText("Results:");
        alert.setContentText(errorMsg);
        alert.showAndWait();
    }

    @FXML
    void btnDeleteMovieClicked(ActionEvent event) {
        String title = cbDeleteMovie.getValue();
        if(title != null){
            try
            {
                Connection conn = ConnectionFactory.getConnection();

                String query = "Delete from showtime where MovieID in (Select MovieID from movies where Title = ?)";

                PreparedStatement prp = conn.prepareStatement(query);

                prp.setString(1, title);

                prp.execute();

                query = "Delete from movies where Title = ?";
                prp = conn.prepareStatement(query);
                prp.setString(1, title);
                prp.execute();

                conn.close();
                cbDeleteMovie.setValue(null);
                lblMsg.setText(title + " has been removed successfully!");
                lblMsg.setVisible(true);


            } catch (Exception e)
            {
                e.printStackTrace();
                lblMsg.setText(title + " has failed to remove! Please try again later.");
                lblMsg.setVisible(true);
            }
        }
        else{
            lblMsg.setText("Please choose a movie to remove");
            lblMsg.setVisible(true);
        }
    }

    @FXML
    void btnDeleteShowtimeClicked(ActionEvent event) {
       ShowTime st = cbDeleteShowtime.getValue();
       String cinema = st.getCinema();
       String movie = st.getMovie();
       String time = LocalDateTime.now().toLocalDate().toString() + " " + st.getTime() + ":00";
       if(st!= null){
           try
           {
               Connection conn = ConnectionFactory.getConnection();

               String query = "Delete from showtime where CinemaID in (Select CinemaID from cinemas where CinemaName = ?) AND MovieID in (Select MovieId from movies where title = ?) AND Time = ?";

               PreparedStatement prp = conn.prepareStatement(query);

               prp.setString(1, cinema);
               prp.setString(2, movie);
               prp.setTimestamp(3, Timestamp.valueOf(time));

               prp.execute();

               conn.close();
               cbDeleteShowtimeMovie.setValue(null);
               lblMsg.setText(movie + " " + st.toString() + " has been removed successfully!");
               lblMsg.setVisible(true);


           } catch (Exception e)
           {
               e.printStackTrace();
               lblMsg.setText(movie + "" + st.toString() + " has failed to remove! Please try again later.");
               lblMsg.setVisible(true);
           }
       }
       else{
           lblMsg.setText("Please choose a showtime to remove");
           lblMsg.setVisible(true);
       }



    }

    public void initDeleteShowtime(){
        cbDeleteShowtime.setDisable(true);
        cbDeleteShowtimeMovie.setOnAction(e->{
            lblMessageChange();
            if (cbDeleteShowtimeMovie.getValue() == null)
            {
                cbDeleteShowtime.setDisable(true);
            }
            getShowTimesForMovieDelete(cbDeleteShowtimeMovie.getValue());
        });

        try
        {
            Connection conn = ConnectionFactory.getConnection();

            // the mysql insert statement
            String query = " SELECT * FROM movies";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            // execute the preparedstatement
            ResultSet rs = preparedStmt.executeQuery();

            while (rs.next())
            {
                cbDeleteShowtimeMovie.getItems().add(rs.getString("Title"));

            }

            conn.close();

        } catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }

    }

    public void getShowTimesForMovieDelete(String movieTitle){
        cbDeleteShowtime.getItems().clear();
        cbDeleteShowtime.setDisable(false);
        try
        {
            Connection conn = ConnectionFactory.getConnection();

            // the mysql insert statement
            String query = " SELECT * FROM showtime s inner join cinemas c on c.cinemaid = s.cinemaid where time > ? AND movieid in (Select movieid from movies where title = ?) order by time";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, LocalDateTime.now().toLocalDate().toString());
            preparedStmt.setString(2, movieTitle);
            // execute the preparedstatement
            ResultSet rs = preparedStmt.executeQuery();
            String time;
            while (rs.next())
            {
                if(rs.getTimestamp("Time").toString().charAt(11) == '0'){
                    time = rs.getTimestamp("Time").toString().substring(12,16);
                }else{
                    time = rs.getTimestamp("Time").toString().substring(11,16);
                }

                ShowTime newShowTime = new ShowTime();
                newShowTime.setCinema(rs.getString("CinemaName"));
                newShowTime.setMovie(movieTitle);
                newShowTime.setTime(time);
                showtimes.add(newShowTime);
                //cbDeleteShowtime.getItems().add(rs.getString("Session") + " " + time + " at " + rs.getString("CinemaName"));

            }
            if(showtimes.isEmpty()){
                cbDeleteShowtime.setDisable(true);
                lblMsg.setText("No showtimes for this movie today");
                lblMsg.setVisible(true);
            }

            conn.close();

        } catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
        cbDeleteShowtime.setItems(showtimes);
    }


    @FXML
    void btnDeleteCinemaClicked(ActionEvent event) {
        String cinema = cbDeleteCinema.getValue();
        if(cinema != null){
            try
            {
                Connection conn = ConnectionFactory.getConnection();

                String query = "Delete from showtime where CinemaID in (Select CinemaID from cinemas where CinemaName = ?)";

                PreparedStatement prp = conn.prepareStatement(query);

                prp.setString(1, cinema);

                prp.execute();

                query = "Delete from cinemas where CinemaName = ?";
                prp = conn.prepareStatement(query);
                prp.setString(1, cinema);
                prp.execute();

                conn.close();
                cbDeleteCinema.setValue(null);
                lblMsg.setText(cinema + " has been removed successfully!");
                lblMsg.setVisible(true);


            } catch (Exception e)
            {
                e.printStackTrace();
                lblMsg.setText(cinema + " has failed to remove! Please try again later.");
                lblMsg.setVisible(true);
            }
        }
        else{
            lblMsg.setText("Please choose a cinema to remove");
            lblMsg.setVisible(true);
        }
    }

    public void initDeleteCinema(){
        cbDeleteCinema.setOnAction(e-> {
            lblMessageChange();
        });
        try
        {
            Connection conn = ConnectionFactory.getConnection();

            // the mysql insert statement
            String query = " SELECT * FROM cinemas";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            // execute the preparedstatement
            ResultSet rs = preparedStmt.executeQuery();

            while (rs.next())
            {
                cbDeleteCinema.getItems().add(rs.getString("CinemaName"));

            }

            conn.close();

        } catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }

    public void initDeleteMovie(){
        cbDeleteMovie.setOnAction(e-> {
            lblMessageChange();
        });
        try
        {
            Connection conn = ConnectionFactory.getConnection();

            // the mysql insert statement
            String query = " SELECT * FROM movies";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            // execute the preparedstatement
            ResultSet rs = preparedStmt.executeQuery();

            while (rs.next())
            {
                cbDeleteMovie.getItems().add(rs.getString("Title"));

            }

            conn.close();

        } catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }


}
