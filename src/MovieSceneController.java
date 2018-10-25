import com.jfoenix.controls.JFXButton
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Scene
import javafx.scene.control.TableColumn
import javafx.scene.control.TableRow
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import javafx.stage.Stage

import java.net.URL
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.ResourceBundle

class MovieSceneController : Initializable {

    @FXML
    private val btnHome: JFXButton? = null


    internal var movieModels = FXCollections.observableArrayList<MovieModel>()

    @FXML
    private val table: TableView<MovieModel>? = null

    @FXML
    private val colID: TableColumn<MovieModel, String>? = null

    @FXML
    private val colTitle: TableColumn<MovieModel, String>? = null

    @FXML
    private val colRating: TableColumn<MovieModel, String>? = null


    @FXML
    internal fun btnHomeClicked(event: ActionEvent) {
        try {
            val loader = FXMLLoader(javaClass.getResource("MovieApp.fxml"))
            val stage = btnHome!!.scene.window as Stage

            val scene = Scene(loader.load())
            stage.scene = scene


        } catch (e: Exception) {

        }

    }


    override fun initialize(location: URL, resources: ResourceBundle) {
        try {
            val conn = ConnectionFactory.getConnection()

            // the mysql insert statement
            val query = " SELECT * FROM movies"

            // create the mysql insert preparedstatement
            val preparedStmt = conn.prepareStatement(query)
            // execute the preparedstatement
            val rs = preparedStmt.executeQuery()
            movieModels.clear()
            while (rs.next()) {

                val movie = MovieModel()

                movie.title = rs.getString("Title")
                movie.movieID = rs.getString("MovieID")
                movie.rating = rs.getString("Rating")


                movieModels.add(movie)
            }

            conn.close()

        } catch (e: Exception) {
            System.err.println("Got an exception!")
            System.err.println(e.message)
        }

        colID!!.setCellValueFactory(PropertyValueFactory("MovieID"))
        colTitle!!.setCellValueFactory(PropertyValueFactory("title"))
        colRating!!.setCellValueFactory(PropertyValueFactory("Rating"))

        //adding an event listener for double clicks
        table!!.setRowFactory { tv ->
            val row = TableRow<MovieModel>()
            row.setOnMouseClicked { event ->
                if (event.clickCount == 2 && !row.isEmpty) {
                    val rowData = row.item
                    println(rowData.title)
                }
            }
            row
        }

        table.setItems(movieModels)


    }


}
