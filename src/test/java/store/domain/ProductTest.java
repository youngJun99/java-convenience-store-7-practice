package store.domain;

import org.junit.jupiter.api.Test;
import store.domain.promotion.Promotion;
import store.dto.ProductOrderRequest;
import store.dto.ProductOrderResponse;
import store.reader.PromotionReader;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


class ProductTest {

    @Test
    void test() throws IOException {
        PromotionReader promotionReader = new PromotionReader();
        List<Promotion> list = promotionReader.createPromotions("src/main/resources/promotions.md");
        Product product = new Product("콜라",1000, Optional.of(list.getFirst()),10,10);
        ProductOrderRequest request = new ProductOrderRequest("콜라",2, LocalDateTime.now());
        ProductOrderResponse response = product.checkOrder(request);
        System.out.println(response);
    }
}
