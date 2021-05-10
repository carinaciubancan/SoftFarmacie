package server;

import persistance.ComandaRepository;
import persistance.FarmacistRepository;
import persistance.MedicRepository;
import persistance.MedicamentRepository;
import services.IObserver;
import services.IServices;
import services.MyException;
import spital.Comanda;
import spital.Farmacist;
import spital.Medic;
import spital.Medicament;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceImplementation implements IServices {
    private MedicRepository medicRepository;
    private MedicamentRepository medicamentRepository;
    private ComandaRepository comandaRepository;
    private FarmacistRepository farmacistRepository;
    private Map<Integer, IObserver> mediciLogati;
    private Map<Integer, IObserver> farmacistiLogati;


    public ServiceImplementation(MedicRepository medicRepository, MedicamentRepository medicamentRepository,
                                 ComandaRepository comandaRepository,FarmacistRepository farmacistRepository) {
        this.medicRepository = medicRepository;
        this.medicamentRepository = medicamentRepository;
        this.comandaRepository=comandaRepository;
        this.farmacistRepository=farmacistRepository;
        mediciLogati=new ConcurrentHashMap<>();
        farmacistiLogati=new ConcurrentHashMap<>();
    }

    @Override
    public  synchronized void loginM(Medic medic, IObserver obs) throws MyException {
        Medic medic1 = medicRepository.findMedicByUsernamePassword(medic.getUsername(),medic.getPassword());
        if(medic1!=null){
            if(mediciLogati.get(medic1.getId())!=null){
                throw new MyException("Medicul este deja logat!");
            }
            mediciLogati.put(medic1.getId(),obs);
        }else{
            throw new MyException("Autentificare esuata!");
        }
    }
    @Override
    public synchronized void logoutF(Farmacist farmacist, IObserver obs) throws MyException {
        mediciLogati.remove(farmacist);
    }
    @Override
    public synchronized void loginF(Farmacist farmacist, IObserver obs) throws MyException {
        Farmacist farmacist1 = farmacistRepository.findFarmacistByUsernamePassword(farmacist.getUsername(),farmacist.getPassword());
        if(farmacist1!=null){
            if(mediciLogati.get(farmacist.getId())!=null){
                throw new MyException("Farmacistul este deja logat!");
            }
            mediciLogati.put(farmacist1.getId(),obs);
        }else{
            throw new MyException("Autentificare esuata!");
        }
    }
    @Override
    public synchronized void logoutM(Medic medic, IObserver obs) throws MyException {
        mediciLogati.remove(medic);
    }
    @Override
    public synchronized List<Medicament> getMedicamenteStoc() {
        return medicamentRepository.getMedicamenteStoc();
    }
    @Override
    public synchronized List<Comanda> getComenzi() {
        return comandaRepository.getComenzi();
    }
    @Override
    public  synchronized void deleteComanda(Integer id) {
        comandaRepository.delete(id);
        notifyDeleteComanda();
    }


    @Override
    public synchronized void modificaComanda(String status, String nivel, Date dataExpedirii, Integer idMedicament) {
       // Comanda comanda=comandaRepository.findOne(idComanda);
        Comanda comandaNoua=new Comanda(nivel,status,dataExpedirii,idMedicament);
        comandaRepository.update(comandaNoua);
        notifyOnoreazaComanda();
    }

    @Override
    public synchronized void adaugaComanda( Integer idMedicament,String nivel){
        Comanda comanda=new Comanda(nivel,"nerealizata", new Date(),idMedicament);
        comandaRepository.add(comanda);
        notifyComandaUpdated();
    }


    private final int defaultThreadsNo=5;

    private void notifyComandaUpdated() {
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for(var o: mediciLogati.entrySet()) {
            executor.execute(() -> {
                try {
                    o.getValue().comandaUpdated();
                } catch (MyException | RemoteException e) {
                    System.err.println("Error notifying medic adaugare comanda " + e);
                }
            });
        }
        for(var o: farmacistiLogati.entrySet()) {
            executor.execute(() -> {
                try {
                    o.getValue().comandaUpdated();
                } catch (MyException | RemoteException e) {
                    System.err.println("Error notifying farmacist adaugare comanda " + e);
                }
            });
        }
        executor.shutdown();
    }

    private void notifyOnoreazaComanda(){
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for(var o: mediciLogati.entrySet()) {
            executor.execute(() -> {
                try {
                    o.getValue().onorareUpdated();
                } catch (MyException | RemoteException e) {
                    System.err.println("Error notifying medic adaugare comanda " + e);
                }
            });
        }
        for(var o: farmacistiLogati.entrySet()) {
            executor.execute(() -> {
                try {
                    o.getValue().onorareUpdated();
                } catch (MyException | RemoteException e) {
                    System.err.println("Error notifying farmacist adaugare comanda " + e);
                }
            });
        }
        executor.shutdown();
    }

    private void notifyDeleteComanda(){
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for(var o: mediciLogati.entrySet()) {
            executor.execute(() -> {
                try {
                    o.getValue().deleteUpdated();
                } catch (MyException | RemoteException e) {
                    System.err.println("Error notifying medic adaugare comanda " + e);
                }
            });
        }
        for(var o: farmacistiLogati.entrySet()) {
            executor.execute(() -> {
                try {
                    o.getValue().deleteUpdated();
                } catch (MyException | RemoteException e) {
                    System.err.println("Error notifying farmacist adaugare comanda " + e);
                }
            });
        }
        executor.shutdown();
    }

    public List<Medicament> findMedicamente(String nume){
        return medicamentRepository.findMedicamentDupaNume(nume);
    }


}
