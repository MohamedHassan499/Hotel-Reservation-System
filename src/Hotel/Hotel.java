package Hotel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Mohamed
 */
public class Hotel extends Application {

    public static final String USERNAME = "root";
    public static final String PASSWORD = "";

    public static final String CONN_STRING = "jdbc:mysql://localhost:3306/Project";

    Statement s;
    ResultSet r;
    public Connection con;

    @Override
    public void start(Stage stage) throws SQLException, ClassNotFoundException, IOException {


        Parent root = FXMLLoader.load(getClass().getResource("/Login/loginForm.fxml"));
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public Connection connectDataBase() throws SQLException {
        con = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
        return con;
    }
    
    public static void main(String[] args) throws SQLException {
        launch(args);
    }
}
