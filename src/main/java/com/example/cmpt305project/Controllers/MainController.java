/**
 * Name: Charuni Liyanage, Simon Gordon, Olasubomi Badiru
 * Class: CMPT 305 AS01
 * Instructor: Dr. Indratmo Indratmo
 */
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
import com.esri.arcgisruntime.symbology.SimpleFillSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.example.cmpt305project.HelperClasses.FxUtilTest;
import com.example.cmpt305project.PropertyAssessmentHandler.PropertyAssessmentClasses.Address;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.text.NumberFormat;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import com.example.cmpt305project.HelperClasses.AddressTable;

import static com.example.cmpt305project.HelperClasses.ConvexHull.convexHull;
import static eu.hansolo.tilesfx.tools.SunMoonCalculator.EARTH_RADIUS;

public class MainController implements Initializable {

    @FXML
    private StackPane stackPaneMap;
    //-----TableView variables
    @FXML
    private TableView<AddressTable> table;

    @FXML
    private TableColumn<AddressTable, String> address;

    @FXML
    private TableColumn<AddressTable, Integer> propertyValue;

    @FXML
    private TableColumn<AddressTable, String> garage;

    @FXML
    private TableColumn<AddressTable, String> assessmentClass;
    // ---------------------------------------------
    @FXML
    private TextField searchNeighbourhoodTextField;

    @FXML
    private TabPane menuTabPane;
    @FXML
    private Slider mapRangeSlider;
    @FXML
    private Label findMapDescirptionDisplay;

    private Point lastMouseClickPoint;
    private String defaultfindMapDescirptionDisplayText = "Table for property stats when user clicks on map";
    //I added a combo box textfield
    @FXML
    private ComboBox<String> AddressTextField;
    @FXML
    private ComboBox<String> searchNeighbourhoodComboBox;

    @FXML
    private GridPane gridPane;

    @FXML
    private Label neighbourhoodNameLabel;
    @FXML
    private Label neighbourhoodMinLabel;
    @FXML
    private Label neighbourhoodMaxLabel;
    @FXML
    private Label neighbourhoodAverageLabel;
    @FXML
    private Label neighbourhoodRangeLabel;
    @FXML
    private Label propertyCountLabel;

    private final String FILENAME = "Property_Assessment_Data__Current_Calendar_Year__20240111.csv";
    private PropertyAssessments rangedAssessments;

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
    // Function to get all addresses from property assessments
    public List<Address> getAllAddresses() {
        List<Address> allAddresses = new ArrayList<>();

        // Iterate over all property assessments
        for (PropertyAssessment assessment : assessments.getAssessments().values()) {
            // Get the address from each property assessment and add it to the list
            allAddresses.add(assessment.getAddress());
        }

        return allAddresses;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String yourApiKey = "AAPK4a2ea8e3b119411d8e6b4541a5da4a0fRve45bsPCDMyOfiwCmfpepUybznK3-nbE2cQmIyOVa_aOTQZoO3UlMzocbi4xI1g";
        ArcGISRuntimeEnvironment.setApiKey(yourApiKey);

        // Initialize ComboBox for Find by neighbourhood
        searchNeighbourhoodComboBox.setEditable(true);
        List<String> neighborhoodNamesList = assessments.extractNeighborhoodNames();

        // Convert List<String> to ObservableList<String>
        ObservableList<String> neighborhoodNamesObservableList = FXCollections.observableArrayList(neighborhoodNamesList);
        searchNeighbourhoodComboBox.setItems(neighborhoodNamesObservableList);

        // Use auto-completion for combobox
        FxUtilTest.autoCompleteComboBoxPlus(searchNeighbourhoodComboBox, (typedText, item) -> {
            return item.toLowerCase().startsWith(typedText.toLowerCase());
        });

        // Prevents spacebar from autofilling
        ComboBoxListViewSkin<?> comboBoxListViewSkin = new ComboBoxListViewSkin<>(searchNeighbourhoodComboBox);
        searchNeighbourhoodComboBox.getEditor().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.SPACE) {
                event.consume();
            }
        });
        comboBoxListViewSkin.getPopupContent().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.SPACE) {
                event.consume();
            }
        });
        searchNeighbourhoodComboBox.setSkin(comboBoxListViewSkin);

        // Combo box for search by address
        // Initialize ComboBox for Find by neighbourhood
        AddressTextField.setEditable(true);
        List<String> addressList = (List<String>) (Object) extract(assessments, assessment -> (assessment.getAddress().toString()));

        // Convert List<String> to ObservableList<String>
        ObservableList<String> addressObservableList = FXCollections.observableArrayList(addressList);
        AddressTextField.setItems(addressObservableList);

        // Use auto-completion for combobox
        FxUtilTest.autoCompleteComboBoxPlus(AddressTextField, (typedText, item) -> {
            return item.toLowerCase().startsWith(typedText.toLowerCase());
        });

        // BUG FIXED: Prevents spacebar from autofilling
        ComboBoxListViewSkin<?> addressComboBoxListViewSkin = new ComboBoxListViewSkin<>(AddressTextField);
        AddressTextField.getEditor().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.SPACE) {
                event.consume();
            }
        });
        addressComboBoxListViewSkin.getPopupContent().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.SPACE) {
                event.consume();
            }
        });
        AddressTextField.setSkin(addressComboBoxListViewSkin);

        // ------------------------------------------------------------------
        // This is where the autocomplete feature is implemeneted

        // Populate with all address
        // Populate the ObservableList with property addresses
        ObservableList<String> items = FXCollections.observableArrayList();
        // Fetch all addresses from property assessments
        List<Address> allAddresses = getAllAddresses();

        // Add each address to the ObservableList as a string
        for (Address address : allAddresses) {
            // Check if the suite number is empty
            if (address.getSuite().isEmpty()) {
                // If suite number is empty, add only the house number and street name
                items.add(address.getHouseNumber() + " " + address.getStreetName());
            } else {
                // If suite number is not empty, add the full address
                items.add(address.toString());
            }
        }

        // Initialize ComboBox
        AddressTextField.setEditable(true);
        AddressTextField.setItems(items);

        // Use auto-completion for combobox
        FxUtilTest.autoCompleteComboBoxPlus(AddressTextField, (typedText, item) -> {
            // Ignore leading spaces in typed text
            String trimmedTypedText = typedText.stripLeading();
            return item.toLowerCase().startsWith(trimmedTypedText.toLowerCase()) &&
                    item.toLowerCase().indexOf(trimmedTypedText.toLowerCase()) == 0;
        });

        // ---------------------------------------------------------------

        // Setting the map to the MapView
        mapView.setMap(map);
        mapView.setViewpoint(new Viewpoint(53.5461, -113.4937, 72223.819286));

        // Add mouse click event handler
        mapView.setOnMouseClicked(this::handleMapClick);

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
            //add conditoin here for if mouseclick != null
            drawRange(lastMouseClickPoint , value);
            rangedAssessments = assessmentsInRange(value / Float.parseFloat("2500.84"));
            changefindMapDescirptionDisplay(rangedAssessments);
            //action to change size of map range here
        });

    }
    private void changefindMapDescirptionDisplay(PropertyAssessments soughtAssessments){
        int min = soughtAssessments.findMin();
        int max = soughtAssessments.findMax();
        int range = max - min;
        int mean = soughtAssessments.findMean();
        int median = soughtAssessments.findMedian();

        Locale currentLocale = new Locale.Builder().setLanguage("en").setRegion("US").build();
        Currency currentCurrency = Currency.getInstance(currentLocale);
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(currentLocale);

        String retString = "";
        retString += String.format("Statistics of Selected Range\nn = %d", soughtAssessments.getAssessments().size());
        retString += "\nmin = "+ currencyFormatter.format(min);
        retString += "\nmax = " + currencyFormatter.format(max);
        retString += "\nrange = " + currencyFormatter.format(range);
        retString += "\nmean = " + currencyFormatter.format(mean);
        retString += "\nmedian = " +  currencyFormatter.format(median);

        findMapDescirptionDisplay.setText(retString);
    }

    private void handleMapClick(javafx.scene.input.MouseEvent event) {
        if ((event.isStillSincePress() && event.getButton() == MouseButton.PRIMARY) && (menuTabPane.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("Find on Map"))){
            Point2D point = new Point2D(event.getX(), event.getY());

            Point mapClick = mapView.screenToLocation(point);
            String mapPointString = CoordinateFormatter.toLatitudeLongitude(mapClick, CoordinateFormatter.LatitudeLongitudeFormat.DECIMAL_DEGREES, 4);

            lastMouseClickPoint = CoordinateFormatter.fromLatitudeLongitude(mapPointString, SpatialReferences.getWgs84());
            Float range = Float.valueOf(String.format("%.1f", mapRangeSlider.getValue()));
            drawRange(lastMouseClickPoint , range);
            rangedAssessments = assessmentsInRange(range / Float.parseFloat("2500.84"));
            if (!rangedAssessments.getAssessments().isEmpty()){
                changefindMapDescirptionDisplay(rangedAssessments);
            }
            else{
                findMapDescirptionDisplay.setText("No properties in this range");
            }

        }
    }

    private PropertyAssessments assessmentsInRange(Float range){

        return putAssessmentsinObject(search(assessments, propertyAssessment -> (range >= calculateDistance(lastMouseClickPoint.getY(), lastMouseClickPoint.getX(), propertyAssessment.getLocation().getLatitude(), propertyAssessment.getLocation().getLongitude()))));
    }
    private PropertyAssessments putAssessmentsinObject(List<PropertyAssessment> assessments){
        PropertyAssessments retAssessments  = new PropertyAssessments("sought assessments");
        for (PropertyAssessment assessment: assessments){
            retAssessments.addAssessment(assessment);
        }
        return retAssessments;
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
    public PropertyAssessments filterAssessmentsByNeighbourhood(String neighbourhoodName) {
        Predicate<PropertyAssessment> neighbourhoodPredicate = assessment ->
                assessment.getNeighbourhoodData().getNeighbourhoodname().equalsIgnoreCase(neighbourhoodName);

        return assessments.filterAssessments(neighbourhoodPredicate);
    }

    @FXML
    public void drawNeighbourhood(){
        String neighbourhoodName = FxUtilTest.getComboBoxValue(searchNeighbourhoodComboBox);
        // Filter PropertyAssessments by the selected neighbourhood name
        PropertyAssessments filteredAssessments = filterAssessmentsByNeighbourhood(neighbourhoodName);
        Locale currentLocale = new Locale.Builder().setLanguage("en").setRegion("US").build();
        Currency currentCurrency = Currency.getInstance(currentLocale);
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(currentLocale);

        neighbourhoodNameLabel.setText(neighbourhoodName);
        propertyCountLabel.setText(String.valueOf(filteredAssessments.getAssessments().size()));
        neighbourhoodMinLabel.setText(currencyFormatter.format(filteredAssessments.findMin()));
        neighbourhoodMaxLabel.setText(currencyFormatter.format(filteredAssessments.findMax()));
        neighbourhoodAverageLabel.setText(currencyFormatter.format(filteredAssessments.findMean()));
        neighbourhoodRangeLabel.setText(currencyFormatter.format(filteredAssessments.findMax() - filteredAssessments.findMin()));


        //TextFields.bindAutoCompletion(searchNeighbourhoodComboBox.getEditor(), searchNeighbourhoodComboBox.getItems());
        // Remove all graphics from the graphics overlay
        graphicsOverlayNeighbourhood.getGraphics().clear();

        if (true){ //check if neighbourhoood exists
            //get points

            //draw on graphic overlay
            Graphic item = drawonMap(createPoints(convexPoints(neighbourhoodName)), graphicsOverlayNeighbourhood);
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

    // when address is searched the address is pinned on the map
    public void pinpointProperty(Point point, GraphicsOverlay graphicsOverlay){

        // create a graphic to show the geodesic sector geometry
        // create a red simple marker symbol
        SimpleMarkerSymbol symbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.RED, 20);

        // create a new graphic with a our point and symbol
        Graphic graphic = new Graphic(new Point(  point.getX(), point.getY(), SpatialReferences.getWgs84()), symbol);

        graphicsOverlay.getGraphics().add(graphic);
    }

    // This is where i draw the circle once i locate the address
    @FXML
    public void drawAddress() {
        // grab the string from the textfield area
        String addressSearch = FxUtilTest.getComboBoxValue(AddressTextField);

        // Remove all graphics from the graphics overlay
        graphicsOverlayAddressPane.getGraphics().clear();
        if (true) { //check if neighbourhoood exists

            // ---------------------------------
            // Get all property assessments
            Map<Integer, PropertyAssessment> assessmentsMap = assessments.getAssessments();

            // Create an observable list to store filtered address table data
            ObservableList<AddressTable> filteredAddressTableData = FXCollections.observableArrayList();

            // populate the table on the left with the property assessments
            // --------making address table ----------
            // Iterate over each property assessment
            for (PropertyAssessment assessment : assessmentsMap.values()) {
                // Check if the address matches the search address
                String fullAddress = assessment.getAddress().toString().trim();
                // Remove extra spaces between street number and name
                fullAddress = fullAddress.replaceAll("\\s+", " ");

                // if address search matches the address in the database
                if (fullAddress.trim().toLowerCase().contains(addressSearch.trim().toLowerCase())) {
                    //set the values to the table
                    address.setCellValueFactory(new PropertyValueFactory<AddressTable,String>("address"));
                    propertyValue.setCellValueFactory(new PropertyValueFactory<AddressTable,Integer>("propertyValue"));
                    garage.setCellValueFactory(new PropertyValueFactory<AddressTable,String>("garage"));
                    assessmentClass.setCellValueFactory(new PropertyValueFactory<AddressTable,String>("assessmentClass"));

                    // Extract required data from the property assessment
                    int propertyValue = assessment.getAssessedValue();
                    String garage = assessment.getGarage();
                    String assessmentClass = assessment.getAssessClassInfo().getHighestClass();

                    // Create an AddressTable object with the extracted data
                    AddressTable addressTableEntry = new AddressTable(fullAddress, propertyValue, garage, assessmentClass);

                    // Add the AddressTable entry to the filtered observable list
                    filteredAddressTableData.add(addressTableEntry);
                    // Populate the table with the filtered address table data
                    table.setItems(filteredAddressTableData);
                    pinpointProperty(new Point(assessment.getLocation().getLongitude(), assessment.getLocation().getLatitude()) , graphicsOverlayAddressPane);
                    mapView.setViewpoint(new Viewpoint( assessment.getLocation().getLatitude(), assessment.getLocation().getLongitude(), 1000));

                }
            }
        }
    }



}