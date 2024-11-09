package store.utils;

public class ProductInputParser {

    public static String extractProductName(String input) {
        return input.substring(1, input.indexOf("-"));
    }

    public static int extractQuantity(String input) {
        return Integer.parseInt(input.substring(input.indexOf("-") + 1, input.indexOf("]")));
    }
}
