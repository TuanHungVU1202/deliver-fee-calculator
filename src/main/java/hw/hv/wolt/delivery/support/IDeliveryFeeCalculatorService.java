package hw.hv.wolt.delivery.support;

import hw.hv.wolt.delivery.DeliveryFeeDTO;

import java.text.ParseException;

public interface IDeliveryFeeCalculatorService {

    int calculateDeliveryFee (int cartVal, int distance, int numberOfItem, String time) throws Exception;

    DeliveryFeeDTO getDeliveryFeeDTO(int deliveryFee);
}
