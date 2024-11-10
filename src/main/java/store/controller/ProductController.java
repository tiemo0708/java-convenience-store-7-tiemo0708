package store.controller;

import store.domain.Product;
import store.service.ProductService;
import store.service.PurchaseService;
import store.utils.ProductInputParser;
import store.validator.InputConfirmValidator;
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
    private final InputConfirmValidator inputConfirmValidator;

    public ProductController(ProductService productService, OutputView outputView, InputView inputView, PurchaseService purchaseService, InputPurchaseValidator inputPurchaseValidator, PromotionController promotionController, InputConfirmValidator inputConfirmValidator) {
        this.productService = productService;
        this.outputView = outputView;
        this.inputView = inputView;
        this.purchaseService = purchaseService;
        this.inputPurchaseValidator = inputPurchaseValidator;
        this.promotionController = promotionController;
        this.inputConfirmValidator = inputConfirmValidator;
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
                applyPromotionsAndCheckStock(purchaseItems);
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
                int updatedQuantity = promotionController.handlePromotion(productName, quantity, promotionName);
                purchaseItems.put(productName, updatedQuantity);
            }
        }
    }

    private void applyPromotionsAndCheckStock(Map<String, Integer> purchaseItems) {
        for (Map.Entry<String, Integer> entry : purchaseItems.entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();
            Product promoProduct = productService.getProductByNameAndPromotion(productName, true);
            Product normalProduct = productService.getProductByNameAndPromotion(productName, false);

            int promoStock = getPromoStock(promoProduct);
            int normalStock = getNormalStock(normalProduct);

            handlePromotionStock(productName, quantity, promoProduct, normalProduct, promoStock, normalStock);
        }
    }
    private int getPromoStock(Product promoProduct) {
        if (promoProduct == null) {
            return 0;
        }
        return promoProduct.getStockQuantity();
    }
    private int getNormalStock(Product normalProduct) {
        if (normalProduct == null) {
            return 0;
        }
        return normalProduct.getStockQuantity();
    }
    private void handlePromotionStock(String productName, int quantity, Product promoProduct, Product normalProduct, int promoStock, int normalStock) {
        if (quantity <= promoStock) {
            promoProduct.decreaseStock(quantity);
            productService.updateProduct(promoProduct);
            return;
        }

        handleMixedStock(productName, quantity, promoProduct, normalProduct, promoStock, normalStock);
    }
    private void handleMixedStock(String productName, int quantity, Product promoProduct, Product normalProduct, int promoStock, int normalStock) {
        int totalStock = promoStock + normalStock;

        // 총 재고가 구매 수량을 만족하지 못할 때 예외 처리
        if (quantity > totalStock) {
            throw new IllegalArgumentException("해당 상품의 재고가 부족합니다.");
        }

        // 프로모션 재고부터 사용
        int remainingQuantity = quantity;

        if (promoStock > 0) {
            int promoUsed = Math.min(promoStock, remainingQuantity);
            promoProduct.decreaseStock(promoUsed);
            productService.updateProduct(promoProduct);
            remainingQuantity -= promoUsed;
        }

        // 일반 재고 사용
        if (remainingQuantity > 0) {
            String confirmInput = inputView.isPromotionInvalid(remainingQuantity, productName);

            inputConfirmValidator.validateConfirmation(confirmInput);

            if (confirmInput.equalsIgnoreCase("Y")) {
                normalProduct.decreaseStock(remainingQuantity);
                productService.updateProduct(normalProduct);
            } else {
                throw new IllegalArgumentException("구매가 취소되었습니다.");
            }
        }
    }


}
