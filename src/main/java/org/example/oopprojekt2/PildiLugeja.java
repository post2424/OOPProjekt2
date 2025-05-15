package org.example.oopprojekt2;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class PildiLugeja {
    public static void main(String[] args) {
        File pilt = new File("testImage.jpg");
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("tessdata-main");
        tesseract.setLanguage("est");
        String tekst = "";
        try {
            tekst = tesseract.doOCR(pilt);
        } catch (TesseractException e) {
            throw new RuntimeException(e);
        }
        try (FileWriter writer = new FileWriter("output.txt")) {
            writer.write(tekst);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> tükeldatudTekst = Arrays.asList(tekst.split("\\R"));
        //Leiab nimekirjra alguse ja lõpu;
        int nimekirjaAlgus = -1;
        int nimekirjaLõpp;
        for (int i = 0; i < tükeldatudTekst.size(); i++) {
            if (nimekirjaAlgus==-1) {
                if (tükeldatudTekst.get(i).contains("Toode|Kogus"))nimekirjaAlgus = i+1;
            }else if (tükeldatudTekst.get(i).contains("Kokku")) {nimekirjaLõpp = i-1;break;}
        }
        /*
        Ost ost = new Ost();
        for (int i = nimekirjaAlgus; i <= nimekirjaLõpp; i++) {
            String
            ost.lisaToode(new Toode())
        }

         */

    }

}
