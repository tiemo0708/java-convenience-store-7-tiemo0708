package store.view;

import store.domain.Product;
import store.utils.ProductFormatter;

import java.util.List;

public class OutputView {
    private static final String WELCOME_MESSAGE = "안녕하세요. W편의점입니다.";
    private static final String PRODUCT_LIST_MESSAGE = "현재 보유하고 있는 상품입니다.\n";

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
}
