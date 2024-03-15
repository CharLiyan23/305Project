package com.example.cmpt305project.Controllers;

import com.example.cmpt305project.HelperClasses.SceneSwitch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    private BorderPane borderPane;
    @FXML
    private AnchorPane anchorPlane;
    private Button button;
    @FXML
    public void setFindonMapButton(ActionEvent event) throws IOException {
        new SceneSwitch("MapPage.fxml", event);

    }
    @FXML
    public void setFindonNeighbourhoodButton(ActionEvent event) throws IOException {
        new SceneSwitch("Neighbourhood.fxml", event);

    }
    @FXML
    public void setFindonAddressButton(ActionEvent event) throws IOException {
        new SceneSwitch("Address.fxml", event);

    }

}