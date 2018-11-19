import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;



public class MovieApp extends Application {

final String pw = "admin";


    @FXML
    private JFXButton btnMovies;

    @FXML
    private JFXButton btnCinemas;

    @FXML
    private JFXButton btnLogin;

    @FXML
    void btnCinemasClicked(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CinemaScene.fxml"));
            Stage stage = (Stage) btnCinemas.getScene().getWindow();

            Scene scene = new Scene(loader.load());
            stage.setScene(scene);


        }catch (Exception e){
            System.err.print(e.getMessage());
        }
    }

    @FXML
    void btnMoviesClicked(ActionEvent event) {
        try{
           FXMLLoader loader = new FXMLLoader(getClass().getResource("MovieScene.fxml"));
           Stage stage = (Stage) btnMovies.getScene().getWindow();

            Scene scene = new Scene(loader.load());
            stage.setScene(scene);


       }catch (Exception e){
            System.err.print(e.getMessage());
       }
    }
    @FXML
    void btnLoginClicked(ActionEvent event) throws IOException {
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setTitle("Admin Login");
        inputDialog.setHeaderText("Enter the password");
        Optional<String> result = inputDialog.showAndWait();
        if(result.isPresent())
        {
            if (result.get().equals(pw))
            {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminScene.fxml"));
                Stage stage = (Stage) btnLogin.getScene().getWindow();

                Scene scene = new Scene(loader.load());
                stage.setScene(scene);

            } else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Error");
                alert.setHeaderText("Login failed!");
                alert.setContentText("WRONG PASSWORD");

                alert.showAndWait();
            }
        }



    }
    @Override
    public void start(Stage primaryStage)
    {
        Parent root = null;
        try
        {
            root = FXMLLoader.load(getClass().getResource("MovieApp.fxml"));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        if(root != null)
        {
            primaryStage.setTitle("Project 4 by Harshad Kapoor, Tanvir Hossain, and Yassine Raouz");
            primaryStage.setScene(new Scene(root, 740, 500));
            primaryStage.setResizable(true);
            // primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.show();
        }
    }

    public static void main(String[] args)
    {
        launch(args);
    }


}
