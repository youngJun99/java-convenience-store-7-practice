package store.domain;

import jdk.incubator.vector.VectorOperators;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import store.domain.promotion.Promotion;
import store.reader.PromotionReader;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void test() throws IOException {
        PromotionReader promotionReader = new PromotionReader();
        List<Promotion> list = promotionReader.createPromotions("src/main/resources/promotions.md");
        Product product = new Product("콜라",1000, Optional.of(list.getFirst()),10,10);

    }
}
