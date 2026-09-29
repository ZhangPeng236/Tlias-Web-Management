package com.hugehard;

import com.hugehard.utils.AliyunOSSOperator;
import com.hugehard.utils.AliyunOSSProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

// @ComponentScan(basePackages = {"com.example", "com.itheima", "com.hugehard"}) // 扫描指定包下的类，默认扫描启动类所在包及其子包将不再声明
// 自动配置实现方案一：@Component + @ComponentScan，使用繁琐，性能低

// 自动配置实现方案二：@Import
// @Import(TokenParser.class) // ①导入指定普通类
// @Import(AliyunOSSProperties.class) // ②导入指定配置类
// @Import(MyImportSelector.class) // ③导入ImportSelector接口的实现类 - 批量导入
// @EnableHeaderConfig // ④EnableXxxx注解，封装@Import注解，方便且优雅

@ServletComponentScan // 开启了SpringBoot对Servlet组件的支持
@SpringBootApplication // 具备组件扫描功能，但是默认扫描的是启动类所在包及其子包
public class TliasWebManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(TliasWebManagementApplication.class, args);
    }

    // 一般不直接定义在启动类中，一般定义在配置类中
    // @Bean // 将方法返回值交给IOC容器管理，成为IOC容器的bean对象
    // public AliyunOSSOperator aliyunOSSOperator(@Autowired AliyunOSSProperties aliyunOSSProperties) {
    //     return new AliyunOSSOperator(aliyunOSSProperties);
    // }
}