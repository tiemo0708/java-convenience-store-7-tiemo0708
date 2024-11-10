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

    public void updateProduct(Product updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            if (product.getName().equals(updatedProduct.getName()) &&
                    ((product.getPromotion() != null && updatedProduct.getPromotion() != null) ||
                            (product.getPromotion() == null && updatedProduct.getPromotion() == null))) {
                products.set(i, updatedProduct);
                return;
            }
        }
    }
}
