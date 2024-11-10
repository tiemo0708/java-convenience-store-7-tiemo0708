package store.domain;

import java.math.BigDecimal;

public class PurchaseRecord {
    private String productName;
    private int purchasedQuantity;
    private int freeQuantity;
    private BigDecimal discountAmount;
    private BigDecimal totalCost;

    public PurchaseRecord(String productName, int purchasedQuantity, int freeQuantity, BigDecimal discountAmount, BigDecimal totalCost) {
        this.productName = productName;
        this.purchasedQuantity = purchasedQuantity;
        this.freeQuantity = freeQuantity;
        this.discountAmount = discountAmount;
        this.totalCost = totalCost;
    }

    // Getters
    public String getProductName() {
        return productName;
    }

    public int getPurchasedQuantity() {
        return purchasedQuantity;
    }

    public int getFreeQuantity() {
        return freeQuantity;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }
}
