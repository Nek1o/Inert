package com.inert.controllers;

import com.inert.Main;
import com.inert.programSearch.Program;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddProgramController {

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    private  FileChooser fileChooser = new FileChooser();


    @FXML
    private ChoiceBox<String> programChoiceBox;

    {
        ObservableList<String> programNames = FXCollections.observableArrayList();
        try {
            for (Map.Entry<String, Program> entry : MainController.presetManager.getPrograms().entrySet()) {
                programNames.add(entry.getKey());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        programChoiceBox = new ChoiceBox<>(programNames);
        programChoiceBox.setItems(programNames);

        //programChoiceBox.show();
    }

    @FXML
    private Button fileChooseButton;

    @FXML
    void onMouseClickedCancelButton(MouseEvent event) {
        MainController.AddProgramControllerHelper.setFiles(new ArrayList<>());
        MainController.AddProgramControllerHelper.getAddProgramStage().close();
    }

    @FXML
    void onMouseClickedFileChooseButton(MouseEvent event) {

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("EXE", "*.exe"));
        List<File> fileList = fileChooser.showOpenMultipleDialog(
                MainController.AddProgramControllerHelper.getAddProgramStage());
        if (fileList != null) {
            MainController.AddProgramControllerHelper.setFiles(fileList);
        }

        MainController.AddProgramControllerHelper.getAddProgramStage().close();
    }

    @FXML
    void onMouseClickedOkButton(MouseEvent event) {
        programChoiceBox.hide();
        MainController.AddProgramControllerHelper.setFiles(new ArrayList<>());
        ChoiceBox<String> choiceBox;
        ObservableList<String> programNames = FXCollections.observableArrayList();
        try {
            for (Map.Entry<String, Program> entry : MainController.presetManager.getPrograms().entrySet()) {
                programNames.add(entry.getKey());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        choiceBox = new ChoiceBox<>(programNames);
        choiceBox.setLayoutX(14);
        choiceBox.setLayoutY(98);
        choiceBox.setPrefSize(110, 25);
        mainAnchorPane.getChildren().add(choiceBox);
        choiceBox.show();
        //MainController.AddProgramControllerHelper.getAddProgramStage().close();
    }

    @FXML
    void onMouseClickedProgramChoiceBox(MouseEvent event) {
        ObservableList<String> programNames = FXCollections.observableArrayList();
        try {
            for (Map.Entry<String, Program> entry : MainController.presetManager.getPrograms().entrySet()) {
                programNames.add(entry.getKey());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        programChoiceBox = new ChoiceBox<>(programNames);
    }

}
