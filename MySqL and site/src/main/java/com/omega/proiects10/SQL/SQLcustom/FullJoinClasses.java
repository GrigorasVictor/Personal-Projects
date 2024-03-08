package com.omega.proiects10.SQL.SQLcustom;

public class FullJoinClasses {
    private String idf;
    private String idc;
    private String idp;

    private String numef;
    private String numec;
    private String numep;

    private int stare;
    private String oras;
    private String culoare;
    private int masa;
    private int cantitate;

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

    public String getNumef() {
        return numef;
    }

    public void setNumef(String numef) {
        this.numef = numef;
    }

    public String getNumec() {
        return numec;
    }

    public void setNumec(String numec) {
        this.numec = numec;
    }

    public String getNumep() {
        return numep;
    }

    public void setNumep(String numep) {
        this.numep = numep;
    }

    public int getStare() {
        return stare;
    }

    public void setStare(int stare) {
        this.stare = stare;
    }

    public String getOras() {
        return oras;
    }

    public void setOras(String oras) {
        this.oras = oras;
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

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    @Override
    public String toString() {
        return "FullJoinClasses{" +
                "idf='" + idf + '\'' +
                ", idc='" + idc + '\'' +
                ", idp='" + idp + '\'' +
                ", numef='" + numef + '\'' +
                ", numec='" + numec + '\'' +
                ", numep='" + numep + '\'' +
                ", stare=" + stare +
                ", oras='" + oras + '\'' +
                ", culoare='" + culoare + '\'' +
                ", masa=" + masa +
                ", cantitate=" + cantitate +
                '}';
    }
}
