package store.validator;

import store.utils.ErrorMessages;

public class InputPurchaseValidatorImpl implements InputPurchaseValidator{

    private static final String PRODUCT_INPUT_REGEX = "\\[.*-\\d+\\]";

    @Override
    public void validateProductInput(String input) {
        if (!input.matches(PRODUCT_INPUT_REGEX)) {
            throw new IllegalArgumentException(ErrorMessages.PRODUCT_NOT_FOUND_MESSAGE);
        }
    }

    @Override
    public void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_QUANTITY_MESSAGE);
        }
    }
}

