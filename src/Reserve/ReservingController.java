package Reserve;

import java.net.URL;
import java.util.regex.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import Hotel.Hotel;

/**
 * FXML Controller class
 *
 * @author Mohamed
 */
public class ReservingController extends Hotel implements Initializable {
    
    String genderType;
    String nationality;
    String roomType;
    String date;
    String first, lastName, ageNumber, streetAddress, ID, feeType;
    String phoneNumber, muchToStay, w, creditNumber;
    Matcher matcher;
    Pattern pattern;
    
    Connection con;
    Alert ar;
    
    @FXML
    private RadioButton male;
    @FXML
    private RadioButton female;
    @FXML
    private ComboBox combo;

    @FXML
    private RadioButton cash;
    @FXML
    private TextField creditText;
    @FXML
    ToggleGroup wifi;
    @FXML
    private RadioButton yes;
    @FXML
    private RadioButton no;

    @FXML
    private TextField id;
    @FXML
    private TextField phno;
    @FXML
    private TextField sur;
    @FXML
    private TextField age;
    @FXML
    private TextField address;
    @FXML
    private TextField firstName;
    @FXML
    private ComboBox roomNo;
    @FXML
    private DatePicker much;
    @FXML
    private TextField much1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        combo.getItems().removeAll(combo.getItems());
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
            w = "Yes";
        } else if (no.isSelected()) {
            w = "No";
        }
    }

    @FXML
    private void reserveRoom(ActionEvent event) {
        roomType = roomNo.getSelectionModel().getSelectedItem().toString();
    }

    @FXML
    private void reservebtn(ActionEvent event) throws Exception {

        first = firstName.getText();
        lastName = sur.getText();
        ageNumber = age.getText();
        streetAddress = address.getText();
        ID = id.getText();
        feeType = creditText.getText();
        phoneNumber = phno.getText();
        muchToStay = much1.getText();
        
        Date res;
        Calendar cal = Calendar.getInstance();
        res = cal.getTime();
        String rs = res.toString();
        try {
            con = connectDataBase();
            
            pattern = pattern.compile("[A-Z][a-zA-Z][^#&<>\\\"~;$^%{}?]{1,20}$");
            matcher = pattern.matcher(first);
            if(!matcher.matches()){
                throw new firstName2Exception("Error");
            }
            
            matcher = pattern.matcher(lastName);
            
            if (!matcher.matches()) {
                throw new surName2Exception("Error");
            }
            
            if(ageNumber.isEmpty()){
                throw new age2Exception("Error");
            }
            int convertAge = Integer.parseInt(ageNumber);
            if(!(convertAge >= 10 && convertAge <= 150)){
                throw new age2Exception("Error");
            }

            if (nationality == null) {
                throw new nationality2Exception("Error");
            }

            pattern = pattern.compile("\\d{1,5}\\s\\w.\\s(\\b\\w*\\b\\s){1,2}\\w*\\.");
            matcher = pattern.matcher(streetAddress);
            
            if(matcher.matches() || streetAddress.isEmpty()){
                throw new address2Exception("Error1");
            }
            
            
            pattern = pattern.compile("(?i)^(?=.*[a-z])[a-z0-9]{8,20}$");
            matcher = pattern.matcher(ID);
            
            if(matcher.matches() || ID.isEmpty()){
                throw new ID2Exception("Error4");
            }
            
            
            if (genderType == null) {
                throw new gender2Exception("Error");
            }
            
            
            pattern = pattern.compile("^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$");
            matcher = pattern.matcher(phoneNumber);
            
            if(!matcher.matches() || phoneNumber.isEmpty() ){
                throw new phone2Exception("Error");
            }
            
            if(much == null){
                throw new much2Exception("Error");
            }
            
            if(muchToStay.isEmpty() || muchToStay.isEmpty()){
                throw new reserve2Exception("Error");
            }
            int convertDate = Integer.parseInt(muchToStay);
            if(!(muchToStay != null && convertDate >= 1)){
                
                throw new reserve2Exception("Error");
                
            }

            if (roomType == null) {
                throw new roomNumber2Exception("Error");
            }
            if (w == null) {
                throw new WIFI2Exception("Error");
            }
            if (feeType == null) {
                throw new Fees2Exception("Error");
            }

            if (feeType.equals("Credit")) {
                
                pattern = pattern.compile("^(\\d{4}-){3}\\d{4}$|^(\\d{4} ){3}\\d{4}$|^\\d{16}");
                matcher = pattern.matcher(creditNumber);
                if(!matcher.matches() || creditNumber.isEmpty()){
                    throw new credit2Exception("Error");
                }
            }

            PreparedStatement insert = con.prepareStatement("INSERT INTO Reserving_Guest VALUES ('" + first + "', '" + lastName + "', '" + ageNumber + "', '" + nationality + "', '" + streetAddress + "', '" + ID + "', "
                    + "'" + genderType + "', '" + phoneNumber + "', '" + much.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "', '" + rs + "', '" + muchToStay + "', '" + roomType + "', '" + w + "', '" + feeType + "', '" + creditNumber + "')");
            insert.executeUpdate();
            ar = new Alert(AlertType.INFORMATION);
            switch (genderType) {
                case "Male":
                    ar.setContentText("We're waiting for you Mr, " + first + "!");
                    ar.setTitle("Welcome");
                    ar.show();
                    break;
                case "Female":
                    ar.setContentText("We're waiting for you Ms, " + first + "!");
                    ar.setTitle("Welcome");
                    ar.show();
                    break;
            }
            Parent root2 = FXMLLoader.load(getClass().getResource("reserving.fxml"));
            Scene scene2 = new Scene(root2);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene2);
            window.show();

        } catch (firstName2Exception ex) {
             
            ar = new Alert(Alert.AlertType.ERROR);
            ar.setContentText("Invaild first name!");
            ar.setTitle("Error");
            ar.show();

        } catch (surName2Exception ex) {
             
            ar = new Alert(Alert.AlertType.ERROR);
            ar.setContentText("Invaild last name!");
            ar.setTitle("Error");
            ar.show();

        } catch (age2Exception ex) {
             
            ar = new Alert(Alert.AlertType.ERROR);
            ar.setContentText("Invaild age!");
            ar.setTitle("Error");
            ar.show();

        } catch (nationality2Exception ex) {
            ar = new Alert(Alert.AlertType.ERROR);
            ar.setContentText("Please choose nationality!");
            ar.setTitle("Error");
            ar.show();

        } catch (ID2Exception ex) {
             
            ar = new Alert(Alert.AlertType.ERROR);
            ar.setContentText("Invaild ID!");
            ar.setTitle("Error");
            ar.show();
            
        } catch (gender2Exception ex) {
             
            ar = new Alert(Alert.AlertType.ERROR);
            ar.setContentText("Please choose your gender!");
            ar.setTitle("Error");
            ar.show();
            
        } catch (phone2Exception ex) {
             
            ar = new Alert(Alert.AlertType.ERROR);
            ar.setContentText("Invaild phone number!");
            ar.setTitle("Error");
            ar.show();
            
        } catch (reserve2Exception ex) {
             
            ar = new Alert(Alert.AlertType.ERROR);
            ar.setContentText("Invaild reserve duration!");
            ar.setTitle("Error");
            ar.show();

        }
        
        catch (credit2Exception ex) {
             
            ar = new Alert(Alert.AlertType.ERROR);
            ar.setContentText("Invalid credit number!");
            ar.setTitle("Error");
            ar.show();
        } catch (roomNumber2Exception ex) {
            ar = new Alert(Alert.AlertType.ERROR);
            ar.setContentText("Please choose room!");
            ar.setTitle("Error");
            ar.show();

        } catch (address2Exception ex) {
            
            ar = new Alert(Alert.AlertType.ERROR);
            ar.setContentText("Invalid address!");
            ar.setTitle("Error");
            ar.show();
        } catch (WIFI2Exception ex) {
            ar = new Alert(Alert.AlertType.ERROR);
            ar.setContentText("Select WI-FI!");
            ar.setTitle("Error");
            ar.show();

        } catch (Fees2Exception ex) {
            ar = new Alert(Alert.AlertType.ERROR);
            ar.setContentText("Select Fee type!");
            ar.setTitle("Error");
            ar.show();

        } catch (Exception ex) {
             
            ar = new Alert(AlertType.ERROR);
            ar.setContentText("Select when reservation starts");
            ar.setTitle("Error");
            ar.show();
        }
    }
}

class age2Exception extends Exception {

    public age2Exception(String msg) {
    }
}

class phone2Exception extends Exception {

    public phone2Exception(String msg) {
    }
}

class ID2Exception extends Exception {

    public ID2Exception(String msg) {
    }
}

class reserve2Exception extends Exception {

    public reserve2Exception(String msg) {
    }
}

class credit2Exception extends Exception {

    public credit2Exception(String msg) {
    }
}

class firstName2Exception extends Exception {

    public firstName2Exception(String msg) {
    }
}

class surName2Exception extends Exception {

    public surName2Exception(String msg) {
    }
}

class address2Exception extends Exception {

    public address2Exception(String msg) {
    }
}

class nationality2Exception extends Exception {

    public nationality2Exception(String msg) {
    }
}

class roomNumber2Exception extends Exception {

    public roomNumber2Exception(String msg) {
    }
}

class gender2Exception extends Exception {

    public gender2Exception(String msg) {
    }
}

class much2Exception extends Exception{
    
    public much2Exception(String msg){
        
    }
    
}

class WIFI2Exception extends Exception {

    public WIFI2Exception(String msg) {
    }
}

class Fees2Exception extends Exception {

    public Fees2Exception(String msg) {
    }
}

class dateException extends Exception {

    public dateException(String msg) {

    }
}
