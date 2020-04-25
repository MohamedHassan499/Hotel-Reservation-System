package MainMenu;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import Login.*;
/**
 * FXML Controller class
 *
 * @author Mohamed
 */
public class MainMenuController extends ProjectController implements Initializable {

    @FXML
    private Hyperlink checkin;
    @FXML
    private Text welcome;
    
            private ObservableList<ObservableList> data;
    private TableView tableview;

    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
       public void buildData(){
          Connection c ;
          data = FXCollections.observableArrayList();
          try{
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project", "root", "");
            //SQL FOR SELECTING ALL OF CUSTOMER
            String SQL = "SELECT * FROM Guest ORDER BY FIRST_NAME";
            //ResultSet
            ResultSet rs = c.createStatement().executeQuery(SQL);

            /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             **********************************/
            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                //We are using non property style for making dynamic table
                final int j = i;                
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {                                                                                              
                        return new SimpleStringProperty(param.getValue().get(j).toString());                        
                    }                    
                });

                tableview.getColumns().addAll(col); 
            }

            /********************************
             * Data added to ObservableList *
             ********************************/
            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                data.add(row);
            }

            //FINALLY ADDED TO TableView
            tableview.setItems(data);
          }catch(Exception e){
              e.printStackTrace();
          }
      }
       
       
       public void buildData2(){
          Connection c ;
          data = FXCollections.observableArrayList();
          try{
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project", "root", "");
            //SQL FOR SELECTING ALL OF CUSTOMER
            String SQL = "SELECT * from ROOMS ORDER BY ROOM_NO";
            //ResultSet
            ResultSet rs = c.createStatement().executeQuery(SQL);

            /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             **********************************/
            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                //We are using non property style for making dynamic table
                final int j = i;                
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {                                                                                              
                        return new SimpleStringProperty(param.getValue().get(j).toString());                        
                    }                    
                });

                tableview.getColumns().addAll(col); 
            }

            /********************************
             * Data added to ObservableList *
             ********************************/
            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                data.add(row);
            }

            //FINALLY ADDED TO TableView
            tableview.setItems(data);
          }catch(Exception e){
              System.out.println("Error on Building Data");             
          }
      }


    

    @FXML
    private void checkinbtn(ActionEvent event) throws IOException {
        
                 Parent root2 = FXMLLoader.load(getClass().getResource("/Checkin/check.fxml"));
                 Scene scene2= new Scene(root2);           
                 Stage window= new Stage();
                 window.setScene(scene2);
                 window.show();
               
    }

    private void checkoutbtn(ActionEvent event) throws IOException {
                 Parent root2 = FXMLLoader.load(getClass().getResource("/CheckOut/chechout.fxml"));
                 Scene scene2= new Scene(root2);           
                 Stage window= new Stage();
                 window.setScene(scene2);
                 window.show();
               
    
    }

    @FXML
    private void checkout(ActionEvent event) throws IOException {
        
          Parent root2 = FXMLLoader.load(getClass().getResource("/CheckOut/checkout.fxml"));
                 Scene scene2= new Scene(root2);           
                 Stage window= new Stage();
                 window.setScene(scene2);
                 window.show();
               
    }

    @FXML
    private void logoutbtn(ActionEvent event) throws IOException {
          Parent root2 = FXMLLoader.load(getClass().getResource("/Login/loginForm.fxml"));
                 Scene scene2= new Scene(root2);           
                 Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
                 window.setScene(scene2);
                 window.show();
    }

    @FXML
    private void guestBtn(ActionEvent event) throws IOException {
                 Parent root2 = FXMLLoader.load(getClass().getResource("/Cancel/cancel.fxml"));
                 Scene scene2= new Scene(root2);           
                 Stage window= new Stage();
                 window.setScene(scene2);
                 window.show();
    }

    @FXML
    private void roomBookingBtn(ActionEvent event) throws IOException {
                 Parent root2 = FXMLLoader.load(getClass().getResource("/Reserve/reserving.fxml"));
                 Scene scene2= new Scene(root2);           
                 Stage window= new Stage();
                 window.setScene(scene2);
                 window.show();
    }

    @FXML
    private void guestData(ActionEvent event) {
        tableview = new TableView();
        buildData();

        //Main Scene
        Scene scene = new Scene(tableview);        
        Stage stage= new Stage();
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void roomsData(ActionEvent event) {
                tableview = new TableView();
        buildData2();

        //Main Scene
        Scene scene = new Scene(tableview);        
        Stage stage= new Stage();
        stage.setScene(scene);
        stage.show();

    }
    
}
