package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class PatientViewAppointment implements Initializable {
    @FXML private TableView<TablePatientAppointment> tablePatient;
    @FXML private TableColumn<TablePatientAppointment, LocalDate> col_date;
    @FXML private TableColumn<TablePatientAppointment, LocalTime> col_time;

    @FXML private Label AppointmentHiLabel;
    private String userID;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @FXML void BackHyperlinkPressed(ActionEvent event){
        try {
            Parent changeViewStaffParent = FXMLLoader.load(getClass().getResource("LoginPatient.fxml"));
            Scene changeViewStaffScene = new Scene(changeViewStaffParent);

            //This line gets stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(changeViewStaffScene);
            window.show();
        }catch (IOException e){
            System.out.println("Error"+ e);
        }
    }

    @FXML
    void AppointmentButtonPressed(ActionEvent event){
        try {
            Parent changeViewStaffParent = FXMLLoader.load(getClass().getResource("RealPatientAppointment.fxml"));
            Scene changeViewStaffScene = new Scene(changeViewStaffParent);

            //This line gets stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(changeViewStaffScene);
            window.show();
        }catch (IOException e){
            System.out.println("Error"+ e);
        }
    }


    ObservableList<TablePatientAppointment> list = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String name,userID;
        try {


            //from admintimeeditordatabase
            String query = "SELECT * FROM userprofile where email = "+ "'" + email.getEmailLogin()+"'";

            Connection connection = ConnectionManage.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            //from userprofile

            while (rs.next()){
                name = rs.getString("name");
                AppointmentHiLabel.setText("Hi,"+name);
                userID = rs.getString("ID");
                setUserID(userID);
            }

            query = "SELECT * FROM appointmentlist where ID = "+ "'" +getUserID()+"'";
            rs = st.executeQuery(query);
            while (rs.next()){
                Date date = rs.getDate("Date");
                LocalDate localDate = date.toLocalDate();
                String time = rs.getString("time");
                LocalTime localTime = LocalTime.parse(time);
                list.add(new TablePatientAppointment(localTime,localDate));
            }


            col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
            col_time.setCellValueFactory(new PropertyValueFactory<>("time"));

            tablePatient.setItems(list);

        } catch (SQLException e) {
            System.out.println("Error connection "+ e);
        }


    }
}
