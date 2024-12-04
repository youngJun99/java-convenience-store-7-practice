package store.service;

import store.domain.Store;
import store.dto.*;
import store.handler.InputHandler;
import store.utils.ErrorCatcher;
import store.view.OutputView;

import java.util.List;

public class OrderService {

    private final InputHandler inputHandler;
    private final OutputView outputView;

    public OrderService(InputHandler inputHandler, OutputView outputView) {
        this.inputHandler = inputHandler;
        this.outputView = outputView;
    }

    public List<ProductReceipt> processOrder(Store store) {
        List<ProductInventory> inventories = store.showInventories();
        outputView.printInventoryStatus(inventories);
        List<ProductOrderRequest> requests = ErrorCatcher.returnRetryHandler(inputHandler::getOrders);
        List<ProductOrderResponse> responses = store.requestOrder(requests);
        List<ConfirmedOrderRequest> confirmedRequests = confirmOrders(responses);
        return store.executeOrder(confirmedRequests);
    }

    public boolean continueShopping() {
        return inputHandler.continueShopping();
    }

    private List<ConfirmedOrderRequest> confirmOrders(List<ProductOrderResponse> responses) {
        return responses.stream()
                .map(response -> {
                    if (response.executable()) {
                        return new ConfirmedOrderRequest(
                                response.productName(),
                                response.normalInventory(),
                                response.promotionInventory(),
                                response.orderedTime()
                        );
                    }
                    if (response.unPromotableInventory() != 0) {
                        boolean buyUnPromotable = ErrorCatcher.returnRetryHandler(() -> inputHandler.confirmUnPromotableOrders(response));
                        if (buyUnPromotable) {
                            return new ConfirmedOrderRequest(
                                    response.productName(),
                                    response.unPromotableInventory(),
                                    response.promotionInventory(),
                                    response.orderedTime());
                        }
                        return new ConfirmedOrderRequest(
                                response.productName(),
                                response.normalInventory(),
                                response.promotionInventory(),
                                response.orderedTime());
                    }
                    boolean getBonuse = ErrorCatcher.returnRetryHandler(() -> inputHandler.confirmBonusReceivableOrders(response));
                    if (getBonuse) {
                        return new ConfirmedOrderRequest(
                                response.productName(),
                                response.normalInventory(),
                                response.promotionInventory() + response.bonusReceivable(),
                                response.orderedTime());
                    }
                    return new ConfirmedOrderRequest(
                            response.productName(),
                            response.normalInventory(),
                            response.promotionInventory(),
                            response.orderedTime());
                }).toList();
    }
}
