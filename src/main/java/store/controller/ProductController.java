package store.controller;

import store.domain.Product;
import store.domain.Promotion;
import store.domain.PromotionResult;
import store.domain.PurchaseRecord;
import store.service.ProductService;
import store.service.PromotionService;
import store.service.PurchaseService;
import store.utils.ProductInputParser;
import store.validator.InputConfirmValidator;
import store.validator.InputPurchaseValidator;
import store.view.InputView;
import store.view.OutputView;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    public void printWelcomeMessage() {
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
                List<PurchaseRecord> purchaseRecords = new ArrayList<>();
                // Step 1: 프로모션 날짜와 유효성 확인
                //validatePromotions(purchaseItems);

                // Step 2: 재고 확인 및 적용 가능한 최대 수량 조정
                adjustStockAndCheck(purchaseItems);

                // Step 3: 프로모션 혜택 적용
                applyPromotions(purchaseItems,purchaseRecords);

                // Step 4: 재고 차감
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

    private void validatePromotions(Map<String, Integer> purchaseItems) {
        for (Map.Entry<String, Integer> entry : purchaseItems.entrySet()) {
            String productName = entry.getKey();
            Product product = productService.getProductByName(productName);
            if (product != null && product.getPromotion() != null) {
                String promotionName = product.getPromotion();
                // 중복된 로직 제거: PromotionController에서 날짜 검증만 하도록 변경
              //  promotionController.validatePromotionDate(promotionName);
            }
        }
    }

    private void adjustStockAndCheck(Map<String, Integer> purchaseItems) {
        for (Map.Entry<String, Integer> entry : purchaseItems.entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();
            Product promoProduct = productService.getProductByNameAndPromotion(productName, true);
            Product normalProduct = productService.getProductByNameAndPromotion(productName, false);

            int promoStock = getPromoStock(promoProduct);
            int normalStock = getNormalStock(normalProduct);

            if (quantity > promoStock + normalStock) {
                throw new IllegalArgumentException("해당 상품의 재고가 부족합니다.");
            }
        }
    }

    private int getPromoStock(Product promoProduct) {
        return promoProduct != null ? promoProduct.getStockQuantity() : 0;
    }

    private int getNormalStock(Product normalProduct) {
        return normalProduct != null ? normalProduct.getStockQuantity() : 0;
    }

    private void applyPromotions(Map<String, Integer> purchaseItems, List<PurchaseRecord> purchaseRecords) {
        for (Map.Entry<String, Integer> entry : purchaseItems.entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();
            Product product = productService.getProductByName(productName);
            if (product != null && product.getPromotion() != null) {
                String promotionName = product.getPromotion();
                BigDecimal productPrice = product.getPrice();

                // PromotionController에서 PromotionResult 반환받음
                PromotionResult promotionResult = promotionController.applyPromotionLogic(productName, quantity, promotionName, productPrice);

                // 업데이트된 수량을 반영
                int updatedQuantity = promotionResult.getUpdatedQuantity();
                purchaseItems.put(productName, updatedQuantity);

                // PurchaseRecord 생성하여 할인 정보 저장
                PurchaseRecord record = new PurchaseRecord(
                        productName,
                        updatedQuantity,
                        promotionResult.getFreeQuantity(),
                        promotionResult.getDiscountAmount(),
                        productPrice.multiply(BigDecimal.valueOf(promotionResult.getUpdatedQuantity()))
                );

                purchaseRecords.add(record);
            }
        }
    }


    private void updateInventory(Map<String, Integer> purchaseItems) {
        for (Map.Entry<String, Integer> entry : purchaseItems.entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();
            Product promoProduct = productService.getProductByNameAndPromotion(productName, true);
            Product normalProduct = productService.getProductByNameAndPromotion(productName, false);

            int remainingQuantity = quantity;

            if (promoProduct != null && promoProduct.getStockQuantity() > 0) {
                int promoUsed = Math.min(promoProduct.getStockQuantity(), remainingQuantity);
                promoProduct.decreaseStock(promoUsed);
                //productService.updateProduct(promoProduct);
                remainingQuantity -= promoUsed;
            }

            if (remainingQuantity > 0 && normalProduct != null && normalProduct.getStockQuantity() >= remainingQuantity) {
                normalProduct.decreaseStock(remainingQuantity);
              //  productService.updateProduct(normalProduct);
            }
        }
    }
}
