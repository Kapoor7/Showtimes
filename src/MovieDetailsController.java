import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class MovieDetailsController {


    @FXML
    private Label lblPlaying;

    @FXML
    private  TableView<CinemaModel> table;

     ObservableList<CinemaModel> CinemaModels = FXCollections.observableArrayList();

    @FXML
    private  TableColumn<CinemaModel, String> colID;

    @FXML
    private  TableColumn<CinemaModel, String> colTitle;

    @FXML
    private  TableColumn<CinemaModel, String> coltime;
   // private  TableColumn<CinemaModel, Timestamp> coltime;

    @FXML
    private TableColumn<CinemaModel, String> colLaction; // It means location


    private  String name;

    @FXML
    private JFXButton btnBack;



    @FXML
    void btnBackClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MovieScene.fxml"));
            Stage stage = (Stage) btnBack.getScene().getWindow();

            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        }catch (Exception e){

        }
    }

    @FXML
    private JFXButton btnHome;

    @FXML

    public void initData(MovieModel movie){
        name = movie.getTitle();
        lblPlaying.setText(lblPlaying.getText() + name);

        try
        {
            Connection conn = ConnectionFactory.getConnection();

            // the mysql insert statement
            /*String query = " SELECT * FROM showtime s inner join cinemas c on s.cinemaid = c.cinemaid \n" +
                    "where movieid = " +  movie.getMovieID() ;*/
            String query = " SELECT *FROM showtime s inner join cinemas c on s.cinemaid = c.cinemaid \n" +
                    "where movieid = " +  movie.getMovieID()+ " and time > ? group by C.CinemaID" ; // get distinct movie theaters playing a certain movie

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, LocalDateTime.now().toLocalDate().toString());
           // preparedStmt.setString(1, movie.getMovieID());
            // execute the preparedstatement
            ResultSet rs = preparedStmt.executeQuery();
            CinemaModels.clear();
            while (rs.next()) //for each distinct movie theater get all the showtimes
            {
                int cinemaID = rs.getInt("CinemaID");
                String query2 = "SELECT * FROM showtime s inner join cinemas c on s.cinemaid = c.cinemaid where movieid = ? and c.cinemaID = ? and time > ?";
                PreparedStatement preparedStmt2 = conn.prepareStatement(query2);
                preparedStmt2.setString(1, movie.getMovieID());
                preparedStmt2.setInt(2, cinemaID);
                preparedStmt2.setString(3, LocalDateTime.now().toLocalDate().toString());
                ResultSet rs2 = preparedStmt2.executeQuery();
                StringBuilder sb = new StringBuilder("");
                while(rs2.next()){
                    if(rs2.getTimestamp("Time").toString().charAt(11) == '0'){
                        sb.append(rs2.getTimestamp("Time").toString().substring(12,16) + " ");
                    }else{
                        sb.append(rs2.getTimestamp("Time").toString().substring(11,16) + " ");
                    }

                }
                CinemaModel cinema = new CinemaModel();

                cinema.setShowTimes(sb.toString());
                cinema.setCinemaName(rs.getString("CinemaName"));
                cinema.setAddressX(rs.getInt("addressX"));
                cinema.setAddressY(rs.getInt("addressY"));
                cinema.setCinemaID(rs.getString("CinemaID"));




                CinemaModels.add(cinema);
            }

            conn.close();

        } catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }


        colID.setCellValueFactory(new PropertyValueFactory<>("CinemaID"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("CinemaName"));
        //coltime.setCellValueFactory(new PropertyValueFactory<>("time"));
        coltime.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getShowTimes() ));

        colLaction.setCellValueFactory(c -> {
            if(c.getValue() != null){
                return new SimpleStringProperty(c.getValue().getAddressX() + "," + c.getValue().getAddressY());
            }else{
                return new SimpleStringProperty("<no address>");
            }
        });

        table.setItems(CinemaModels);

    }


    @FXML
    void btnHomeClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MovieApp.fxml"));
            Stage stage = (Stage) btnHome.getScene().getWindow();

            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        }catch (Exception e){

        }

    }

}


