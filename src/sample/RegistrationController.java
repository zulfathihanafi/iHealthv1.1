package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {


    public TextField field_name;
    public TextField field_ic;
    public TextField field_phone;
    public TextField field_email;
    public TextField field_password;
    public TextField field_Repassword;

    public String name,IC,phone,email,password;


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
            Parent parent = FXMLLoader.load(getClass().getResource("LoginPatient[[.fxml"));
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


    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }
}
