package store.domain;

public class Product {

    private final String name;
    private final int price;
    private final Promotion promotion;
    private int normalQuantity;
    private int promotionQuantity;

    public Product(String name, int price, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.promotion = promotion;
        this.normalQuantity = 0;
        this.promotionQuantity = 0;
    }

    public void refillNormalQuantity(int refill) {
        this.normalQuantity += refill;
    }

    public void refillPromotionQuantity(int refill) {
        this.promotionQuantity += refill;
    }

}
