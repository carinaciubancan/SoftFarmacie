package controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import services.IObserver;
import services.IServices;
import services.MyException;
import spital.Comanda;
import spital.Farmacist;
import spital.Medic;
import spital.Medicament;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;

public class FarmacistController extends UnicastRemoteObject implements IObserver, Serializable {
    private IServices service;
    private Farmacist farmacistConectat;
    ObservableList<Comanda> modelComenzi = FXCollections.observableArrayList();

    @FXML
    TableView<Comanda> tabelComenzi;


    public FarmacistController()throws RemoteException {

    }

    public void initialize(){
        tabelComenzi.setItems(modelComenzi);
    }

    public void initModel(){
        for(Comanda m:service.getComenzi()){
            System.out.println(m);
        }
        modelComenzi.setAll(service.getComenzi());
    }

    public void setContext(IServices service)throws RemoteException{
        this.service=service;
        initModel();
    }

    public void setFarmacistConectat(Farmacist farmacist)throws MyException {
        this.farmacistConectat=farmacist;
        initModel();
    }
    public void logout(){
        try{
            service.logoutF(farmacistConectat,this);
            System.out.println(0);
        } catch (MyException e) {
            System.out.println("Logout error "+e);
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

    }

    @Override
    public void deleteUpdated() throws MyException, RemoteException {
        Platform.runLater(()->{
            modelComenzi.setAll(service.getComenzi());
            tabelComenzi.refresh();
        });
    }

    public void onoreazaComanda(MouseEvent mouseEvent) {
        Comanda comandaSelectata = tabelComenzi.getSelectionModel().getSelectedItem();
        if(comandaSelectata != null){
            try{
                modelComenzi.remove(comandaSelectata);
                service.modificaComanda(comandaSelectata.getStatus(),"realizata",
                        comandaSelectata.getDataExpedierii(),comandaSelectata.getIdMedicament());

                MessageBox.showMessage(null,Alert.AlertType.INFORMATION,"Yey!","Comanda onorata cu succes!");

            } catch (Exception e) {
                MessageBox.showErrorMessage(null, e.getMessage());
            }
        }else {
            MessageBox.showErrorMessage(null, "NIMIC SELECTAT");
        }
    }
}
