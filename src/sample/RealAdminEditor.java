package sample;

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

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import static javax.swing.JOptionPane.*;

public class RealAdminEditor implements Initializable {
    @FXML private DatePicker StartDatePicker;
    @FXML private DatePicker EndDatePicker;
    @FXML private ComboBox<String> OpenTimeHour;
    @FXML private ComboBox<String> OpenTimeMin;
    @FXML private ComboBox<String> CloseTimeHour;
    @FXML private ComboBox<String> CloseTimeMin;
    @FXML private Label CurrentTimeLabel;
    @FXML private TextField limitField;


    private LocalTime openTime;
    private LocalTime closeTime;
    private int OpenHour,OpenMinute,CloseHour,CloseMinute;
    private LocalDate StartDate, EndDate;
    private int limit;

    public int getLimit() {
        return limit;
    }
    public void setLimit(int limit) {
        this.limit = limit;
    }

    public LocalDate getStartDate() {
        return StartDate;
    }
    public void setStartDate(LocalDate startDate) {
        StartDate = startDate;
    }
    public LocalDate getEndDate() {
        return EndDate;
    }
    public void setEndDate(LocalDate endDate) {
        EndDate = endDate;
    }

    public LocalTime getOpenTime() {
        return openTime;
    }
    public void setOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }
    public LocalTime getCloseTime() {
        return closeTime;
    }
    public void setCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
    }

    public int getOpenHour() {
        return OpenHour;
    }
    public void setOpenHour(int openHour) {
        OpenHour = openHour;
    }
    public int getOpenMinute() {
        return OpenMinute;
    }
    public void setOpenMinute(int openMinute) {
        OpenMinute = openMinute;
    }
    public int getCloseHour() {
        return CloseHour;
    }
    public void setCloseHour(int closeHour) {
        CloseHour = closeHour;
    }
    public int getCloseMinute() {
        return CloseMinute;
    }
    public void setCloseMinute(int closeMinute) {
        CloseMinute = closeMinute;
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
    @FXML
    void ExceptionButton(ActionEvent event){
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




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String sentence;
        try {


            //from admintimeeditordatabase
            String query = "SELECT * FROM admintimedateeditor ";

            Connection connection = ConnectionManage.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            //from userprofile

            while (rs.next()){
                sentence = "Current setting\n"+"Open date : "+rs.getString("opendate")+
                        "\t\tClose date : "+rs.getString("closedate")+
                        "\nOpen time : "+rs.getString("opentime")+
                        "\t\tClose time : "+rs.getString("closetime")+
                        "\nLimit : "+rs.getString("limitPatient");
                CurrentTimeLabel.setText(sentence);

            }


        } catch (SQLException e) {
            System.out.println("Error connection "+ e);
        }







        String[] hour = new String[24];
        String[] minutes = {"00","30"};

        //date
        Callback<DatePicker, DateCell> dayCellFactory= this.getDayCellFactory();
        StartDatePicker.setDayCellFactory(dayCellFactory);


        Callback<DatePicker, DateCell> dayCellFactory2= this.getDayCellFactory2();
        EndDatePicker.setDayCellFactory(dayCellFactory2);
        //startdate listener
        StartDatePicker.valueProperty().addListener((observable,oldValue,newValue) ->{
                    setStartDate(newValue);
                    System.out.println(newValue);
                }
        );
        //enddate listener
        EndDatePicker.valueProperty().addListener((observable,oldValue,newValue) ->{
                    setEndDate(newValue);
                    System.out.println(newValue);
                }

        );
        //limit listener
        limitField.textProperty().addListener((observable,oldValue,newValue) ->{
                    setLimit(Integer.parseInt(newValue));
                    System.out.println(newValue);
                }

        );



        //the combobox
        {
        for (int i =0;i<hour.length;i++){
            hour[i] = ""+i;
            if(hour[i].length()!=2){
                hour[i]="0"+hour[i];
            }
        }
//        for (int i =0;i<minutes.length;i++){
//            minutes[i] = ""+i;
//            if(minutes[i].length()!=2){
//                minutes[i]="0"+minutes[i];
//            }
//        }
        //setting of the value in combobox
        OpenTimeHour.getItems().setAll(hour);
        OpenTimeMin.getItems().setAll(minutes);
        CloseTimeHour.getItems().setAll(hour);
        CloseTimeMin.getItems().setAll(minutes);

        //listener set
        OpenTimeHour.getSelectionModel().selectedItemProperty().addListener((options,oldVale,newValue)->{
           setOpenHour(Integer.parseInt(newValue));
        });
        OpenTimeMin.getSelectionModel().selectedItemProperty().addListener((options,oldVale,newValue)->{
            setOpenMinute(Integer.parseInt(newValue));
            LocalTime open = LocalTime.of(getOpenHour(),getOpenMinute(),0);
            setOpenTime(open);System.out.println(open);
        });
        CloseTimeHour.getSelectionModel().selectedItemProperty().addListener((options,oldVale,newValue)->{
            setCloseHour(Integer.parseInt(newValue));
        });
        CloseTimeMin.getSelectionModel().selectedItemProperty().addListener((options,oldVale,newValue)->{
            setCloseMinute(Integer.parseInt(newValue));
            LocalTime close = LocalTime.of(getCloseHour(),getCloseMinute(),0);
            setCloseTime(close);
            System.out.println(close);
        });

        //setTime



        }

    }

    //Date picker editor for start date
    private Callback<DatePicker, DateCell> getDayCellFactory() {

        return new Callback<DatePicker, DateCell>() {

            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        LocalDate today = LocalDate.now();

                        boolean b = empty||item.compareTo(today)<0;

                        setDisable(b);


                    }
                };
            }
        };
    }
    //date picker editor for end date
    private Callback<DatePicker, DateCell> getDayCellFactory2() {

        return new Callback<DatePicker, DateCell>() {

            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        LocalDate today = LocalDate.now();
                        LocalDate start = getStartDate();
                        boolean b = empty||item.compareTo(today)<0||item.compareTo(start)<0;

                        setDisable(b);


                    }
                };
            }
        };
    }
    @FXML void SubmitButton(ActionEvent event){
        event.getSource();
        System.out.println(getCloseHour()+"min");
        System.out.println("start"+ Date.valueOf(getStartDate())+"end"+getEndDate()+"open"+getOpenTime()+" "+getCloseTime());
        try {

            String query = "INSERT INTO `admintimedateeditor` (`opendate`, `closedate`, `opentime`, `closetime`,`limitPatient`) VALUES (?,?,?,?,?) ";

            Connection connection = ConnectionManage.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setDate(1, Date.valueOf(getStartDate()));
            preparedStatement.setDate(2, Date.valueOf(getEndDate()));
            preparedStatement.setTime(3, Time.valueOf(getOpenTime()));
            preparedStatement.setTime(4, Time.valueOf(getCloseTime()));
            preparedStatement.setInt(5,getLimit());

            preparedStatement.execute();
            showMessageDialog(null,"Submit success","Success", PLAIN_MESSAGE);

        } catch (SQLException e) {
            showMessageDialog(null,"Submit failed","Error", WARNING_MESSAGE);
            System.out.println("Error connection" + e.getMessage());
        }

    }
}
