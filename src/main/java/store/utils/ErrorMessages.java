package store.utils;

public class ErrorMessages {
    private static final String ERROR_PREFIX = "[ERROR] ";
    public static final String INSUFFICIENT_STOCK_MESSAGE = ERROR_PREFIX+"재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.";
    public static final String INVALID_INPUT_MESSAGE = ERROR_PREFIX+"올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.";
    public static final String INVALID_QUANTITY_MESSAGE = ERROR_PREFIX+"잘못된 입력입니다. 다시 입력해 주세요.";
    public static final String PRODUCT_NOT_FOUND_MESSAGE = ERROR_PREFIX+"존재하지 않는 상품입니다. 다시 입력해 주세요.";
    public static final String INVALID_YES_NO_INPUT_MESSAGE = ERROR_PREFIX+"잘못된 입력입니다. 다시 입력해 주세요.";

    private ErrorMessages() {
    }
}
