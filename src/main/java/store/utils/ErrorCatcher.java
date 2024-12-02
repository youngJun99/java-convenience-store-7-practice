package store.utils;

import store.constants.Errors;

import java.util.function.Supplier;

public class ErrorCatcher {

    private static final int RETRY_LIMIT = 10;

    //값을 반환 받아야 할 때
    public static <T> T returnRetryHandler(Supplier<T> method) {
        int retry = 0;
        while (retry < RETRY_LIMIT) {
            retry++;
            try {
                return method.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        System.err.printf((Errors.OVER_MAX_RETRIES.getMessage()),RETRY_LIMIT);
        System.exit(1);
        return null;
    }

    //값의 반환이 필요 없을 때
    public static void voidRetryHandler(Runnable method) {
        int retry = 0;
        while (retry < RETRY_LIMIT) {
            retry++;
            try {
                method.run();
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        System.err.printf((Errors.OVER_MAX_RETRIES.getMessage()),RETRY_LIMIT);
        System.exit(1);
    }
}

