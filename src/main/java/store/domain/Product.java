package store.domain;

public class Product {
    private String name;
    private int price;
    private int stockQuantity;
    private Promotion promotion;

    public Product(String name, int price, int stockQuantity, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.promotion = promotion;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public Promotion getPromotion() {
        return promotion;
    }
}

