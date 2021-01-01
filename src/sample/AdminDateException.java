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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import static javax.swing.JOptionPane.WARNING_MESSAGE;

public class AdminDateException implements Initializable {
    @FXML private TableView<TableDateException> tableAdmin;
    @FXML private TableColumn<TableDateException, LocalDate> col_date;
    @FXML private TableColumn<TableDateException, String> col_num;
    @FXML private TextField numBox;
    @FXML private DatePicker dateExcept;

    private LocalDate exceptDate;
    public LocalDate getExceptDate() {
        return exceptDate;
    }
    public void setExceptDate(LocalDate exceptDate) {
        this.exceptDate = exceptDate;
    }

    private LocalDate startDate;
    private LocalDate endDate;
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    private String num;
    public String getNum() {
        return num;
    }
    public void setNum(String num) {
        this.num = num;
    }

    @FXML void DeleteButtonPressed(ActionEvent event){
        event.getSource();
        try {



            //from admintimeeditordatabase
            String query = " DELETE FROM `dateexception` WHERE `dateexception`.`Num` = "+getNum();
            System.out.println(getNum());
            Connection connection = ConnectionManage.getConnection();
            Statement st = connection.createStatement();
            st.executeUpdate(query);
            JOptionPane.showMessageDialog(null,"Success delete date exception","Success",JOptionPane.PLAIN_MESSAGE);

        }catch (SQLException e){
            System.out.println(e);
        }
        try {
            Parent changeViewStaffParent = FXMLLoader.load(getClass().getResource("AdminDateException.fxml"));
            Scene changeViewStaffScene = new Scene(changeViewStaffParent);

            //This line gets stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(changeViewStaffScene);
            window.show();
        }catch (IOException e){
            System.out.println("Error"+ e);
        }


    }
    @FXML void AddButtonPressed(ActionEvent event){
        event.getSource();
        try {

            String query = "INSERT INTO `dateexception` (`num`, `dateexception`) VALUES (null,?) ";

            Connection connection = ConnectionManage.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            Date date = Date.valueOf(getExceptDate());


            preparedStatement.setDate(1,date);


            preparedStatement.execute();
            connection.close();
            JOptionPane.showMessageDialog(null,"Success update","Success",JOptionPane.PLAIN_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Fail","Warning", WARNING_MESSAGE);
            System.out.println("Error connection" + e.getMessage());

        }
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("AdminDateException.fxml"));
            Scene scene = new Scene(parent);

            //This line gets stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(scene);
            window.show();
        }catch (IOException e){
            System.out.println("Error"+ e);
        }

    }
    @FXML void BackHyperlinkPressed(ActionEvent event){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("RealAdminEditor.fxml"));
            Scene scene = new Scene(parent);

            //This line gets stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(scene);
            window.show();
        }catch (IOException e){
            System.out.println("Error"+ e);
        }
    }

    ObservableList<TableDateException> list = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //for table
        try{
        String query = "SELECT * FROM dateexception";
        Connection connection = ConnectionManage.getConnection();
        Statement st = connection.createStatement();
        ResultSet rs ;

        rs = st.executeQuery(query);
        while (rs.next()){
            Date date = rs.getDate("dateexception");
            LocalDate localDate = date.toLocalDate();
            String num = rs.getString("num");

            list.add(new TableDateException(num,localDate));
        }


        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_num.setCellValueFactory(new PropertyValueFactory<>("num"));

        tableAdmin.setItems(list);

    } catch (
    SQLException e) {
        System.out.println("Error connection "+ e);
    }
        //for calendar editor
        databaseDataCollector();
        Callback<DatePicker, DateCell> dayCellFactory= this.getDayCellFactory();
        dateExcept.setDayCellFactory(dayCellFactory);

        //listener to current FXML for date
        dateExcept.valueProperty().addListener((observable,oldValue,newValue) ->{
                    setExceptDate(newValue);
                    System.out.println(newValue);
                }
        );

        numBox.textProperty().addListener((observable,oldValue,newValue) ->{
            System.out.println(newValue+"old"+oldValue);
            setNum(newValue);
        } );
    }
    public void databaseDataCollector(){
        try {


            //from admintimeeditordatabase
            String query = "SELECT * FROM admintimedateeditor";
            Connection connection = ConnectionManage.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()){
                Date DBStartDate = rs.getDate("opendate");
                setStartDate((DBStartDate.toLocalDate()));
                Date DBEndDate = rs.getDate("closedate");
                setEndDate(DBEndDate.toLocalDate());

            }

        } catch (SQLException e) {
            System.out.println("Error connection "+ e);
        }

    }
    private Callback<DatePicker, DateCell> getDayCellFactory() {

        Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {

            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        LocalDate start = getStartDate();
                        LocalDate end = getEndDate();
                        boolean b = empty||item.compareTo(start)<0||item.compareTo(end)>0;

                        try{
                            String query = "SELECT * FROM dateexception";
                            Connection connection = ConnectionManage.getConnection();
                            Statement st = connection.createStatement();
                            ResultSet rs = st.executeQuery(query);

                            while (rs.next()){
                                Date date = rs.getDate("dateexception");
                                LocalDate except = date.toLocalDate();
                                b =b|| item.compareTo(except)==0;

                            }}catch (SQLException e) {
                            System.out.println("Error "+e);
                        }

                        setDisable(b);


                    }
                };
            }
        };
        return dayCellFactory;
    }
}
