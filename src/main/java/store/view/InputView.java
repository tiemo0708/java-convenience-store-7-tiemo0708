package store.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {
    private static final String PRODUCT_INPUT_MESSAGE = "\n구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    public String getProductInput() {
        System.out.println(PRODUCT_INPUT_MESSAGE);
        return Console.readLine();
    }
}
