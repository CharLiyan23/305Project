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

public interface PropertyAssessmentDAO  {

    public PropertyAssessment getByAccountNumber();
    public List<PropertyAssessment> getByNeighbourhod();

}
