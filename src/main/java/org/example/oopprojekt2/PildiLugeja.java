package org.example.oopprojekt2;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import java.io.File;
public class PildiLugeja {
    public static void main(String[] args) {
        File pilt = new File("testImage.jpg");
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("tessdata-main"); // relative path to where tessdata is in the repo
        tesseract.setLanguage("eng");
        try{
            String tekst = tesseract.doOCR(pilt);
            System.out.println(tekst);
        } catch (TesseractException e) {
            throw new RuntimeException(e);
        }

    }
}
