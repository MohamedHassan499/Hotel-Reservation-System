package ForgotPassword;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import Hotel.Hotel;

/**
 * FXML Controller class
 *
 * @author Mohamed
 */
public class ForgotPasswordController extends Hotel implements Initializable {

    String a;

    @FXML
    private ComboBox select;
    @FXML
    private PasswordField password1;
    @FXML
    private PasswordField password2;

    Alert ar;
    Connection con;
    @FXML
    private Button update;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            con = connectDataBase();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        ResultSet rec;
        try {
            select.getItems().removeAll(select.getItems());
            con = connectDataBase();
            Statement st = con.createStatement();
            rec = st.executeQuery("SELECT USERNAME FROM Receptionist ORDER BY USERNAME");

            while (rec.next()) {
                String e = rec.getString("USERNAME");
                select.getItems().add(e);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        select.getSelectionModel().select(null);
    }

    @FXML
    private void updateBtn(ActionEvent event) {
        String b = password1.getText();
        String c = password2.getText();

        try {
            if (b.equals("") || c.equals("") || a == null) {
                throw new emptyException2("Error");
            }
            if (c.equals(b)) {
                PreparedStatement insert = con.prepareStatement("UPDATE Receptionist SET PASSWORD='" + c + "' WHERE USERNAME='" + a + "'");
                insert.executeUpdate();
                ar = new Alert(AlertType.INFORMATION);
                ar.setContentText("You have updated password successfully");
                ar.setTitle("Congratulations");
                ar.show();
                password1.setText("");
                password2.setText("");
            } else {
                ar = new Alert(AlertType.INFORMATION);
                ar.setContentText("Passwords aren't matched!");
                ar.setTitle("Hint");
                ar.show();
            }
        } catch (emptyException2 ex) {
            ar = new Alert(AlertType.ERROR);
            ar.setContentText("Cannot edit empty usernames or passwords!");
            ar.setTitle("Hint");
            ar.show();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void userBox(ActionEvent event) {
        a = select.getSelectionModel().getSelectedItem().toString();
    }

}

class emptyException2 extends Exception {

    public emptyException2(String msg) {

    }
}
