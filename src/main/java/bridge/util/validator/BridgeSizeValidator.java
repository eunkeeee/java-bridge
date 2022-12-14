package bridge.util.validator;

public class BridgeSizeValidator extends Validator {
    @Override
    public void validate(String input) throws IllegalArgumentException {
        validateNumeric(input);
        validateRange(input);
    }
}
