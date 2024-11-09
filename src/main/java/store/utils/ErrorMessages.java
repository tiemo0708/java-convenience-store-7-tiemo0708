package store.utils;

public class ErrorMessages {
    private static final String ERROR_PREFIX = "[ERROR] ";
    public static final String INSUFFICIENT_STOCK_MESSAGE = ERROR_PREFIX+"재고가 부족합니다.";
    public static final String INVALID_INPUT_MESSAGE = ERROR_PREFIX+"입력이 올바르지 않습니다. 형식에 맞춰 입력해 주세요. (예: [사이다-2])";
    public static final String INVALID_QUANTITY_MESSAGE = ERROR_PREFIX+"수량은 0보다 커야 합니다.";
    public static final String PRODUCT_NOT_FOUND_MESSAGE = ERROR_PREFIX+"해당 상품이 존재하지 않습니다.";
    private ErrorMessages() {
    }
}
