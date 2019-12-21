package com.inert.controllers;

import com.inert.presets.Preset;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class AddPresetController {

    //public static String presetName;

    //public static String getPresetName() {
       // return presetName;
    //}

    @FXML
    private TextField presetNameInput;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    @FXML
    void onMouseClickedCancelButton(MouseEvent event) {
        presetNameInput.clear();
        MainController.AddPresetControllerHelper.getAddPresetStage().close();
    }

    @FXML
    void onMouseClickedOkButton(MouseEvent event) throws IOException {
        if (presetNameInput.getText().equals("") ||
                presetNameInput.getText() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Attention");
            alert.setContentText("Name text field can't be blank!");
            alert.show();
        } else if(MainController.presetManager.contains(presetNameInput.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Attention");
            alert.setContentText("The preset with this name is already exist!");
            alert.show();
            MainController.AddPresetControllerHelper.setPresetName("");
            presetNameInput.clear();
        } else {
            MainController.AddPresetControllerHelper.setPresetName(presetNameInput.getText());
            presetNameInput.clear();
            MainController.AddPresetControllerHelper.getAddPresetStage().close();
        }
    }

    @FXML
    void onKeyPressedOkButton(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (presetNameInput.getText().equals("") ||
                    presetNameInput.getText() == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Attention");
                alert.setContentText("Name text field can't be blank!");
                alert.show();
            } else if(MainController.presetManager.contains(presetNameInput.getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Attention");
                alert.setContentText("The preset with this name exists already!");
                alert.show();
                MainController.AddPresetControllerHelper.setPresetName("");
                presetNameInput.clear();
            } else {
                MainController.AddPresetControllerHelper.setPresetName(presetNameInput.getText());
                presetNameInput.clear();
                MainController.AddPresetControllerHelper.getAddPresetStage().close();
            }
        }
    }

    @FXML
    void onKeyPressedPresetNameInput(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (presetNameInput.getText().equals("") ||
                    presetNameInput.getText() == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Attention");
                alert.setContentText("Name text field can't be blank!");
                alert.show();
            } else if(MainController.presetManager.contains(presetNameInput.getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Attention all fortnite gamers");
                alert.setContentText("The preset with this name exists already!");
                alert.show();
                MainController.AddPresetControllerHelper.setPresetName("");
                presetNameInput.clear();
            } else {
                MainController.AddPresetControllerHelper.setPresetName(presetNameInput.getText());
                presetNameInput.clear();
                MainController.AddPresetControllerHelper.getAddPresetStage().close();
            }
        }
    }

}
