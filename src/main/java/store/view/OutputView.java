package store.view;

import store.dto.ProductInventory;
import store.dto.ProductReceipt;

import java.text.DecimalFormat;
import java.util.List;

public class OutputView {

    private static final String INVENTORY_VIEW_FORMAT = "- %s %s원 %s %s";
    private static final DecimalFormat moneyFormat = new DecimalFormat("#,###");

    public void printInventoryStatus(List<ProductInventory> inventories) {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.");
        inventories.forEach(productInventory -> {
                    String output = String.format(INVENTORY_VIEW_FORMAT,
                            productInventory.name(),
                            moneyFormat.format(productInventory.price()),
                            getInventory(productInventory.inventory()),
                            productInventory.promotionName());
                    System.out.println(output);
                    });
    }

    private String getInventory(int inventory) {
        if(inventory == 0) {
            return "재고없음";
        }
        return inventory+"개";
    }

    public void printReceipt(List<ProductReceipt> receipt) {
        System.out.println("==============W 편의점================");
        System.out.println("상품명\t\t수량\t금액");
    }
}


