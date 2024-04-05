/**
 * Name: Charuni Liyanage, Simon Gordon, Olasubomi Badiru
 * Class: CMPT 305 AS01
 * Instructor: Dr. Indratmo Indratmo
 */
package com.example.cmpt305project.PropertyAssessmentHandler.PropertyAssessmentClasses;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.Objects;

public class PropertyAssessment {

    private final int accountNumber;
    private final Address address;
    private final String garage;

    private final Neighbourhood neighbourhoodData;
    private final String ward;
    private final int assessedValue;
    private final Location location;
    private final AssessClass assessClassInfo;


    //Alt constructor
    public PropertyAssessment(PropertyAssessment target){
        this.accountNumber = target.getaccNumber();
        this.address = target.getAddress();
        this.location = target.getLocation();
        this.garage = target.getGarage();
        this.neighbourhoodData = target.getNeighbourhoodData();
        this.ward = target.getWard();
        this.assessedValue = target.getAssessedValue();
        this.assessClassInfo = target.getAssessClassInfo();

    }
    public PropertyAssessment(
        int accountNumber,
        Address address,
        Location location,
        String garage,
        Neighbourhood neighbourhoodData,
        String ward,
        int assessedValue,
        AssessClass assessClassInfo){

        this.accountNumber = accountNumber;
        this.address = address;
        this.location = location;
        this.garage = garage.toLowerCase(Locale.ROOT);;
        this.neighbourhoodData = neighbourhoodData;
        this.ward = ward.toLowerCase(Locale.ROOT);;
        this.assessedValue = assessedValue;
        this.assessClassInfo = assessClassInfo;

    }

    @Override
    public String toString(){
        Locale currentLocale = new Locale.Builder().setLanguage("en").setRegion("US").build();
        Currency currentCurrency = Currency.getInstance(currentLocale);
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(currentLocale);

        return ("\nAccount number = " + this.accountNumber +
                "\nAddress = " + this.address +
                "\nAssessed value = $" + currencyFormatter.format(this.assessedValue)  +
                "\nAssessment class = " + this.assessClassInfo +
                "\nNeighbourhood = " + this.neighbourhoodData +
                "\nLocation = " + this.location
        );
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.accountNumber,
                this.address,
                this.location,
                this.garage,
                this.neighbourhoodData,
                this.ward,
                this.assessedValue,
                this.assessClassInfo);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof PropertyAssessment){
            PropertyAssessment other = new PropertyAssessment((PropertyAssessment) obj);
            return Objects.equals(other.getaccNumber(), accountNumber);
        }
        return false;
    }

    // Getters
    public int getaccNumber(){

        return accountNumber;
    }

    public Address getAddress(){
        return address;
    }

    public Location getLocation(){
        return location;
    }
    public String getGarage(){
        return garage;
    }

    public String getWard(){
        return ward;
    }

    public Neighbourhood getNeighbourhoodData() {
        return neighbourhoodData;
    }

    public int getAssessedValue() {
        return assessedValue;
    }

    public AssessClass getAssessClassInfo() {
        return new AssessClass(assessClassInfo);
    }
}
