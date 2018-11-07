import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MovieDetailsController {

    @FXML
    private static String name;
    @FXML
    private JFXButton btnHome;

    @FXML

    public static void setname(String k){
        name = k;
    }

    @FXML
    private Button sample;


    @FXML
    void sample_clicked(ActionEvent event) {
        System.out.println(name);

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


