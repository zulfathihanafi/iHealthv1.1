package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Front implements Initializable {


@FXML private Button StaffButton;
@FXML private Button PatientButton;


@FXML void PatientButtonPressed(ActionEvent event){
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
    void StaffButtonPressed(ActionEvent event) {
    try {
        Parent parent = FXMLLoader.load(getClass().getResource("LoginAdmin.fxml"));
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
     ConnectionManage.getConnection();


    }
}
