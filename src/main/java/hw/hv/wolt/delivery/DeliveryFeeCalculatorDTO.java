package hw.hv.wolt.delivery;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class DeliveryFeeCalculatorDTO {

    @NotNull
    @NotEmpty
    @JsonProperty("cart_value")
    private int cartValue;

    @NotNull
    @NotEmpty
    @JsonProperty("delivery_distance")
    private int deliveryDistance;

    @NotNull
    @NotEmpty
    @JsonProperty("number_of_items")
    private int numberOfItems;

    @NotNull
    @NotEmpty
    @JsonProperty("time")
    private String time;

    public int getCartValue() {
        return cartValue;
    }

    public void setCartValue(int cartValue) {
        this.cartValue = cartValue;
    }

    public int getDeliveryDistance() {
        return deliveryDistance;
    }

    public void setDeliveryDistance(int deliveryDistance) {
        this.deliveryDistance = deliveryDistance;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
