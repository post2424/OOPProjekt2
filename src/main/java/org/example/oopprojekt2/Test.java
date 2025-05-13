package org.example.oopprojekt2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Test {
    public static void kirjutaTsekki(String failinimi, Ost ost) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(failinimi))) {
            for (Toode toode : ost.getTooted()) {
                bw.write(toode.getKirjeldus());
                bw.write(';');
                bw.write(String.valueOf(toode.getKogus()));
                bw.write(';');
                bw.write(String.valueOf(Math.round(toode.getKogus()*toode.getHind()*100.0)/100.0));
                bw.newLine();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Toode toode = new Toode("banaan", 1.02, 1.89);
        Toode toode2 = new Toode("vorst", 2.09);
        Toode toode3 = new Toode("jogurt", 1.59);
        ArrayList<Toode> tooted = new ArrayList<>();
        tooted.add(toode);
        tooted.add(toode2);
        tooted.add(toode3);
        Ost ost = new Ost(tooted);
        kirjutaTsekki("test.txt", ost);
    }
}
