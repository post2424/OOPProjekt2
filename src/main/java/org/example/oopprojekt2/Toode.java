package org.example.oopprojekt2;

public class Toode {
    private String kirjeldus;
    private double hind;
    private double kogus;

    public Toode(String kirjeldus, double hind, double kogus) {
        this.kirjeldus = kirjeldus;
        this.hind = hind;
        this.kogus = kogus;
    }

    public Toode(String kirjeldus, double hind) { /*saab luua ka kogust määramata, siis loetakse koguseks vaikimisi 1*/
        this.kirjeldus = kirjeldus;
        this.hind = hind;
        this.kogus = 1;
    }
}
