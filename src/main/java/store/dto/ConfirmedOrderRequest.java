package store.dto;

import java.time.LocalDateTime;

public record ConfirmedOrderRequest(
        String productName,
        int normalInventory,
        int promotionInventory,
        LocalDateTime orderTime
) {
}

