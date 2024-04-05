/**
 * Name: Charuni Liyanage, Simon Gordon, Olasubomi Badiru
 * Class: CMPT 305 AS01
 * Instructor: Dr. Indratmo Indratmo
 */
package com.example.cmpt305project.PropertyAssessmentHandler.PropertyAssessmentClasses;

import java.util.Locale;
import java.util.Objects;

public class Address{
    /*

    Public class to handle addresses
     fields: houseNUmber, streetNumber, suite, any fied may be empty when initializing an instance of cmpt305.PropertyAssessmentClasses.Address
     */
    private String houseNumber;
    private String streetName;
    private String suite = "";

    public Address(Address oldAddress){
        this(oldAddress.getStreetName(), oldAddress.getHouseNumber(), oldAddress.getSuite());
    }

    public Address(String streetName, String houseNumber, String suite){
        this.houseNumber = houseNumber;
        this.suite = suite.toLowerCase(Locale.ROOT);
        this.streetName = streetName.toLowerCase(Locale.ROOT);
    }

    public Address(){

    }

    @Override
    public boolean equals (Object anObject){
        if(anObject instanceof Address){
            return (((Address) anObject).getHouseNumber().equals(this.houseNumber)) && (((Address) anObject).getStreetName().equals(this.streetName))
                    && (((Address) anObject).getSuite().equals(this.suite));
        }
        return false;
    }

    @Override
    public String toString(){

        String retString = "";

        retString += this.suite + "  ";
        retString += this.houseNumber + "  ";
        retString += this.streetName;

        return retString;
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.suite, this.houseNumber, this.streetName);

    }

    //getters

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getSuite() {
        return suite;
    }
}
