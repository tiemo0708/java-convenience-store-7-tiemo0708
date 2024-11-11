package store.service;

import camp.nextstep.edu.missionutils.DateTimes;
import store.domain.Promotion;
import store.domain.Promotions;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class PromotionService {
    private final Promotions promotions;

    public PromotionService(Promotions promotions) {
        this.promotions = promotions;
    }

    public Promotion validatePromotionDate(String promotionName) {
        LocalDateTime currentDate = DateTimes.now();

        Promotion promotion = promotions.getPromotions().stream()
                .filter(p -> p.getPromotionName().equals(promotionName))
                .findFirst()
                .orElse(null);
        if (promotion!=null) {
            LocalDate startDate = LocalDate.parse(promotion.getStartDate());
            LocalDate endDate = LocalDate.parse(promotion.getEndDate());
            if (currentDate.isBefore(startDate.atStartOfDay()) || currentDate.isAfter(endDate.atStartOfDay())) {
                return null;
            }
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
