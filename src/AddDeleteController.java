import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;


public class AddDeleteController  {

    @FXML
    private Label lblMsg;

    @FXML
    private JFXTextField txtMovie;

    @FXML
    private JFXButton btnBack;

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
    void btnCSubmitClicked(ActionEvent event) {
        String cinema = txtCinema.getText();
        if (cinema.length()==0 || txtX.getText().length()==0 || txtY.getText().length()==0){
            lblMsg.setText("Please Enter all the Fields");
            lblMsg.setVisible(true);
            return;
        }
        int x = Integer.parseInt(txtX.getText());
        int y = Integer.parseInt(txtY.getText());
        if (x<0 || x >100 || y<0 || y >100){
            lblMsg.setText("Please Enter address in the range of [0,100]");
            lblMsg.setVisible(true);
        }

        else {
            try {
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


            } catch (Exception e) {
                lblMsg.setText(cinema + " has failed to add! Please try again");
                lblMsg.setVisible(true);
            }

            txtCinema.setText("");
            txtY.setText("");
            txtX.setText("");

        }

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
    void btnTSubmitClicked(ActionEvent event) {/*
        String cinema = cbCinema.getValue();
        String movie = cbMovie.getValue();
        String session = cbSession.getValue();
        String time = cbTime.getValue();
        if (cinema.length()==0 || movie.length()==0 || session.length()==0 || time.length()==0){
            lblMsg.setText("Please Enter all the Fields");
            lblMsg.setVisible(true);
            return;
        }

        else {
            try {
                Connection conn = ConnectionFactory.getConnection();

                String query = "SELECT MovieId FROM cs370project4.movies where Title = movie";
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                // execute the preparedstatement
                ResultSet rs = preparedStmt.executeQuery();
                String movieID = rs.getString("MovieID");

                query = "SELECT CinemaID FROM cs370project4.cinemas where CinemaName = cinema";
                preparedStmt = conn.prepareStatement(query);
                rs = preparedStmt.executeQuery();
                String cinemaID = rs.getString("CinemaID");

                query = "insert into showtimes(CinemaID, MovieID, Session, Time) values (?, ?, ?, ?)";

                PreparedStatement prp = conn.prepareStatement(query);

                prp.setString(1, cinemaID);
                prp.setString(2, movieID);
                prp.setString(3, session);
                prp.setString(4, time);


                prp.execute();

                conn.close();
                lblMsg.setText(movie + " has been added at "+ cinema +" successfully!");
                lblMsg.setVisible(true);


            } catch (Exception e) {
                lblMsg.setText("Failed to add! Please try again");
                lblMsg.setVisible(true);
            }

        }*/

    }

    public void initTime(){
        cbSession.getItems().add("Afternoon");
        cbSession.getItems().add("Evening");
        cbSession.getItems().add("Midnight");
        cbTime.getItems().add("06:00");
        cbTime.getItems().add("01:30");
        cbTime.getItems().add("02:15");

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
    void btnBackClicked(ActionEvent event) {

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminScene.fxml"));
            Stage stage = (Stage) btnBack.getScene().getWindow();

            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

        }catch (Exception e){

        }

    }

    @FXML
    void lblMessageChange(KeyEvent event) {
        lblMsg.setVisible(false);
    }

    @FXML
    void btnSubmitClicked(ActionEvent event) {
        String movie = txtMovie.getText();
        String rating = cbRating.getValue();
        if(movie.length() == 0 || rating == null) {
            lblMsg.setText("Please Enter Both Fields");
            lblMsg.setVisible(true);
        }
        else {
            try {
                Connection conn = ConnectionFactory.getConnection();

                String query = "insert into movies(Title, Rating) values (?, ?)";

                PreparedStatement prp = conn.prepareStatement(query);

                prp.setString(1, movie);
                prp.setString(2, cbRating.getValue());

                prp.execute();

                conn.close();
                lblMsg.setText(movie + " has been added successfully!");
                lblMsg.setVisible(true);


            } catch (Exception e) {
                lblMsg.setText(movie + " has failed to add! Please try again later.");
                lblMsg.setVisible(true);
            }

            txtMovie.setText("");

        }
    }

    public void initAddMovie(){
        cbRating.getItems().add("G");
        cbRating.getItems().add("Pg-13");
        cbRating.getItems().add("R");
        cbRating.getItems().add("NC-17");
        cbRating.getItems().add("NR");
    }


}
