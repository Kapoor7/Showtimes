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

        }

         @FXML
        void btnAddCClicked(ActionEvent event) {}

        @FXML
        void btnAddTimeClicked(ActionEvent event) {}


        @FXML
        void btnDeleteMClicked(ActionEvent event) {}

        @FXML
        void btnDeleteCClicked(ActionEvent event) {}


        @FXML
        void btnRemoveTimeClicked(ActionEvent event) {}


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
