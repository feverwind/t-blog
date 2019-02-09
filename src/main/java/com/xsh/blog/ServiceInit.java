package com.xsh.blog;

import com.xsh.blog.config.DruidConfiguration;
import com.xsh.blog.constant.WebConst;
import com.xsh.blog.model.Vo.OptionVo;
import com.xsh.blog.service.IOptionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by longtaiye on 2019/2/9.
 */
@Slf4j
@Component
public class ServiceInit implements CommandLineRunner {
    @Resource
    private IOptionService optionService;

    @Override
    public void run(String ...args) {
        log.info("-----Service Init Start-------");

        List<OptionVo> voList = optionService.getOptions();

        /* 读取配置到initConfig HashMap中 */
        voList.forEach((option) -> {
            WebConst.initConfig.put(option.getName(), option.getValue());
        });

        log.info("-----Service Init End-------");
    }
}
