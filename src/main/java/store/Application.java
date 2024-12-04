package store;

import camp.nextstep.edu.missionutils.DateTimes;
import store.controller.Controller;

import java.time.LocalDateTime;

public class Application {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        Controller controller = appConfig.controller();
        controller.run();
    }
}
