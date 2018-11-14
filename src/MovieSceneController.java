import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MovieSceneController implements Initializable{

    @FXML
    private JFXButton btnHome;

    @FXML
    private JFXButton btnApply;

    @FXML
    private JFXCheckBox chkG;

    @FXML
    private JFXCheckBox chkPg13;

    @FXML
    private JFXCheckBox chkR;

    @FXML
    private JFXCheckBox chkPg;

    @FXML
    private JFXCheckBox chkNC17;

    @FXML
    private JFXCheckBox chkSelectAll;
	

    ObservableList<MovieModel> movieModels = FXCollections.observableArrayList();

    @FXML
    private TableView<MovieModel> table;

    @FXML
    private TableColumn<MovieModel, String> colID;

    @FXML
    private TableColumn<MovieModel, String> colTitle;

    @FXML
    private TableColumn<MovieModel, String> colRating;

    private void showAlertWithHeaderText(String errorMsg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Results:");
        alert.setContentText(errorMsg);
        alert.showAndWait();
    }

    @FXML
    void btnApplyClicked(ActionEvent event) {
        List<String> Ratings = new ArrayList<>();
        if(chkG.isSelected()){
            Ratings.add("G");
        }
        if(chkPg.isSelected()){
            Ratings.add("Pg");
        }
        if(chkPg13.isSelected()){
            Ratings.add("Pg-13");
        }
        if(chkR.isSelected()){
            Ratings.add("R");
        }
        if(chkNC17.isSelected()){
            Ratings.add("NC-17");
        }
        if(Ratings.size() == 0){
            showAlertWithHeaderText("Please choose a rating.");
            return;
        }

        StringBuilder sb = new StringBuilder("Select * from movies where Rating in (");

        for(int i = 0 ; i < Ratings.size(); i++){
            sb.append(i==Ratings.size() -1 ? "'" + Ratings.get(i) + "'": "'" + Ratings.get(i) + "'" + ",");
        }sb.append(")");

        try
        {
            Connection conn = ConnectionFactory.getConnection();

            // the mysql insert statement
            String query = sb.toString();

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
        colRating.setCellValueFactory(new PropertyValueFactory<>("Rating"));

        //adding an event listener for double clicks
        table.setRowFactory(tv -> {
            TableRow<MovieModel> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && (!row.isEmpty())) {
                    MovieModel rowData = row.getItem();
                    System.out.println(rowData.getTitle());
                }
            });
            return row;
        });

        table.setItems(movieModels);


    }

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
    @FXML
    void chkSelectAllClicked(ActionEvent event) {
        if(chkSelectAll.isSelected() == true)
        {
            chkG.setSelected(true);
            chkPg.setSelected(true);
            chkPg13.setSelected(true);
            chkR.setSelected(true);
            chkNC17.setSelected(true);
        }else{
            chkG.setSelected(false);
            chkPg.setSelected(false);
            chkPg13.setSelected(false);
            chkR.setSelected(false);
            chkNC17.setSelected(false);
        }


    }

    @FXML
    void bindCheckbox(ActionEvent event) {
        if(chkSelectAll.isSelected() == true){
            chkSelectAll.setSelected(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        chkG.setSelected(true);
        chkPg.setSelected(true);
        chkPg13.setSelected(true);
        chkR.setSelected(true);
        chkNC17.setSelected(true);
        chkSelectAll.setSelected(true);
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
        colRating.setCellValueFactory(new PropertyValueFactory<>("Rating"));

        //adding an event listener for double clicks
        table.setRowFactory(tv -> {
            TableRow<MovieModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    MovieModel rowData = row.getItem();
                    System.out.println(rowData.getTitle());

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("MovieDetails.fxml"));
                        Stage stage = (Stage) btnApply.getScene().getWindow();
                        Scene scene = new Scene(loader.load());
                        MovieDetailsController mdc = loader.getController();
                        mdc.initData(rowData);
                        stage.setScene(scene);
                    }catch (Exception e){

                    }

                }
            });
            return row;
        });

        table.setItems(movieModels);

    }

}
