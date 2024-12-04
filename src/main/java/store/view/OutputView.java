package store.view;

import store.dto.PayReceipt;
import store.dto.ProductInventory;
import store.dto.ProductReceipt;

import java.text.DecimalFormat;
import java.util.List;

public class OutputView {

    private static final String INVENTORY_VIEW_FORMAT = "- %s %s원 %s %s";
    private static final String RECEIPT_VIEW_FORMAT = "%s\t\t%d \t%s";
    private static final String BONUS_VIEW_FORMAT = "%s\t\t%d";
    private static final String FINAL_PAYMENT_VIEW_FORMAT = """
            총구매액		%d	%s
            행사할인			-%s
            멤버십할인			-%s
            내실돈			 %s
            """;
    private static final DecimalFormat MONEY_FORMAT = new DecimalFormat("#,###");

    public void printInventoryStatus(List<ProductInventory> inventories) {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.");
        inventories.forEach(productInventory -> {
            String output = String.format(INVENTORY_VIEW_FORMAT,
                    productInventory.name(),
                    MONEY_FORMAT.format(productInventory.price()),
                    getInventory(productInventory.inventory()),
                    productInventory.promotionName());
            System.out.println(output);
        });
    }

    private String getInventory(int inventory) {
        if (inventory == 0) {
            return "재고없음";
        }
        return inventory + "개";
    }

    public void printBoughtReceipt(List<ProductReceipt> receipt) {
        System.out.println("==============W 편의점================");
        System.out.println("상품명\t\t수량\t금액");
        receipt.forEach(productReceipt -> {
            System.out.println(String.format(RECEIPT_VIEW_FORMAT,
                    productReceipt.productName(),
                    productReceipt.boughtAmount(),
                    MONEY_FORMAT.format(productReceipt.boughtAmount() * productReceipt.productPrice())));
        });
        System.out.println("        =============증\t정===============");
        receipt.stream()
                .filter(productReceipt -> productReceipt.promotedAmount() != 0)
                .forEach(productReceipt -> {
                    System.out.println(String.format(BONUS_VIEW_FORMAT,
                            productReceipt.productName(),
                            productReceipt.promotedAmount()));
                });
        System.out.println("        ====================================");
    }

    public void printFinalCalculationReceipt(PayReceipt payReceipt) {
        System.out.println(String.format(FINAL_PAYMENT_VIEW_FORMAT,
                payReceipt.totalBuy(),
                MONEY_FORMAT.format(payReceipt.totalPay()),
                MONEY_FORMAT.format(payReceipt.discount()),
                MONEY_FORMAT.format(payReceipt.memberShipDiscount()),
                MONEY_FORMAT.format(payReceipt.needToPay())));
    }
}

