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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class AdminAppointmentViewer implements Initializable {

    @FXML private TableView<TableAdminAppointment> tableAdmin;
    @FXML private TableColumn<TableAdminAppointment,String> col_num;
    @FXML private TableColumn<TableAdminAppointment,String> col_patient;
    @FXML private TableColumn<TableAdminAppointment, LocalDate> col_date;
    @FXML private TableColumn<TableAdminAppointment, LocalTime> col_time;


    @FXML private TextField numBox;
    private String ID;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @FXML
    void BackHyperlinkPressed(ActionEvent event){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("AfterLoginAdmin.fxml"));
            Scene scene = new Scene(parent);

            //This line gets stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(scene);
            window.show();
        }catch (IOException e){
            System.out.println("Error"+ e);
        }
    }
    @FXML void DeleteButtonPressed(ActionEvent event){
        event.getSource();
        try {



            //from admintimeeditordatabase
            String query = " DELETE FROM `appointmentlist` WHERE `appointmentlist`.`Num` = "+getID();
            System.out.println(getID());
            Connection connection = ConnectionManage.getConnection();
            Statement st = connection.createStatement();
            st.executeUpdate(query);
            JOptionPane.showMessageDialog(null,"Success delete appointment","Success",JOptionPane.PLAIN_MESSAGE);

        }catch (SQLException e){
            System.out.println(e);
        }
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("AdminAppointmentViewer.fxml"));
            Scene scene = new Scene(parent);

            //This line gets stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(scene);
            window.show();
        }catch (IOException e){
            System.out.println("Error"+ e);
        }
    }

    ObservableList<TableAdminAppointment> list = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
      //listener
        numBox.textProperty().addListener((observable,oldValue,newValue) ->{
            System.out.println(newValue+"old"+oldValue);
            setID(newValue);
        } );
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String DBurl ="jdbc:mysql://localhost:3307/userregistration?serverTimezone=UTC";
            String userName = "root";
            String password = "";

            //from admintimeeditordatabase
            String query = "SELECT * FROM appointmentlist ";

            Connection connection = DriverManager.getConnection(DBurl,userName,password);
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            //from userprofile

            while (rs.next()){
                String num = rs.getString("num");
                String id = rs.getString("ID");
                Date date = rs.getDate("Date");
                LocalDate localDate = date.toLocalDate();
                String time = rs.getString("time");
                LocalTime localTime = LocalTime.parse(time);
                list.add(new TableAdminAppointment(num,id,localDate,localTime));
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error connection "+ e);
        }



    col_num.setCellValueFactory(new PropertyValueFactory<>("num"));
    col_patient.setCellValueFactory(new PropertyValueFactory<>("patient_id"));
    col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
    col_time.setCellValueFactory(new PropertyValueFactory<>("time"));

    tableAdmin.setItems(list);


    }
}
