package controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import services.IObserver;
import services.IServices;
import services.MyException;
import spital.Comanda;
import spital.Medic;
import spital.Medicament;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;

public class MedicController extends UnicastRemoteObject implements IObserver, Serializable {
    private IServices service;
    private Medic medicConectat;
    ObservableList<Medicament> modelMedicamente = FXCollections.observableArrayList();
    ObservableList<Comanda> modelComenzi = FXCollections.observableArrayList();

    @FXML
    TableView<Medicament> tabelMedicamente;
    @FXML
    TableView<Comanda> tabelComenzi;
    @FXML
    Button buttonTrimiteComanda;
    @FXML
    TextField textField1;
    @FXML
    TextField textField2;
    @FXML
    TextField textField3;
    @FXML
    TextField textField4;
    @FXML
    TextField textField5;
    @FXML
    TextField textFieldMedicament;
    @FXML
    TextField textFieldGramaj;
    @FXML
    Button buttonCautare;


    public MedicController()throws RemoteException {

    }

    public void initialize(){
        tabelMedicamente.setItems(modelMedicamente);
        tabelComenzi.setItems(modelComenzi);
    }

    public void initModel(){
        modelMedicamente.setAll((Collection<? extends Medicament>) this.service.getMedicamenteStoc());
        modelComenzi.setAll(service.getComenzi());
    }

    public void setContext(IServices service)throws RemoteException{
        this.service=service;
        initModel();
    }
    public void setMedicConectat(Medic medic)throws MyException {
        this.medicConectat=medic;
        initModel();
    }
    public void logout(){
        try{
            service.logoutM(medicConectat,this);
            System.out.println(0);
        } catch (MyException e) {
            System.out.println("Logout error "+e);
        }
    }


    public void stergeComanda(MouseEvent mouseEvent) {
        Comanda comandaSelectata=tabelComenzi.getSelectionModel().getSelectedItem();
        if(comandaSelectata != null){
            try{
                service.deleteComanda(comandaSelectata.getId());
                MessageBox.showMessage(null, Alert.AlertType.INFORMATION,"Message","Stergere cu succes");
                modelComenzi.setAll(service.getComenzi());
            } catch (Exception e) {
                MessageBox.showErrorMessage(null, e.getMessage());
            }
        }
        else{
            MessageBox.showErrorMessage(null, "NIMIC SELECTAT!");
        }

    }

    @Override
    public void comandaUpdated() throws MyException, RemoteException {
        Platform.runLater(()->{
            modelComenzi.setAll(service.getComenzi());
            tabelComenzi.refresh();
        });
    }

    @Override
    public void onorareUpdated() throws MyException, RemoteException {
        Platform.runLater(()->{
            modelComenzi.setAll(service.getComenzi());
            tabelComenzi.refresh();
        });
    }

    @Override
    public void deleteUpdated() throws MyException, RemoteException {
        Platform.runLater(()->{
            modelComenzi.setAll(service.getComenzi());
            tabelComenzi.refresh();
        });
    }

    public void selectMedicament(MouseEvent mouseEvent){
        Medicament medicamentSelectat = tabelMedicamente.getSelectionModel().getSelectedItem();
        textField1.setText(medicamentSelectat.getId().toString());
        textField2.setText(medicamentSelectat.getNume());
        textField3.setText(String.valueOf(medicamentSelectat.getGramaj()));
        textField4.setText(String.valueOf(medicamentSelectat.getNrBuc()));
    }

    public void trimiteComanda(MouseEvent mouseEvent) {
        Medicament medicamentSelectat = tabelMedicamente.getSelectionModel().getSelectedItem();
        if(medicamentSelectat != null){
            try{
                service.adaugaComanda(medicamentSelectat.getId(),textField5.getText());
                Platform.runLater(()->{
                    modelComenzi.setAll(service.getComenzi());
                    tabelComenzi.refresh();
                });
                MessageBox.showMessage(null,Alert.AlertType.INFORMATION,"Yey!","Comanda realizata cu succes!");
                textField1.setText("");
                textField2.setText("");
                textField3.setText("");
                textField4.setText("");
            } catch (Exception e) {
                MessageBox.showErrorMessage(null, e.getMessage());
            }
        }else {
            MessageBox.showErrorMessage(null, "NIMIC SELECTAT");
        }
    }

    public void cautareMedicament(MouseEvent mouseEvent) {
        String nume=textFieldMedicament.getText();
        if(nume!=null){
            modelMedicamente.setAll(service.findMedicamente(nume));
            tabelMedicamente.refresh();
        }

    }

    public void refreshMedicamente(MouseEvent mouseEvent) {
        Platform.runLater(()->{
            modelMedicamente.setAll(service.getMedicamenteStoc());
            tabelMedicamente.refresh();
        });
    }
}
