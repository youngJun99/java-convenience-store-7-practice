package store.dto;

import camp.nextstep.edu.missionutils.DateTimes;

public record ProductOrderRequest(
        String productName,
        int buyAmount,
        DateTimes orderTime
) {
}
