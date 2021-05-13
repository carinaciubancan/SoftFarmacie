package spital;

import java.io.Serializable;
import java.util.Date;

public class Comanda implements Entity<Integer>, Serializable {
    private int id;
    private String nivel;
    private String status;
    private Date dataExpedierii;
    private int idMedicament;

    public Comanda(){

    }

    public Comanda(String nivel, String status, Date dataExpedierii, int idMedicament) {
        this.nivel = nivel;
        this.status = status;
        this.dataExpedierii = dataExpedierii;
        this.idMedicament = idMedicament;
    }

    public int getIdMedicament() {
        return idMedicament;
    }

    public void setIdMedicament(int idMedicament) {
        this.idMedicament = idMedicament;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id=id;

    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDataExpedierii() {
        return dataExpedierii;
    }

    public void setDataExpedierii(Date dataExpedierii) {
        this.dataExpedierii = dataExpedierii;
    }
}
