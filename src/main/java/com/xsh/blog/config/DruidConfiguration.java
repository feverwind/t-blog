package com.xsh.blog.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.xsh.blog.model.Vo.OptionVo;
import com.xsh.blog.service.IOptionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfiguration {
    private static final String DB_PREFIX = "spring.datasource";

    @Resource
    private IOptionService optionService;

    //读取相关的属性配置
    @ConfigurationProperties(prefix = DB_PREFIX)
    @Bean
    public DataSource dataSource(){
        return new DruidDataSource();
    }

    //配置一个管理后台的Servlet,配置Druid的监控
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        Map<String,String> initParams = new HashMap<String, String>();
        initParams.put("loginUsername","admin");

        /* 优先使用数据库中存放的密码 */
        OptionVo optionVo = optionService.getOptionByName("site_druidpassword");
        if (optionVo != null && StringUtils.isNotBlank(optionVo.getValue())){
            initParams.put("loginPassword", optionVo.getValue());
        }else {
            initParams.put("loginPassword","feverwind");
        }

        bean.setInitParameters(initParams);
        return bean;
    }

    //配置一个web监控的filter
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());

        Map<String,String> initParams = new HashMap<>();
        initParams.put("exclusions","*.js,*.css,*.gif,*.jpg,*.png,*.ico,/druid/*");

        bean.setInitParameters(initParams);

        bean.setUrlPatterns(Arrays.asList("/*"));

        return  bean;
    }
}