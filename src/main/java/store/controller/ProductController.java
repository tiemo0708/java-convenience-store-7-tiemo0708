package store.controller;

import store.domain.Product;
import store.domain.Promotion;
import store.domain.PromotionResult;
import store.domain.PurchaseRecord;
import store.service.ProductService;
import store.utils.ErrorMessages;
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
    private final InputPurchaseValidator inputPurchaseValidator;
    private final PromotionController promotionController;
    private final InputConfirmValidator inputConfirmValidator;

    public ProductController(ProductService productService, OutputView outputView, InputView inputView, InputPurchaseValidator inputPurchaseValidator, PromotionController promotionController, InputConfirmValidator inputConfirmValidator) {
        this.productService = productService;
        this.outputView = outputView;
        this.inputView = inputView;
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
        boolean validInput = true;
        while (validInput) {
            handleInitialDisplay();
            String input = inputView.getProductInput();
            try {
                Map<String, Integer> purchaseItems = getValidatedPurchaseItems(input);
                List<PurchaseRecord> purchaseRecords = new ArrayList<>();

                for (Map.Entry<String, Integer> entry : purchaseItems.entrySet()) {
                    handleSingleProductPurchase(entry.getKey(), entry.getValue(), purchaseRecords);
                }

                BigDecimal membershipDiscount = applyMembershipDiscount(purchaseRecords);
                updateInventory(purchaseRecords);
                printReceipt(purchaseRecords, membershipDiscount);
                validInput = askForAdditionalPurchase();
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private boolean askForAdditionalPurchase() {
        String confirmInput = inputView.askForAdditionalPurchase();
        try {
            inputConfirmValidator.validateConfirmation(confirmInput);
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
            return askForAdditionalPurchase();
        }
        return confirmInput.equalsIgnoreCase("Y");
    }

    private void printReceipt(List<PurchaseRecord> purchaseRecords, BigDecimal membershipDiscount) {
        outputView.printReceipt(purchaseRecords, membershipDiscount);
    }

    private Map<String, Integer> getValidatedPurchaseItems(String input) {
        Map<String, Integer> purchaseItems = new HashMap<>();
        String[] items = input.split(",");
        for (String item : items) {
            boolean validItem = false;
            while (!validItem) {
                try {
                    inputPurchaseValidator.validateProductInput(item);
                    String productName = ProductInputParser.extractProductName(item);
                    int quantity = ProductInputParser.extractQuantity(item);
                    inputPurchaseValidator.validateQuantity(quantity);
                    purchaseItems.put(productName, quantity);
                    validItem = true;
                } catch (IllegalArgumentException e) {
                    outputView.printError(e.getMessage());
                    input = inputView.getProductInput();  // 전체 입력을 다시 받기
                    return getValidatedPurchaseItems(input);  // 전체 입력을 재검증하기 위해 재귀 호출
                }
            }
        }
        return purchaseItems;
    }

    private void handleSingleProductPurchase(String productName, int quantity, List<PurchaseRecord> purchaseRecords) {
        int promoStock;
        while (true) {
            try {
                promoStock = adjustStockAndCheckForSingleProduct(productName, quantity);
                break;
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
                String input = inputView.getProductInput(); // 전체 입력을 다시 받기
                Map<String, Integer> purchaseItems = getValidatedPurchaseItems(input);
                purchaseRecords.clear(); // 기존의 잘못된 purchaseRecords 초기화

                for (Map.Entry<String, Integer> entry : purchaseItems.entrySet()) {
                    handleSingleProductPurchase(entry.getKey(), entry.getValue(), purchaseRecords);
                }
                return;
            }
        }

        Product product = productService.getProductByName(productName);
        if (product == null) {
            nonApplyPromotion(productName, quantity, purchaseRecords);
            return;
        }

        if (product.getPromotion() == null) {
            nonApplyPromotion(productName, quantity, purchaseRecords);
            return;
        }

        String promotionName = product.getPromotion();
        Promotion applicablePromotion = promotionController.validatePromotionDate(promotionName);
        if (applicablePromotion == null) {
            nonApplyPromotion(productName, quantity, purchaseRecords);
            return;
        }

        PromotionResult promotionResult = promotionController.applyPromotionLogic(productName, quantity, promotionName, product.getPrice(), promoStock);
        applyPromotion(productName, quantity, purchaseRecords, promotionResult);
    }

    private void applyPromotion(String productName, int quantity, List<PurchaseRecord> purchaseRecords, PromotionResult promotionResult) {
        Product product = productService.getProductByName(productName);
        BigDecimal productPrice = product.getPrice();
        int updatedQuantity = promotionResult.getUpdatedQuantity();
        PurchaseRecord record = new PurchaseRecord(
                productName,
                updatedQuantity,
                promotionResult.getFreeQuantity(),
                promotionResult.getDiscountAmount(),
                productPrice.multiply(BigDecimal.valueOf(updatedQuantity)),
                promotionResult.getPromotionalAmount()
        );
        purchaseRecords.add(record);
    }

    private void nonApplyPromotion(String productName, int quantity, List<PurchaseRecord> purchaseRecords) {
        Product product = productService.getProductByName(productName);
        PurchaseRecord record = new PurchaseRecord(
                productName,
                quantity,
                0,
                BigDecimal.ZERO,
                product.getPrice().multiply(BigDecimal.valueOf(quantity)),
                BigDecimal.ZERO
        );
        purchaseRecords.add(record);
    }

    private BigDecimal applyMembershipDiscount(List<PurchaseRecord> purchaseRecords) {
        BigDecimal totalDiscountableAmount = BigDecimal.ZERO;

        for (PurchaseRecord record : purchaseRecords) {
            totalDiscountableAmount = totalDiscountableAmount.add(record.getTotalCost().subtract(record.getPromotionalAmount()));
        }


            String confirmInput = inputView.isMembershipInvalid();
            try {
                inputConfirmValidator.validateConfirmation(confirmInput);
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
                return applyMembershipDiscount(purchaseRecords);
            }
            if (!confirmInput.equalsIgnoreCase("Y")) {
                return BigDecimal.ZERO;
            }


        BigDecimal membershipDiscount = totalDiscountableAmount.multiply(BigDecimal.valueOf(0.3));
        BigDecimal maxMembershipDiscount = BigDecimal.valueOf(8000);
        if (membershipDiscount.compareTo(maxMembershipDiscount) > 0) {
            return maxMembershipDiscount;
        }

        return membershipDiscount;
    }

    private int adjustStockAndCheckForSingleProduct(String productName, int quantity) {
        Product promoProduct = productService.getProductByNameAndPromotion(productName, true);
        Product normalProduct = productService.getProductByNameAndPromotion(productName, false);

        int promoStock = getPromoStock(promoProduct);
        int normalStock = getNormalStock(normalProduct);

        if (quantity > promoStock + normalStock) {
            throw new IllegalArgumentException(ErrorMessages.INSUFFICIENT_STOCK_MESSAGE);
        }
        if (quantity > promoStock && promoProduct != null) {
            return confirmPartialFullPricePayment(quantity, promoStock, productName);
        }
        return promoStock;
    }

    private int confirmPartialFullPricePayment(int quantity, int promoStock, String productName) {
        String confirmInput = inputView.isPromotionInvalid((quantity - promoStock)+1, productName);
        try {
            inputConfirmValidator.validateConfirmation(confirmInput);
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
            return confirmPartialFullPricePayment(quantity, promoStock, productName);
        }
        if (confirmInput.equalsIgnoreCase("Y")) {
            return promoStock;
        }
        return -1;
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

    private void updateInventory(List<PurchaseRecord> purchaseItems) {
        for (PurchaseRecord record : purchaseItems) {
            String productName = record.getProductName();
            int totalQuantity = record.getPurchasedQuantity();

            Product promoProduct = productService.getProductByNameAndPromotion(productName, true);
            Product normalProduct = productService.getProductByNameAndPromotion(productName, false);

            int remainingQuantity = totalQuantity;

            if (promoProduct != null && promoProduct.getStockQuantity() > 0) {
                int promoUsed = Math.min(promoProduct.getStockQuantity(), remainingQuantity);
                promoProduct.decreaseStock(promoUsed);
                remainingQuantity -= promoUsed;
            }

            if (remainingQuantity > 0 && normalProduct != null && normalProduct.getStockQuantity() >= remainingQuantity) {
                normalProduct.decreaseStock(remainingQuantity);
            }
        }
    }
}
