package com.daily.common;

import com.daily.service.IMerchantTableService;
import org.springframework.context.ApplicationContext;

import java.util.Timer;
import java.util.TimerTask;

public class FreeTableTimer extends TimerTask {

    private int tableId;

    private static ApplicationContext context;


    public static void startTimeTask(int tableId, long second) {
        Timer timer = new Timer();
        long delay = second * 1000;
        timer.schedule(new FreeTableTimer(tableId), delay);
    }

    public static void setContext(ApplicationContext context) {
        FreeTableTimer.context = context;
    }

    private FreeTableTimer(int tableId) {
        this.tableId = tableId;
    }

    @Override
    public void run() {
        IMerchantTableService iMerchantTableService = context.getBean(IMerchantTableService.class);
        System.err.println("准备空闲一个id为" + tableId + "的桌子");
        if (!iMerchantTableService.isArrived(tableId)) {
            System.err.println("成功空闲一个id为" + tableId + "的桌子");
            iMerchantTableService.freeTable(tableId);
        }
    }
}
