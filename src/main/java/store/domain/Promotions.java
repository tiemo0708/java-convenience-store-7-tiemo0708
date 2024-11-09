package store.domain;

import java.util.Collections;
import java.util.List;


public class Promotions {
    private final List<Promotion> promotions;

    public Promotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public List<Promotion> getPromotions() {
        return Collections.unmodifiableList(promotions);
    }

    public void applyPromotion(String productName, int quantity) {

    }
}
