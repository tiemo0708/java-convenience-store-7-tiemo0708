package store.domain;

import java.math.BigDecimal;

public class PromotionResult {
    private int updatedQuantity;  // 프로모션 적용 후 최종 구매 수량
    private int freeQuantity;     // 추가로 무료 제공된 제품 수량
    private BigDecimal discountAmount;   // 할인 금액

    public PromotionResult(int updatedQuantity, int freeQuantity, BigDecimal discountAmount) {
        this.updatedQuantity = updatedQuantity;
        this.freeQuantity = freeQuantity;
        this.discountAmount = discountAmount;
    }

    // Getters
    public int getUpdatedQuantity() {
        return updatedQuantity;
    }

    public int getFreeQuantity() {
        return freeQuantity;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }
}
