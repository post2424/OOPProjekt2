package com.sample;
import java.io.*;
import java.nio.file.Files;
import java.util.*;
import okhttp3.*;

public class PildiLugeja {
    private static final String API_KEY = "K84337961188957K84337961188957";
    private static final String OCR_URL = "https://api.ocr.space/parse/image";
    public static String tekst;

    private static String OCR(File pilt) throws IOException {
        byte[] bytes = Files.readAllBytes(pilt.toPath());
        String base64 = Base64.getEncoder().encodeToString(bytes);
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("apikey", API_KEY)
                .addFormDataPart("base64Image", "data:image/png;base64," + base64)
                .addFormDataPart("language", "swe")
                .addFormDataPart("isOverlayRequired", "false")
                .addFormDataPart("isTable","true")
                .build();
        Request request = new Request.Builder()
                .url(OCR_URL)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
    public static String[] parseText(String s){
        String tekstiAlgus  = "Toode\\tKogus\\tKokku";
        int infoAlgus = s.lastIndexOf(tekstiAlgus);
        int infoLõpp = s.lastIndexOf("Soodustused");
        String vahepealne = s.substring(infoAlgus+tekstiAlgus.length(),infoLõpp);
        String[] väljasta = vahepealne.split("\\\\t.{0,1}\\\\r\\\\n");
        return väljasta;
    }
    public static void prindi(String[] arr ){
        for (String el: arr){
            System.out.println("AA");
            System.out.println(el);
        }
    }
    public static Ost leiaPildistInfo(File pilt) throws IOException {

        tekst = OCR(pilt);
        String[] tükeldatudTekst = parseText(tekst);
        prindi(tükeldatudTekst);
        Ost välja = new Ost(new ArrayList<>());
        for (int i = 1; i < tükeldatudTekst.length-1; i++) {
            String praeguneTekst = tükeldatudTekst[i];
            String[] tükeldatud2 = praeguneTekst.split("\\\\t");
            String nimetus = tükeldatud2[0];
            System.out.println(tükeldatud2[1]);
            Double kogus = Double.parseDouble(tükeldatud2[1].replaceAll(",","."));
            Double hind = Double.parseDouble(tükeldatud2[2].replaceAll(",","."));
            välja.getTooted().add(new Toode(nimetus,kogus,hind/kogus, "Jüri"));
        }
        return välja;
    }


}
