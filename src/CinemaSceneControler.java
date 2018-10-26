import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.util.Callback;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class CinemaSceneControler implements Initializable{

    @FXML
    private JFXButton btnHome;
    @FXML
    private JFXTextField txtRadius;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXButton btnFilter;



    ObservableList<CinemaModel> CinemaModels = FXCollections.observableArrayList();
    @FXML
    private TableView<CinemaModel> table2;

    @FXML
    private TableColumn<CinemaModel, String> colID;

    @FXML
    private TableColumn<CinemaModel, String> colName;

    @FXML
    private TableColumn<CinemaModel, String> colAddress;

    @FXML
    void btnFilterClicked(ActionEvent event) {
        if(txtAddress.getText().length() != 0 && txtRadius.getText().length() != 0){

            String[] clientAddress = txtAddress.getText().split(",");
            int x = 0; int y=0;
            try
            {
                 x = Integer.parseInt(clientAddress[0]);
                 y = Integer.parseInt(clientAddress[1]);
            }catch(Exception e){

                System.err.print(e.getMessage());
                showAlertWithHeaderText("Please enter a valid address.");
                return;
            }

            try
            {
                Connection conn = ConnectionFactory.getConnection();

                // the mysql insert statement
                String query = " SELECT * FROM cinemas";

                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                // execute the preparedstatement
                ResultSet rs = preparedStmt.executeQuery();
                CinemaModels.clear();
                while (rs.next())
                {

                    if(getDistance(x, y, Integer.parseInt(rs.getString("addressX")), Integer.parseInt(rs.getString("addressY"))) <= Integer.parseInt(txtRadius.getText())){
                        CinemaModel cinema = new CinemaModel();

                        cinema.setCinemaID(rs.getString("CinemaID"));
                        cinema.setCinemaName(rs.getString("CinemaName"));
                        cinema.setAddressX(rs.getInt("addressX"));
                        cinema.setAddressY(rs.getInt("addressY"));


                        CinemaModels.add(cinema);
                    }

                }

                conn.close();

            } catch (Exception e)
            {
                System.err.println("Got an exception!");
                System.err.println(e.getMessage());
            }

            colID.setCellValueFactory(new PropertyValueFactory<>("CinemaID"));
            colName.setCellValueFactory(new PropertyValueFactory<>("CinemaName"));

            //concatenating x and y into one field
            colAddress.setCellValueFactory(c -> {
                if (c.getValue() != null) {
                    return new SimpleStringProperty(c.getValue().getAddressX() + "," + c.getValue().getAddressY());
                } else {
                    return new SimpleStringProperty("<no address>");
                }
            });

            //adding an event listener for double clicks
            table2.setRowFactory(tv -> {
                TableRow<CinemaModel> row = new TableRow<>();
                row.setOnMouseClicked(e -> {
                    if (e.getClickCount() == 2 && (!row.isEmpty())) {
                        CinemaModel rowData = row.getItem();
                        System.out.println(rowData.getCinemaName());
                    }
                });
                return row;
            });


            table2.setItems(CinemaModels);



        }else{
            //please enter valid inputs
        }

    }

    double getDistance(int x1, int y1, int x2, int y2){
        return Math.sqrt(Math.pow((x2 - x1) , 2) + Math.pow((y2 - y1), 2) );
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

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        txtRadius.setStyle("-fx-text-inner-color: #c0b283;");
        txtAddress.setStyle("-fx-text-inner-color: #c0b283;");
        try
        {
            Connection conn = ConnectionFactory.getConnection();

            // the mysql insert statement
            String query = " SELECT * FROM cinemas";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            // execute the preparedstatement
            ResultSet rs = preparedStmt.executeQuery();
            CinemaModels.clear();
            while (rs.next())
            {

                CinemaModel cinema = new CinemaModel();

                cinema.setCinemaID(rs.getString("CinemaID"));
                cinema.setCinemaName(rs.getString("CinemaName"));
                cinema.setAddressX(rs.getInt("addressX"));
                cinema.setAddressY(rs.getInt("addressY"));


                CinemaModels.add(cinema);
            }

            conn.close();

        } catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }

        colID.setCellValueFactory(new PropertyValueFactory<>("CinemaID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("CinemaName"));

        //concatenating x and y into one field
        colAddress.setCellValueFactory(c -> {
            if (c.getValue() != null) {
                return new SimpleStringProperty(c.getValue().getAddressX() + "," + c.getValue().getAddressY());
            } else {
                return new SimpleStringProperty("<no address>");
            }
        });

        //adding an event listener for double clicks
        table2.setRowFactory(tv -> {
            TableRow<CinemaModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    CinemaModel rowData = row.getItem();
                    System.out.println(rowData.getCinemaName());
                }
            });
            return row;
        });


        table2.setItems(CinemaModels);

    }

    private void showAlertWithHeaderText(String errorMsg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Error");
        alert.setHeaderText("Results:");
        alert.setContentText(errorMsg);
        alert.showAndWait();
    }

}
