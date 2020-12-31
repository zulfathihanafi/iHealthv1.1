package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AfterLoginAdmin implements Initializable {
    @FXML
    void ViewButtonPressed(ActionEvent event){
        try {
            Parent changeViewStaffParent = FXMLLoader.load(getClass().getResource("AdminAppointmentViewer.fxml"));
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
    void TimeEditorPressed(ActionEvent event){
        try {
            Parent changeViewStaffParent = FXMLLoader.load(getClass().getResource("RealAdminEditor.fxml"));
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
    void BackHyperlinkPressed(ActionEvent event){
        try {
            Parent changeViewStaffParent = FXMLLoader.load(getClass().getResource("LoginAdmin.fxml"));
            Scene changeViewStaffScene = new Scene(changeViewStaffParent);

            //This line gets stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(changeViewStaffScene);
            window.show();
        }catch (IOException e){
            System.out.println("Error"+ e);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
