package com.albert;

import com.albert.api.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.builders.ReferenceBuilder;

/**
 * RPC Client
 */
@Slf4j
public class AppDubboClient {
    public static void main(String[] args) {

    }

    public void callDirectly() {
        // 使用标准的Dubbo Client请求服务，指定server地址即可发起RPC调用
        DemoService demoService = (DemoService) ReferenceBuilder.newBuilder()
                .interfaceClass(DemoService.class)
                .url("tri://localhost:50051")
                .build()
                .get();

        String resp = demoService.sayHello("albert");
        log.info("call directly resp: {}", resp);
    }

    public void callWithRegistry() {
        // 使用注册中心
        DemoService demoService = (DemoService) ReferenceBuilder.newBuilder()
                .interfaceClass(DemoService.class)
                .addRegistry(new RegistryConfig("nacos://127.0.0.1:8848"))
                .build()
                .get();

        String resp = demoService.sayHello("albert");
        log.info("call with registry resp: {}", resp);

    }
}
