package store.dto;

public record PayReceipt(
        int totalBuy,
        int totalPay,
        int discount,
        int memberShipDiscount,
        int needToPay
) {
}

