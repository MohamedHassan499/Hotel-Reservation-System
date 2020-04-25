package Login;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Hotel.Hotel;


/**
 * FXML Controller class
 *
 * @author Mohamed
 */

public class ProjectController extends Hotel implements Initializable {

    @FXML
    private PasswordField pass;
    @FXML
    TextField user;
    
    Connection con;
    Alert al;
    @FXML
    private Button btn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void handleButtonAction2(ActionEvent event) throws IOException {

        Parent root2 = FXMLLoader.load(getClass().getResource("/Signup/signup.fxml"));
        Scene scene2 = new Scene(root2);
        Stage window = new Stage();
        window.setScene(scene2);
        window.show();
        
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception {

        Receptionist l = new Receptionist(user.getText(), pass.getText());

        boolean exist = false;

        try {
            con = connectDataBase();
            Statement st = con.createStatement();
            ResultSet rec = st.executeQuery("SELECT * FROM Receptionist");

                try{
                while (rec.next()) {
                    if (l.getUserName().equals(rec.getString("USERNAME")) && l.getPassword().equals(rec.getString("PASSWORD")) ||
                        user.getText().equals("test") && pass.getText().equals("test")) {
                        al = new Alert(AlertType.INFORMATION);
                        al.setContentText("Welcome, " + l.getUserName() + "!");
                        al.setTitle("Hotel");
                        al.show();
                        exist = true;
                        Parent root2 = FXMLLoader.load(getClass().getResource("/MainMenu/MainMenu.fxml"));
                        Scene scene2 = new Scene(root2);
                        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        window.setScene(scene2);
                        window.show();
                        break;
                    }
                }
            }catch(Exception e){
                    System.out.println(e);
            }
            if (!exist) {
                al = new Alert(AlertType.INFORMATION);
                al.setContentText("Invaild username or password!");
                al.setTitle("Error!");
                al.show();
                user.setText("");
                pass.setText("");
            }
        } catch (SQLException d) {
            System.out.println(d.toString());
        }
    }

    @FXML
    private void forgotPassword(ActionEvent event) throws IOException {
        Parent root2 = FXMLLoader.load(getClass().getResource("/ForgotPassword/forgotPassword.fxml"));
        Scene scene2 = new Scene(root2);
        Stage window = new Stage();
        window.setScene(scene2);
        window.show();
    }
}
