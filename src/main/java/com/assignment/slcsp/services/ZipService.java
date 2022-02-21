package com.assignment.slcsp.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.assignment.slcsp.entity.ZipRate;

public interface ZipService {
	
	List<ZipRate> parseCSV(String path) throws IOException;

    void saveCSV(String path);

    Optional<Long> getRateAreaByZipCode(String zipCode);

}
