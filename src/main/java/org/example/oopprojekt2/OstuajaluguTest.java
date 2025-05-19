package org.example.oopprojekt2;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OstuajaluguTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage lava) throws Exception{
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("ostud.ser"));
        List<Ost> ostud = (List<Ost>) in.readObject();
        in.close();
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(5,5,5,5));
        Scene stseen = new Scene(vbox, 510, 400);
        if (ostud.isEmpty()) {
            Text tühi = new Text("Ostuajaloos pole veel ühtegi kirjet.");
            vbox.getChildren().add(tühi);
        } else {
            for (Ost ost : ostud) {
                Text ostuKirjeldus = new Text("Ost "+ost.getOstuaeg());
                ostuKirjeldus.setFont(new Font(14));
                vbox.getChildren().add(ostuKirjeldus);
                for (Toode toode : ost.getTooted()) {
                    Text tooteInfo = new Text(toode.toString());
                    vbox.getChildren().add(tooteInfo);
                }
                Text makstud = new Text();
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
