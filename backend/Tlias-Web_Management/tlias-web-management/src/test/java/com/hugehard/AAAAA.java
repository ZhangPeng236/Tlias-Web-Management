package com.hugehard;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author 张鹏
 * @version 1.0
 * 我亦无他，唯手熟尔
 */
@Slf4j
public class AAAAA {
    @Test
    public void testLog() {
        B b = new B();
        b.start();
        for (int i = 1; i <= 10; i++) {
            log.info("主线程：{},{}", i);
        }
    }
}

@Slf4j
class B extends Thread {
    @Override
    public void run() {
        int a = 20;
        try {
            a = 30;
            log.info("内层结果1是：{}", a / 0);
            a = 40;
        } catch (Exception e) {
            a = 50;
            log.info("内层结果2是：{}", a);
        } finally {
            a = 60;
            log.info("内层结果3是：{}", a);
        }
    }
}