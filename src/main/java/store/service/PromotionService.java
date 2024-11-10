package store.service;

import camp.nextstep.edu.missionutils.DateTimes;
import store.domain.Promotion;
import store.domain.Promotions;

import java.time.LocalDate;


public class PromotionService {
    private final Promotions promotions;
    public PromotionService(Promotions promotions) {
        this.promotions = promotions;
    }

    public void applyPromotion(String productName, int quantity) {
        promotions.applyPromotion(productName, quantity);
    }

    public Promotion validatePromotionDate(String promotionName) {
        LocalDate currentDate = DateTimes.now().toLocalDate();

        Promotion promotion = promotions.getPromotions().stream()
                .filter(p -> p.getPromotionName().equals(promotionName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 프로모션을 찾을 수 없습니다."));

        LocalDate startDate = LocalDate.parse(promotion.getStartDate());
        LocalDate endDate = LocalDate.parse(promotion.getEndDate());

        if (currentDate.isBefore(startDate) || currentDate.isAfter(endDate)) {
            return null;
        }
        return promotion;
    }

    public Promotion findPromotionName(String promotionName) {
        return promotions.getPromotions().stream()
                .filter(promotion -> promotion.getPromotionName().equals(promotionName))
                .findFirst()
                .orElse(null);
    }
}
