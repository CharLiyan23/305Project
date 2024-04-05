/**
 * Name: Charuni Liyanage, Simon Gordon, Olasubomi Badiru
 * Class: CMPT 305 AS01
 * Instructor: Dr. Indratmo Indratmo
 */
package com.example.cmpt305project.HelperClasses;

import com.example.cmpt305project.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
public class SceneSwitch {

    public SceneSwitch(String fxml, ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml));
        Stage prevStage = (Stage)((Node)event.getSource()).getScene().getWindow();

        // Store the previous stage properties
        double previousWidth = prevStage.getWidth();
        double previousHeight = prevStage.getHeight();
        double previousX = prevStage.getX();
        double previousY = prevStage.getY();

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        // Apply the previous stage properties to the new stage
        stage.setWidth(previousWidth);
        stage.setHeight(previousHeight);
        stage.setX(previousX);
        stage.setY(previousY);
        stage.show();
    }
}
