package com.albert.cloud;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.Event;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.Executor;

@Slf4j
public class ServiceProvider {
    private String serverIp = "localhost";
    private int serverPort = 8848;
    private String serverAddr = "localhost:8848";
    private ConfigService configService;
    private NamingService namingService;

    /**
     * 配置中心初始化
     * @throws NacosException
     */
    public void initConfigService() throws NacosException {
        configService = NacosFactory.createConfigService(serverAddr);
        log.info("ConfigServerStatus: {}", configService.getServerStatus());
        // 配置id
        String dataId = "com.albert.log.level";
        // 配置分组
        String group = "com.albert.cloud";

        // 发布配置
        configService.publishConfig(dataId, group, "DEBUG");

        long timeout = 5000L;
        // 获取配置
        configService.getConfig(dataId, group, timeout);

        // 监听配置
        configService.addListener(dataId, group, new Listener() {
            @Override
            public Executor getExecutor() {
                // 使用自定义线程池进行异步监听回调时使用
                return null;
            }

            @Override
            public void receiveConfigInfo(String configInfo) {
                log.info("receive config: {}", configInfo);
            }
        });
    }

    /**
     * 注册中心初始化
     * @throws NacosException
     */
    public void initNamingService() throws NacosException {
        namingService = NacosFactory.createNamingService(serverAddr);
        log.info("NamingServerStatus: {}", namingService.getServerStatus());

        Instance instance = new Instance();
        // 集群名称，默认值DEFAULT
        instance.setClusterName("DEFAULT");
        instance.setIp(serverIp);
        instance.setPort(serverPort);

        // 服务名
        String serviceName = "com.albert.service.pay";
        // 分组名，默认值DEFAULT_GROUP
        String groupName = "ALBERT";
        // 注册实例
        namingService.registerInstance(serviceName, groupName, instance);
        // 获取全部实例
        List<Instance> instanceList = namingService.getAllInstances(serviceName, groupName);
        instanceList.forEach(ins -> log.info("instance: {}", ins));

        // 监听服务, 监听服务下的实例列表变化
        namingService.subscribe(serviceName, groupName, new com.alibaba.nacos.api.naming.listener.EventListener() {
            @Override
            public void onEvent(Event event) {
                if (event instanceof NamingEvent) {
                    log.info(((NamingEvent) event).getServiceName());
                }
            }
        });
    }

}
