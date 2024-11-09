package store.service;

import store.domain.Product;
import store.utils.ErrorMessages;

import java.util.List;

public class ProductService {
    private List<Product> products;

    public ProductService(List<Product> products) {
        this.products = products;
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public void purchaseProduct(String productName, int quantity) {
        Product product = products.stream()
                .filter(p -> p.getName().equals(productName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessages.PRODUCT_NOT_FOUND_MESSAGE));

        product.decreaseStock(quantity);
    }

}
