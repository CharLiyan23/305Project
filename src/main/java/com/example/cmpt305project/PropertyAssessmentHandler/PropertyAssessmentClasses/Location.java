/**
 * Name: Charuni Liyanage, Simon Gordon, Olasubomi Badiru
 * Class: CMPT 305 AS01
 * Instructor: Dr. Indratmo Indratmo
 */
package com.example.cmpt305project.PropertyAssessmentHandler.PropertyAssessmentClasses;

import java.util.Objects;

public class Location {

    private final double latitude;
    private final double longtitude;

    public Location(double longtitude, double latitude){
        this.latitude = latitude;
        this.longtitude = longtitude;

    }
    public Location (Location target){
        this(target.getLongitude(), target.getLatitude());
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.latitude, this.longtitude);
    }

    @Override
    public String toString(){

        String retString = "(";
        retString += this.latitude + ", ";
        retString += this.longtitude + ")";

        return retString;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Location){
            Location other = new Location((Location) obj);
            return Objects.equals(latitude, other.getLatitude()) && Objects.equals(longtitude, other.getLongitude()); //check doubles.compare
                }
        return false;
    }



    //getters

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longtitude;
    }

}
