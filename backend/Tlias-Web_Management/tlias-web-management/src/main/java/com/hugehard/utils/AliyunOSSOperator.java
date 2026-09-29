package com.hugehard.utils;

import com.aliyun.oss.*;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component // -- 假设该类为第三方类，不能使用@Component注解
public class AliyunOSSOperator {

    // 原始方式：环境信息写死在java代码中
    // private String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
    // private String bucketName = "zpbucketname";
    //private String region = "cn-hangzhou";

    // 方式一：通过@Value注解一个属性一个属性的注入
    // @Value("${aliyun.oss.endpoint}")
    // private String endpoint;
    // @Value("${aliyun.oss.bucketName}")
    // private String bucketName;
    // @Value("${aliyun.oss.region}")
    // private String region;

     // 方式二：
     @Autowired // @Autowired注解，只能在SpringBoot的容器中，否则会报错
     private AliyunOSSProperties aliyunOSSProperties;

    // 使用有参构造对属性进行赋值
    // private final AliyunOSSProperties aliyunOSSProperties;
    //
    // public AliyunOSSOperator(AliyunOSSProperties aliyunOSSProperties) {
    //     this.aliyunOSSProperties = aliyunOSSProperties;
    // }

    public String upload(byte[] content, String originalFilename) throws Exception {
        String endpoint = aliyunOSSProperties.getEndpoint();
        String bucketName = aliyunOSSProperties.getBucketName();
        String region = aliyunOSSProperties.getRegion();

        // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
        EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();

        // 填写Object完整路径，例如202406/1.png。Object完整路径中不能包含Bucket名称。
        //获取当前系统日期的字符串,格式为 yyyy/MM
        String dir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM"));
        //生成一个新的不重复的文件名
        String newFileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
        String objectName = dir + "/" + newFileName;

        // 创建OSSClient实例。
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
        OSS ossClient = OSSClientBuilder.create()
                .endpoint(endpoint)
                .credentialsProvider(credentialsProvider)
                .clientConfiguration(clientBuilderConfiguration)
                .region(region)
                .build();

        try {
            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(content));
        } finally {
            ossClient.shutdown();
        }

        return endpoint.split("//")[0] + "//" + bucketName + "." + endpoint.split("//")[1] + "/" + objectName;
    }
}