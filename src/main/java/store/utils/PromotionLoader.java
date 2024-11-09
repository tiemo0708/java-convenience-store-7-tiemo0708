package store.utils;

import store.domain.Promotion;

import java.util.List;
import java.util.stream.Collectors;

public class PromotionLoader {

    public static List<Promotion> loadPromotionsFromFile(String filePath) {
        List<String> lines = FileReaderUtility.readLines(filePath);

        return lines.stream()
                .skip(1)
                .map(PromotionLoader::parsePromotion)
                .collect(Collectors.toList());
    }

    private static Promotion parsePromotion(String line) {
        String[] parts = line.split(",");
        String promotionName = parts[0];
        int buyQuantity = Integer.parseInt(parts[1]);
        int getQuantity = Integer.parseInt(parts[2]);
        String startDate = parts[3];
        String endDate = parts[4];
        return new Promotion(promotionName, buyQuantity, getQuantity, startDate, endDate);
    }
}