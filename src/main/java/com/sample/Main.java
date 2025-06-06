package com.sample;
import com.gluonhq.attach.pictures.PicturesService;
import com.gluonhq.attach.pictures.impl.*;
import com.gluonhq.attach.display.DisplayService;
import com.gluonhq.attach.util.Platform;
import com.gluonhq.attach.util.Services;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.visual.Swatch;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.*;
import javafx.embed.swing.SwingFXUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static com.gluonhq.charm.glisten.application.AppManager.HOME_VIEW;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import atlantafx.base.theme.PrimerDark;

public class Main extends Application {
    @Override
    public void start(Stage aken) throws IOException {
        Main.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
    if (Platform.isAndroid()) {
        PicturesService.create().ifPresent(service -> {
            service.imageProperty().addListener((obs, ov, image) -> {
                if (image != null) {
                    File fail = service.getImageFile().get();
                    try {
                        Ost ost = PildiLugeja.leiaPildistInfo(fail);
                        uusTseen(aken, ost);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            });
            service.asyncLoadImageFromGallery();
        });
    }else{
        File fail = new File("base64Image.jpg");
        try {
            Ost ost = PildiLugeja.leiaPildistInfo(fail);
            uusTseen(aken, ost);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    }
    private static boolean  juhendKuvatud = false;
    public static void uusTseen(Stage aken, Ost ost){
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(5,5,5,5));
        javafx.scene.control.ScrollPane scrollpane = new javafx.scene.control.ScrollPane(vbox);
        scrollpane.setFitToWidth(true);
        scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); //näitab ainult vertikaalset kerimisriba ja ainult siis, kui sisu ära ei mahu
        scrollpane.setStyle("-fx-background-color: -color-bg-default;");
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double width = screenBounds.getWidth();
        double height = screenBounds.getHeight();
        String kasutaja1 = "Jüri"; //need kasutajalt küsida?
        String kasutaja2 = "Mati";
        Text juhend = new Text("Palun valige tooted, mille eest peaks maksma "+kasutaja2+". " +
                "Tooted, mille eest peaks maksma "+kasutaja1+", jätke valimata." +
                "\nSoovi korral saate pärast 'Arvuta' nupule vajutamist veel oma valikut muuta. " +
                "Kui olete lõpliku valiku teinud, siis 'Kinnita' nupule vajutades väljastatakse mõlemale tšekk.");
        juhend.setFont(new javafx.scene.text.Font(14));
        Button juhendiNupp = new Button("Kuva juhend");
        StackPane.setAlignment(juhendiNupp, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(juhendiNupp, new Insets(10));
        juhendiNupp.setOnMouseReleased(event -> {
            if (!juhendKuvatud)vbox.getChildren().add(juhend);
            else vbox.getChildren().remove(juhend);
            juhendKuvatud = !juhendKuvatud;
            });
        StackPane juur = new StackPane(scrollpane,juhendiNupp);
        Scene stseen = new Scene(juur, width, height);
        //näiteost testimiseks, hiljem loeb pildi pealt



        scrollpane.viewportBoundsProperty().addListener((obs, oldVal, newVal) -> { //wrapib teksti, kui akna laius muuta
            juhend.setWrappingWidth(newVal.getWidth());
        });
        //tooted
        ArrayList<CheckBox> tootevalikud = new ArrayList<>();
        for (Toode toode : ost.getTooted()) {
            CheckBox cb = new CheckBox(toode.getKirjeldus()+", kogus: "+toode.getKogus()+", koguhind: "+toode.koguhind());
            tootevalikud.add(cb);
            vbox.getChildren().add(cb);
        }
        //nupp 'arvuta'
        Button arvuta = new Button("Arvuta");
        vbox.getChildren().add(arvuta);
        //esimese poolt makstav
        HBox maksta1 = new HBox();
        maksta1.setSpacing(7);
        Text nimi1 = new Text(kasutaja1+" peab maksma: ");
        nimi1.setFont(new javafx.scene.text.Font(14));
        Text summa1 = new Text(String.valueOf(arvutaKogusumma(kasutaja1, ost)));
        summa1.setFont(new javafx.scene.text.Font(20));
        maksta1.getChildren().addAll(nimi1, summa1);
        //teise poolt makstav
        HBox maksta2 = new HBox();
        maksta2.setSpacing(7);
        Text nimi2 = new Text(kasutaja2+" peab maksma: ");
        nimi2.setFont(new javafx.scene.text.Font(14));
        Text summa2 = new Text(String.valueOf(arvutaKogusumma(kasutaja2, ost)));
        summa2.setFont(new javafx.scene.text.Font(20));
        maksta2.getChildren().addAll(nimi2, summa2);
        vbox.getChildren().addAll(maksta1, maksta2);
        //nupule 'arvuta' klikkides
        arvuta.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                for (int i = 0; i < tootevalikud.size(); i++) {
                    if (((tootevalikud.get(i)).isSelected())) {
                        ost.getTooted().get(i).setKelleMaksta(kasutaja2);
                    } else {
                        ost.getTooted().get(i).setKelleMaksta(kasutaja1);
                    }
                }
                summa1.setText(String.valueOf(arvutaKogusumma(kasutaja1, ost)));
                summa2.setText(String.valueOf(arvutaKogusumma(kasutaja2, ost)));
            }
        });
        //nupp 'kinnita'
        Button kinnita = new Button("Kinnita");
        vbox.getChildren().addAll(kinnita);
        kinnita.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    valjastaKaksTsekki(ost, kasutaja2, kasutaja1);
                    DateTimeFormatter formaat = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
                    Text kinnitus = new Text("Valmis! Väljastatud on kaks tšekki, mille leiate failidest '"+kasutaja1+"-"+ LocalDateTime.now().format(formaat)+".txt' ja '"+kasutaja2+"-"+LocalDateTime.now().format(formaat)+".txt'");
                    vbox.getChildren().add(kinnitus);
                    kinnitus.setWrappingWidth(scrollpane.getViewportBounds().getWidth());
                    scrollpane.viewportBoundsProperty().addListener((obs, oldVal, newVal) -> {
                        kinnitus.setWrappingWidth(newVal.getWidth());
                    });
                    kinnitus.setFont(new Font(14));
                    java.util.List<Ost> ostud;
                    //ostu salvestamine. mdea kas see on kõige parem viis teha seda lmao
                    try {
                        ObjectInputStream in = new ObjectInputStream(new FileInputStream("ostud.ser"));
                        ostud = (List<Ost>) in.readObject();
                        in.close();
                    } catch (Exception e) {
                        ostud = new ArrayList<>(); // kui faili pole olemas
                    }
                    ostud.add(ost);
                    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("ostud.ser"));
                    out.writeObject(ostud);
                    out.close();
                } catch (IOException e) { // ta ei lase mul throws exception päisesse panna, idk mis teha sellega lmao
                    throw new RuntimeException(e);
                }
            }
        });
        //lõpp
        aken.setTitle("Ostukorvi planeerija");
        aken.setScene(stseen);
        aken.show();
    }
    public static double arvutaKogusumma(String nimi, Ost ost) { //leiab ühe inimese poolt tasutava summa nime järgi
        double kogusumma = 0;
        for (Toode toode : ost.getTooted()) {
            if (toode.getKelleMaksta().equals(nimi)) {
                kogusumma += toode.koguhind();
            }
        }
        return Math.round(kogusumma*100.0)/100.0;
    }
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
                double summa = toode.koguhind();
                kogusumma += summa;
                bw.write(String.valueOf(summa));
                bw.newLine();
            }
            bw.write("Kogusumma: ");
            bw.write("\t");
            bw.write(String.valueOf(Math.round(kogusumma*100.0)/100.0));
        }
    }
    public static void valjastaKaksTsekki(Ost ost, String nimi1, String nimi2) throws IOException {
        ArrayList<Toode> tooted1 = new ArrayList<>();
        ArrayList<Toode> tooted2 = new ArrayList<>();
        for (Toode toode : ost.getTooted()) {
            if (toode.getKelleMaksta().equals(nimi1)) {
                tooted1.add(toode);
            } else if (toode.getKelleMaksta().equals(nimi2)) {
                tooted2.add(toode);
            }
        }
        DateTimeFormatter formaat = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"); // muudan formaati, sest failinimedes ei saa kooloneid kasutada
        kirjutaTsekki(nimi1+"-"+ LocalDateTime.now().format(formaat)+".txt", new Ost(tooted1));
        kirjutaTsekki(nimi2+"-"+LocalDateTime.now().format(formaat)+".txt", new Ost(tooted2));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
