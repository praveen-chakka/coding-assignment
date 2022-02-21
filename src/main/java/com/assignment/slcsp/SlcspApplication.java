package com.assignment.slcsp;


import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.assignment.slcsp.repository.PlanRepository;
import com.assignment.slcsp.repository.ZipRateRepository;
import com.assignment.slcsp.services.PlanService;
import com.assignment.slcsp.services.ZipService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class SlcspApplication implements ApplicationRunner {
	
	public static String zips_path = null;
	public static String plans_path = null;
	public static String slcsp_path = null;
	
	private final PlanService planService;
	private final PlanRepository planRepository;
	private final ZipRateRepository zipRateRepository;
	private final ZipService zipService;

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(SlcspApplication.class, args);
		ctx.close();
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("Entering the application");
		
		for (String temp : args.getOptionNames()) {
	        if (temp.contains("plans")) {
	        	plans_path = temp;
	        } else if (temp.contains("zips")) {
	        	zips_path = temp;
	        } else if (temp.contains("slcsp")) {
	        	slcsp_path = temp;
	        }
	     } 
		log.info("plans_Path is -->  " + plans_path);
		log.info("slcsp_path is -->  " + slcsp_path);
		log.info("zips_Path is -->  " + zips_path);
		
		if (slcsp_path == null)
			slcsp_path = SlcspApplication.class.getClassLoader().getResource("slcsp.csv").getPath();
		if (plans_path == null) 
			plans_path = SlcspApplication.class.getClassLoader().getResource("plans.csv").getPath();
		if (zips_path == null) 
			zips_path = SlcspApplication.class.getClassLoader().getResource("zips.csv").getPath(); 
		
        zipRateRepository.deleteAll();
        planRepository.deleteAll();
        
        zipService.saveCSV(zips_path);
        planService.saveCSV(plans_path);

        planService.printSecondLowestSilverRate(slcsp_path);
        
        planService.prepareSecondLowestSilverRateCSV(slcsp_path);
        
        log.info("Exiting the application");
	}
}
