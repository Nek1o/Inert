package com.inert;

import com.inert.controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;


public class Main extends Application {

    //TODO сделать новый стэйдж с добавлением пресета (ввод названия, выбор из списка найденных программ, указание новой)
    //TODO сделать отдельный стейдж с выбором программ из найденных или с добавлением через FileChooser

//    private PresetManager presetManager;
//    private ToggleGroup presetToggleGroup;
//    private List<ToggleButton> addPresetToggleButtons;
//
//
//public MainController() {
//    presetManager = new PresetManager();
//    presetToggleGroup = new ToggleGroup();
//    addPresetToggleButtons = new ArrayList<>();
//    addPresetVBox = new VBox();
//}

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("InertInterfaceFXML.fxml"));
        Scene scene = new Scene(root, 720, 338);
        MainController mainController = new MainController();
        primaryStage.setTitle("Inert");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        primaryStage.show();



    }


    @Override
    public void init() {

    }



    public static void main(String[] args) {
        launch(args);
    }
}
