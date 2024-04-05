/**
 * Name: Charuni Liyanage, Simon Gordon, Olasubomi Badiru
 * Class: CMPT 305 AS01
 * Instructor: Dr. Indratmo Indratmo
 */
package com.example.cmpt305project.HelperClasses;

public class AddressTable {
   String address;
   int propertyValue;
   String garage;

   String assessmentClass;


    public AddressTable(String address, int propertyValue, String garage, String assessmentClass) {
        this.address = address;
        this.propertyValue = propertyValue;
        this.garage = garage;
        this.assessmentClass = assessmentClass;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPropertyValue(int propertyValue) {
        this.propertyValue = propertyValue;
    }

    public void setGarage(String garage) {
        this.garage = garage;
    }

    public void setAssessmentClass(String assessmentClass) {
        this.assessmentClass = assessmentClass;
    }

    public String getAddress() {
        return address;
    }

    public int getPropertyValue() {
        return propertyValue;
    }

    public String getGarage() {
        return garage;
    }

    public String getAssessmentClass() {
        return assessmentClass;
    }
}
