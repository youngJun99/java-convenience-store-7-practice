package store.dto;

public record ProductReceipt(
        String productName,
        int productPrice,
        int boughtAmount,
        int promotedAmount
) {
}
