package store.config;

import store.StoreApplication;
import store.controller.ProductController;
import store.domain.Product;
import store.service.ProductService;
import store.utils.ProductLoader;
import store.view.OutputView;

import java.util.List;

public class AppConfig {
    public ProductController productController() {
        return new ProductController(productService(), outputView());
    }

    public ProductService productService() {
        return new ProductService(productList());
    }
    public StoreApplication storeApplication() {
        return new StoreApplication(productController());
    }
    public List<Product> productList() {
        return ProductLoader.loadProductsFromFile("src/main/resources/products.md");
    }


    public OutputView outputView() {
        return new OutputView();
    }
}
