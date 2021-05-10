package services;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IObserver extends Remote {
    void comandaUpdated() throws MyException, RemoteException;
    void onorareUpdated() throws MyException, RemoteException;
    void deleteUpdated() throws MyException, RemoteException;
}
