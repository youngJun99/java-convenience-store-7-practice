package store.service;

import store.constants.Errors;
import store.domain.Store;
import store.domain.promotion.Promotion;
import store.reader.PromotionReader;
import store.reader.StoreReader;

import java.io.IOException;
import java.util.List;

public class StoreGenerateService {

    private static final String PROMOTION_PATH = "src/main/resources/promotions.md";
    private static final String PRODUCTS_PATH = "src/main/resources/products.md";

    private final PromotionReader promotionReader;
    private final StoreReader storeReader;

    public StoreGenerateService(PromotionReader promotionReader, StoreReader storeReader) {
        this.promotionReader = promotionReader;
        this.storeReader = storeReader;
    }

    public Store generate() {
        try {
            return readStore();
        } catch (IOException e) {
            System.err.println(Errors.STORE_READING_FAILED.getMessage());
            System.exit(1);
            return null;
        }
    }

    private Store readStore() throws IOException {
        List<Promotion> promotions = promotionReader.createPromotions(PROMOTION_PATH);
        return storeReader.createStore(promotions, PRODUCTS_PATH);
    }
}
