package store.controller;

import store.domain.Promotion;
import store.domain.PromotionResult;
import store.service.PromotionService;
import store.validator.InputConfirmValidator;
import store.view.InputView;
import store.view.OutputView;

import java.math.BigDecimal;

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


    public PromotionResult applyPromotionLogic(String productName, int quantity, String promotionName, BigDecimal productPrice) {
        Promotion applicablePromotion = promotionService.findPromotionName(promotionName);
        if (applicablePromotion == null) {
            return new PromotionResult(quantity, 0, BigDecimal.ZERO); // 유효하지 않은 경우, 수량 그대로 반환
        }

        int buyQuantity = applicablePromotion.getBuyQuantity();
        int getQuantity = applicablePromotion.getGetQuantity();
        int freeQuantity = 0;
        BigDecimal discountAmount = BigDecimal.ZERO;

        if (shouldOfferFreeProduct(buyQuantity, quantity)) {
            String confirmInput = inputView.confirmPromotionAdditionMessage(productName,getQuantity);
            inputConfirmValidator.validateConfirmation(confirmInput);
            if (confirmInput.equalsIgnoreCase("Y")) {
                quantity++;
            }
        }
        if(buyQuantity==2){
            freeQuantity = quantity/3;
        }
        if (buyQuantity==1){
            freeQuantity = quantity/2;
        }
        discountAmount=productPrice.multiply(BigDecimal.valueOf(freeQuantity));
        return new PromotionResult(quantity, freeQuantity, discountAmount);
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
}
