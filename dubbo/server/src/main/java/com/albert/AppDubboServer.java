package com.albert;

import com.albert.api.DemoService;
import com.albert.impl.DemoServiceImpl;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.bootstrap.builders.ServiceBuilder;

/**
 * RPC Server
 */
public class AppDubboServer {
    public static void main( String[] args ) {

    }

    public void startAlone() {
        // 注册服务并启动server
        DubboBootstrap.getInstance()
                .protocol(new ProtocolConfig(CommonConstants.TRIPLE, 50051))
                .service(ServiceBuilder.newBuilder().interfaceClass(DemoService.class).ref(new DemoServiceImpl()).build())
                .start()
                .await();
    }

    public void startWithRegistry() {
        // 使用注册中心
        DubboBootstrap.getInstance()
                .application("PAY")
                .registry(new RegistryConfig("nacos://127.0.0.1:8848"))
//                .protocol(new ProtocolConfig(CommonConstants.TRIPLE, 50051))
                .service(ServiceBuilder.newBuilder().interfaceClass(DemoService.class).ref(new DemoServiceImpl()).build())
                .start()
                .await();


    }
}
