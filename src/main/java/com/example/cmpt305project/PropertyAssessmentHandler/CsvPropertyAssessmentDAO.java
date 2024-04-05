/**
 * Name: Charuni Liyanage, Simon Gordon, Olasubomi Badiru
 * Class: CMPT 305 AS01
 * Instructor: Dr. Indratmo Indratmo
 */
package com.example.cmpt305project.PropertyAssessmentHandler;

import com.example.cmpt305project.PropertyAssessmentHandler.PropertyAssessmentClasses.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class CsvPropertyAssessmentDAO implements PropertyAssessmentDAO{

    @Override
    public PropertyAssessment getByAccountNumber() {
        return null;
    }

    @Override
    public List<PropertyAssessment> getByNeighbourhod() {
        return null;
    }

    public static PropertyAssessments loadAssessmentsCSV(String fileName) {

        PropertyAssessments assessments = new PropertyAssessments("Edmonton");


        try {
            String[][] data = readData(fileName);
            assessments = populatePropertyAssessments(data, "Edmonton");
            System.out.println("File read successful");

        } catch (IOException e) {
            System.out.println("Failed to read " + fileName + ", error: "+ e);
        }
        return assessments;

    }

    private static PropertyAssessments populatePropertyAssessments(String[][] data, String assessmentName){

        PropertyAssessments assessments = new PropertyAssessments(assessmentName);
        PropertyAssessment temp;

        int accountNumber = 0;

        //bigger objects
        Address address;
        Location location;
        AssessClass assessClass;
        Neighbourhood neighbourhoodInfo;


        //smaller types
        String houseNumber;
        String suite;
        String garage;
        String streetName;
        int neighbourHoodID = 0;
        String neighbourhood;
        String ward;
        int assessedValue = 0;
        float lat = 0;
        float longt = 0;
        int pointLocation;
        String assessclass1 = "";
        String assessclass2 = "";
        String assessclass3 = "";
        float assessclassperc1, assessclassperc2, assessclassperc3;
        AssessClass assessclassInfo;


        for(String[] row : data){
            try{
                if(!(row[0].isEmpty())){
                    accountNumber = Integer.parseInt(row[0]);
                }
                else{
                    continue; //ignore row since there's no accountNumber
                }

            }
            catch(Exception e){
                System.out.println(e);
            }
            suite = row[1];
            houseNumber = row[2];
            streetName = row[3];
            garage = row[4];
            try{
                if(!(row[5].isEmpty())){
                    neighbourHoodID = Integer.parseInt(row[5]); //try except for int parse
                }
                else{
                    neighbourHoodID = 0;
                }

            }
            catch(Exception e){
                System.out.println(e);
            }

            neighbourhood = row[6];
            ward = row[7];

            try{
                if(!(row[8].isEmpty())){
                    assessedValue = Integer.parseInt(row[8]);  //try except for int parse
                }
                else{
                    assessedValue = 0;
                }

            }
            catch(Exception e){
                System.out.println(e);
            }

            try{
                if(!(row[9].isEmpty())){
                    lat = Float.parseFloat(row[9]);
                }
                else{
                    lat = 0;
                }
            }
            catch(Exception e){
                System.out.println(e);
            }

            try{
                if(!(row[10].isEmpty())){
                    longt = Float.parseFloat(row[10]);
                }
                else{
                    longt = 0;
                }

            }
            catch(Exception e){
                System.out.println(e );
            }

            if (row[12].isEmpty()){
                assessclassperc1 = 0;
            }
            else{
                assessclassperc1 = Float.parseFloat(row[12]);
            }
            if (row[13].isEmpty()){
                assessclassperc2 = 0;
            }
            else{
                assessclassperc2 = Float.parseFloat(row[13]);
            }
            if (row[14].isEmpty()){
                assessclassperc3 = 0;
            }
            else{
                assessclassperc3 = Float.parseFloat(row[14]);
            }


            try{
                if((row.length > 15) && !(row[15].isEmpty())) {
                    assessclass1 = row[15];
                }
                if((row.length > 16) && !(row[16].isEmpty())) {
                    assessclass2 = row[16];
                }
                if((row.length > 17) && !(row[17].isEmpty())) {
                    assessclass3 = row[17];
                }
            }
            catch (Exception e){
                System.out.println("no assessment class found in row: " + e);
            }




            address = new Address(streetName, houseNumber, suite);
            location = new Location(longt, lat);
            neighbourhoodInfo = new Neighbourhood(neighbourHoodID, neighbourhood);
            assessclassInfo = new AssessClass();
            assessclassInfo.addClass(assessclass1, assessclassperc1);
            assessclassInfo.addClass(assessclass2, assessclassperc2);
            assessclassInfo.addClass(assessclass3, assessclassperc3);
            temp = new PropertyAssessment(accountNumber, address, location, garage, neighbourhoodInfo, ward, assessedValue, assessclassInfo);


            assessments.addAssessment(temp);
        }

        return assessments;
    }

    /*** Read the contents of a CSV file and return data as a 2D array of String.
     * @param csvFileName - the CSV file name
     * @return data - the values in the CSV file
     */
    private static String[][] readData(String csvFileName) throws IOException {
        // Create a stream to read the CSV file
        String[][] data;
        int index;
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(csvFileName))) {
            // Skip the header - this assumes the first line is a header
            reader.readLine();

            // Create 2D array to store all rows of data as String
            int initialSize = 100;
            data = new String[initialSize][];

            // Read the file line by line and store all rows into a 2D array
            index = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                // Split a line by comma works for simple CSV files
                String[] values = line.split(",");  //split values in that line into an array of strings

                // Check if the array is full
                if (index == data.length)
                    // Array is full, create and copy all values to a larger array
                    data = Arrays.copyOf(data, data.length * 2);

                data[index++] = values;
            }
        }

        // Remove empty rows in the array and return it
        return Arrays.copyOf(data, index);
    }
}
