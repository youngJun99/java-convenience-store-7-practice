package store.controller;

import store.domain.Store;
import store.dto.ProductReceipt;
import store.service.OrderService;
import store.service.ReceiptPrintService;
import store.service.StoreGenerateService;
import store.utils.ErrorCatcher;

import java.util.List;

public class Controller {

    private final StoreGenerateService storeGenerateService;
    private final OrderService orderService;
    private final ReceiptPrintService receiptPrintService;

    public Controller(StoreGenerateService storeGenerateService, OrderService orderService, ReceiptPrintService receiptPrintService) {
        this.storeGenerateService = storeGenerateService;
        this.orderService = orderService;
        this.receiptPrintService = receiptPrintService;
    }

    public void run() {
        Store generatedStore = ErrorCatcher.returnRetryHandler(storeGenerateService::generate);
        boolean continueShopping = true;

        while (continueShopping) {
            List<ProductReceipt> receipt = orderService.processOrder(generatedStore);
            receiptPrintService.receiptPrintAndContinue(receipt);
            continueShopping = orderService.continueShopping();
        }
    }
}
