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

    public void handlePromotion(String productName, int quantity, String promotionName) {
        boolean validInput = false;
        while (!validInput) {
            try {
                Promotion applicablePromotion = validatePromotionDate(promotionName);
                if (applicablePromotion != null) {
                    confirmPromotionAddition(quantity, applicablePromotion, productName);
                }
                validInput = true;
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private void confirmPromotionAddition(int quantity, Promotion applicablePromotion, String productName) {
        int buyQuantity = applicablePromotion.getBuyQuantity();
        int getQuantity = applicablePromotion.getGetQuantity();
        if ((buyQuantity == 2 && (quantity % 3) == 2) || (buyQuantity == 1 && (quantity % 2) == 1)) {
            String confirmInput = inputView.confirmPromotionAdditionMessage(productName);
            inputConfirmValidator.validateConfirmation(confirmInput);
        }

    }

    private Promotion validatePromotionDate(String promotionName) {
        return promotionService.validatePromotionDate(promotionName);
    }

    private void applyPromotion(String productName, int quantity) {
        promotionService.applyPromotion(productName, quantity);
    }

}
