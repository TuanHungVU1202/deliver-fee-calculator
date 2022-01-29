package hw.hv.wolt.delivery;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Component
public class DeliveryFeeDTO {

    @NotNull
    @NotEmpty
    @JsonProperty("delivery_fee")
    private int deliveryFee;

    public int getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(int deliveryFee) {
        this.deliveryFee = deliveryFee;
    }
}
