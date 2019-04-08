package sample;

import java.util.Iterator;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.control.Label;

import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;


public class Main extends Application  {
    TextField text;
    Button btn, btn2, x, y, z;
    Stage myStage;
    Scene scene, scene2;
    HashMap<String, Scene> sceneMap;
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Pitch");

        myStage = primaryStage;

        btn2 = new Button("Play Pitch");
        sceneMap = new HashMap<String, Scene>();

        x = new Button("2Players");
        y = new Button("3Players");
        z = new Button("4Players");


        btn2.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event){
                myStage.setScene(sceneMap.get("gamePlay"));
            }
        });


        x.setOnAction(event -> {
            myStage.setScene(sceneMap.get("nwescene"));
            PitchDealer pitch = new PitchDealer(2);

            pitch.run(myStage);

        });

        y.setOnAction(event -> {
            myStage.setScene(sceneMap.get("nwescene"));
            PitchDealer pitch = new PitchDealer(3);

            pitch.run(myStage);

        });

        z.setOnAction(event -> {
            myStage.setScene(sceneMap.get("nwescene"));
            PitchDealer pitch = new PitchDealer(4);

            pitch.run(myStage);

        });

        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(70));

        HBox newScene = new HBox(10, x,y,z);

        pane.setCenter(btn2);

        scene = new Scene(pane, 400, 500);
        scene2 = new Scene(newScene, 400, 500);

        sceneMap.put("welcome", scene);
        sceneMap.put("gamePlay", scene2);

        primaryStage.setScene(sceneMap.get("welcome"));
        primaryStage.show();
    }
}
