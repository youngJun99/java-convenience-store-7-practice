package store.handler;

import camp.nextstep.edu.missionutils.DateTimes;
import store.dto.ProductOrderRequest;
import store.dto.ProductOrderResponse;
import store.view.InputView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputHandler {

    private static final String ORDER_REGEX = "\\[([가-힣a-zA-Z0-9]+)-(\\d+)\\]";

    private final InputView inputView;
    private final InputValidator inputValidator;

    public InputHandler(InputView inputView, InputValidator inputValidator) {
        this.inputView = inputView;
        this.inputValidator = inputValidator;
    }

    public List<ProductOrderRequest> getOrders() {
        String request = inputView.printOrderRequest();
        inputValidator.validateOrder(request);
        Pattern pattern = Pattern.compile(ORDER_REGEX);
        Matcher matcher = pattern.matcher(request);
        List<ProductOrderRequest> orderList = new ArrayList<>();
        while (matcher.find()) {
            orderList.add(new ProductOrderRequest(matcher.group(1), Integer.parseInt(matcher.group(2)), DateTimes.now()));
        }
        return orderList;
    }

    public boolean confirmOrders(ProductOrderResponse response) {
        if (response.unPromotableInventory() != 0) {
            String input = inputView.printNotPromotableRequest(response.productName(), response.unPromotableInventory());
            inputValidator.validateCustomerResponse(input);
            return input.equals("Y");
        }
        String input = inputView.bonusReceivableRequest(response.productName(), response.bonusReceivable());
        inputValidator.validateCustomerResponse(input);
        return input.equals("Y");
    }

    public boolean continueShopping() {
        String input = inputView.printContinueBuyRequest();
        inputValidator.validateCustomerResponse(input);
        return input.equals("Y");
    }
}
