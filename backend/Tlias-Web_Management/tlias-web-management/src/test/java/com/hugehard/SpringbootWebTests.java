package com.hugehard;

import com.google.gson.Gson;
import com.hugehard.pojo.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class SpringbootWebTests {
    @Autowired
    private ApplicationContext applicationContext; // IOC 容器

    @Autowired
    private Gson gson; // 可以自动将对象转为 JSON

    @Test
    public void testGson() {
        System.out.println(gson.toJson(Result.success("Hello Gson")));
    }

    @Test
    public void testScope() {
        for (int i = 0; i < 1000; i++) {
            Object deptController = applicationContext.getBean("deptController");
            System.out.println(deptController);
        }
    }
}