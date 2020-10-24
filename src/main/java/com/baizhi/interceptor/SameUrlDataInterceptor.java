package com.baizhi.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.baizhi.annotation.SameUrlData;
import com.baizhi.service.impl.RedisServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Title: 防止用户重复提交数据拦截器
 * @Description: 将用户访问的uri和参数结合token存入redis，每次访问进行验证是否重复请求接口
 */

@Component
public class SameUrlDataInterceptor extends HandlerInterceptorAdapter {

    private static RedisServiceImpl redisServiceimpl;

    @Override
    /*
     * preHandle: 执行controller中的方法之前执行
     * handler: 方法对象
     * */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle的handler是:  " + handler);
        /*
        instanceof 运算符 用于：判断 该运算符前面引用类型变量指向的对象是否是后面类，或者其子类、接口实现类创建的对象。
        如果是则返回true，否则返回false，
        其使用格式如下：     引用类型变量 instanceof （类、抽象类或接口）
        * */
        if (handler instanceof HandlerMethod) {
            Method method = ((HandlerMethod) handler).getMethod();
            System.out.println("method方法 和 名字 ：" + method + "   " + method.getName());
            //获取方法上的注解
            SameUrlData annotation = method.getAnnotation(SameUrlData.class);
            System.out.println("方法上的注解" + annotation);
            if (annotation != null) {
                //说明当前方法上有这个注解 防止重复请求注解
                if (repeatDataValidator(request, annotation.value())) {
                    //请求数据相同
                    System.out.println("请求数据相同,url: " + request.getServletPath());
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("statusCode", "500");
                    jsonObject.put("message", "请勿重复请求");
                    //response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json; charset=utf-8");
                    response.getWriter().write(jsonObject.toString());
                    response.getWriter().close();
                    return false;
                } else {
                    //如果不是重复相同数据
                    return true;
                }
            }
            return true;
        }

        return super.preHandle(request, response, handler);

    }

    /*
    验证同一个url数据是否相同提交，相同返回true   重复数据验证器
    * */
    public boolean repeatDataValidator(HttpServletRequest request, long time) {
        if (redisServiceimpl == null) {
            //从当前容器内获取上下文，从而取到容器内的bean对象
            WebApplicationContext webApplicationContext = WebApplicationContextUtils
                    .getRequiredWebApplicationContext(request.getServletContext());
            redisServiceimpl = webApplicationContext.getBean(RedisServiceImpl.class);
        }
        System.out.println("request.getRequestURI()是： " + request.getRequestURI());
        /*  前台加请求头token，后台接收
            $.ajax({
            type: "post",
            url: areaComp,
            dataType: "json",
            contentType: "application/json",
            data: data,
            async: false,
                //再次添加头部信息
            beforeSend: function(request) {
                request.setRequestHeader("token", token);
            },
            success: function (data){})*/
        String redisKey = request.getRequestURI() + request.getHeader("token");
        String preUrlParams = redisServiceimpl.get(redisKey);
        //判断perUrlParams是否为空  null,""," "返回true 其他false
        if (StringUtils.isBlank(preUrlParams)) {
            //如果上一个数据为null，表示还没有请求
            redisServiceimpl.set(redisKey, "yes", time);
            return false;
        } else {
            //否则，已经有过请求
            return true;
        }
    }

    //三、注册拦截器
    //在WebMVCConfig中注册拦截器


}
