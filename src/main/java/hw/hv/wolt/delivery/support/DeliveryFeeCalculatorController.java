package hw.hv.wolt.delivery.support;

import hw.hv.wolt.common.Constants;
import hw.hv.wolt.common.Utils;
import hw.hv.wolt.delivery.DeliveryFeeCalculatorDTO;
import hw.hv.wolt.delivery.DeliveryFeeDTO;
import hw.hv.wolt.exception.DeliveryFeeCalculatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class DeliveryFeeCalculatorController {
    private static final Logger logger = LoggerFactory.getLogger(DeliveryFeeCalculatorController.class);

    @Autowired
    private IDeliveryFeeCalculatorService deliveryFeeCalculatorService;

    /**
     *
     * @param deliveryFeeCalculatorDTO
     * @return deliveryFeeDTO
     * @throws Exception
     */
    @PostMapping(value = "/deliveryfee", headers = "Accept=application/json")
    public ResponseEntity<?> getDeliveryFee(@RequestBody DeliveryFeeCalculatorDTO deliveryFeeCalculatorDTO) throws Exception {
        // Example requestJson
        // {"cart_value": 790, "delivery_distance": 2235, "number_of_items": 4, "time": "2021-10-12T13:00:00Z"}
        String returnStr;
        try {
            int cartValue = deliveryFeeCalculatorDTO.getCartValue();
            int deliverDist = deliveryFeeCalculatorDTO.getDeliveryDistance();
            int numberOfItem = deliveryFeeCalculatorDTO.getNumberOfItems();
            String time = deliveryFeeCalculatorDTO.getTime();

            int deliveryFee = deliveryFeeCalculatorService.calculateDeliveryFee(cartValue, deliverDist, numberOfItem, time);
            DeliveryFeeDTO deliveryFeeDTO = deliveryFeeCalculatorService.getDeliveryFeeDTO(deliveryFee);

            return new ResponseEntity<>(deliveryFeeDTO, HttpStatus.OK);
        } catch (DeliveryFeeCalculatorException deliveryFeeCalculatorException) {
            returnStr = Utils.customMessageObj(Constants.RETURN_MESSAGE_KEY, deliveryFeeCalculatorException.getMessage());
            return new ResponseEntity<>(returnStr, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("Delivery Fee Calculator error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}
