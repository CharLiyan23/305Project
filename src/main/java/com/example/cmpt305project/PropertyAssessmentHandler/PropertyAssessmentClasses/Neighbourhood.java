/**
 * Name: Charuni Liyanage, Simon Gordon, Olasubomi Badiru
 * Class: CMPT 305 AS01
 * Instructor: Dr. Indratmo Indratmo
 */
package com.example.cmpt305project.PropertyAssessmentHandler.PropertyAssessmentClasses;

import java.util.Objects;

public class Neighbourhood {
    private final int neighbourhoodID;
    private final String neighbourhoodname;

    public Neighbourhood(int neighbourhoodID, String neighbourhoodname){
        this.neighbourhoodname = neighbourhoodname;
        this.neighbourhoodID = neighbourhoodID;

    }
    public Neighbourhood(Neighbourhood target){
        this.neighbourhoodID = target.getNeighbourhoodID();
        this.neighbourhoodname = target.getNeighbourhoodname();
    }

    public int getNeighbourhoodID() {
        return neighbourhoodID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(neighbourhoodID, neighbourhoodname);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Neighbourhood){
            return (((Neighbourhood) obj).getNeighbourhoodID() == this.neighbourhoodID) && (((Neighbourhood) obj).getNeighbourhoodname().equals(this.neighbourhoodname));
        }
        return false;
    }

    @Override
    public String toString() {
        return getNeighbourhoodname();
    }

    public String getNeighbourhoodname() {
        return neighbourhoodname;
    }

}
