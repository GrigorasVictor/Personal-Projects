package com.omega.proiects10.SQL;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "componente")
public class Componente {
    @Id
    private String idc;
    @Column(nullable = false, unique = true, length = 50, name = "numec")
    private String numec;
    @Column(nullable = false, unique = true, length = 50, name = "culoare")
    private String culoare;
    @Column(nullable = false, unique = true, length = 50, name = "masa")
    private int masa;
    @Column(nullable = false, unique = true, length = 50, name = "oras")
    private String oras;

    public String getIdc() {
        return idc;
    }
    public void setIdc(String idc) {
        this.idc = idc;
    }
    public String getNumec() {
        return numec;
    }
    public void setNumec(String numec) {
        this.numec = numec;
    }
    public String getCuloare() {
        return culoare;
    }
    public void setCuloare(String culoare) {
        this.culoare = culoare;
    }
    public int getMasa() {
        return masa;
    }
    public void setMasa(int masa) {
        this.masa = masa;
    }
    public String getOras() {
        return oras;
    }
    public void setOras(String oras) {
        this.oras = oras;
    }

    @Override
    public String toString() {
        return "Componente{" +
                "idc='" + idc + '\'' +
                ", numec='" + numec + '\'' +
                ", culoare='" + culoare + '\'' +
                ", masa=" + masa +
                ", oras='" + oras + '\'' +
                '}';
    }
}
