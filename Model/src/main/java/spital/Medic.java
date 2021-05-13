package spital;

import java.io.Serializable;
import java.util.Objects;

public class Medic implements Entity<Integer>, Serializable {
    private int id;
    private String username;
    private String password;

    public Medic() {

    }

    public Medic(String username, String password) {
        this.username = username;
        this.password = password;
        this.id=id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medic medic = (Medic) o;
        return id == medic.id &&
                Objects.equals(username, medic.username) &&
                Objects.equals(password, medic.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password);
    }

    @Override
    public String toString() {
        return "Medic{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
