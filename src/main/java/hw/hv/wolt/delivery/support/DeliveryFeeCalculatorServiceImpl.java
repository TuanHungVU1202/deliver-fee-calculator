package hw.hv.wolt.delivery.support;

import hw.hv.wolt.common.Utils;
import hw.hv.wolt.delivery.DeliveryFeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Calendar;

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
    public int calculateDeliveryFee(int cartVal, int distance, int numberOfItem, String time) throws Exception {
        // Base fee
        // 1e = 100 cents
        int deliveryFee = 200;
        int surcharge = 0;
        int minCartVal = 1000;
        int maxFee = 1500;

        // Cart value >= 100e
        if (cartVal >= 10000){
            return 0;
        }

        surcharge = calculateBaseOnCartValue(surcharge, cartVal, minCartVal);

        // Distance base
        // Only add more fee when distance > 1000m
        int distanceDiff = distance - 1000;
        // 500m additional, change if needed
        int additionDist = 500;
        int additionFee = 100;

        deliveryFee = calculateBaseOnDistance(deliveryFee, distanceDiff, additionDist, additionFee);

        // Item base
        int baseItemNo = 4;
        surcharge = calculateBaseOnItem(surcharge, numberOfItem, baseItemNo);

        deliveryFee = deliveryFee + surcharge;

        // Time base after got the total
        deliveryFee = calculateBaseOnTime(deliveryFee, time);


        // Constraint, total fee is always 15e maximum
        if (deliveryFee >= maxFee){
            deliveryFee = maxFee;
        }

        return deliveryFee;
    }

    private int calculateBaseOnCartValue(int surcharge, int cartVal, int minCartVal){
        if (cartVal < minCartVal){
            surcharge = minCartVal - cartVal;
        }
        return surcharge;
    }

    private int calculateBaseOnDistance(int deliveryFee, int distanceDiff, int additionDist, int additionFee){
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
        return deliveryFee;
    }

    private int calculateBaseOnItem(int surcharge, int numberOfItem, int baseItemNo){
        if (numberOfItem > baseItemNo){
            surcharge += (numberOfItem - baseItemNo) * 50;
        }
        return surcharge;
    }

    private int calculateBaseOnTime(int deliveryFee, String time){
        Calendar dateTime = Utils.isoTimeStrToDateTimeUTC(time);
        int hour = dateTime.get(Calendar.HOUR_OF_DAY);
        int minute = dateTime.get(Calendar.MINUTE);
        int second = dateTime.get(Calendar.SECOND);

        if (dateTime.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY){
            if (hour >= 15 && hour <= 19){
                if (!(hour == 19 && (minute > 0 || second > 0))){
                    deliveryFee = (int) (deliveryFee * 1.1);
                }
            }
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

    public int getFeeBaseOnCartValue(int surcharge, int cartVal, int minCartVal){
        return calculateBaseOnCartValue(surcharge, cartVal, minCartVal);
    }

    public int getFeeBaseOnDistance(int deliveryFee, int distanceDiff, int additionDist, int additionFee){
        return calculateBaseOnDistance(deliveryFee, distanceDiff, additionDist, additionFee);
    }

    public int getFeeBaseOnItem(int surcharge, int numberOfItem, int baseItemNo){
        return calculateBaseOnItem(surcharge, numberOfItem, baseItemNo);
    }

    public int getFeeBaseOnTime(int deliveryFee, String time){
        return calculateBaseOnTime(deliveryFee, time);
    }
}
