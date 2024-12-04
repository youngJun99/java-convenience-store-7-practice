package store;

import store.controller.Controller;
import store.handler.InputHandler;
import store.handler.InputValidator;
import store.reader.PromotionReader;
import store.reader.StoreReader;
import store.service.OrderService;
import store.service.ReceiptPrintService;
import store.service.StoreGenerateService;
import store.view.InputView;
import store.view.OutputView;

public class AppConfig {

    public Controller controller() {
        InputHandler inputHandler = new InputHandler(
                new InputView(),
                new InputValidator());
        OutputView outputView = new OutputView();

        return new Controller(
                new StoreGenerateService(
                        new PromotionReader(),
                        new StoreReader()),
                new OrderService(inputHandler, outputView),
                new ReceiptPrintService(inputHandler, outputView));
    }
}
