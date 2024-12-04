package store.reader;

import store.domain.Product;
import store.domain.Store;
import store.domain.promotion.Promotion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StoreReader {

    public Store createStore(List<Promotion> promotions, String productsPath) throws IOException {
        List<Product> products = new ArrayList<>();
        Path path = Paths.get(productsPath);
        List<String> lines = Files.readAllLines(path);
        for (int i = 1; i < lines.size(); i++) {
            String[] data = lines.get(i).split(",");
            String productName = data[0].trim();
            int price = Integer.parseInt(data[1].trim());
            boolean isAlreadyProcessed = products.stream()
                    .filter(product -> product.getName().equals(productName))
                    .findAny()
                    .map(product -> {
                        product.refillNormalQuantity(Integer.parseInt(data[2].trim()));
                        return true;
                    })
                    .orElse(false);
            if (isAlreadyProcessed) {
                continue;
            }
            String promotionName = data[3].trim();
            if (promotionName.equals("null")) {
                products.add(new Product(productName, price, null, Integer.parseInt(data[2].trim()), 0));
                continue;
            }
            Optional<Promotion> promotion = promotions.stream()
                    .filter(promotionSelected -> promotionSelected.getName().equals(promotionName))
                    .findFirst();
            products.add(new Product(productName, price, promotion, 0, Integer.parseInt(data[2].trim())));
        }
        return new Store(products);
    }

}
