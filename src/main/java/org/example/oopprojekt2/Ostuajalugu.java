package org.example.oopprojekt2;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.*;
import java.util.List;

public class Ostuajalugu extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage lava) throws Exception{
        //andmefaili lugemine
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("ostud.ser"));
        List<Ost> ostud = (List<Ost>) in.readObject();
        in.close();
        //stseeni loomine
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(5,5,5,5));
        vbox.setAlignment(Pos.TOP_CENTER);
        ScrollPane scrollpane = new ScrollPane(vbox);
        scrollpane.setFitToWidth(true);
        scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); //näitab ainult vertikaalset kerimisriba ja ainult siis, kui sisu ära ei mahu
        Scene stseen = new Scene(scrollpane, 510, 400);
        //kui ühtegi ostu pole veel kinnitatud
        if (ostud.isEmpty()) {
            Text tühi = new Text("Ostuajaloos pole veel ühtegi kirjet.");
            vbox.getChildren().add(tühi);
        } else {
            for (Ost ost : ostud) {
                Text ostuKirjeldus = new Text("Ost "+ost.getOstuaeg());
                ostuKirjeldus.setFont(new Font(14));
                scrollpane.viewportBoundsProperty().addListener((obs, oldVal, newVal) -> { //wrapib teksti, kui akna laius muuta
                    ostuKirjeldus.setWrappingWidth(newVal.getWidth());
                });
                ostuKirjeldus.setTextAlignment(TextAlignment.CENTER); // see vbox.setAlignment for some reason ei centeri Text objekte ära, mdea miks
                vbox.getChildren().add(ostuKirjeldus);
                //loetleb ostu tooted
                for (Toode toode : ost.getTooted()) {
                    Text tooteInfo = new Text(toode.toString());
                    vbox.getChildren().add(tooteInfo);
                }
                //info makse kohta
                Text makstud = new Text();
                makstud.setFont(new Font(14));
                makstud.setTextAlignment(TextAlignment.CENTER);
                vbox.getChildren().add(makstud);
                if (ost.kasOnTagasiMakstud()) {
                    makstud.setText("Selle ostu eest on tagasi makstud. ");
                } else {
                    makstud.setText("Selle ostu eest ei ole veel tagasi makstud.");
                    Button maksa = new Button("Maksa tagasi"); // võiks mingi parem nimi olla? aga ma ei mõtle välja
                    vbox.getChildren().add(maksa);
                    maksa.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            ost.maksaTagasi();
                            makstud.setText("Selle ostu eest on tagasi makstud. ");
                            vbox.getChildren().remove(maksa);
                        }
                    });
                }
                scrollpane.viewportBoundsProperty().addListener((obs, oldVal, newVal) -> { //wrapib teksti, kui akna laius muuta
                    makstud.setWrappingWidth(newVal.getWidth());
                });
                //eraldab ostud üksteisest
                Separator joon = new Separator();
                vbox.getChildren().add(joon);
            }
            lava.setOnCloseRequest(windowEvent -> {
                //uuendab andmefaili
                try {
                    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("ostud.ser"));
                    out.writeObject(ostud);
                    out.close();
                } catch (IOException e) { //?? prolly peaks ära muutma
                    e.printStackTrace();
                }
            });
        }
        lava.setTitle("Ostuajalugu");
        lava.setScene(stseen);
        lava.show();
    }
}
