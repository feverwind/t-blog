package com.xsh.blog.interceptor;


import com.xsh.blog.utils.TaleUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

/**
 * 向mvc中添加自定义组件
 * Created by BlueT on 2017/3/9.
 */
@Component
public class WebMvcConfig implements WebMvcConfigurer {
    @Resource
    private BaseInterceptor baseInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /* 对除了 /static/admin/ 目录下的静态文件的访问外进行拦截 */
        registry.addInterceptor(baseInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/admin/js/**","/admin/images/**", "/admin/css/**");
    }

    /**
     * 添加静态资源文件，外部可以直接访问地址
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**").addResourceLocations("file:"+ TaleUtils.getUplodFilePath()+"upload/");
//        super.addResourceHandlers(registry);
    }
}
