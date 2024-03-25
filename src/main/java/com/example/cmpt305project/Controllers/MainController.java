package com.example.cmpt305project.Controllers;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.geometry.*;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.BasemapStyle;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleFillSymbol;
import com.esri.arcgisruntime.symbology.Symbol;
import com.example.cmpt305project.HelperClasses.CPoint;
import com.example.cmpt305project.HelperClasses.SceneSwitch;
import com.example.cmpt305project.PropertyAssessmentHandler.CsvPropertyAssessmentDAO;
import com.example.cmpt305project.PropertyAssessmentHandler.PropertyAssessmentClasses.Neighbourhood;
import com.example.cmpt305project.PropertyAssessmentHandler.PropertyAssessmentClasses.PropertyAssessment;
import com.example.cmpt305project.PropertyAssessmentHandler.PropertyAssessmentClasses.PropertyAssessments;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.example.cmpt305project.HelperClasses.ConvexHull.convexHull;

public class MainController implements Initializable {

    @FXML
    private StackPane stackPaneMap;

    @FXML
    private TextField searchNeighbourhoodTextField;

    private final String FILENAME = "Property_Assessment_Data__Current_Calendar_Year__20240111.csv";

    PropertyAssessments assessments = loadAssessments(FILENAME);

    // Creating a MapView
    MapView mapView = new MapView();

    // Creating an ArcGISMap with a basemap
    ArcGISMap map = new ArcGISMap(BasemapStyle.ARCGIS_TOPOGRAPHIC);
    GraphicsOverlay graphicsOverlay = new GraphicsOverlay();

    @FXML
    Stage stage;

    public MainController() throws IOException {
    }

    /*
    * Function to search for assessments based on a certain criteria specified in predicate p
    * */
    List<PropertyAssessment> search(PropertyAssessments assessments, Predicate<PropertyAssessment> p){
        return assessments.getAssessments().values().parallelStream().filter(p).distinct().toList();
    }

    /*
     * Function to extract string information as specified in func; expected to be used to collect anything
     * */
    List<Object> extract (PropertyAssessments assessments, Function<PropertyAssessment, Object> func){
        return assessments.getAssessments().values().parallelStream()
                .map(func)
                .distinct().toList();
    }

    public List<CPoint> convexPoints(String neighbourhoodName){

        PropertyAssessments neighbourhoodAssessments = assessments.filterAssessments(new Predicate<PropertyAssessment>() {
            @Override
            public boolean test(PropertyAssessment propertyAssessment) {
                return propertyAssessment.getNeighbourhoodData().getNeighbourhoodname().equalsIgnoreCase(neighbourhoodName);
            }
        });
        List<Object> allpoints = extract(neighbourhoodAssessments, propertyAssessment -> new CPoint(propertyAssessment.getLocation().getLatitude(), propertyAssessment.getLocation().getLongitude()));
        List<CPoint> points = (List<CPoint>) (Object) allpoints;
        return convexHull(points);
    }
    public List<Point> createPoints(List<CPoint> cPoints){
        return cPoints.parallelStream().map(cPoint -> new Point(cPoint.getY(),cPoint.getX())).toList();
    }

    public Graphic drawonMap(List<Point> polygonPoints){
        PointCollection pointCollection = new PointCollection(SpatialReferences.getWgs84());
        pointCollection.addAll(polygonPoints);
        Polygon polygon = new Polygon(pointCollection);

        // Create a simple fill symbol for the polygon
        SimpleFillSymbol polygonFillSymbol = new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, Color.web("#FF4500"), null);

        Graphic polygonGraphic = new Graphic(polygon, polygonFillSymbol);

        // Create a graphic for the polygon and add it to the graphics overlay
        graphicsOverlay.getGraphics().add(polygonGraphic);
        return polygonGraphic;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String yourApiKey = "AAPK4a2ea8e3b119411d8e6b4541a5da4a0fRve45bsPCDMyOfiwCmfpepUybznK3-nbE2cQmIyOVa_aOTQZoO3UlMzocbi4xI1g";
        ArcGISRuntimeEnvironment.setApiKey(yourApiKey);





        // Setting the map to the MapView
        mapView.setMap(map);
        mapView.setViewpoint(new Viewpoint(53.5461, -113.4937, 72223.819286));
        mapView.getGraphicsOverlays().add(graphicsOverlay);

        // Adding the MapView to the StackPane
        stackPaneMap.getChildren().add(mapView);
//        stage.setOnCloseRequest(event -> mapView.dispose());
    }

    private PropertyAssessments loadAssessments(String fileName) throws IOException {
        return CsvPropertyAssessmentDAO.loadAssessmentsCSV(fileName);
    }

    @FXML
    public void testAction() throws IOException{
        stackPaneMap.setStyle("-fx-background-color: #000000");

    }
    @FXML
    public void drawNeighbourhood(){
        String neighbourhodName = searchNeighbourhoodTextField.getText();
        if (true){ //check if neighbourhoood exists
            //get points

            //draw on graphic overlay
            Graphic item = drawonMap(createPoints(convexPoints(neighbourhodName)));
        }

    }

}