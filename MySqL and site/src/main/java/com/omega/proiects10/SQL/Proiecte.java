package com.omega.proiects10.SQL;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "proiecte")
public class Proiecte {
    @Id
    private String idp;
    @Column(nullable = false, unique = true, length = 50, name = "numep")
    private String numep;
    @Column(nullable = false, unique = true, length = 5, name = "oras")
    private String oras;

    public String getIdp() {
        return idp;
    }
    public void setIdp(String idp) {
        this.idp = idp;
    }
    public String getNumep() {
        return numep;
    }
    public void setNumep(String numep) {
        this.numep = numep;
    }
    public String getOras() {
        return oras;
    }
    public void setOras(String oras) {
        this.oras = oras;
    }

    @Override
    public String toString() {
        return "Proiecte{" +
                "idp='" + idp + '\'' +
                ", numep='" + numep + '\'' +
                ", oras='" + oras + '\'' +
                '}';
    }
}
