package com.assignment.slcsp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.slcsp.entity.Plan;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
	
	List<Plan> findByPlanId(String planId) throws Exception;
	
	List<Plan> findAllPlansByRateArea(long rateArea);

    List<Plan> findByRateAreaAndMetalLevelOrderByRateAsc(long rateArea, String metalLevel);

}
