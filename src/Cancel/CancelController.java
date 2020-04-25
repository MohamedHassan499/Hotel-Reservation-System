package Cancel;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Hotel.Hotel;

/**
 * FXML Controller class
 *
 * @author Mohamed
 */
public class CancelController extends Hotel implements Initializable {

    @FXML
    private TextField room;
    @FXML
    private ComboBox guest;
    String c, personName;
    Connection con;
    Alert ar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            ResultSet rec;
            con = connectDataBase();
            Statement st = con.createStatement();
            rec = st.executeQuery("SELECT FIRST_NAME FROM Reserving_Guest ORDER BY FIRST_NAME");

            while (rec.next()) {
                String e = rec.getString("FIRST_NAME");
                guest.getItems().add(e);
            }
        } catch (Exception ex) {

        }
    }

    @FXML
    private void guestCombo(ActionEvent event) {
        personName = guest.getSelectionModel().getSelectedItem().toString();
        cancelText(event);
    }

    @FXML
    private void cancelText(ActionEvent event) {

        ResultSet rec;

        try {
            Statement st2 = con.createStatement();
            rec = st2.executeQuery("SELECT ROOM_NUMBER FROM Reserving_Guest WHERE FIRST_NAME='" + personName + "'");
            rec.next();
            c = rec.getString("ROOM_NUMBER");
            room.setText(c);
        } catch (Exception ex) {

        }
    }

    @FXML
    private void cancelBtn(ActionEvent event) throws SQLException {
        PreparedStatement delete;
        ResultSet rec;
        try {
            if (personName == null) {
                throw new emptyUserException("Error");
            }
            delete = con.prepareStatement("DELETE FROM Reserving_Guest WHERE FIRST_NAME='" + personName + "'");
            delete.executeUpdate();

            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("Good bye!");
            a.setContentText("Reservation has been cancelled successfully");
            a.show();

            Parent root2 = FXMLLoader.load(getClass().getResource("cancel.fxml"));
            Scene scene2 = new Scene(root2);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene2);
            window.show();

        } catch (emptyUserException e) {
            ar = new Alert(AlertType.ERROR);
            ar.setTitle("Error");
            ar.show();
        } catch (Exception ex) {
            ar = new Alert(Alert.AlertType.ERROR);
            ar.setContentText("Deletion error");
            ar.setTitle("Error");
            ar.show();
        }
    }

}

class emptyUserException extends Exception {

    public emptyUserException(String msg) {
    }
}
