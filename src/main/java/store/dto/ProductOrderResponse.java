package store.dto;

public record ProductOrderResponse(
        String productName,
        boolean executable,
        int normalInventory,
        int promotionInventory,
        int unPromotableInventory,
        int bonusReceivable
) {
    public static ProductOrderResponse executableNormalOrder(String productName, int normalInventory, int promotionInventory) {
        return new ProductOrderResponse(productName, true, normalInventory, promotionInventory, 0, 0);
    }

    public static ProductOrderResponse executablePromotionOrder(String productName, int promotionInventory) {
        return new ProductOrderResponse(productName, true, 0, promotionInventory, 0, 0);
    }

    public static ProductOrderResponse bonusReceivableOrder(String productName, int promotionInventory, int bonusReceivable) {
        return new ProductOrderResponse(productName, false, 0, promotionInventory, 0, bonusReceivable);
    }

    public static ProductOrderResponse unPromotableOrder(String productName, int promotionInventory, int unPromotableInventory) {
        return new ProductOrderResponse(productName, true, 0, promotionInventory, unPromotableInventory, 0);
    }
}
