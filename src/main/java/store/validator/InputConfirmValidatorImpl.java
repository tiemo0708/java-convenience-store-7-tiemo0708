package store.validator;

import store.utils.ErrorMessages;

public class InputConfirmValidatorImpl implements InputConfirmValidator{
    @Override
    public void validateConfirmation(String input) {
        if (!input.equalsIgnoreCase("Y") && !input.equalsIgnoreCase("N")) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_YES_NO_INPUT_MESSAGE);
        }
    }
}
