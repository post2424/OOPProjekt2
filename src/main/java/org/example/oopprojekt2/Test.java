package org.example.oopprojekt2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
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
    public static void valjastaKaksTsekki(Ost ost, String nimi1, String nimi2) throws IOException { /*praegu annan nimed argumentideks, prolly on parem viis seda teha kuidagi idk*/
        ArrayList<Toode> tooted1 = new ArrayList<>();
        ArrayList<Toode> tooted2 = new ArrayList<>();
        for (Toode toode : ost.getTooted()) {
            if (toode.getKelleMaksta().equals(nimi1)) {
                tooted1.add(toode);
            } else if (toode.getKelleMaksta().equals(nimi2)) { /*siin võiks nt erindi visata kui ei võrdu kummagi nimega*/
                tooted2.add(toode);
            }
        }
        Ost ost1 = new Ost(tooted1);
        Ost ost2 = new Ost(tooted2);
        kirjutaTsekki(nimi1+"-"+LocalDate.now()+".txt", ost1);
        kirjutaTsekki(nimi2+"-"+LocalDate.now()+".txt", ost2);
    }
    public static void main(String[] args) throws IOException {
        Toode toode = new Toode("banaan", 1.02, 1.89, "Jüri");
        Toode toode2 = new Toode("vorst", 2.09, "Mati");
        Toode toode3 = new Toode("jogurt", 1.59, "Mati");
        Toode toode4 = new Toode("muna", 1.79, "Jüri");
        Toode toode5 = new Toode("kartul", 2.71, 0.89, "Jüri");
        ArrayList<Toode> tooted = new ArrayList<>();
        tooted.add(toode);
        tooted.add(toode2);
        tooted.add(toode3);
        tooted.add(toode4);
        tooted.add(toode5);
        Ost ost = new Ost(tooted);
        valjastaKaksTsekki(ost, "Jüri", "Mati");
    }
}
