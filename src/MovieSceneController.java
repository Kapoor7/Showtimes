import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class MovieSceneController implements Initializable{

    @FXML
    private JFXButton btnHome;



    ObservableList<MovieModel> movieModels = FXCollections.observableArrayList();

    @FXML
    private TableView<MovieModel> table;

    @FXML
    private TableColumn<MovieModel, String> colID;

    @FXML
    private TableColumn<MovieModel, String> colTitle;


    @FXML
    void btnHomeClicked(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MovieApp.fxml"));
            Stage stage = (Stage) btnHome.getScene().getWindow();

            Scene scene = new Scene(loader.load());
            stage.setScene(scene);


        }catch (Exception e){

        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        try
        {
            Connection conn = ConnectionFactory.getConnection();

            // the mysql insert statement
            String query = " SELECT * FROM movies";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            // execute the preparedstatement
            ResultSet rs = preparedStmt.executeQuery();
            movieModels.clear();
            while (rs.next())
            {

                MovieModel movie = new MovieModel();

                movie.setTitle(rs.getString("Title"));
                movie.setMovieID(rs.getString("MovieID"));
                movie.setRating(rs.getString("Rating"));


                movieModels.add(movie);
            }

            conn.close();

        } catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }

        colID.setCellValueFactory(new PropertyValueFactory<>("MovieID"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        //adding an event listener for double clicks
        table.setRowFactory(tv -> {
            TableRow<MovieModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    MovieModel rowData = row.getItem();
                    System.out.println(rowData.getTitle());
                }
            });
            return row;
        });

        table.setItems(movieModels);



    }




}
