package org.example.oopprojekt2;

public class Toode {
    private String kirjeldus;
    private double kogus;
    private double hind;
    private String kelleMaksta;

    public Toode(String kirjeldus, double kogus, double hind, String kelleMaksta) {
        this.kirjeldus = kirjeldus;
        this.kogus = kogus;
        this.hind = hind;
        this.kelleMaksta = kelleMaksta;
    }

    public Toode(String kirjeldus, double hind, String kelleMaksta) { /*saab luua ka kogust määramata, siis loetakse koguseks vaikimisi 1*/
        this.kirjeldus = kirjeldus;
        this.kogus = 1;
        this.hind = hind;
        this.kelleMaksta = kelleMaksta;
    }

    public String getKirjeldus() {
        return kirjeldus;
    }

    public double getKogus() {
        return kogus;
    }

    public double getHind() {
        return hind;
    }

    public String getKelleMaksta() {
        return kelleMaksta;
    }

    public void setKelleMaksta(String kelleMaksta) {
        this.kelleMaksta = kelleMaksta;
    }
}
