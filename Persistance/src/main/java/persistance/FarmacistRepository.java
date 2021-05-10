package persistance;

import spital.Farmacist;
import spital.Medic;

public interface FarmacistRepository extends CRUDRepository<Integer, Farmacist> {
    Farmacist findFarmacistByUsernamePassword(String username,String password);
}
