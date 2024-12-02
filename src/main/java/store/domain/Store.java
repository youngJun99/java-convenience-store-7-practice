package store.domain;

import store.constants.Errors;
import store.dto.*;

import java.util.List;

public class Store {

    private final List<Product> products;

    public Store(List<Product> products) {
        this.products = products;
    }

    public List<ProductInventory> showInventories() {
        return products.stream()
                .flatMap(product -> product.requestInventoryInfo().stream())
                .toList();
    }

    public List<ProductOrderResponse> requestOrder(List<ProductOrderRequest> requests) {

        return products.stream()
                .map(product -> {
                    ProductOrderRequest productRequest = requests.stream()
                            .filter(request -> request.productName().equals(product.getName()))
                            .findAny()
                            .orElseThrow(() -> new IllegalArgumentException(Errors.NOT_EXISTING_PRODUCT.getMessage()));
                    return product.checkOrder(productRequest);
                }).toList();
    }

    public List<ProductReceipt> executeOrder(List<ConfirmedOrderRequest> requests) {
        return products.stream()
                .map(product -> {
                    ConfirmedOrderRequest confirmedRequest = requests.stream()
                            .filter(request -> request.productName().equals(product.getName()))
                            .findFirst().get();
                    return product.executeOrder(confirmedRequest);
                }).toList();
    }
}
