package com.example.cmpt305project.Controllers;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.geometry.*;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.BasemapStyle;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.*;
import com.example.cmpt305project.HelperClasses.CPoint;
import com.example.cmpt305project.PropertyAssessmentHandler.CsvPropertyAssessmentDAO;
import com.example.cmpt305project.PropertyAssessmentHandler.PropertyAssessmentClasses.PropertyAssessment;
import com.example.cmpt305project.PropertyAssessmentHandler.PropertyAssessmentClasses.PropertyAssessments;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.example.cmpt305project.HelperClasses.ConvexHull.convexHull;
import static eu.hansolo.tilesfx.tools.SunMoonCalculator.EARTH_RADIUS;
import static java.lang.Math.abs;

public class MainController implements Initializable {

    @FXML
    private StackPane stackPaneMap;

    @FXML
    private TextField searchNeighbourhoodTextField;

    @FXML
    private TabPane menuTabPane;
    @FXML
    private Slider mapRangeSlider;

    private Point lastMouseClickPoint;

    private final String FILENAME = "Property_Assessment_Data__Current_Calendar_Year__20240111.csv";
    private List<PropertyAssessment> rangedAssessments;

    PropertyAssessments assessments = loadAssessments(FILENAME);

    // Creating a MapView
    MapView mapView = new MapView();

    // Creating an ArcGISMap with a basemap
    ArcGISMap map = new ArcGISMap(BasemapStyle.ARCGIS_TOPOGRAPHIC);
    GraphicsOverlay graphicsOverlayAddressPane = new GraphicsOverlay();
    GraphicsOverlay graphicsOverlayNeighbourhood = new GraphicsOverlay();
    GraphicsOverlay graphicsOverlayMapPane = new GraphicsOverlay();

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
     * Function to extract information as specified in func; expected to be used to collect anything
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

    public Graphic drawonMap(List<Point> polygonPoints, GraphicsOverlay graphicOverlay){
        PointCollection pointCollection = new PointCollection(SpatialReferences.getWgs84());
        pointCollection.addAll(polygonPoints);
        Polygon polygon = new Polygon(pointCollection);

        // Create a simple fill symbol for the polygon
        SimpleFillSymbol polygonFillSymbol = new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, Color.web("#FF4500", .4), new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.BLUE, 2));

        Graphic polygonGraphic = new Graphic(polygon, polygonFillSymbol);

        // Create a graphic for the polygon and add it to the graphics overlay
        graphicOverlay.getGraphics().add(polygonGraphic);
        return polygonGraphic;
    }

    // Method to calculate the distance between two points using the Haversine formula
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // Convert latitude and longitude from degrees to radians
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        // Calculate the differences between the two points' latitudes and longitudes
        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        // Apply the Haversine formula
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.pow(Math.sin(dLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double retdouble = EARTH_RADIUS * c;

        return retdouble; // Distance between the two points in kilometers
    }
    private List<Point> getPoints(Point startPoint, Double range){
        List<Point> retPoints = new ArrayList<>();
        List<PropertyAssessment> filteredAssessments = assessments.getAssessments().values().parallelStream()
                .filter(propertyAssessment -> (calculateDistance( propertyAssessment.getLocation().getLatitude(), propertyAssessment.getLocation().getLongitude(), startPoint.getX(), startPoint.getY()) < range)).toList();

        return retPoints;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String yourApiKey = "AAPK4a2ea8e3b119411d8e6b4541a5da4a0fRve45bsPCDMyOfiwCmfpepUybznK3-nbE2cQmIyOVa_aOTQZoO3UlMzocbi4xI1g";
        ArcGISRuntimeEnvironment.setApiKey(yourApiKey);





        // Setting the map to the MapView
        mapView.setMap(map);
        mapView.setViewpoint(new Viewpoint(53.5461, -113.4937, 72223.819286));

        // Add mouse click event handler
        mapView.setOnMouseClicked(e -> handleMapClick(e));

        // add overlays to mapview
        mapView.getGraphicsOverlays().add(graphicsOverlayAddressPane);
        mapView.getGraphicsOverlays().add(graphicsOverlayNeighbourhood);
        mapView.getGraphicsOverlays().add(graphicsOverlayMapPane);

        // Adding the MapView to the StackPane
        stackPaneMap.getChildren().add(mapView);

        // Add listener to handle tab selection changes -- works
        menuTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab != null) {
                Tab selectedTab = (Tab) newTab;
                if ("Find By Address".equalsIgnoreCase(selectedTab.getText())) {
                    handleTabSelection(graphicsOverlayAddressPane);

                } else if ("Find by Neighbourhood".equalsIgnoreCase(selectedTab.getText())) {
                    handleTabSelection(graphicsOverlayNeighbourhood);
                }
                else if ("Find on Map".equalsIgnoreCase(selectedTab.getText())) {
                    handleTabSelection(graphicsOverlayMapPane);
                }
            }
        });


        //configure slider
        mapRangeSlider.valueProperty().addListener((ObservableValue<? extends Number > num, Number oldVal, Number newVal) -> {
            Float value = Float.valueOf(String.format("%.1f", newVal));
            drawRange(lastMouseClickPoint , value);
            rangedAssessments = assessmentsInRange(value / Float.parseFloat("3280.84"));
            System.out.print("\nSize = " + rangedAssessments.size());
            //action to change size of map range here
        });


//        stage.setOnCloseRequest(event -> mapView.dispose());
    }

    private void handleMapClick(javafx.scene.input.MouseEvent event) {
        if ((event.isStillSincePress() && event.getButton() == MouseButton.PRIMARY) && (menuTabPane.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("Find on Map"))){
            Point2D point = new Point2D(event.getX(), event.getY());

            Point mapClick = mapView.screenToLocation(point);
            String mapPointString = CoordinateFormatter.toLatitudeLongitude(mapClick, CoordinateFormatter.LatitudeLongitudeFormat.DECIMAL_DEGREES, 4);

            lastMouseClickPoint = CoordinateFormatter.fromLatitudeLongitude(mapPointString, SpatialReferences.getWgs84());
            Float range = Float.valueOf(String.format("%.1f", mapRangeSlider.getValue()));
            drawRange(lastMouseClickPoint , range);
            rangedAssessments = assessmentsInRange(range / Float.parseFloat("3280.84"));

            System.out.print("\nSize = " + rangedAssessments.size());
        }
    }

    private List<PropertyAssessment> assessmentsInRange(Float range){

        return search(assessments, propertyAssessment -> (range >= calculateDistance(lastMouseClickPoint.getY(), lastMouseClickPoint.getX(), propertyAssessment.getLocation().getLatitude(), propertyAssessment.getLocation().getLongitude())));
    }

    private void handleTabSelection(GraphicsOverlay graphicsOverlay) {
        // Clear existing GraphicsOverlays
        mapView.getGraphicsOverlays().clear();
        // Add the selected GraphicsOverlay
        mapView.getGraphicsOverlays().add(graphicsOverlay);
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
        // Remove all graphics from the graphics overlay
        graphicsOverlayNeighbourhood.getGraphics().clear();

        if (true){ //check if neighbourhoood exists
            //get points

            //draw on graphic overlay
            Graphic item = drawonMap(createPoints(convexPoints(neighbourhodName)), graphicsOverlayNeighbourhood);
        }

    }

    public void drawRange(Point point, Float distance){
        //only displays to the Find o Map graphics overlay
        // clear any displayed graphic
        graphicsOverlayMapPane.getGraphics().clear();

        // get the point as a web mercator point
        Point mapPoint = (Point) GeometryEngine.project( point,  SpatialReferences.getWebMercator());

        // 500 metre buffer
        Polygon polygon = GeometryEngine.buffer( mapPoint, distance); //replace distance with value from slider

        // some styles
        SimpleLineSymbol simpleLineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.BLUE, 5);
        SimpleFillSymbol simpleFillSymbol = new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, Color.web("#FF4500", .4), simpleLineSymbol);

        // make a graphic and draw it!
        Graphic graphic = new Graphic(polygon, simpleFillSymbol);
        graphicsOverlayMapPane.getGraphics().add(graphic);

    }


    public void pinpointProperty(Point point, GraphicsOverlay graphicsOverlay){

        // create a graphic to show the geodesic sector geometry
        // create a red simple marker symbol
        SimpleMarkerSymbol symbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.RED, 10);

        // create a new graphic with a our point and symbol
        Graphic graphic = new Graphic(new Point(  point.getX(), point.getY(), SpatialReferences.getWgs84()), symbol);

        graphicsOverlay.getGraphics().add(graphic);
    }

}