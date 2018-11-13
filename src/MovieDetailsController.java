import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class MovieDetailsController {

    @FXML
    private  TableView<CinemaModel> table;

     ObservableList<CinemaModel> CinemaModels = FXCollections.observableArrayList();

    @FXML
    private  TableColumn<CinemaModel, String> colID;

    @FXML
    private  TableColumn<CinemaModel, String> colTitle;

    @FXML
    private  TableColumn<CinemaModel, String> coltime;

    @FXML
    private TableColumn<CinemaModel, String> colLaction; // It means location

    @FXML
    private  String name;
    @FXML
    private JFXButton btnHome;

    @FXML

    public void initData(MovieModel movie){
        name = movie.getTitle();

        try
        {
            Connection conn = ConnectionFactory.getConnection();

            // the mysql insert statement
            String query = " SELECT * FROM showtime s inner join cinemas c on s.cinemaid = c.cinemaid \n" +
                    "where movieid = " +  movie.getMovieID() ;

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
           // preparedStmt.setString(1, movie.getMovieID());
            // execute the preparedstatement
            ResultSet rs = preparedStmt.executeQuery();
            CinemaModels.clear();
            while (rs.next())
            {

                CinemaModel cinema = new CinemaModel();

                cinema.setTime(rs.getTimestamp("time"));
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
        coltime.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTime().toString().substring(11,16) ));
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


