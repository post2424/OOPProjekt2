package org.example.oopprojekt2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Test {
    public static void kirjutaTsekki(String failinimi, Ost ost) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(failinimi))) {
            double kogusumma = 0;
            bw.write("Toode:");
            bw.write("\t");
            bw.write("Kogus:");
            bw.write("\t");
            bw.write("Kokku:");
            bw.newLine();
            for (Toode toode : ost.getTooted()) {
                bw.write(toode.getKirjeldus());
                bw.write("\t");
                bw.write(String.valueOf(toode.getKogus()));
                bw.write("\t");
                double summa = Math.round(toode.getKogus()*toode.getHind()*100.0)/100.0;
                kogusumma += summa;
                bw.write(String.valueOf(summa));
                bw.newLine();
            }
            bw.write("Kogusumma: ");
            bw.write("\t");
            bw.write(String.valueOf(Math.round(kogusumma*100.0)/100.0));
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
