package store.domain;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Promotions {
    private final List<Promotion> promotions;

    public Promotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    // 프로모션 목록을 수정 불가능한 형태로 반환
    public List<Promotion> getPromotions() {
        return Collections.unmodifiableList(promotions);
    }

}
