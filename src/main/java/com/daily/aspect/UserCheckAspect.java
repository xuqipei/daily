package com.daily.aspect;

import com.daily.common.Const;
import com.daily.common.ServerResponse;
import com.daily.entity.DailyMerchant;
import com.daily.entity.DailyUser;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * Created by xuqipei on 17-7-24.
 */
@Aspect
@Component
public class UserCheckAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserCheckAspect.class);

    @Autowired
    private HttpSession session;

    @Pointcut("execution(* com.daily.controller..*.aop*(..))")
    public void userPointcut() {
    }

    @Pointcut("execution(* com.daily.controller..*.admin*(..))")
    public void adminPoint() {
    }

    @Around("userPointcut()")
    public ServerResponse userAround(ProceedingJoinPoint pjp) throws Throwable {
        LOGGER.info("普通用户登录检测");
        DailyUser dailyUser = (DailyUser) session.getAttribute(Const.CURRENT_USER);
        Object object;
        if (dailyUser == null) {
            object = ServerResponse.createByErrorMessage("未登录,请登录");
        } else {
            object = pjp.proceed();
        }
        ServerResponse serverResponse = (ServerResponse) object;
        return serverResponse;
    }

    @Around("adminPoint()")
    public ServerResponse adminAround(ProceedingJoinPoint pjp) throws Throwable {
        LOGGER.info("商户登录检测");
        DailyMerchant dailyMerchant = (DailyMerchant) session.getAttribute(Const.CURRENT_ADMIN);
        Object object;
        if (dailyMerchant == null) {
            object = ServerResponse.createByErrorMessage("未登录,请登录");
        } else {
            object = pjp.proceed();
        }
        ServerResponse serverResponse = (ServerResponse) object;
        return serverResponse;
    }
}
