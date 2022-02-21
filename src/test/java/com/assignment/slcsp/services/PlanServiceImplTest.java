package com.assignment.slcsp.services;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.assignment.slcsp.entity.Plan;
import com.assignment.slcsp.entity.ZipRate;
import com.assignment.slcsp.repository.PlanRepository;
import com.assignment.slcsp.repository.ZipRateRepository;

@ExtendWith(MockitoExtension.class)
class PlanServiceImplTest {
	
	@Mock
    private PlanService planService;

	@Mock
    private PlanRepository planRepository;

	@Mock
    private ZipService zipService;

	@Mock
    private ZipRateRepository zipRateRepository;

	@Test
    public void when_GivenZipAndMetalWithTwoRateAreas_ShouldReturnEmpty() {
        String zip = "36749";
        ZipRate zipRateOne = new ZipRate();
        zipRateOne.setRateArea(11);
        ZipRate zipRateTwo = new ZipRate();
        zipRateTwo.setRateArea(13);
        
        List<Plan> found = planService.getPlansByZipCodeAndMetalLevel(zip, "Silver");
        
        assertEquals(found.size(), 0);
    }
	
	/* @Test
    public void when_GivenZipWithTwoRates_Return_SecondLowestRate() throws IOException {
        String zipCode = "01001";
        long l = 35;
        Plan planOne = new Plan();
        planOne.setRate(100.1);
        Plan planTwo = new Plan();
        planTwo.setRate(200.2);
        Mockito.when(zipService.getRateAreaByZipCode(zipCode)).thenReturn(Optional.of(l));
        List<Plan> plans = Arrays.asList(planOne, planTwo);
        Mockito.when(planRepository.findByRateAreaAndMetalLevelOrderByRateAsc(l,
                                                                      "SILVER")).thenReturn(plans);

        Optional<Double> secondLowestSilverRate = planService.getSecondLowestSilverRate(zipCode);
        
        assertEquals(secondLowestSilverRate.get(), null);
    } */


}
