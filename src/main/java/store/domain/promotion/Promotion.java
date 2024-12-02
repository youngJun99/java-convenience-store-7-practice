package store.domain.promotion;

import java.time.LocalDateTime;

public class Promotion {

    private final String name;
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final int buy;
    private final int get;

    public Promotion(String name, LocalDateTime start, LocalDateTime end, int buy, int get) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.buy = buy;
        this.get = get;
    }

    public boolean isPromotable(LocalDateTime orderedTime) {
        return orderedTime.isAfter(start) && orderedTime.isBefore(end);
    }

}
