/**
 * Name: Charuni Liyanage, Simon Gordon, Olasubomi Badiru
 * Class: CMPT 305 AS01
 * Instructor: Dr. Indratmo Indratmo
 */
package com.example.cmpt305project.PropertyAssessmentHandler.PropertyAssessmentClasses;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class AssessClass{
    private Map<String, Float> assessClasses = new HashMap<String, Float>();

    public AssessClass(){

    }

    public AssessClass(AssessClass target){
        this.assessClasses = target.getAssessClasses();
    }

    public AssessClass(Map<String, Float> assessmentsData){
        this.assessClasses = new HashMap<String, Float>(assessmentsData);

    }
    @Override
    public String toString(){
        StringBuilder retString = new StringBuilder("[");
        for(String className : assessClasses.keySet()){
            retString.append(className).append("  ");
            retString.append(assessClasses.get(className));
            retString.append("%,");
        }
        retString.append("]");
        return retString.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.assessClasses);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof AssessClass){
            return ((AssessClass) obj).assessClasses.equals(this.assessClasses);
        }
        return false;
    }

    public String getHighestClass(){
        String retString = "";
        float perc = 0;
        float tempPerc = 0;
        for (String classes: assessClasses.keySet()){
            tempPerc = assessClasses.get(classes);
            if (tempPerc > perc){
                retString = classes;
                perc = tempPerc;
            }
        }
        return retString;
    }

    public HashMap<String, Float> getAssessClasses(){
        return new HashMap<String, Float> (this.assessClasses);
    }
    public void addClass(String assessClassName, Float assessPerc){
        float prevValue = 0F;
        if (this.assessClasses.containsKey(assessClassName.toLowerCase())){
            try{
                prevValue = this.assessClasses.get(assessClassName);
                this.assessClasses.put(assessClassName, prevValue + assessPerc);
            }
            catch (Exception e){

            }


        }
        else{
            this.assessClasses.put(assessClassName.toLowerCase(Locale.ROOT), assessPerc);
        }
    }

}
