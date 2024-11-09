package store.controller;

import store.domain.Product;
import store.service.ProductService;
import store.service.PurchaseService;
import store.view.InputView;
import store.view.OutputView;

import java.util.List;

public class ProductController {
    private final ProductService productService;
    private final OutputView outputView;
    private final InputView inputView;
    private final PurchaseService purchaseService;

    public ProductController(ProductService productService, OutputView outputView, InputView inputView, PurchaseService purchaseService) {
        this.productService = productService;
        this.outputView = outputView;
        this.inputView = inputView;
        this.purchaseService = purchaseService;
    }
    public void printWelcomeMessage(){
        outputView.printWelcomeMessage();
    }
    public void printProductList() {
        List<Product> products = productService.getAllProducts();
        outputView.printProductList(products);
    }

    public void handleInitialDisplay() {
        this.printWelcomeMessage();
        this.printProductList();
    }

    public void handlePurchase() {
        boolean validInput = false;
        while (!validInput) {
            String input = inputView.getProductInput();
            try {
                purchaseService.processPurchase(input);
                validInput = true;
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }
}
