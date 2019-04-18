package com.xxx.trail.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.nio.charset.Charset;

/**
 * @ClassName WebMvcConfig
 * @Description 相当于 springmvc-config.xml中的配置信息
 * @Author Lilg
 * @Date 2019/4/17 21:36
 * @Version 1.0
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.xxx.trail.controller") // 包扫描 <context:component-scan base-package="com.xxx.*.controller" />
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    /*<!-- 定义视图解析器 -->
	<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<!-- 前缀 -->
		<property name="prefix" value="/WEB-INF/views/" />
		<!-- 后缀 -->
		<property name="suffix" value=".jsp" />
	</bean>*/
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    /*<!-- 文件上传解析器，id属性必须是multipartResolver，底层代码就按这个名称去调用 -->
	<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
		<!-- 设置文件上传的最大值10M 1024*1024*10-->
		<property name="maxUploadSize" value="10485760"/>
	</bean>*/
    @Bean(name = "multipartResolver") // bean必须写name属性且必须为multipartResolver
    protected CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setMaxUploadSize(5 * 1024 * 1024);
        commonsMultipartResolver.setMaxInMemorySize(0);
        commonsMultipartResolver.setDefaultEncoding("UTF-8");
        return commonsMultipartResolver;
    }

    @Bean
    public HttpMessageConverter configureHttpMessageConverter() {
        return new StringHttpMessageConverter(Charset.forName("UTF-8"));
    }

    /*<!-- 处理静态资源被“/”所拦截的问题 -->
	<mvc:default-servlet-handler />*/
    // 静态资源的处理
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
