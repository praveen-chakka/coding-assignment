package com.assignment.slcsp.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.assignment.slcsp.entity.ZipRate;
import com.assignment.slcsp.repository.PlanRepository;
import com.assignment.slcsp.repository.ZipRateRepository;

@ExtendWith(MockitoExtension.class)
class ZipServiceImplTest {
	
	@Mock
    private ZipService zipService;

	@Mock
    private PlanRepository planRepository;

	@Mock
    private ZipRateRepository zipRateRepository;

	
	@Test
	void when_GivenPath_Should_ParseCsv() throws IOException {
        String path = getClass().getClassLoader().getResource("zips.csv").getPath();

        List<ZipRate> rateAreas = zipService.parseCSV(path);

	}

}
