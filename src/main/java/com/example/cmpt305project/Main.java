/**
 * Name: Charuni Liyanage, Simon Gordon, Olasubomi Badiru
 * Class: CMPT 305 AS01
 * Instructor: Dr. Indratmo Indratmo
 */
package com.example.cmpt305project;

import com.example.cmpt305project.PropertyAssessmentHandler.CsvPropertyAssessmentDAO;
import com.example.cmpt305project.PropertyAssessmentHandler.PropertyAssessmentClasses.PropertyAssessments;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;

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
        Image icon = new Image("ImageResources/vecteezy_building-logo-icon-design-vector_15567358_121/vecteezy_building-logo-icon-design-vector_15567358.jpg");

        stage.getIcons().add(icon);
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