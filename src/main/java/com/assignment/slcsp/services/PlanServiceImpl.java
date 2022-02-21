package com.assignment.slcsp.services;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import com.assignment.slcsp.entity.Plan;
import com.assignment.slcsp.repository.PlanRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PlanServiceImpl implements PlanService {
	
	public static final String METAL_LEVEL_SILVER = "Silver";
	
	
	private final PlanRepository planRepository;
	private final ZipService zipService;
	
	private CSVParser csvParser;

	@Override
	public List<Plan> parseCSV(String path) throws IOException {
		Reader fileReader = new FileReader(path);
		csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase());
		Iterable<CSVRecord> csvRecords = csvParser.getRecords();
		
		List<Plan> parsedPlans = new ArrayList<Plan>();
		
	      for (CSVRecord csvRecord : csvRecords) {
	    	  Plan plan = new Plan();
	    	  plan.setPlanId(csvRecord.get("plan_id"));
	    	  plan.setState(csvRecord.get("state"));
	    	  plan.setMetalLevel(csvRecord.get("metal_level"));
	          plan.setRate(Double.parseDouble(csvRecord.get("rate")));
	          plan.setRateArea(Long.parseLong(csvRecord.get("rate_area")));
	          if (checkNotEmpty(plan)) {
	        	  log.warn("This record has empty column" + plan);
	          }
	          parsedPlans.add(plan);
	      }
	      return parsedPlans;
	}

	@Override
	public void saveCSV(String path) {
		
		try {
            List<Plan> parsed = parseCSV(path);
            parsed.stream().forEach(plan -> planRepository.save(plan));
        } catch (IOException e) {
            log.error("Failed plan rate infomation to db.\n" +e.getMessage());
        }

	}

	@Override
	public void printSecondLowestSilverRate(String path) {
		try (Reader fileReader = new FileReader(path);) {
            csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase());
    		Iterable<CSVRecord> records = csvParser.getRecords();
            
            System.out.println("zipcode,rate");
            records.forEach(record -> {
                String zipcode = record.get("zipcode");
                Optional<Double> rate = getSecondLowestSilverRate(zipcode);
                if (rate.isPresent())
                    System.out.println(zipcode + "," + rate.get());
                else
                    System.out.println(zipcode + ",");
            });
        } catch (FileNotFoundException e) {
        	log.error("Check file path.\n" + e.getMessage());
        } catch (IOException e) {
        	log.error("Make sure file format is correct.\n" +e.getMessage());
        }

	}
	
	@Override
	public void prepareSecondLowestSilverRateCSV(String path) {
		try(Reader fileReader = new FileReader(path);) {
            log.info("Reading the CSV File --> " + path);
            
            csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase());
    		Iterable<CSVRecord> records = csvParser.getRecords();

            FileWriter fileWriter = new FileWriter(path);
            CSVPrinter csvPrinter = CSVFormat.DEFAULT.withHeader("zipcode","rate").print(fileWriter);

            log.info("Writing to CSV --->" + path);
            records.forEach(record -> {
                try {
                    String zipcode = record.get("zipcode");
                    Optional<Double> rate = getSecondLowestSilverRate(zipcode);
                    if (rate.isPresent())
                        csvPrinter.printRecord(zipcode, rate.get());
                    else
                        csvPrinter.printRecord(zipcode, "");
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            });
            fileWriter.close();
            log.info("Completed writing to the csv.");
        } catch (FileNotFoundException e) {
            log.error("Check SLCSP file path.\n" + e.getMessage());
        } catch (IOException e) {
            log.error("Make sure the format of your SLCSP file is correct.\n" + e.getMessage());
        }
	}
	
	@Override
	public Optional<Double> getSecondLowestSilverRate(String zipCode) {
		List<Plan> plans = getPlansByZipCodeAndMetalLevel(zipCode, METAL_LEVEL_SILVER);
        if (plans.size() == 1) {
            return Optional.of(plans.get(0).getRate());
        } else if (plans.size() >= 2) {
            return Optional.of(plans.get(1).getRate());
        } else {
            return Optional.empty();
        }
	}
	
	@Override
	public List<Plan> getPlansByZipCodeAndMetalLevel(String zipCode, String metalLevel) {
		
		Optional<Long> rateAreaByZipCode = zipService.getRateAreaByZipCode(zipCode);

        if (rateAreaByZipCode.isPresent())
            return planRepository.findByRateAreaAndMetalLevelOrderByRateAsc(rateAreaByZipCode.get(),
                                                                            metalLevel);

        return Collections.EMPTY_LIST;
	}
	
	private Boolean checkNotEmpty(Plan plan) {
        return plan.getPlanId().isEmpty() ||
                plan.getMetalLevel().isEmpty() ||
                plan.getRate() == null ||
                plan.getRateArea() == 0 ||
                plan.getState().isEmpty();
    }

}
