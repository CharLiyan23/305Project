package com.example.cmpt305project;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.BasemapStyle;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.example.cmpt305project.PropertyAssessmentHandler.CsvPropertyAssessmentDAO;
import com.example.cmpt305project.PropertyAssessmentHandler.PropertyAssessmentClasses.PropertyAssessments;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    //hard coded property assessment dataset
    private final String FILENAME = "Property_Assessment_Data__Current_Calendar_Year__20240111.csv";

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("City of Edmonton Property Assessments");
        stage.setScene(scene);
        stage.show();
        //load assessments from CSV file
        PropertyAssessments assessments = loadAssessments();


    }

    public PropertyAssessments loadAssessments() throws IOException {
        return CsvPropertyAssessmentDAO.loadAssessmentsCSV(FILENAME);
    }
    public static void main(String[] args) {
        launch();
    }

}