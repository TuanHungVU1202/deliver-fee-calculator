package hw.hv.wolt.delivery.support;

import hw.hv.wolt.common.Utils;
import hw.hv.wolt.delivery.DeliveryFeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryFeeCalculatorServiceImpl implements IDeliveryFeeCalculatorService{

    @Autowired
    private DeliveryFeeDTO deliveryFeeDTO;

    /**
     *
     * @param cartVal cart_value: Value of the shopping cart in cents.
     * @param distance delivery_distance: The distance between the store and customer’s location in meters.
     * @param numberOfItem number_of_items: The number of items in the customer's shopping cart.
     * @param time time: Order time in ISO format.
     * @return final delivery fee
     */
    @Override
    public int calculateDeliveryFee(int cartVal, int distance, int numberOfItem, String time) {
        // Base fee
        // 1e = 100 cents
        int deliveryFee = 200;
        int surcharge = 0;
        int minCartVal = 1000;
        int maxFee = 1500;
//        cartVal = Utils.centsToEur(cartVal);
        // Cart value >= 100e
        if (cartVal >= 10000){
            return 0;
        }

        if (cartVal < minCartVal){
            surcharge = minCartVal - cartVal;
        }

        // Distance base
        // Only add more fee when distance > 1000m
        int distanceDiff = distance - 1000;
        // 500m additional, change if needed
        int additionDist = 500;
        int additionFee = 100;

        // for first 500m additional distance
        if (distanceDiff > 0){
            if (distanceDiff/additionDist == 0){
                deliveryFee += additionFee;
            } else {
                // If the difference is not 0 => greater than the rounded value, add 1eu to base fee
                // E.g 501 % 500 = 1 or 1021 % 500 = 21
                if (distanceDiff % additionDist != 0){
                    deliveryFee += 100;
                }
                // Dividend gave how many time distanceDiff is greater than 500m
                // E.g 501 / 500 = 1 => multiply 1 with 1 addition fee
                // 2022/500 = 4 => multiply 4 with 1 addition fee
                deliveryFee += additionFee * (distanceDiff/additionDist);
            }
        }


        // Item base
        int baseItemNo = 4;
        if (numberOfItem > baseItemNo){
            surcharge += (numberOfItem - baseItemNo) * 50;
        }

        deliveryFee = deliveryFee + surcharge;

        // Time base after got the total
        Utils.parseStrToDateTimeUTC(time);

        if (deliveryFee >= maxFee){
            deliveryFee = maxFee;
        }

        return deliveryFee;
    }

    /**
     *
     * @param deliveryFee
     * @return deliveryFeeDTO
     */
    @Override
    public DeliveryFeeDTO getDeliveryFeeDTO(int deliveryFee) {
        deliveryFeeDTO.setDeliveryFee(deliveryFee);
        return deliveryFeeDTO;
    }
}
