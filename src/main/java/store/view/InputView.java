package store.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {
    private static final String PRODUCT_INPUT_MESSAGE = "\n구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    private static final String PROMOTION_MESSAGE = "현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)\n";


    public String getProductInput() {
        System.out.println(PRODUCT_INPUT_MESSAGE);
        return Console.readLine();
    }

    public String confirmPromotionAdditionMessage(String productName) {
        System.out.printf(PROMOTION_MESSAGE, productName);
        return Console.readLine();
    }
}
