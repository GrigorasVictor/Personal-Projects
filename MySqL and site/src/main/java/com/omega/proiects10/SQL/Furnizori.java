package com.omega.proiects10.SQL;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "furnizori")
public class Furnizori {
    @Id
    private String idf;
    @Column(nullable = false, unique = true, length = 50, name = "numef")
    private String numef;
    @Column(nullable = false, unique = true, length = 5, name = "stare")
    private String stare;
    @Column(nullable = false, unique = true, length = 5, name = "oras")
    private String oras;

    public String getIdf() {
        return idf;
    }
    public void setIdf(String idf) {
        this.idf = idf;
    }
    public String getNumef() {
        return numef;
    }
    public void setNumef(String numef) {
        this.numef = numef;
    }
    public String getStare() {
        return stare;
    }
    public void setStare(String stare) {
        this.stare = stare;
    }
    public String getOras() {
        return oras;
    }
    public void setOras(String oras) {
        this.oras = oras;
    }

    @Override
    public String toString() {
        return "Furnizori{" +
                "idf='" + idf + '\'' +
                ", numef='" + numef + '\'' +
                ", stare='" + stare + '\'' +
                ", oras='" + oras + '\'' +
                '}';
    }
}
