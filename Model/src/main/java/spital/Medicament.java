package spital;

import java.io.Serializable;
import java.util.Objects;

public class Medicament implements Entity<Integer>, Serializable {
    private int id;
    private String nume;
    private int gramaj;
    private int nrBuc;

    public Medicament(){}

    public Medicament(String nume, int nrBuc, int gramaj) {
        this.nume = nume;
        this.gramaj = gramaj;
        this.nrBuc = nrBuc;
        this.id=id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getGramaj() {
        return gramaj;
    }

    public void setGramaj(int gramaj) {
        this.gramaj = gramaj;
    }

    public int getNrBuc() {
        return nrBuc;
    }

    public void setNrBuc(int nrBuc) {
        this.nrBuc = nrBuc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medicament that = (Medicament) o;
        return id == that.id &&
                gramaj == that.gramaj &&
                nrBuc == that.nrBuc &&
                Objects.equals(nume, that.nume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nume, gramaj, nrBuc);
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id=id;
    }

    @Override
    public String toString() {
        return "Medicament{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", gramaj=" + gramaj +
                ", nrBuc=" + nrBuc +
                '}';
    }
}
