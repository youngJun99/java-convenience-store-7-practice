package store.handler;

import store.constants.Errors;

public class InputValidator {

    private static final String REGEX_OF_PRODUCT_ORDER = "^\\[([가-힣a-zA-Z0-9]+)-\\d+\\](,\\[([가-힣a-zA-Z0-9]+)-\\d+\\])*$";
    private static final String REGEX_OF_CUSTOMER_RESPONSE = "^[YN]$";

    public void validateOrder(String orderInput) {
        if (!orderInput.matches(REGEX_OF_PRODUCT_ORDER)) {
            throw new IllegalArgumentException(Errors.ORDER_INPUT_NOT_VALID.getMessage());
        }
    }

    public void validateCustomerResponse(String responseInput) {
        if (!responseInput.matches(REGEX_OF_CUSTOMER_RESPONSE)) {
            throw new IllegalArgumentException(Errors.INPUT_NOT_VALID.getMessage());
        }
    }
}
