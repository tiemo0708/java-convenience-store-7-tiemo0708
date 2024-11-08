package store.utils;

import store.domain.Product;

public class ProductFormatter {

    public static String formatProduct(Product product) {
        StringBuilder productInfo = new StringBuilder("- ");
        productInfo.append(product.getName()).append(" ");

        productInfo.append(String.format("%,d", product.getPrice())).append("원 ");

        if (product.getStockQuantity() > 0) {
            productInfo.append(product.getStockQuantity()).append("개 ");
        }
        if (product.getStockQuantity() <= 0) {
            productInfo.append("재고 없음 ");
        }
        if (product.getPromotion().equals("null")) {
            productInfo.append("");
        }

        if (!product.getPromotion().equals("null")) {
            productInfo.append(product.getPromotion());
        }

        return productInfo.toString();
    }
}
