package store.domain;

public class Promotion {
    private String promotionName;
    private int buyQuantity;
    private int getQuantity;
    private String startDate;
    private String endDate;

    public Promotion(String promotionName, int buyQuantity, int getQuantity, String startDate, String endDate) {
        this.promotionName = promotionName;
        this.buyQuantity = buyQuantity;
        this.getQuantity = getQuantity;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    public String getPromotionName() {
        return promotionName;
    }

    public int getBuyQuantity() {
        return buyQuantity;
    }

    public int getGetQuantity() {
        return getQuantity;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

}
