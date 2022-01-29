package hw.hv.wolt.delivery.support;

import hw.hv.wolt.delivery.DeliveryFeeDTO;

public interface IDeliveryFeeCalculatorService {

    int calculateDeliveryFee (int cartVal, int distance, int numberOfItem, String time);

    DeliveryFeeDTO getDeliveryFeeDTO(int deliveryFee);
}
