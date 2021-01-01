package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginPatientController implements Initializable {


    @FXML
    private TextField emailTextBox;
    @FXML
    private PasswordField PassTextBox;
    private String email;
    private Connection con = null;
    private PreparedStatement st = null;
    private ResultSet rs = null;

    @FXML
    void SignUpHyperlinkPressed(ActionEvent event){
        //Register
        System.out.println("Hello");
    }
    @FXML void BackHyperlinkPressed(ActionEvent event){
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
        String email = emailTextBox.getText();
        String pass = PassTextBox.getText();
        String md5Pass = getMd5(pass);
        System.out.println(md5Pass);
        try{

            PreparedStatement st;
            ResultSet rs;
            String query = "SELECT * FROM `userprofile` WHERE `email` = ? AND `password` = ?";
            System.out.println(pass);

            Connection con = ConnectionManage.getConnection();

            st= con.prepareStatement(query);
            st.setString(1, email);
            st.setString(2, md5Pass);
            rs = st.executeQuery();

            if(rs.next())
            {
                // show a new form
                sample.email.setEmailLogin(emailTextBox.getText());
                JOptionPane.showMessageDialog(null,"Success login");
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
                //close the current form (login form)


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
    public static String getMd5(String input)
    {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest message = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] message_byte = message.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger messageText = new BigInteger(1, message_byte);

            // Convert message digest into hex value
            String toMD5 = messageText.toString(16);
            while (toMD5.length() < 32) {
                toMD5 = "0" + toMD5;
            }
            return toMD5;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            System.out.println("Error Algorithm");
            throw new RuntimeException(e);
        }
    }






}
