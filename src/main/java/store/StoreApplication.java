package store;

import store.controller.ProductController;

public class StoreApplication {
    private final ProductController productController;

    public StoreApplication(ProductController productController) {
        this.productController = productController;
    }

    public void run() {
        productController.handleInitialDisplay();

    }
}
