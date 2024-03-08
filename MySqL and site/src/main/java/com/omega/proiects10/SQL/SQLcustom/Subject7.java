package com.omega.proiects10.SQL.SQLcustom;

public class Subject7 {
    private String oras;
    private int nr_cmp;
    private int nr_fnr;
    private int nr_pr;

    public String getOras() {
        return oras;
    }

    public void setOras(String oras) {
        this.oras = oras;
    }

    public int getNr_cmp() {
        return nr_cmp;
    }

    public void setNr_cmp(int nr_cmp) {
        this.nr_cmp = nr_cmp;
    }

    public int getNr_fnr() {
        return nr_fnr;
    }

    public void setNr_fnr(int nr_fnr) {
        this.nr_fnr = nr_fnr;
    }

    public int getNr_pr() {
        return nr_pr;
    }

    public void setNr_pr(int nr_pr) {
        this.nr_pr = nr_pr;
    }

    @Override
    public String toString() {
        return "Subject7{" +
                "oras='" + oras + '\'' +
                ", nr_cmp=" + nr_cmp +
                ", nr_fnr=" + nr_fnr +
                ", nr_pr=" + nr_pr +
                '}';
    }
}
