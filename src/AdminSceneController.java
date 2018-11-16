import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminSceneController implements Initializable {



        @FXML
        private JFXButton addM;

        @FXML
        private JFXButton AddC;

        @FXML
        private JFXButton AddTime;

        @FXML
        private JFXButton DeleteM;

        @FXML
        private JFXButton DeleteC;

        @FXML
        private JFXButton RemoveTime;

        @FXML
        private JFXButton btnHome;


        @FXML
        void btnaddMClicked (ActionEvent event){
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AddMovie.fxml"));
                Stage stage = (Stage) addM.getScene().getWindow();

                Scene scene = new Scene(loader.load());
                AddDeleteController s = loader.getController();
                s.initAddMovie();
                stage.setScene(scene);


            }catch (Exception e){

            }

        }

         @FXML
        void btnAddCClicked(ActionEvent event) {

             try{
                 FXMLLoader loader = new FXMLLoader(getClass().getResource("AddCinema.fxml"));
                 Stage stage = (Stage) btnHome.getScene().getWindow();

                 Scene scene = new Scene(loader.load());

                 stage.setScene(scene);


             }catch (Exception e){

             }

         }

        @FXML
        void btnAddTimeClicked(ActionEvent event) {
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AddTime.fxml"));
                Stage stage = (Stage) btnHome.getScene().getWindow();

                Scene scene = new Scene(loader.load());
                AddDeleteController T = loader.getController();
                T.initTime();

                stage.setScene(scene);


            }catch (Exception e){

            }
        }


        @FXML
        void btnDeleteMClicked(ActionEvent event) {
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("DeleteMovie.fxml"));
                Stage stage = (Stage) DeleteM.getScene().getWindow();

                Scene scene = new Scene(loader.load());
                AddDeleteController s = loader.getController();
                s.initDeleteMovie();
                stage.setScene(scene);


            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @FXML
        void btnDeleteCClicked(ActionEvent event) {

            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("DeleteCinema.fxml"));
                Stage stage = (Stage) DeleteC.getScene().getWindow();

                Scene scene = new Scene(loader.load());
                AddDeleteController s = loader.getController();
                s.initDeleteCinema();
                stage.setScene(scene);


            }catch (Exception e){
                e.printStackTrace();
            }

        }


        @FXML
        void btnRemoveTimeClicked(ActionEvent event) {
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("DeleteShowtime.fxml"));
                Stage stage = (Stage) RemoveTime.getScene().getWindow();

                Scene scene = new Scene(loader.load());
                AddDeleteController s = loader.getController();
                s.initDeleteShowtime();
                stage.setScene(scene);


            }catch (Exception e){
                e.printStackTrace();
            }

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
    public void initialize(URL location, ResourceBundle resources) {
        btnHome.setText("Logout");
    }
}
