package com.example.cmpt305project.Controllers;

import com.example.cmpt305project.HelperClasses.SceneSwitch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class NeighbourhoodController {

    @FXML
    public void returnToMainPage(ActionEvent event) throws IOException {
        new SceneSwitch("main.fxml", event);

    }
}
