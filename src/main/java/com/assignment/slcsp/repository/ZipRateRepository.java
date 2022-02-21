package com.assignment.slcsp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.slcsp.entity.ZipRate;

@Repository
public interface ZipRateRepository extends JpaRepository<ZipRate, Long> {
	
	public List<ZipRate> findAllZipCodeRateAreasByZipCode(String zipCode);

}
