package com.assignment.slcsp.services;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import com.assignment.slcsp.entity.ZipRate;
import com.assignment.slcsp.repository.ZipRateRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ZipServiceImpl implements ZipService {
	
	private final ZipRateRepository zipRateRepository;
	private CSVParser csvParser;

	@Override
	public List<ZipRate> parseCSV(String path) throws IOException {
		Reader fileReader = new FileReader(path);
		csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase());
		Iterable<CSVRecord> csvRecords = csvParser.getRecords();
		
		List<ZipRate> parsedZipRates = new ArrayList<ZipRate>();
		
	      for (CSVRecord csvRecord : csvRecords) {
	    	  ZipRate zipRate = new ZipRate();
	          zipRate.setZipCode(csvRecord.get("zipcode"));
	          zipRate.setState(csvRecord.get("state"));
	          zipRate.setCountyCode(csvRecord.get("county_code"));
	          zipRate.setName(csvRecord.get("name"));
	          zipRate.setRateArea(Long.parseLong(csvRecord.get("rate_area")));
	          if (checkNotEmpty(zipRate)) {
	        	  log.warn("This record has empty column" + zipRate);
	          }
	    	  parsedZipRates.add(zipRate);
	      }
	      return parsedZipRates;
	}

	@Override
	public void saveCSV(String path) {
		try {
            List<ZipRate> parsedZipRates = parseCSV(path);
            parsedZipRates.stream().forEach(zip -> zipRateRepository.save(zip));
        } catch (IOException e) {
        	log.error("Failed saving zip rates to db.\n" + e.getMessage());
        }
	}

	@Override
	public Optional<Long> getRateAreaByZipCode(String zip) {
		List<ZipRate> rateAreas = zipRateRepository.findAllZipCodeRateAreasByZipCode(zip);

        if (rateAreas.size() == 1) {
            return Optional.of(rateAreas.get(0).getRateArea());
        }

        return Optional.empty();
	}
	
	private Boolean checkNotEmpty(ZipRate zipRate) {
        return zipRate.getZipCode().isEmpty() ||
        		zipRate.getCountyCode().isEmpty() ||
        		zipRate.getName().isEmpty() ||
        		zipRate.getRateArea() == 0 ||
        		zipRate.getState().isEmpty();
    }

}
