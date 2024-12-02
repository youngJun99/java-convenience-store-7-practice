package store.constants;

public enum Errors {

    //시스템 에러
    ORDER_INPUT_NOT_VALID("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요"),
    ORDER_OVER_INVENTORY("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    NOT_EXISTING_PRODUCT("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    INPUT_NOT_VALID("잘못된 입력입니다. 다시 입력해 주세요.`");


    private static final String PREFIX = "[ERROR] ";

    private String message;

    Errors(String message) {
        this.message = message;
    }

    public String getMessage() {
        return PREFIX + message;
    }
}
