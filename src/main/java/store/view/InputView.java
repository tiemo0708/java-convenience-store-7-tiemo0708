package store.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {
    private static final String PRODUCT_INPUT_MESSAGE = "\n구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    private static final String PROMOTION_MESSAGE = "현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)\n";
    private static final String PROMOTION_INVALID_MESSAGE = "현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)\"\n";
    private static final String MEMBERSHIP_INVALID_MESSAGE = "\n멤버십 할인을 받으시겠습니까? (Y/N)";

    private static final String ASK_ADDITIONAL_PURCHASE_MESSAGE = "\n감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)";

    public String getProductInput() {
        System.out.println(PRODUCT_INPUT_MESSAGE);
        return Console.readLine();
    }

    public String confirmPromotionAdditionMessage(String productName, int getQuantity) {
        System.out.printf(PROMOTION_MESSAGE, productName, getQuantity);
        return Console.readLine();
    }

    public String isPromotionInvalid(int remainingQuantity, String productName) {
        System.out.printf(PROMOTION_INVALID_MESSAGE, productName, remainingQuantity);
        return Console.readLine();
    }

    public String isMembershipInvalid() {
        System.out.println(MEMBERSHIP_INVALID_MESSAGE);
        return Console.readLine();
    }

    public String askForAdditionalPurchase() {
        System.out.println(ASK_ADDITIONAL_PURCHASE_MESSAGE);
        return Console.readLine();
    }
}
