package com.revengemission.sso.oauth2.resource.coupon.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Task {

    /**
     * 每隔5分钟执行一次
     */
    @Scheduled(cron = "0 */5 * * * *")
    public void task1() {
        System.out.println("[ 当前时间" + LocalDateTime.now() + " ]");
    }

    /**
     * 每天凌晨2点30分0秒执行
     */
    @Scheduled(cron = "0 30 2 * * *")
    public void task2() {
        System.out.println("[ 当前时间" + LocalDateTime.now() + " ]");
    }
}
