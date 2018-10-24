import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MovieApp extends Application {

    @FXML
    private JFXButton btnMovies;

    @FXML
    private JFXButton btnCinemas;

    @FXML
    void btnCinemasClicked(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CinemaScene.fxml"));
            Stage stage = (Stage) btnCinemas.getScene().getWindow();

            Scene scene = new Scene(loader.load());
            stage.setScene(scene);


        }catch (Exception e){

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
        primaryStage.setTitle("Project 4 by Harshad Kapoor and Tanvir Hossain");
        primaryStage.setScene(new Scene(root, 740, 500));
        primaryStage.setResizable(true);
       // primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }


}
