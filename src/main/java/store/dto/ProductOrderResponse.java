package store.dto;

public record ProductOrderResponse(
        boolean executable,
        int normalInventory,
        int promotionInventory,
        int unPromotableInventory,
        int bonusReceivable
) {
    public static ProductOrderResponse executableNormalOrder(int normalInventory) {
        return new ProductOrderResponse(true, normalInventory, 0, 0, 0);
    }

    public static ProductOrderResponse executablePromotionOrder(int promotionInventory) {
        return new ProductOrderResponse(true, 0, promotionInventory, 0, 0);
    }

    public static ProductOrderResponse bonusReceivableOrder(int promotionInventory, int bonusReceivable) {
        return new ProductOrderResponse(false, 0, promotionInventory, 0, bonusReceivable);
    }

    public static ProductOrderResponse unPromotableOrder(int promotionInventory, int unPromotableInventory) {
        return new ProductOrderResponse(true, 0, promotionInventory, unPromotableInventory, 0);
    }
}
