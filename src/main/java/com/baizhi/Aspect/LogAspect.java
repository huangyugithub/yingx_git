package com.baizhi.Aspect;

import com.baizhi.annotation.AddLog;
import com.baizhi.entity.Admin;
import com.baizhi.entity.Log;
import com.baizhi.service.LogService;
import com.baizhi.util.UUIDUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
//声明当前类是配置类，以后会交给工厂进行管理
@Configuration
public class LogAspect {

    @Resource
    private HttpSession session;
    @Resource
    private LogService logService;

    //@Around("execution(* com.baizhi.service.impl.*.*(..)) && !execution(* com.baizhi.service.impl.*.login*(..))")
    public Object addLog(ProceedingJoinPoint joinPoint) {
        //方法的名字，返回值什么的都可以同个joinPoint这个参数获取到
        //记录 谁  时间   操作   是否成功
        Admin admin = (Admin) session.getAttribute("admin");
        //时间
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(date);
        //操作  哪个方法  方法名
        String name = joinPoint.getSignature().getName();
        //放行
        String message = null;
        try {
            Object proceed = joinPoint.proceed();
            message = "success";
            System.out.println("管理员：" + admin.getUsername() + "--  时间：" + format + "--  操作：" + name + "--  状态：" + message);
            //System.out.println("------"+proceed); //proceed是方法的返回值
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            message = "error";
            System.out.println("管理员：" + admin.getUsername() + "--  时间：" + format + "--  操作：" + name + "--  状态：" + message);
            return null;
        }
    }

    @Around("@annotation(com.baizhi.annotation.AddLog)")
    public Object addLogs(ProceedingJoinPoint joinPoint) {
        //方法的名字，返回值什么的都可以同个joinPoint这个参数获取到
        //记录 谁  时间   操作   是否成功
        Admin admin = (Admin) session.getAttribute("admin");
        //时间
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(date);
        //操作  哪个方法  方法名
        String name = joinPoint.getSignature().getName();
        //获取 方法上的注解信息
        //先得到方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();//拿到方法对象
        //获取方法上的注解
        AddLog annotation = method.getAnnotation(AddLog.class);
        //获取注解中的内容
        String value = annotation.value();

        //放行
        String message = null;
        try {
            Object proceed = joinPoint.proceed();
            message = "success";
            System.out.println("管理员：" + admin.getUsername() + "  时间：" + format + "  操作：" + value + "  状态：" + message);
            //System.out.println("------"+proceed); //proceed是方法的返回值
            String id = UUIDUtil.getUUID();
            Log log = new Log(id, admin.getUsername(), value, date, message);
            //日志入库
            logService.insertLog(log);

            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            message = "error";
            System.out.println("管理员：" + admin.getUsername() + "  时间：" + format + "  操作：" + value + "  状态：" + message);
            return null;
        }

    }
}
