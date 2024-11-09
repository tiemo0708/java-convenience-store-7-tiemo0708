package store.controller;

import store.service.PromotionService;
import store.view.InputView;
import store.view.OutputView;

public class PromotionController {
    private final PromotionService promotionService;
    private final OutputView outputView;
    private final InputView inputView;
    public PromotionController(PromotionService promotionService, OutputView outputView, InputView inputView) {
        this.promotionService = promotionService;
        this.outputView = outputView;
        this.inputView = inputView;
    }

    public void handlePromotion(String productName, int quantity, String promotionName) {
        validatePromotionDate(promotionName);
    }

    private Boolean validatePromotionDate(String promotionName) {
        return promotionService.validatePromotionDate(promotionName);
    }

    private void applyPromotion(String productName, int quantity) {
        promotionService.applyPromotion(productName, quantity);
    }

}
