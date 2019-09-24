package com.xxx.trail.config;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 加载Spring 启动的内容
 * @author Lilg
 * @version 1.0
 * @since 1.0
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
