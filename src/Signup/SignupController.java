package Signup;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import Hotel.Hotel;

/**
 * FXML Controller class
 *
 * @author Mohamed
 */
public class SignupController extends Hotel implements Initializable {

    @FXML
    private TextField user;
    @FXML
    private PasswordField pass1;
    @FXML
    private PasswordField pass2;
    
    
    Connection con;
    Alert ar;

    @Override
    public void initialize(URL url, ResourceBundle rb) { 

    }

    @FXML
    private void add(ActionEvent event) {
        String a = user.getText();
        String b = pass1.getText();
        String c = pass2.getText();

        try {
            if (a.equals("") || b.equals("") || c.equals("")) {
                throw new emptyException("Error");
            }
            con = connectDataBase();
            if (c.equals(b)) {
                PreparedStatement insert = con.prepareStatement("INSERT INTO Receptionist VALUES ('" + a + "', '" + b + "')");
                insert.executeUpdate();
                ar = new Alert(AlertType.INFORMATION);
                ar.setContentText("You have registered successfully");
                ar.setTitle("Congratulations");
                ar.show();
            } else {
                ar = new Alert(AlertType.INFORMATION);
                ar.setContentText("Passwords aren't matched!");
                ar.setTitle("Hint");
                ar.show();
            }

        } catch (emptyException ex) {
            ar = new Alert(AlertType.ERROR);
            ar.setContentText("Cannot add empty usernames or passwords!");
            ar.setTitle("Hint");
            ar.show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

class emptyException extends Exception {

    public emptyException(String msg) {

    }
}
