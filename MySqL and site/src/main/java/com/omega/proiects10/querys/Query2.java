package com.omega.proiects10.querys;

public class Query2 {
    private String oras;
    private int masa1;
    private int masa2;
    public String getOras() {
        return oras;
    }
    public void setOras(String oras) {
        this.oras = oras;
    }
    public int getMasa1() {
        return masa1;
    }
    public void setMasa1(int masa1) {
        this.masa1 = masa1;
    }
    public int getMasa2() {
        return masa2;
    }
    public void setMasa2(int masa2) {
        this.masa2 = masa2;
    }

    @Override
    public String toString() {
        return "Query2{" +
                "oras='" + oras + '\'' +
                ", masa1=" + masa1 +
                ", masa2=" + masa2 +
                '}';
    }
}
