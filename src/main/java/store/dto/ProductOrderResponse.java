package store.dto;

public record ProductOrderResponse(
    boolean executable,
    int normalInventory,
    int promotionInventory,
    int unPromotableInventory,
    int bonusReceivable
) {
}
