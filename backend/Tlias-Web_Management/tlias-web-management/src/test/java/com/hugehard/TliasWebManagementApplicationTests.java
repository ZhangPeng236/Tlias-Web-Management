package com.hugehard;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
class TliasWebManagementApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("求倒数测试开始===========================");
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(0);
        numbers.add(5);
        for (Integer number : numbers) {
            try {
                System.out.println(number + "的倒数是：" + (1 / number));
            } catch (Exception e) {
                System.out.println("出现异常：" + e.getMessage());
            }
        }
        System.out.println("求倒数测试结束===========================");
    }
}