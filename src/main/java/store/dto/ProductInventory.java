package store.dto;

public record ProductInventory(
        String name,
        int price,
        int inventory,
        String promotionName
) {
}
