package com.example.cmpt305project.Controllers;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.BasemapStyle;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.example.cmpt305project.HelperClasses.SceneSwitch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private StackPane stackPaneMap;


    @FXML
    Stage stage;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String yourApiKey = "AAPK4a2ea8e3b119411d8e6b4541a5da4a0fRve45bsPCDMyOfiwCmfpepUybznK3-nbE2cQmIyOVa_aOTQZoO3UlMzocbi4xI1g";
        ArcGISRuntimeEnvironment.setApiKey(yourApiKey);



        // Creating a MapView
        MapView mapView = new MapView();

        // Creating an ArcGISMap with a basemap
        ArcGISMap map = new ArcGISMap(BasemapStyle.ARCGIS_TOPOGRAPHIC);

        // Setting the map to the MapView
        mapView.setMap(map);
        mapView.setViewpoint(new Viewpoint(34.027, -118.805, 72223.819286));

        // Adding the MapView to the StackPane
        stackPaneMap.getChildren().add(mapView);
//        stage.setOnCloseRequest(event -> mapView.dispose());
    }

    @FXML
    public void testAction() throws IOException{
        stackPaneMap.setStyle("-fx-background-color: #000000");

    }

}