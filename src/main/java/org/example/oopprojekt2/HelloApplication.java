package org.example.oopprojekt2;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Group juur = new Group();
        Scene stseen = new Scene(juur, 320, 240);
        Ost ost = new Ost(Arrays.asList(
                new Toode("banaan", 1.02, 1.89, "Jüri"),
                new Toode("vorst", 2.09, "Mati"),
                new Toode("jogurt", 1.59, "Mati"),
                new Toode("muna", 1.79, "Jüri"),
                new Toode("kartul", 2.71, 0.89, "Jüri")
        ));
        VBox vbox = new VBox();
        for (Toode toode : ost.getTooted()) {
            CheckBox cb = new CheckBox(toode.getKirjeldus()+", kogus: "+toode.getKogus()+", koguhind: "+Math.round(toode.getHind()*toode.getKogus()*100.0)/100.0);
            vbox.getChildren().add(cb);
        }
        Button nupp = new Button("Kinnita");
        vbox.getChildren().add(nupp);
        juur.getChildren().add(vbox);
        nupp.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                for (int i = 0; i < vbox.getChildren().size(); i++) {
                    if (((CheckBox) vbox.getChildren().get(i)).isSelected()) {
                        ost.getTooted().get(i).setKelleMaksta("Mati");
                    }
                }
            }
        });
        System.out.println(ost);
        stage.setTitle("Ostukorvi planeerija");
        stage.setScene(stseen);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}