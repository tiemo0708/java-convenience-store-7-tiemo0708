package store.controller;

import store.domain.Promotion;
import store.service.PromotionService;
import store.validator.InputConfirmValidator;
import store.view.InputView;
import store.view.OutputView;

public class PromotionController {
    private final PromotionService promotionService;
    private final OutputView outputView;
    private final InputView inputView;
    private final InputConfirmValidator inputConfirmValidator;

    public PromotionController(PromotionService promotionService, OutputView outputView, InputView inputView, InputConfirmValidator inputConfirmValidator) {
        this.promotionService = promotionService;
        this.outputView = outputView;
        this.inputView = inputView;
        this.inputConfirmValidator = inputConfirmValidator;
    }

    public int handlePromotion(String productName, int quantity, String promotionName) {
        boolean validInput = false;
        while (!validInput) {
            try {
                Promotion applicablePromotion = validatePromotionDate(promotionName);

                if (applicablePromotion == null) {
                    return quantity; // 프로모션이 없으면 현재 수량 그대로 반환
                }

                quantity = applyPromotionLogic(productName, quantity, applicablePromotion);
                validInput = true;

            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
        return quantity;
    }

    private int applyPromotionLogic(String productName, int quantity, Promotion applicablePromotion) {
        int buyQuantity = applicablePromotion.getBuyQuantity();
        int getQuantity = applicablePromotion.getGetQuantity();

        if (shouldOfferFreeProduct(buyQuantity, quantity)) {
            String confirmInput = inputView.confirmPromotionAdditionMessage(productName);
            inputConfirmValidator.validateConfirmation(confirmInput);
            if (confirmInput.equalsIgnoreCase("Y")) {
                quantity += getQuantity; // 무료 제품 추가
            }
        }

        return quantity;
    }

    private boolean shouldOfferFreeProduct(int buyQuantity, int quantity) {

        if (buyQuantity == 2 && (quantity % 3) == 2) {
            return true; // 탄산 2+1의 경우
        }
        if (buyQuantity == 1 && (quantity % 2) == 1) {
            return true; // MD추천 상품, 반짝할인의 경우
        }
        return false;
    }

    private Promotion validatePromotionDate(String promotionName) {
        return promotionService.validatePromotionDate(promotionName);
    }

}
