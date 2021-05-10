package services;


import spital.Comanda;
import spital.Farmacist;
import spital.Medic;
import spital.Medicament;

import java.util.Date;
import java.util.List;

public interface IServices {
    public void loginM(Medic medic, IObserver obs) throws MyException ;
    public void logoutM(Medic medic, IObserver obs) throws MyException ;
    public void loginF(Farmacist farmacist, IObserver obs) throws MyException ;
    public void logoutF(Farmacist farmacist, IObserver obs) throws MyException ;

    public List<Medicament> getMedicamenteStoc();
    public List<Comanda> getComenzi();
    public void adaugaComanda(Integer idMedicament,String nivel);
    public void deleteComanda(Integer id);
    public void modificaComanda(String status, String nivel, Date dataExpedirii, Integer idMedicament);
    public List<Medicament> findMedicamente(String nume);
}
