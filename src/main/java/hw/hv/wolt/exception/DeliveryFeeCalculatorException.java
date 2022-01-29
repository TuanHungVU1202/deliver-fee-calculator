package hw.hv.wolt.exception;

import org.springframework.http.HttpStatus;

public class DeliveryFeeCalculatorException extends RuntimeException {
    private HttpStatus httpStatus;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for later retrieval by the {@link #getMessage()}
     *                method.
     */
    public DeliveryFeeCalculatorException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public DeliveryFeeCalculatorException() {
        super();
    }

    public DeliveryFeeCalculatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeliveryFeeCalculatorException(String message) {
        super(message);
    }

    public DeliveryFeeCalculatorException(Throwable cause) {
        super(cause);
    }

}
