package persistance;

import spital.Medic;

public interface MedicRepository extends CRUDRepository<Integer, Medic>{
    Medic findMedicByUsernamePassword(String username,String password);
}
