package com.omega.proiects10.SQL;

import jakarta.persistence.*;

@Entity
@Table(name = "livrari")
public class Livrari {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idl;

    @Column(nullable = false, unique = true, length = 5, name = "idf")
    private String idf;

    @Column(nullable = false, unique = true, length = 5, name = "idc")
    private String idc;

    @Column(nullable = false, unique = true, length = 5, name = "idp")
    private String idp;

    public Long getIdl() {
        return idl;
    }

    public void setIdl(Long idl) {
        this.idl = idl;
    }

    public String getIdf() {
        return idf;
    }

    public void setIdf(String idf) {
        this.idf = idf;
    }

    public String getIdc() {
        return idc;
    }

    public void setIdc(String idc) {
        this.idc = idc;
    }

    public String getIdp() {
        return idp;
    }

    public void setIdp(String idp) {
        this.idp = idp;
    }

    @Override
    public String toString() {
        return "Livrari{" +
                "idl=" + idl +
                ", idf='" + idf + '\'' +
                ", idc='" + idc + '\'' +
                ", idp='" + idp + '\'' +
                '}';
    }
}
