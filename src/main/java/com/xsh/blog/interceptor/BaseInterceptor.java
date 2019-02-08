package com.xsh.blog.interceptor;

import com.xsh.blog.constant.WebConst;
import com.xsh.blog.dto.Types;
import com.xsh.blog.model.Vo.OptionVo;
import com.xsh.blog.model.Vo.UserVo;
import com.xsh.blog.service.IOptionService;
import com.xsh.blog.service.IUserService;
import com.xsh.blog.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器
 * Created by BlueT on 2017/3/9.
 */
@Component
public class BaseInterceptor implements HandlerInterceptor {
    private static final Logger LOGGE = LoggerFactory.getLogger(BaseInterceptor.class);
    private static final String USER_AGENT = "user-agent";

    @Resource
    private IUserService userService;

    @Resource
    private IOptionService optionService;

    private MapCache cache = MapCache.single();

    @Resource
    private Commons commons;

    @Resource
    private AdminCommons adminCommons;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String uri = request.getRequestURI();

        LOGGE.info("UserAgent: {}", request.getHeader(USER_AGENT));
        LOGGE.info("用户访问地址: {}, 来路地址: {}", uri, IPKit.getIpAddrByRequest(request));


        //请求拦截处理
        UserVo user = TaleUtils.getLoginUser(request);
        if (null == user) {
            Integer uid = TaleUtils.getCookieUid(request);
            if (null != uid) {
                //这里还是有安全隐患,cookie是可以伪造的
                user = userService.queryUserById(uid);
                request.getSession().setAttribute(WebConst.LOGIN_SESSION_KEY, user);
            }

            /* admin 的路径访问跳转到登陆页面 */
            if (uri.startsWith("/admin") && !uri.startsWith("/admin/login")) {
                response.sendRedirect(request.getContextPath() + "/admin/login");
                return false;
            }
        }

        //设置get请求的token
        if (request.getMethod().equals("GET")) {
            String csrfToken = UUID.UU64();
            // 默认存储30分钟
            cache.hset(Types.CSRF_TOKEN.getType(), csrfToken, uri, 30 * 60);
            request.setAttribute("_csrf_token", csrfToken);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        OptionVo ov = optionService.getOptionByName("site_record");

        //一些工具类和公共方法
        httpServletRequest.setAttribute("commons", commons);
        httpServletRequest.setAttribute("option", ov);
        httpServletRequest.setAttribute("adminCommons", adminCommons);
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
