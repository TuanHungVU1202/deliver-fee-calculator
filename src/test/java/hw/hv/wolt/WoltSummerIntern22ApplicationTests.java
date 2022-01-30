package hw.hv.wolt;

import hw.hv.wolt.delivery.support.DeliveryFeeCalculatorServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class WoltSummerIntern22ApplicationTests {

	@Autowired
	DeliveryFeeCalculatorServiceImpl deliveryFeeCalculatorService;

	@Test
	void contextLoads() {
	}

	@Test
	@DisplayName("Calculate surcharge based on Cart Value")
	void feeBaseOnCartValue(){
		int minCartVal = 1000;

		int cartVal = 890;
		int surcharge = deliveryFeeCalculatorService.getFeeBaseOnCartValue(0, cartVal, minCartVal);
		assertEquals(110, surcharge);
	}

	@Test
	@DisplayName("Calculate base on Distance")
	void feeBaseOnDistance(){
		int distanceDiff = 1499 - 1000;
		int deliveryFee = deliveryFeeCalculatorService.getFeeBaseOnDistance(200, distanceDiff, 500, 100);
		assertEquals(300, deliveryFee);

		distanceDiff = 1500 - 1000;
		deliveryFee = deliveryFeeCalculatorService.getFeeBaseOnDistance(200, distanceDiff, 500, 100);
		assertEquals(300, deliveryFee);

		distanceDiff = 1501 - 1000;
		deliveryFee = deliveryFeeCalculatorService.getFeeBaseOnDistance(200, distanceDiff, 500, 100);
		assertEquals(400, deliveryFee);
	}

	@Test
	@DisplayName("Calculate base on Number of Items")
	void feeBaseOnItem(){
		int baseItemNo = 4;
		int numberOfItem = 4;
		int surcharge = deliveryFeeCalculatorService.getFeeBaseOnItem(0, numberOfItem, baseItemNo);
		assertEquals(0, surcharge);

		numberOfItem = 2;
		surcharge = deliveryFeeCalculatorService.getFeeBaseOnItem(0, numberOfItem, baseItemNo);
		assertEquals(0, surcharge);

		numberOfItem = 5;
		surcharge = deliveryFeeCalculatorService.getFeeBaseOnItem(0, numberOfItem, baseItemNo);
		assertEquals(50, surcharge);

		numberOfItem = 10;
		surcharge = deliveryFeeCalculatorService.getFeeBaseOnItem(0, numberOfItem, baseItemNo);
		assertEquals(300, surcharge);
	}

	@Test
	@DisplayName("Calculate base on UTC Time")
	void feeBaseOnTime(){
		int constantFee = 100;
		// Not Friday
		String time = "2021-01-16T13:00:00Z";
		int deliveryFee = deliveryFeeCalculatorService.getFeeBaseOnTime(constantFee, time);
		assertEquals(constantFee, deliveryFee);

		time = "2022-01-27T16:59:59Z";
		deliveryFee = deliveryFeeCalculatorService.getFeeBaseOnTime(constantFee, time);
		assertEquals(constantFee, deliveryFee);

		// Friday NOT rush 3PM-7PM
		time = "2022-01-28T13:59:59Z";
		deliveryFee = deliveryFeeCalculatorService.getFeeBaseOnTime(constantFee, time);
		assertEquals(constantFee, deliveryFee);

		time = "2022-01-28T19:00:01Z";
		deliveryFee = deliveryFeeCalculatorService.getFeeBaseOnTime(constantFee, time);
		assertEquals(constantFee, deliveryFee);

		time = "2022-01-28T19:01:01Z";
		deliveryFee = deliveryFeeCalculatorService.getFeeBaseOnTime(constantFee, time);
		assertEquals(constantFee, deliveryFee);

		time = "2022-01-28T19:01:00Z";
		deliveryFee = deliveryFeeCalculatorService.getFeeBaseOnTime(constantFee, time);
		assertEquals(constantFee, deliveryFee);

		// Friday RUSH [3-7PM] with edge cases
		time = "2022-01-28T18:59:59Z";
		deliveryFee = deliveryFeeCalculatorService.getFeeBaseOnTime(constantFee, time);
		int expectedVal = (int) (constantFee * 1.1);
		assertEquals(expectedVal, deliveryFee);

		time = "2022-01-28T19:00:00Z";
		deliveryFee = deliveryFeeCalculatorService.getFeeBaseOnTime(constantFee, time);
		expectedVal = (int) (constantFee * 1.1);
		assertEquals(expectedVal, deliveryFee);

		time = "2022-01-28T15:00:00Z";
		deliveryFee = deliveryFeeCalculatorService.getFeeBaseOnTime(constantFee, time);
		expectedVal = (int) (constantFee * 1.1);
		assertEquals(expectedVal, deliveryFee);

		time = "2022-01-28T15:00:01Z";
		deliveryFee = deliveryFeeCalculatorService.getFeeBaseOnTime(constantFee, time);
		expectedVal = (int) (constantFee * 1.1);
		assertEquals(expectedVal, deliveryFee);

		time = "2022-01-28T15:01:00Z";
		deliveryFee = deliveryFeeCalculatorService.getFeeBaseOnTime(constantFee, time);
		expectedVal = (int) (constantFee * 1.1);
		assertEquals(expectedVal, deliveryFee);
	}
}
