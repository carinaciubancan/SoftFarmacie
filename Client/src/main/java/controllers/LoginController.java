package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.IServices;
import services.MyException;
import spital.Farmacist;
import spital.Medic;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class LoginController extends UnicastRemoteObject {
    private MedicController medicController;
    private FarmacistController farmacistController;
    private Parent parentMedic;
    private Parent parentFarmacist;
    private IServices service;

    @FXML
    TextField textFieldUserName;
    @FXML
    PasswordField passwordField;
    @FXML
    Button buttonLogin;

    public LoginController() throws RemoteException {}
    @FXML
    public void initialize(){

    }
    public void setContext(IServices service)throws RemoteException{this.service=service;}
    public void setControllerMedic(MedicController medicController1){this.medicController=medicController1;}
    public void setControllerFarmacist(FarmacistController farmacistController1){this.farmacistController=farmacistController1;}
    public void setParents(Parent parentMedic,Parent parentFarmacist){
        this.parentMedic=parentMedic;
        this.parentFarmacist=parentFarmacist;
    }

    public void handleLoginMedic(javafx.event.ActionEvent actionEvent) {
        String username=textFieldUserName.getText();
        String password = passwordField.getText();
        try{
            Medic medic=new Medic(username,password);
            service.loginM(medic,medicController);
            Stage stage=new Stage();
            stage.setScene(new Scene(parentMedic));
            stage.setOnCloseRequest(event->{
                medicController.logout();
                System.exit(0);
            });
            medicController.setMedicConectat(medic);
            stage.show();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        } catch (MyException e) {
            MessageBox.showErrorMessage(null,e.getMessage());
        }
    }

    public void handleLoginFarmacist(ActionEvent actionEvent) {
        String username=textFieldUserName.getText();
        String password = passwordField.getText();
        try{
            Farmacist farmacist=new Farmacist(username,password);
            service.loginF(farmacist,farmacistController);
            Stage stage=new Stage();
            stage.setScene(new Scene(parentFarmacist));
            stage.setOnCloseRequest(event->{
                farmacistController.logout();
                System.exit(0);
            });
            farmacistController.setFarmacistConectat(farmacist);
            stage.show();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        } catch (MyException e) {
            MessageBox.showErrorMessage(null,e.getMessage());
        }
    }



}
