package com.daily;

import com.daily.common.FreeTableTimer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DailyApplication {
    public static void main(String[] args) {
        final ApplicationContext applicationContext = SpringApplication.run(DailyApplication.class, args);
        FreeTableTimer.setContext(applicationContext);
    }
}
