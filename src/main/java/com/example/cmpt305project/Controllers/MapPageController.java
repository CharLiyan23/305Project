package com.example.cmpt305project.Controllers;

import com.example.cmpt305project.HelperClasses.SceneSwitch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;


public class MapPageController {
    @FXML
    public void returnToMainPage(ActionEvent event) throws IOException {
        new SceneSwitch("main.fxml", event);

    }
}
