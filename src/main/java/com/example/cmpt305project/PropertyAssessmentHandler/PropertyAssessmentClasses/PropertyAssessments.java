/**
 * Name: Charuni Liyanage, Simon Gordon, Olasubomi Badiru
 * Class: CMPT 305 AS01
 * Instructor: Dr. Indratmo Indratmo
 */
package com.example.cmpt305project.PropertyAssessmentHandler.PropertyAssessmentClasses;

import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.*;
import java.util.function.Predicate;

import static java.util.Collections.sort;

public class PropertyAssessments{

    private final String cityName;
    private final Map<Integer, PropertyAssessment> assessments;

    public PropertyAssessments(String name){
        this.cityName = name;
        this.assessments = new HashMap<Integer,PropertyAssessment>();
    }

    public PropertyAssessments(PropertyAssessments target){
        this.cityName = target.cityName;
        this.assessments = new HashMap<Integer,PropertyAssessment>(target.assessments); //change to <integer,propertyassessment>
    }


    /**
    @parameters - String testString - assessment attribute to test (eg: assess classes)
                - String filterString - specific name filter checks for  (eg: RESIDENTIAL)
                - PropertyAssessmentPredicate p: filter predicate
    @return - String showing statistics summary results after filtration

     **/
    public String filterStat(String testString, String filterString, PropertyAssessmentPredicate p){
        String retString = "";
        PropertyAssessments filteredAssessments = new PropertyAssessments("filtered");

        PropertyAssessment index;
        for (int accountNumber: assessments.keySet()){
            index = assessments.get(accountNumber);
            if (p.test(index)){
                filteredAssessments.addAssessment(index);

            }

        }
        Locale currentLocale = new Locale.Builder().setLanguage("en").setRegion("US").build();
        Currency currentCurrency = Currency.getInstance(currentLocale);
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(currentLocale);


        int min = filteredAssessments.findMin();
        int max = filteredAssessments.findMax();
        int range = max - min;
        int mean = filteredAssessments.findMean();
        int median = filteredAssessments.findMedian();
        retString += String.format("Statistics (%s = %s)\nn = %d\n", filterString, testString, filteredAssessments.assessments.size());
        retString += "min = "+ currencyFormatter.format(min);
        retString += "\nmax = " + currencyFormatter.format(max);
        retString += "\nrange = " + currencyFormatter.format(range);
        retString += "\nmean = " +currencyFormatter.format(mean);
        retString += "\nmedian = " +  currencyFormatter.format(median);

        return retString;
    }

    @Override
    public String toString(){
        return cityName;
    }
    public String getName(){
        return cityName;
    }

    public int findMin(){
        int min = assessments.get(Collections.min(assessments.keySet())).getAssessedValue();//assessments.get();
        int temp = 0;
        for (Integer accountNumber: assessments.keySet()){
            temp = assessments.get(accountNumber).getAssessedValue();
            if (temp < min){
                min = temp;
            }
        }
        return min;
    }

    public int findMax(){
        int max = assessments.get(Collections.min(assessments.keySet())).getAssessedValue();
        int temp = 0;
        for (Integer accountNumber: assessments.keySet()){
            temp = assessments.get(accountNumber).getAssessedValue();
            if (temp > max){
                max = temp;
            }
        }
        return max;
    }
    public int findMean(){
        BigInteger sum = BigInteger.valueOf(0);
        BigInteger value = BigInteger.valueOf(0);
        for (Integer accountNumber: assessments.keySet()){
            value = BigInteger.valueOf(assessments.get(accountNumber).getAssessedValue());
            sum = sum.add(value);       // use stream
        }
        if (assessments.size() > 0){
            return  sum.divide(BigInteger.valueOf(assessments.size())).intValue();
        }
        return 0;
    }

    public int findMedian(){
        List<Integer> values = new ArrayList<Integer>();

        for (Integer accountNumber: assessments.keySet()){
            values.add(assessments.get(accountNumber).getAssessedValue());
        }
        values.sort(Integer::compareTo);
        int middle = values.size()/2;
        return values.get(middle);
    }


    public int addAssessment(PropertyAssessment newAssessment){
        try {
            assessments.put(newAssessment.getaccNumber(), newAssessment);
            return 1;

        }
        catch (Exception e) {
            return  -1;
        }
    }

    //getters

    public PropertyAssessment getAssessment(int accountNUmber){
        return assessments.get(accountNUmber);
    }


    @Override
    public int hashCode(){
        return Objects.hash(assessments, cityName);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof PropertyAssessments){
            PropertyAssessments other = new PropertyAssessments((PropertyAssessments) obj);
            return Objects.equals(other.getName(), cityName);
        }
        return false;
    }
    public PropertyAssessments filterAssessments(Predicate <PropertyAssessment> pred){
        PropertyAssessments retAssessments = new PropertyAssessments("Filtered");
        PropertyAssessment temp;
        Map<Integer, PropertyAssessment> allAssessments = assessments;
        for(int index: allAssessments.keySet()){
            temp = allAssessments.get(index);
            if(pred.test(temp)){
                retAssessments.addAssessment(temp);
            }
        }
        return new PropertyAssessments(retAssessments);
    }
    /**
     * Extracts a list of unique neighborhood names from the property assessments.
     *
     * @return List of unique neighborhood names
     */
    public List<String> extractNeighborhoodNames() {
        Set<String> neighborhoodNames = new HashSet<>();

        for (PropertyAssessment assessment : assessments.values()) {
            String neighborhoodName = assessment.getNeighbourhoodData().getNeighbourhoodname();
            if (neighborhoodName != null && !neighborhoodName.isEmpty()) {
                neighborhoodNames.add(neighborhoodName);
            }
        }

        return new ArrayList<>(neighborhoodNames);
    }

    public Map<Integer, PropertyAssessment> getAssessments() {
        return assessments;
    }
}