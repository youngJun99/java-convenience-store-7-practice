package store.service;

import store.dto.PayReceipt;
import store.dto.ProductReceipt;
import store.handler.InputHandler;
import store.view.OutputView;

import java.util.List;

public class ReceiptPrintService {

    private final OutputView outputView;
    private final InputHandler inputHandler;

    public ReceiptPrintService(OutputView outputView, InputHandler inputHandler) {
        this.outputView = outputView;
        this.inputHandler = inputHandler;
    }

    public void receiptPrintAndContinue(List<ProductReceipt> receipt) {
        outputView.printBoughtReceipt(receipt);
        PayReceipt finalPayment = finalCalculation(receipt);
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
        boolean memberShipDiscount = inputHandler.confirmMemberShipDiscount();
        if (memberShipDiscount) {
            return Math.max(8000, currentPay * 3 / 10);
        }
        return 0;
    }
}
