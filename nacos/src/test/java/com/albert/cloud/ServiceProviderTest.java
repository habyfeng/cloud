package com.albert.cloud;


import com.alibaba.nacos.api.exception.NacosException;
import org.junit.jupiter.api.Test;

public class ServiceProviderTest {

    @Test
    public void testInit() throws InterruptedException, NacosException {
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.initConfigService();
        serviceProvider.initNamingService();
        Thread.currentThread().join();
    }
}
