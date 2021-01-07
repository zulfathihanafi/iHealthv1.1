package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
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

import static javax.swing.JOptionPane.*;

public class RegistrationController implements Initializable {

    private int score=0;
    public TextField field_name;
    public TextField field_ic;
    public TextField field_phone;
    public TextField field_email;
    public PasswordField field_password;
    public PasswordField field_Repassword;

    public String name;
    public String IC;
    public String phone;
    public String email;
    public String password;
    public String repassword;
    public String error_message;

    public String getRepassword() {
        return repassword;
    }
    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getIC() {
        return IC;
    }
    public void setIC(String IC) {
        this.IC = IC;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }



    @FXML
    void hyperlinkLogin(ActionEvent event){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("LoginPatient.fxml"));
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
    void RegisterButton(ActionEvent event){
        event.getSource();

        if(checker()){
            try {

                String query = "INSERT INTO `userprofile` (`ID`,`Name`, `ICNumber`, `PhoneNumber`, `Email`,`Password`) VALUES (null,?,?,?,?,MD5(?)) ";

                Connection connection = ConnectionManage.getConnection();

                PreparedStatement preparedStatement = connection.prepareStatement(query);

                preparedStatement.setString(1, getName());
                preparedStatement.setString(2, getIC());
                preparedStatement.setString(3, getPhone());
                preparedStatement.setString(4, getEmail());
                preparedStatement.setString(5, getPassword());

                preparedStatement.execute();
                showMessageDialog(null,"Submit success","Success", PLAIN_MESSAGE);

                //back to login interface
                try {
                    Parent parent = FXMLLoader.load(getClass().getResource("LoginPatient.fxml"));
                    Scene scene = new Scene(parent);

                    //This line gets stage information
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    window.setScene(scene);
                    window.show();
                }catch (IOException e){
                    System.out.println("Error"+ e);
                }

            } catch (SQLException e) {
                showMessageDialog(null,"Submit failed","Error", WARNING_MESSAGE);
                System.out.println("Error connection" + e.getMessage());
            }
        }
        else{
            JOptionPane.showMessageDialog(null,error_message,"Warning", WARNING_MESSAGE);
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        field_name.textProperty().addListener((observable,oldValue,newValue) ->{
            setName(newValue);
        } );
        field_ic.textProperty().addListener((observable,oldValue,newValue) ->{
            setIC(newValue);
        } );
        field_phone.textProperty().addListener((observable,oldValue,newValue) ->{
            setPhone(newValue);
        } );
        field_email.textProperty().addListener((observable,oldValue,newValue) ->{
            setEmail(newValue);
        } );
        field_password.textProperty().addListener((observable,oldValue,newValue) ->{
            setPassword(newValue);
        } );
        field_Repassword.textProperty().addListener((observable,oldValue,newValue) ->{
            setRepassword(newValue);
        } );
    }
    public boolean checker (){
        error_message="";


        score=4;
        //check email availability
        try {


            //from admintimeeditordatabase
            String query = "SELECT * FROM userprofile where email = "+ "'" + getEmail()+"'";

            Connection connection = ConnectionManage.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            //from userprofile


            while(rs.next()){
                score--;
                error_message = "\nYour email is already used";
            }


        } catch (SQLException e) {
            System.out.println("Error connection "+ e);
        }

        //check ic
        String ic = getIC();
        System.out.println("IC : "+IC+"name"+name);
        if(ic.length()==12){
                if(ic.contains("-")){
                    error_message+= "\nIC must not contain '-' ";
                    score--;
                }

        }
        else {
            error_message+="\nPlease enter correct IC";
            score--;
        }

        //check email
              String email = getEmail();
            if(!email.contains("@")){score--;
            error_message+="\nPlease enter a valid email";}

            if(!getPassword().equals(getRepassword())){
                error_message+="\nPlease enter password correctly";
                score--;
            }


        return score == 4;
    }


}
