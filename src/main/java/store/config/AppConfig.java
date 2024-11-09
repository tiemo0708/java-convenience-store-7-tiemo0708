package store.config;

import store.StoreApplication;
import store.controller.ProductController;
import store.controller.PromotionController;
import store.domain.Product;
import store.domain.Promotion;
import store.domain.Promotions;
import store.service.ProductService;
import store.service.PromotionService;
import store.service.PurchaseService;
import store.utils.ProductLoader;
import store.utils.PromotionLoader;
import store.validator.InputPurchaseValidator;
import store.validator.InputPurchaseValidatorImpl;
import store.view.InputView;
import store.view.OutputView;

import java.util.List;

public class AppConfig {
    public ProductController productController() {
        return new ProductController(productService(), outputView(), inputView(), purchaseService(), inputPurchaseValidator(),promotionController());
    }
    public ProductService productService() {
        return new ProductService(productList());
    }
    public StoreApplication storeApplication() {
        return new StoreApplication(productController(),promotionController());
    }
    public List<Product> productList() {
        return ProductLoader.loadProductsFromFile("src/main/resources/products.md");
    }

    public List<Promotion> promotionsList() {
        return PromotionLoader.loadPromotionsFromFile("src/main/resources/promotions.md");
    }
    public PromotionController promotionController() {
        return new PromotionController(promotionService(), outputView(), inputView());
    }
    public PromotionService promotionService() {
        return new PromotionService(new Promotions(promotionsList()));
    }

    public PurchaseService purchaseService(){
        return new PurchaseService(productService(),inputPurchaseValidator());
    }
    public OutputView outputView() {
        return new OutputView();
    }
    public InputView inputView() {
        return new InputView();
    }
    public InputPurchaseValidator inputPurchaseValidator() {
        return new InputPurchaseValidatorImpl();
    }
}
