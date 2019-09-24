package com.xxx.trail;

import com.xxx.trail.config.RootConfig;
import com.xxx.trail.config.WebMvcConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * 替代 web.xml
 * @author Lilg
 * @version 1.0
 * @since 1.0
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{RootConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebMvcConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};// 请求路径映射，将路径映射到DispatcherServlet上
    }
}
