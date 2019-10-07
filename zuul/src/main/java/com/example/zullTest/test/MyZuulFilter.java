package com.example.zullTest.test;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import  com.netflix.zuul.ZuulFilter;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2019/7/28 17:03
 */
@Component
public class MyZuulFilter extends  ZuulFilter  {

    private static Logger logger = LoggerFactory.getLogger(MyZuulFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext cx = RequestContext.getCurrentContext();
        HttpServletRequest request = cx.getRequest();
        //未知
        logger.info(String.format("%s>>>%s",request.getMethod(),
                request.getRequestURI().toString()));
        Object accessToken =request.getParameter("token");
        if (accessToken==null){
            logger.warn("token is empty");
            cx.setSendZuulResponse(false);
            cx.setResponseStatusCode(401);

            try {
                cx.getResponse().getWriter().write("token isempty");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  null;
        }
        return  null;
    }
}
