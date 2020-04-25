package CheckOut;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
public class CheckoutController extends Hotel implements Initializable {

    @FXML
    private ComboBox guest;
    @FXML
    private TextField Taxes;

    String personName;
    String f;
    String temp;
    String i;
    int i2;
    int price_from_room_class[];
    @FXML
    private TextField fees;
    @FXML
    private TextField room;
    @FXML
    private TextField total;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Taxes.setText("14%"); //Taxes are fixed
        Taxes.setEditable(false);
        ResultSet rec;
        try {
            guest.getItems().removeAll(guest.getItems());
            Connection con = connectDataBase();
            Statement st = con.createStatement();
            rec = st.executeQuery("SELECT FIRST_NAME FROM Guest ORDER BY FIRST_NAME");

            while (rec.next()) {
                String e = rec.getString("FIRST_NAME");
                guest.getItems().add(e);
            }
        } catch (Exception ex) {

        }
        guest.getSelectionModel().select(null);

    }

    @FXML
    private void guestBox(ActionEvent event) {
        personName = guest.getSelectionModel().getSelectedItem().toString();
        roomText(event); // On choosing person name, His data will appear in the textfields and It will be uneditable
        feesText(event);
        totalText(event); 
        fees.setEditable(false);
        total.setEditable(false);
        room.setEditable(false);
    }

    @FXML
    private void feesText(ActionEvent event) {
        price_from_room_class = new int[]{300, 350, 400};
        // Room A price 300, B: 350, C: 400
        ResultSet rec;
        try {
            Statement st2 = con.createStatement();
            rec = st2.executeQuery("SELECT STAY FROM Guest WHERE FIRST_NAME='" + personName + "'");
            rec.next();
            i = rec.getString("STAY");
            int stay = Integer.parseInt(i);
            int totalToPay = price_from_room_class[f.charAt(0) - 'A'] * stay;
            temp = Integer.toString(totalToPay);
            fees.setText(temp);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void roomText(ActionEvent event) {
        ResultSet rec;
        try {
            Statement st2 = con.createStatement();
            rec = st2.executeQuery("SELECT ROOM_NUMBER FROM Guest WHERE FIRST_NAME='" + personName + "'");
            rec.next();
            f = rec.getString("ROOM_NUMBER");
            room.setText(f);
        } 
        catch (Exception ex) {
            System.out.println(ex);
        }

    }

    @FXML
    private void totalText(ActionEvent event) {
        double i3 = Integer.parseInt(temp);
        i3 = i3 * 1.4;
        String s = Double.toString(i3);
        total.setText(s);
    }

    @FXML
    private void checkoutbtn(ActionEvent event) {

        try {
            ResultSet rec;
            Statement st2 = con.createStatement();
            PreparedStatement delete, insert;
            rec = st2.executeQuery("SELECT ROOM_NUMBER FROM Guest WHERE FIRST_NAME='" + personName + "'");
            rec.next();
            String s = rec.getString("ROOM_NUMBER");

            delete = con.prepareStatement("DELETE FROM Guest WHERE FIRST_NAME='" + personName + "'");
            insert = con.prepareStatement("INSERT INTO ROOMS VALUES('" + s + "')");
            delete.executeUpdate();
            insert.executeUpdate();
            Alert a = new Alert(AlertType.INFORMATION);
            a.setHeaderText("Good bye!");
            a.setContentText("Thank you for visiting our hotel");
            a.show();

            Parent root2 = FXMLLoader.load(getClass().getResource("checkout.fxml"));
            Scene scene2 = new Scene(root2);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene2);
            window.show();
        } catch (Exception ex) {
            Alert ar = new Alert(Alert.AlertType.ERROR);
            ar.setContentText("Deletion error");
            ar.setTitle("Error");
            ar.show();
        }
    }
}
