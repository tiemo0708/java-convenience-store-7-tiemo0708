package store.controller;

import store.domain.Product;
import store.service.ProductService;
import store.service.PurchaseService;
import store.utils.ProductInputParser;
import store.validator.InputPurchaseValidator;
import store.view.InputView;
import store.view.OutputView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductController {
    private final ProductService productService;
    private final OutputView outputView;
    private final InputView inputView;
    private final PurchaseService purchaseService;
    private final InputPurchaseValidator inputPurchaseValidator;
    private final PromotionController promotionController;

    public ProductController(ProductService productService, OutputView outputView, InputView inputView, PurchaseService purchaseService, InputPurchaseValidator inputPurchaseValidator, PromotionController promotionController) {
        this.productService = productService;
        this.outputView = outputView;
        this.inputView = inputView;
        this.purchaseService = purchaseService;
        this.inputPurchaseValidator = inputPurchaseValidator;
        this.promotionController = promotionController;
    }
    public void printWelcomeMessage(){
        outputView.printWelcomeMessage();
    }
    public void printProductList() {
        List<Product> products = productService.getAllProducts();
        outputView.printProductList(products);
    }

    public void handleInitialDisplay() {
        this.printWelcomeMessage();
        this.printProductList();
    }

    public void handlePurchase() {
        boolean validInput = false;
        while (!validInput) {
            String input = inputView.getProductInput();
            try {
                Map<String, Integer> purchaseItems = parsePurchaseItems(input);
                applyPromotions(purchaseItems);
                updateInventory(purchaseItems);
                validInput = true;
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private Map<String, Integer> parsePurchaseItems(String input) {
        String[] items = input.split(",");
        Map<String, Integer> purchaseItems = new HashMap<>();
        for (String item : items) {
            inputPurchaseValidator.validateProductInput(item);
            String productName = ProductInputParser.extractProductName(item);
            int quantity = ProductInputParser.extractQuantity(item);
            inputPurchaseValidator.validateQuantity(quantity);
            purchaseItems.put(productName, quantity);
        }
        return purchaseItems;
    }

    private void applyPromotions(Map<String, Integer> purchaseItems) {
        for (Map.Entry<String, Integer> entry : purchaseItems.entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();
            Product product = productService.getProductByName(productName);
            if (product != null && product.getPromotion() != null) {
                String promotionName = product.getPromotion();
                promotionController.handlePromotion(productName, quantity, promotionName);
            }
        }
    }

    private void updateInventory(Map<String, Integer> purchaseItems) {
        for (Map.Entry<String, Integer> entry : purchaseItems.entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();
            productService.purchaseProduct(productName, quantity);
        }
    }
}
