package store.domain;

import store.domain.promotion.Promotion;
import store.dto.ConfirmedOrderRequest;
import store.dto.ProductOrderRequest;
import store.dto.ProductOrderResponse;
import store.dto.ProductReceipt;

import java.util.Optional;

public class Product {

    private final String name;
    private final int price;
    private final Optional<Promotion> promotion;
    private int normalInventory;
    private int promotionInventory;

    public Product(String name, int price, Optional<Promotion> promotion, int normalQuantity, int promotionQuantity) {
        this.name = name;
        this.price = price;
        this.promotion = promotion;
        this.normalInventory = normalQuantity;
        this.promotionInventory = promotionQuantity;
    }

    public void refillNormalQuantity(int refill) {
        this.normalInventory += refill;
    }

    public void refillPromotionQuantity(int refill) {
        this.promotionInventory += refill;
    }

    public ProductOrderResponse checkOrder(ProductOrderRequest request) {
        if (promotion.isPresent() && promotion.get().isPromotable(request.orderTime())) {
            return promotion.get().checkPromotableOrder(request, promotionInventory);
        }

        int requested = request.buyAmount();
        if (requested <= normalInventory) {
            return ProductOrderResponse.executableNormalOrder(name, requested, 0);
        }
        return ProductOrderResponse.executableNormalOrder(name, normalInventory, requested - normalInventory);
    }

    public ProductReceipt purchase(ConfirmedOrderRequest request) {
        this.normalInventory -= request.normalInventory();
        this.promotionInventory -= request.promotionInventory();
        int boughtAmount = request.normalInventory() + request.promotionInventory();
        if (promotion.isPresent() && promotion.get().isPromotable(request.orderTime())) {
            return new ProductReceipt(name, price, boughtAmount, promotion.get().promotionReceived(request));
        }
        return new ProductReceipt(name, price, boughtAmount, 0);
    }
}
