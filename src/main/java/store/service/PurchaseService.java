package store.service;

import store.utils.ProductInputParser;
import store.validator.InputPurchaseValidator;

public class PurchaseService {
    private final ProductService productService;
    private final InputPurchaseValidator inputPurchaseValidator;

    public PurchaseService(ProductService productService, InputPurchaseValidator inputPurchaseValidator) {
        this.productService = productService;
        this.inputPurchaseValidator = inputPurchaseValidator;
    }

    public void processPurchase(String input) {
        String[] items = input.split(",");
        for (String item : items) {
            inputPurchaseValidator.validateProductInput(item);
            String productName = ProductInputParser.extractProductName(item);
            int quantity = ProductInputParser.extractQuantity(item);
            inputPurchaseValidator.validateQuantity(quantity);
            productService.purchaseProduct(productName, quantity);
        }
    }
}