package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

public class RealPatientAppointment implements Initializable {
    //from current fxml that we want
    private String ID;
    private String name;
    private LocalDate date;
    private LocalTime time;
    private int min,hour,limitPatient;

    public int getLimitPatient() {
        return limitPatient;
    }
    public void setLimitPatient(int limitPatient) {
        this.limitPatient = limitPatient; }

    public int getMin() {
        return min;
    }
    public void setMin(int min) {
        this.min = min;
    }
    public int getHour() {
        return hour;
    }
    public void setHour(int hour) {
        this.hour = hour;
    }
    public LocalTime getTime() {
        return time;
    }
    public void setTime(LocalTime time) {
        this.time = time;
    }
    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }



    //from database admintimeeditor

    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime openTime;
    private LocalTime closeTime;

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



    @FXML private DatePicker field_date;
    @FXML private Label TimeInfo;
    @FXML private ComboBox<LocalTime> TimeComboBox;
    @FXML private ComboBox<String> HourComboBox;
    @FXML private ComboBox<String> MinComboBox;


    @FXML
    void BackHyperlinkPressed(ActionEvent event){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("PatientViewAppointment.fxml"));
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
    void SubmitButtonClick(ActionEvent event){
        event.getSource();
        try {

            String query = "INSERT INTO `appointmentlist` (`Num`, `ID`, `Name`, `Date`, `Time`) VALUES (NULL,?,?,?,?) ";

            Connection connection = ConnectionManage.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            Time time = Time.valueOf(getTime());
            System.out.println(time);

            preparedStatement.setString(1,getID());
            preparedStatement.setString(2,getName());
            preparedStatement.setDate(3, Date.valueOf(getDate()));
            preparedStatement.setTime(4, time);

            preparedStatement.execute();

            JOptionPane.showMessageDialog(null,"Success making appointment","Success",JOptionPane.PLAIN_MESSAGE);

            try {
                Parent parent = FXMLLoader.load(getClass().getResource("PatientViewAppointment.fxml"));
                Scene scene = new Scene(parent);

                //This line gets stage information
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                window.setScene(scene);
                window.show();
            }catch (IOException e){
                System.out.println("Error"+ e);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Fail","Warning", WARNING_MESSAGE);
            System.out.println("Error connection" + e.getMessage());
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        databaseDataCollector();
        TimeInfo.setText("Open : "+getOpenTime()+" Close : "+getCloseTime());


        //datecellfactory
        Callback<DatePicker, DateCell> dayCellFactory= this.getDayCellFactory();
        field_date.setDayCellFactory(dayCellFactory);

        //listener to current FXML for date
        field_date.valueProperty().addListener((observable,oldValue,newValue) ->{
                    setDate(newValue);
                    System.out.println(newValue);
                }
        );

        //hour and min combobox

        String[] hour = new String[24];
        String[] minutes = new String[60];
        for (int i =0;i<hour.length;i++){
            hour[i] = ""+i;
            if(hour[i].length()!=2){
                hour[i]="0"+hour[i];
            }
        }
        for (int i =0;i<minutes.length;i++){
            minutes[i] = ""+i;
            if(minutes[i].length()!=2){
                minutes[i]="0"+minutes[i];
            }
        }
        //setting of the value in combobox

            HourComboBox.getItems().setAll(hour);
            MinComboBox.getItems().setAll(minutes);

            HourComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldVale, newValue) -> {
                setHour(Integer.parseInt(newValue));
                ArrayList<LocalTime> time = TimeCalculator(getHour(), getMin());
                TimeComboBox.getItems().setAll(time);

            });
            MinComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldVale, newValue) -> {
                setMin(Integer.parseInt(newValue));
                ArrayList<LocalTime> time = TimeCalculator(getHour(), getMin());
                ArrayList<LocalTime> timeFiltered = TimeFiltering(time);
                TimeComboBox.getItems().setAll(timeFiltered);
            });





        //set value from confirmed time combo box
        TimeComboBox.getSelectionModel().selectedItemProperty().addListener((options,oldVale,newValue)->{
            setTime(newValue);
            System.out.println(Time.valueOf(getTime()));
        });
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

               // setOpenTime(rs.getTime("opentime").toLocalTime());
                String openTime = rs.getString("opentime");
                setOpenTime(LocalTime.parse(openTime));
               // setCloseTime(rs.getTime("closetime").toLocalTime());
                String closeTime = rs.getString("closetime");
                setCloseTime(LocalTime.parse(closeTime));

                setLimitPatient(rs.getInt("limitPatient"));

            }

            //from userprofile
            query = "SELECT * FROM userprofile where email = "+ "'" + email.getEmailLogin()+"'";

            st = connection.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()){
                setName(rs.getString("name"));
                setID(rs.getString("ID"));
            }


        } catch (SQLException e) {
            System.out.println("Error connection "+ e);
        }

    }

    private Callback<DatePicker, DateCell> getDayCellFactory() {

        Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {

            @Override
            public DateCell call(final DatePicker datePicker) {
                 //dateexception


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
    public ArrayList<LocalTime> TimeCalculator(int hour,int min){
        System.out.println("Time : "+min+" min "+" hour "+hour);
        LocalTime userTime = LocalTime.of(getHour(),getMin(),0);
        ArrayList<LocalTime> time = new ArrayList<LocalTime>();
        LocalTime[] tempTime = new LocalTime[3];

        if(min==30||min==0){

            tempTime[0]= userTime.minusMinutes(30);
            tempTime[1]= userTime;
            tempTime[2]= userTime.plusMinutes(30);

        }
        else if (min<30){
            tempTime[0]= userTime.minusMinutes(min);
            tempTime[1]= userTime.minusMinutes(min).plusMinutes(30);


        }
        else {
            tempTime[0]= userTime.minusMinutes(min).plusMinutes(30);
            tempTime[1]= userTime.minusMinutes(min).plusHours(1);


        }


        int length=0;
        if(tempTime[2]==null){
            length=2;
        }
        else length=3;
        for (int i=0;i<length;i++){

            if(tempTime[i].isBefore(getCloseTime())&&tempTime[i].isAfter(getOpenTime())){
                time.add(tempTime[i]);
            }
            else if(tempTime[i].equals(getOpenTime())){
                time.add(tempTime[i]);
            }

        }
        System.out.println("end of line");
        System.out.println(Arrays.toString(time.toArray()));
        time = TimeFiltering(time);

       return time;
    }

    public ArrayList<LocalTime> TimeFiltering (ArrayList<LocalTime> timeFilter) {
        //limiting the time exceed n people
        System.out.println("filter*****************limit"+getLimitPatient());
        try {

            String query = "SELECT * FROM appointmentlist where date = '" + Date.valueOf(getDate())+ "'";

            Connection connection = ConnectionManage.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            LocalTime dataTime;
            int cnt = 0;
            ArrayList<Integer> index = new ArrayList<Integer>();

           for(int i =0;i<timeFilter.size();i++){
               int limit = getLimitPatient();
               rs = st.executeQuery(query);
               System.out.println("Another round");
               cnt=0;
                while (rs.next()) {

                dataTime = rs.getTime("time").toLocalTime();
                    System.out.println("data "+dataTime+"timeFIlter"+timeFilter.get(i));
                if(dataTime.equals(timeFilter.get(i))){
                    cnt++;
                    System.out.print("\nequal, count = "+cnt);

                }
                if(cnt>limit-1){
                    index.add(i);
                    System.out.println("removal");

                }

                }


           }

           for (int i=0;i<index.size();i++){
               int n = index.get(i);
               System.out.println("in index loop "+index.get(i));
               timeFilter.remove(n);
           }


            System.out.println(timeFilter);
           connection.close();
        } catch (SQLException e) {
            System.out.println("Set limit time Error " + e);
        }
        return timeFilter;
    }



}
