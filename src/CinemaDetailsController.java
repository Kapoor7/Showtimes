import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class CinemaDetailsController {
    ObservableList<MovieModel> MovieModels = FXCollections.observableArrayList();
    private String name;

    @FXML
    private TableView<MovieModel> table;

    @FXML
    private TableColumn<MovieModel, String> colID;

    @FXML
    private TableColumn<MovieModel, String> colTitle;

    @FXML
    private TableColumn<MovieModel, Timestamp> coltime;

    @FXML
    private TableColumn<MovieModel, String> colRating;

    @FXML
    private JFXButton btnHome;

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




    public void initData(CinemaModel cinema){
        name = cinema.getCinemaName();

        try
        {
            Connection conn = ConnectionFactory.getConnection();

            // the mysql insert statement
            String query = " SELECT * FROM showtime s inner join movies m on s.movieid = m.movieid \n" +
                    "where cinemaid = " +  cinema.getCinemaID() ;

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            // preparedStmt.setString(1, movie.getMovieID());
            // execute the preparedstatement
            ResultSet rs = preparedStmt.executeQuery();
            MovieModels.clear();
            while (rs.next())
            {

                MovieModel movie = new MovieModel();

                movie.setTime(rs.getTimestamp("time"));
                movie.setTitle(rs.getString("Title"));
                movie.setRating(rs.getString("Rating"));
                movie.setMovieID(rs.getString("MovieID"));




                MovieModels.add(movie);
            }

            conn.close();

        } catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }


        colID.setCellValueFactory(new PropertyValueFactory<>("MovieID"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        coltime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colRating.setCellValueFactory(new PropertyValueFactory<>("Rating"));


        table.setItems(MovieModels);

    }
}
