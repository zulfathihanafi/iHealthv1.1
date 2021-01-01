package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginAdminController implements Initializable {
    private String email;
    private String pass;


    @FXML
    private TextField emailTextBox;
    @FXML
    private PasswordField PassTextBox;



    @FXML
    void BackHyperlinkPressed(ActionEvent event){

        try {
            Parent parent = FXMLLoader.load(getClass().getResource("Front.fxml"));
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
    public void LoginButtonPressed(ActionEvent event){
        email = emailTextBox.getText();
        pass = PassTextBox.getText();
        try{

            String query = "SELECT * FROM `adminpass` WHERE `admin_name` = ? AND `pass` = ?";
            System.out.println(pass);
            Connection con = ConnectionManage.getConnection();
            PreparedStatement st;
            ResultSet rs;
            st= con.prepareStatement(query);
            st.setString(1, email);
            st.setString(2, pass);
            rs = st.executeQuery();

            if(rs.next())
            {
                // show a new form

                JOptionPane.showMessageDialog(null,"Success login");
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




            }else{
                //error message
                JOptionPane.showMessageDialog(null, "Invalid Email or Password","Login Error", 2);
            }
        }catch (SQLException ex) {
            System.out.println("Error");
        }






    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
