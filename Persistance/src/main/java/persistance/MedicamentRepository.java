package persistance;

import spital.Medicament;

import java.util.List;

public interface MedicamentRepository {
    List<Medicament> getMedicamenteStoc();
    List<Medicament> findMedicamentDupaNume(String nume);
}
