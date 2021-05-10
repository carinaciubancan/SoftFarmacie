package persistance;

import spital.Comanda;
import spital.Medicament;

import java.util.List;

public interface ComandaRepository  {
    List<Comanda> getComenzi();
    void delete(Integer id);
    void add(Comanda comanda);
    Comanda findOne(Integer id);
    void update(Comanda elem);
}
