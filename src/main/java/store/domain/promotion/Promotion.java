package store.domain.promotion;

import store.dto.ConfirmedOrderRequest;
import store.dto.ProductOrderRequest;
import store.dto.ProductOrderResponse;

import java.time.LocalDateTime;

public class Promotion {

    private final String name;
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final int buy;
    private final int get;

    public Promotion(String name, LocalDateTime start, LocalDateTime end, int buy, int get) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.buy = buy;
        this.get = get;
    }

    public String getName() {
        return name;
    }

    public boolean isPromotable(LocalDateTime orderTime) {
        return orderTime.isAfter(start) && orderTime.isBefore(end);
    }

    public ProductOrderResponse checkPromotableOrder(ProductOrderRequest request, int promotionInventory) {
        int requested = request.buyAmount();
        if (requested <= promotionInventory) {
            if (requested % (buy + get) == 0) {
                return ProductOrderResponse.executablePromotionOrder(request.productName(), requested, request.orderTime());
            }
            if (requested % (buy + get) == buy && requested + get <= promotionInventory) {
                return ProductOrderResponse.bonusReceivableOrder(request.productName(), promotionInventory, get, request.orderTime());
            }
            int unPromotable = requested % (buy + get);
            int promotable = requested - unPromotable;
            return ProductOrderResponse.unPromotableOrder(request.productName(), promotable, unPromotable, request.orderTime());
        }

        if (promotionInventory % (buy + get) == 0) {
            return ProductOrderResponse.unPromotableOrder(request.productName(), promotionInventory, requested - promotionInventory, request.orderTime());
        }
        int promotable = (buy + get) * (int) (promotionInventory / (buy + get));
        return ProductOrderResponse.unPromotableOrder(request.productName(), promotable, requested - promotable, request.orderTime());
    }

    public int promotionReceived(ConfirmedOrderRequest request) {
        return request.promotionInventory() / (buy + get);
    }

}

