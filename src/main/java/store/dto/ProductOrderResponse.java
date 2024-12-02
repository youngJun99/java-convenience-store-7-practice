package store.dto;

import java.time.LocalDateTime;

public record ProductOrderResponse(
        String productName,
        boolean executable,
        int normalInventory,
        int promotionInventory,
        int unPromotableInventory,
        int bonusReceivable,
        LocalDateTime orderedTime
) {
    public static ProductOrderResponse executableNormalOrder(String productName, int normalInventory, int promotionInventory, LocalDateTime orderedTime) {
        return new ProductOrderResponse(productName, true, normalInventory, promotionInventory, 0, 0, orderedTime);
    }

    public static ProductOrderResponse executablePromotionOrder(String productName, int promotionInventory, LocalDateTime orderedTime) {
        return new ProductOrderResponse(productName, true, 0, promotionInventory, 0, 0, orderedTime);
    }

    public static ProductOrderResponse bonusReceivableOrder(String productName, int promotionInventory, int bonusReceivable, LocalDateTime orderedTime) {
        return new ProductOrderResponse(productName, false, 0, promotionInventory, 0, bonusReceivable, orderedTime);
    }

    public static ProductOrderResponse unPromotableOrder(String productName, int promotionInventory, int unPromotableInventory, LocalDateTime orderedTime) {
        return new ProductOrderResponse(productName, true, 0, promotionInventory, unPromotableInventory, 0, orderedTime);
    }
}
