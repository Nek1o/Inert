package com.inert.controllers;

import com.inert.presets.Preset;
import com.inert.presets.PresetManager;
import com.inert.processes.PresetStarter;
import com.inert.programSearch.Program;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    static PresetManager presetManager;

    {
        try {
            presetManager = new PresetManager();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ToggleGroup presetToggleGroup = new ToggleGroup();
    private List<ToggleButton> addPresetToggleButtons = new ArrayList<>();


    public MainController() {
    }

    @FXML
    private VBox mainVBox;

    @FXML
    private SplitPane mainSplitPane;

    @FXML
    private Font x1;

    @FXML
    private Color x2;

    @FXML
    private Button deleteProfile;

    @FXML
    private Button addProfile;

    @FXML
    private Button deleteAction;

    @FXML
    private Button addProgram;

    @FXML
    private Button addWeb;

    @FXML
    private TextArea informationText;

    @FXML
    private ScrollPane presetScrollPane;

    @FXML
    private Button deletePreset;

    @FXML
    private Button addPreset;

    @FXML
    private Button startPreset;

    @FXML
    private Font x11;

    @FXML
    private Color x21;

    @FXML
    private Font x3;

    @FXML
    private Color x4;

    @FXML
    private VBox addPresetVBox = new VBox();

    @FXML
    public void initialize() {
        Thread task = new Thread(() -> {
            while (true) {
                try {
                    // Если текст, введённый в стэйдже AddPreset не нуль
                    if (AddPresetControllerHelper.getPresetName() != null &&
                            !AddPresetControllerHelper.getPresetName().isEmpty()) {
                        // Новый javafx поток для корректного создания кнопки
                        Platform.runLater(() -> {
                            // Тут метод создания конпке
                            createPresetToggleButton();
                            System.out.println(AddPresetControllerHelper.getPresetName());
                        });
                    }
                    // Вывод информации в информационное поле
                    String name = "";
                    if (addPresetToggleButtons.size() > 0) {
                        for (ToggleButton button : addPresetToggleButtons) {
                            if (button.isSelected()) {
                                name = button.getText();
                                break;
                            }
                        }
                    }
                    if (presetManager.contains(name)) {
                        Preset tempPreset = presetManager.getPreset(name);
                        String info = tempPreset.toString();
                        informationText.setText(info);
                    }
                    // Добавление файлов из fileChooser'а
                    if (!AddProgramControllerHelper.files.isEmpty()) {
                        for (File file : AddProgramControllerHelper.files) {
                            if (!presetManager.getPreset(name).contains(file.getAbsolutePath())) {
                                presetManager.getPreset(name).addProgram(new Program(file.getName(), file.getAbsolutePath()));
                            }
                        }
                        AddProgramControllerHelper.files = new ArrayList<>();
                    }
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        task.start();
    }

    @FXML
    void onMouseClickAddPreset(MouseEvent event) throws IOException {
        AddPresetControllerHelper helper = new AddPresetControllerHelper();
        // Добавление происходит только при повторном нажатии на клави
        // Потому что на момент первого вызова строка на входе пустая.
        // Она заполняется только в самом стейдже.
        // Нужно её вытащить где-то
    }


    @FXML
    void onMouseClickAddProgram(MouseEvent event) throws IOException {
        boolean isSelected = false;
        for (ToggleButton button : addPresetToggleButtons) {
            if (button.isSelected()) {
                isSelected = true;
                break;
            }
        }
        if (isSelected) {
            AddProgramControllerHelper helper = new AddProgramControllerHelper();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Attention");
            alert.setContentText("You should choose the preset first!");
            alert.show();
        }
    }

    @FXML
    void onMouseClickDeleteAction(MouseEvent event) {

    }

    @FXML
    void onMouseClickDeletePreset(MouseEvent event) {

    }

    @FXML
    void onMouseClickDeleteWeb(MouseEvent event) {

    }

    @FXML
    void onMouseClickStartPreset(MouseEvent event) {
        for (ToggleButton button : addPresetToggleButtons) {
            if (button.isSelected())
                PresetStarter.start(presetManager.getPreset(button.getText()));
        }
    }

    @FXML
    void test(ActionEvent event) {

    }


    void createPresetToggleButton() {
        // Чтобы двигать не только скроллбаром
        presetScrollPane.setPannable(true);
        presetScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        // Создание нового пресета
        Preset preset = new Preset(AddPresetControllerHelper.getPresetName());
        if (!presetManager.contains(AddPresetControllerHelper.getPresetName())) {
            presetManager.addPreset(preset);
            // Делаем саму кнопочку
            ToggleButton presetButton = new ToggleButton(AddPresetControllerHelper.getPresetName());
            presetButton.setPrefHeight(35);
            //presetButton.setPrefHeight(50);
            presetButton.setPrefWidth(200);
            presetButton.setAlignment(Pos.CENTER);
            presetButton.setUserData(AddPresetControllerHelper.getPresetName());
            presetButton.setToggleGroup(presetToggleGroup);


            addPresetVBox.setAlignment(Pos.CENTER);
            addPresetVBox.setSpacing(10.);

            addPresetToggleButtons.add(presetButton);
            for (ToggleButton button : addPresetToggleButtons) {
                if (!addPresetVBox.getChildren().contains(button))
                    addPresetVBox.getChildren().add(button);
            }
            presetScrollPane.setContent(addPresetVBox);
        }
        AddPresetControllerHelper.setPresetName("");
    }


    // класс для нормальной работы со вторым стэйджом и контроллером
    static class AddPresetControllerHelper {
        private static String presetName;
        private static Stage addPresetStage;

        AddPresetControllerHelper() throws IOException {
            addPresetStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("..\\AddPresetFXML.fxml"));
            Scene scene = new Scene(root, 300, 150);
            addPresetStage.setTitle("Add preset");
            addPresetStage.setResizable(false);
            addPresetStage.setScene(scene);
            addPresetStage.centerOnScreen();
            addPresetStage.initModality(Modality.APPLICATION_MODAL);
            addPresetStage.show();
        }

        public static Stage getAddPresetStage() {
            return addPresetStage;
        }

        public static String getPresetName() {
            return presetName;
        }

        public static void setPresetName(String newPresetName) {
            presetName = newPresetName;
        }
    }

    static class AddProgramControllerHelper {

        private static String programName;
        private static Stage addProgramStage;
        private static List<File> files = new ArrayList<>();
        private static Scene scene;

        private static Parent root;

        public AddProgramControllerHelper() throws IOException {
            AddProgramController controller = new AddProgramController();
            addProgramStage = new Stage();
            root = FXMLLoader.load(getClass().getResource("..\\AddProgramFXML.fxml"));
            scene = new Scene(root, 300, 175);
            addProgramStage.setTitle("Add program");
            addProgramStage.setResizable(false);
            addProgramStage.setScene(scene);
            addProgramStage.centerOnScreen();
            addProgramStage.initModality(Modality.APPLICATION_MODAL);
            addProgramStage.show();
        }

        public static Stage getAddProgramStage() {
            return addProgramStage;
        }

        public static List<File> getFiles() {
            return files;
        }

        public static Parent getRoot() {
            return root;
        }

        public static Scene getScene() {
            return scene;
        }

        public static void setFiles(List<File> fileList) {
            files = fileList;
        }
    }

}
