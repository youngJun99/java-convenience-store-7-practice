package store.dto;

import camp.nextstep.edu.missionutils.DateTimes;

import java.time.LocalDateTime;

public record ProductOrderRequest(
        String productName,
        int buyAmount,
        LocalDateTime orderTime
) {
}
