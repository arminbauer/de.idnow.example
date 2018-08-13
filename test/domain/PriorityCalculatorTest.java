package domain;

import java.util.Date;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import models.Company;
import models.Identification;
import models.PriorityCalculator;

public class PriorityCalculatorTest {
	
	@Test
	public void testPriorityCalculation()  {
		
		float currentSlaPercentage = 0.8f;
		float slaPercentage = 0.5f;
		long slaTime = 100l;
		long waitingTime = 15l;
		long expectedPriority = 16_006_666l;
		
		Company company = new Company(1l,"",slaTime,slaPercentage,currentSlaPercentage);
		Identification identification = new Identification(1l,"", waitingTime, new Date(), 1l);
		
		assertEquals(expectedPriority, new PriorityCalculator(identification, company).getPriority());
			
	}

}
