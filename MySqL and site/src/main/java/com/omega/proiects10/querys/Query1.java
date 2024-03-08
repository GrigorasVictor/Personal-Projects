package com.omega.proiects10.querys;

public class Query1{
    private String string1;
    private String string2;
    private boolean bool1;
    private boolean bool2;

    public String getString1() {
        return string1;
    }
    public String getString2() {
        return string2;
    }
    public boolean isBool1() {
        return bool1;
    }
    public boolean isBool2() {
        return bool2;
    }
    public void setString1(String string1) {
        this.string1 = string1;
    }
    public void setString2(String string2) {
        this.string2 = string2;
    }
    public void setBool1(boolean bool1) {
        this.bool1 = bool1;
    }
    public void setBool2(boolean bool2) {
        this.bool2 = bool2;
    }

    @Override
    public String toString() {
        return "Query1{" +
                "string1='" + string1 + '\'' +
                ", string2='" + string2 + '\'' +
                ", bool1=" + bool1 +
                ", bool2=" + bool2 +
                '}';
    }
}
