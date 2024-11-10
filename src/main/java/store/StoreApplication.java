package store;

import store.controller.ProductController;
import store.controller.PromotionController;

public class StoreApplication {
    private final ProductController productController;
    private final PromotionController promotionController;

    public StoreApplication(ProductController productController, PromotionController promotionController) {
        this.productController = productController;
        this.promotionController = promotionController;
    }

    public void run() {
        productController.handleInitialDisplay();
        productController.handlePurchase();
        productController.handleInitialDisplay();
    }
}
