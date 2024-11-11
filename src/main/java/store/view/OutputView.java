package store.view;

import store.domain.Product;
import store.domain.PurchaseRecord;
import store.utils.ProductFormatter;

import java.math.BigDecimal;
import java.util.List;

public class OutputView {
    private static final String WELCOME_MESSAGE = "\n안녕하세요. W편의점입니다.";
    private static final String PRODUCT_LIST_MESSAGE = "현재 보유하고 있는 상품입니다.\n";
    private static final String CONVENIENCE_MESSAGE = "/n==============W 편의점================";
    private static final String PRODUCT_LIST_HEADER = String.format("%-10s %5s %10s", "상품명", "수량", "금액");
    private static final String GIFT_HEADER = "============= 증 정 ===============";
    private static final String TOTAL_AMOUNT_LABEL = "총구매액";
    private static final String PROMOTION_DISCOUNT_LABEL = "행사할인";
    private static final String MEMBERSHIP_DISCOUNT_LABEL = "멤버십할인";
    private static final String TOTAL_COST_LABEL = "내실돈";
    private static final String DIVIDER = "====================================";

    public void printWelcomeMessage() {
        System.out.println(WELCOME_MESSAGE);
        System.out.println(PRODUCT_LIST_MESSAGE);
    }

    public void printProductList(List<Product> products) {
        for (Product product : products) {
            String productInfo = ProductFormatter.formatProduct(product);
            System.out.println(productInfo);
        }
    }

    public void printError(String message) {
        System.out.println(message);
    }

    public void printReceipt(List<PurchaseRecord> purchaseRecords, BigDecimal membershipDiscount) {
        System.out.println(CONVENIENCE_MESSAGE);
        System.out.println(PRODUCT_LIST_HEADER);

        // 상품 정보 출력
        purchaseRecords.forEach(record ->
                System.out.println(String.format("%-10s %5d %,10d",
                        record.getProductName(),
                        record.getPurchasedQuantity(),
                        record.getTotalCost().intValue()
                ))
        );

        // 증정 상품 출력
        System.out.println(GIFT_HEADER);
        purchaseRecords.stream()
                .filter(record -> record.getFreeQuantity() > 0)
                .forEach(record ->
                        System.out.println(String.format("%-10s %5d",
                                record.getProductName(),
                                record.getFreeQuantity()
                        ))
                );

        System.out.println(DIVIDER);

        // 합계 및 할인 항목 출력
        BigDecimal totalCost = purchaseRecords.stream()
                .map(PurchaseRecord::getTotalCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal promotionDiscount = purchaseRecords.stream()
                .map(PurchaseRecord::getDiscountAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int totalQuantity = purchaseRecords.stream()
                .mapToInt(PurchaseRecord::getPurchasedQuantity)
                .sum();

        System.out.println(String.format("%-10s %5d %,10d", TOTAL_AMOUNT_LABEL, totalQuantity, totalCost.intValue()));
        System.out.println(String.format("%-10s %,10d", PROMOTION_DISCOUNT_LABEL, promotionDiscount.negate().intValue()));
        System.out.println(String.format("%-10s %,10d", MEMBERSHIP_DISCOUNT_LABEL, membershipDiscount.negate().intValue()));
        System.out.println(String.format("%-10s %,10d", TOTAL_COST_LABEL, totalCost.subtract(promotionDiscount).subtract(membershipDiscount).intValue()));
    }

    private String formatAmount(BigDecimal amount) {
        return String.format("%,d", amount.intValue());
    }
}
