package com.xxx.trail.config;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @ClassName RootConfig
 * @Description 加载spring启动的一些内容
 * @Author Lilg
 * @Date 2019/4/17 22:21
 * @Version 1.0
 */
@Configuration
@ComponentScan(basePackages = { "com.xxx.trail.config", "com.xxx.trail.service.impl" })
@Import(DataSourceConfig.class)
public class RootConfig {

    @Bean
    public BeanNameAutoProxyCreator autoProxyCreator() {
        BeanNameAutoProxyCreator autoProxyCreator = new BeanNameAutoProxyCreator();
        autoProxyCreator.setProxyTargetClass(true);
        // 设置要创建代理的那些Bean的名字
        autoProxyCreator.setBeanNames("*Service*");
        autoProxyCreator.setInterceptorNames("transactionInterceptor");
        return autoProxyCreator;
    }

}
