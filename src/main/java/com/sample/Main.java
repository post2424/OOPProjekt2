package com.sample;
import com.gluonhq.attach.pictures.PicturesService;
import com.gluonhq.attach.display.DisplayService;
import com.gluonhq.attach.util.Services;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.visual.Swatch;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.embed.swing.SwingFXUtils;

import java.awt.image.BufferedImage;


import static com.gluonhq.charm.glisten.application.AppManager.HOME_VIEW;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class Main extends Application {
   private static Ost[] ostud;
    @Override
    public void start(Stage aken) {
        Button valiPilt = new Button("Vali kuvatõmmis galeriist");
        valiPilt.setOnMouseClicked(e -> {valiPilt.fire();valiPiltGaleriist();});
        valiPilt.setOnTouchReleased(e -> {valiPilt.fire();valiPiltGaleriist();});
        StackPane root = new StackPane(valiPilt);
        Scene scene = new Scene(root, 300, 200);
        aken.setTitle("COOP tsekki arveldaja");
        aken.setScene(scene);
        aken.show();
    }
    public static void valiPiltGaleriist(){
        ImageView imageView = new ImageView();
        PicturesService. create().ifPresent(service -> {
            service. imageProperty().addListener((obs, ov, image) -> {
                if (image != null) {
                    imageView.setImage(image);
                    service.getImageFile().ifPresent(file -> {
                        ostud  = PildiLugeja.leiaPildistInfo(file);
                    });
                }
            });
            service.asyncLoadImageFromGallery();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
