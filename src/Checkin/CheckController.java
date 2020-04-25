package Checkin;

import java.io.IOException;
import java.util.regex.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Hotel.Hotel;

/**
 * FXML Controller class
 *
 * @author Mohamed
 */
public class CheckController extends Hotel implements Initializable {

    @FXML
    private TextField id;
    @FXML
    private TextField phno;
    @FXML
    private RadioButton male;
    @FXML
    private RadioButton female;
    @FXML
    private RadioButton yes;
    @FXML
    private RadioButton no;
    @FXML
    private RadioButton cash;
    @FXML
    private TextField creditText;
    @FXML
    private TextField sur;
    @FXML
    private TextField age;
    @FXML
    private TextField address;
    @FXML
    private TextField first;
    @FXML
    private ComboBox combo;
    @FXML
    private ComboBox roomNo;
    @FXML
    private TextField much;
    String genderType, nationality, roomType, feeType;
    boolean cr;
    String firstName, lastName, ageNumber, streetAddress, ID;
    String phoneNumber, muchToStay, wifi, creditNumber;
    
    Matcher matcher;
    Pattern pattern;

    Connection con;
    Alert ar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        combo.getItems().addAll("Egypt", "England", "Japan", "KSA", "USA");
        combo.getSelectionModel().select(null);
        roomNo.getItems().removeAll(roomNo.getItems());
        ResultSet rec;
        try {
            con = connectDataBase();
            Statement st = con.createStatement();
            rec = st.executeQuery("SELECT ROOM_NO FROM ROOMS ORDER BY ROOM_NO");

            while (rec.next()) {
                String e = rec.getString("ROOM_NO");
                roomNo.getItems().add(e);
            }
        } catch (Exception ex) {
            
        }

        roomNo.getSelectionModel().select(null);
    }

    @FXML
    private void TogggleGender(ActionEvent event) {
        if (male.isSelected()) {
            genderType = "Male";
        } else if (female.isSelected()) {
            genderType = "Female";
        }

    }

    @FXML
    private void nationalityCombo(ActionEvent event) {
        nationality = combo.getSelectionModel().getSelectedItem().toString();
    }

    @FXML
    private void reserveRoom(ActionEvent event) {
        roomType = roomNo.getSelectionModel().getSelectedItem().toString();
    }

    @FXML
    private void fees(ActionEvent event) {
        boolean confirm = false;
        if (cash.isSelected()) {
            feeType = "Cash";
            creditText.setText("");
            creditText.setEditable(false);
        } else {
            feeType = "Credit";
            creditText.setText("");
            creditText.setEditable(true);
        }
    }

    @FXML
    private void ToggleWIFI(ActionEvent event) {
        if (yes.isSelected()) {
            wifi = "Yes";
        } else if (no.isSelected()) {
            wifi = "No";
        }
    }

    @FXML
    private void reservebtn(ActionEvent event) throws SQLException, surNameException, IOException {

        firstName = first.getText();
        lastName = sur.getText();
        ageNumber = age.getText();
        streetAddress = address.getText();
        ID = id.getText();
        phoneNumber = phno.getText();
        muchToStay = much.getText();
        creditNumber = creditText.getText();

        Date res;
        Calendar cal = Calendar.getInstance();
        res = cal.getTime();
        String s = res.toString();

        Guest g = new Guest(first.getText(), sur.getText(), id.getText(), phno.getText(),
                (age.getText()), genderType, wifi, feeType, roomType, creditText.getText(),
                address.getText(), nationality, (much.getText()));

        try {
            con = connectDataBase();
            
            pattern = pattern.compile("[A-Z][a-zA-Z][^#&<>\\\"~;$^%{}?]{1,20}$");
            matcher = pattern.matcher(firstName);
            if(!matcher.matches()){
                throw new firstNameException("Error");
            }
            
            matcher = pattern.matcher(lastName);
            
            if (!matcher.matches()) {
                throw new surNameException("Error");
            }
            
            if(ageNumber.isEmpty()){
                throw new ageException("Error");
            }
            int convertAge = Integer.parseInt(ageNumber);
            if(!(convertAge >= 10 && convertAge <= 150)){
                throw new ageException("Error");
            }

            if (nationality == null) {
                throw new nationalityException("Error");
            }

            pattern = pattern.compile("\\d{1,5}\\s\\w.\\s(\\b\\w*\\b\\s){1,2}\\w*\\.");
            matcher = pattern.matcher(streetAddress);
            
            if(matcher.matches() || streetAddress.isEmpty()){
                throw new addressException("Error1");
            }
            
            
            pattern = pattern.compile("(?i)^(?=.*[a-z])[a-z0-9]{8,20}$");
            matcher = pattern.matcher(ID);
            
            if(matcher.matches() || ID.isEmpty()){
                throw new IDException("Error4");
            }
            
            
            if (genderType == null) {
                throw new genderException("Error");
            }
            
            
            pattern = pattern.compile("^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$");
            matcher = pattern.matcher(phoneNumber);
            
            if(!matcher.matches() || phoneNumber.isEmpty() ){
                throw new phoneException("Error");
            }
            
            if(muchToStay.isEmpty() || muchToStay.isEmpty()){
                throw new reserveException("Error");
            }
            int convertDate = Integer.parseInt(muchToStay);
            if(!(muchToStay != null && convertDate >= 1)){
                
                throw new reserveException("Error");
                
            }
            
            if (roomType == null) {
                throw new roomNumberException("Error");
            }
            if (wifi == null) {
                throw new WIFIException("Error");
            }
            if (feeType == null) {
                throw new FeesException("Error");
            }

            if (feeType.equals("Credit")) {
                pattern = pattern.compile("^(\\d{4}-){3}\\d{4}$|^(\\d{4} ){3}\\d{4}$|^\\d{16}");
                matcher = pattern.matcher(creditNumber);
                if(!matcher.matches() || creditNumber.isEmpty()){
                    throw new creditException("Error");
                }
            }

            PreparedStatement insert = con.prepareStatement("INSERT INTO Guest VALUES ('" + g.getFirstName() + "', '" + g.getSurName() + "', '" + g.getAge() + "', '" + g.getNationality() + "', '" + g.getAddress() + "', '" + g.getID() + "',"
                    + "'" + g.getGender() + "', '" + g.getPhoneNo() + "', '" + res + "', '" + g.getReserveDate() + "', '" + g.getRoomNo() + "', '" + g.getWIFI() + "', '" + g.getFeeType() + "', '" + creditNumber + "')");

            insert.executeUpdate();

            ar = new Alert(Alert.AlertType.INFORMATION);
            switch (genderType) {
                case "Male":
                    ar.setContentText("Welcome Mr, " + firstName + "!");
                    ar.setTitle("Welcome");
                    ar.show();
                    break;
                case "Female":
                    ar.setContentText("Welcome Ms, " + firstName + "!");
                    ar.setTitle("Welcome");
                    ar.show();
                    break;
            }
            PreparedStatement delete;
            delete = con.prepareStatement("DELETE FROM ROOMS WHERE ROOM_NO='" + roomType + "' ");
            delete.executeUpdate();
            Parent root2 = FXMLLoader.load(getClass().getResource("check.fxml"));
            Scene scene2 = new Scene(root2);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene2);
            window.show();

        } catch (firstNameException ex) {
            ar = new Alert(Alert.AlertType.ERROR);
            ar.setContentText("Invaild first name!");
            ar.setTitle("Error");
            ar.show();

        } catch (surNameException ex) {
            ar = new Alert(Alert.AlertType.ERROR);
            ar.setContentText("Invaild last name!");
            ar.setTitle("Error");
            ar.show();

        } catch (ageException ex) {
            ar = new Alert(Alert.AlertType.ERROR);
            ar.setContentText("Invaild age!");
            ar.setTitle("Error");
            ar.show();

        } catch (nationalityException ex) {
            ar = new Alert(Alert.AlertType.ERROR);
            ar.setContentText("Please choose nationality!");
            ar.setTitle("Error");
            ar.show();

        } catch (IDException ex) {
            ar = new Alert(Alert.AlertType.ERROR);
            ar.setContentText("Invaild ID!");
            ar.setTitle("Error");
            ar.show();
        } catch (genderException ex) {
            ar = new Alert(Alert.AlertType.ERROR);
            ar.setContentText("Please choose your gender!");
            ar.setTitle("Error");
            ar.show();
        } catch (phoneException ex) {
            ar = new Alert(Alert.AlertType.ERROR);
            ar.setContentText("Invaild phone number!");
            ar.setTitle("Error");
            ar.show();
        } catch (reserveException ex) {
            ar = new Alert(Alert.AlertType.ERROR);
            ar.setContentText("Invaild reserve duration!");
            ar.setTitle("Error");
            ar.show();

        } catch (creditException ex) {

            ar = new Alert(Alert.AlertType.ERROR);
            ar.setContentText("Invalid credit number!");
            ar.setTitle("Error");
            ar.show();
        } catch (roomNumberException ex) {
            ar = new Alert(Alert.AlertType.ERROR);
            ar.setContentText("Please choose room!");
            ar.setTitle("Error");
            ar.show();

        } catch (addressException ex) {

            ar = new Alert(Alert.AlertType.ERROR);
            ar.setContentText("Invalid address!");
            ar.setTitle("Error");
            ar.show();
        } catch (WIFIException ex) {
            ar = new Alert(Alert.AlertType.ERROR);
            ar.setContentText("Select WI-FI!");
            ar.setTitle("Error");
            ar.show();

        } catch (FeesException ex) {
            ar = new Alert(Alert.AlertType.ERROR);
            ar.setContentText("Select Fee type!");
            ar.setTitle("Error");
            ar.show();

        }

    }

}

class ageException extends Exception {

    public ageException(String msg) {
    }
}

class phoneException extends Exception {

    public phoneException(String msg) {
    }
}

class IDException extends Exception {

    public IDException(String msg) {
    }
}

class reserveException extends Exception {

    public reserveException(String msg) {
    }
}

class creditException extends Exception {

    public creditException(String msg) {
    }
}

class firstNameException extends Exception {

    public firstNameException(String msg) {
    }
}

class surNameException extends Exception {

    public surNameException(String msg) {
    }
}

class addressException extends Exception {

    public addressException(String msg) {
    }
}

class nationalityException extends Exception {

    public nationalityException(String msg) {
    }
}

class roomNumberException extends Exception {

    public roomNumberException(String msg) {
    }
}

class genderException extends Exception {

    public genderException(String msg) {
    }
}

class WIFIException extends Exception {

    public WIFIException(String msg) {
    }
}

class FeesException extends Exception {

    public FeesException(String msg) {
    }
}
