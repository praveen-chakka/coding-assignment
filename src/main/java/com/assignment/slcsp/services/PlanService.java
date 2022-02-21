package com.assignment.slcsp.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.assignment.slcsp.entity.Plan;

public interface PlanService {
	
	List<Plan> parseCSV(String path) throws IOException;

    void saveCSV(String path);

    void printSecondLowestSilverRate(String path);
    
    void prepareSecondLowestSilverRateCSV(String path);
    
    List<Plan> getPlansByZipCodeAndMetalLevel(String zipCode, String metalLevel);

    Optional<Double> getSecondLowestSilverRate(String zipCode);

}
