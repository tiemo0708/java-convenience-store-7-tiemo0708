package store.controller;

import store.domain.Product;
import store.service.ProductService;
import store.view.OutputView;

import java.util.List;

public class ProductController {
    private final ProductService productService;
    private final OutputView outputView;
    public ProductController(ProductService productService, OutputView outputView) {

        this.productService = productService;
        this.outputView = outputView;
    }
    public void printWelcomeMessage(){
        outputView.printWelcomeMessage();
    }
    public void printProductList() {
        List<Product> products = productService.getAllProducts();
        outputView.printProductList(products);
    }

}
