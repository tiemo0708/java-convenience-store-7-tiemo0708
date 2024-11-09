package store.domain;


import store.utils.ErrorMessages;

public class Product {
    private String name;
    private int price;
    private int stockQuantity;
    private String promotion;

    public Product(String name, int price, int stockQuantity, String promotion) {
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

    public String getPromotion() {
        return promotion;
    }

    public void decreaseStock(int quantity) {
        if (stockQuantity < quantity) {
            throw new IllegalArgumentException(ErrorMessages.INSUFFICIENT_STOCK_MESSAGE);
        }
        this.stockQuantity -= quantity;
    }
}

