package store.service;

import store.dto.PayReceipt;
import store.dto.ProductReceipt;
import store.handler.InputHandler;
import store.utils.ErrorCatcher;
import store.view.OutputView;

import java.util.List;

public class ReceiptPrintService {

    private final InputHandler inputHandler;
    private final OutputView outputView;

    public ReceiptPrintService(InputHandler inputHandler, OutputView outputView) {
        this.inputHandler = inputHandler;
        this.outputView = outputView;
    }

    public void receiptPrintAndContinue(List<ProductReceipt> receipt) {
        PayReceipt finalPayment = finalCalculation(receipt);
        outputView.printBoughtReceipt(receipt);
        outputView.printFinalCalculationReceipt(finalPayment);
    }

    private PayReceipt finalCalculation(List<ProductReceipt> receipt) {
        int totalBought = receipt.stream()
                .mapToInt(ProductReceipt::boughtAmount)
                .sum();
        int totalPay = receipt.stream()
                .mapToInt(productReceipt -> productReceipt.boughtAmount() * productReceipt.productPrice())
                .sum();
        int discount = receipt.stream()
                .mapToInt(productReceipt -> productReceipt.promotedAmount() * productReceipt.productPrice())
                .sum();
        int memberShipDiscount = processMemberShipDiscount(totalPay - discount);
        int total = totalPay - discount - memberShipDiscount;
        return new PayReceipt(totalBought, totalPay, discount, memberShipDiscount, total);
    }

    private int processMemberShipDiscount(int currentPay) {
        boolean memberShipDiscount = ErrorCatcher.returnRetryHandler(inputHandler::confirmMemberShipDiscount);
        if (memberShipDiscount) {
            return Math.min(8000, currentPay * 3 / 10);
        }
        return 0;
    }
}
