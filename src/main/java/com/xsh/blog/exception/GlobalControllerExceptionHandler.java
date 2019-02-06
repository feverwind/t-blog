package com.xsh.blog.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @Author:xieshuang
 * @Description: Global Exception Handling
 * @Date:Create in 21:01 2018/3/21
 * @Modified By :
 */
@ControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(value = BusinessException.class)
    public String businessExceptionHandler(Exception e) {
        log.error("find exception:e={}",e.getMessage());
        e.printStackTrace();
        return "comm/error_500";
    }


    @ExceptionHandler(value = Exception.class)
    public String exceptionHandler(Exception e){
        log.error("find exception:e={}",e.getMessage());
        e.printStackTrace();
        return "comm/error_404";
    }

}
