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


    public Product getProductByName(String productName) {
        return products.stream()
                .filter(product -> product.getName().equals(productName))
                .findFirst()
                .orElse(null);
    }

    public Product getProductByNameAndPromotion(String productName, boolean isPromotion) {
        for (Product product : products) {
            if (product.getName().equals(productName) && (isPromotion == (!product.getPromotion().equals("null")))) {
                return product;
            }
        }
        return null;
    }
}
