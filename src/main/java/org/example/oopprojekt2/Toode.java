package org.example.oopprojekt2;

public class Toode {
    private String kirjeldus;
    private double kogus;
    private double hind;

    public Toode(String kirjeldus, double kogus, double hind) {
        this.kirjeldus = kirjeldus;
        this.kogus = kogus;
        this.hind = hind;
    }

    public Toode(String kirjeldus, double hind) { /*saab luua ka kogust määramata, siis loetakse koguseks vaikimisi 1*/
        this.kirjeldus = kirjeldus;
        this.kogus = 1;
        this.hind = hind;
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
}
