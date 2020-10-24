package com.baizhi.filter;

import com.baizhi.realm.MyRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration //声明当前类是个配置类
public class ShiroFilter {
    @Bean //将当前方法的返回值交由工厂管理  将shiro过滤器工厂实例交由spring工厂管理
    public ShiroFilterFactoryBean getShiroFilter(DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        HashMap<String, String> map = new HashMap<>();
        //map.put("/**", "authc"); //所有资源设置为认证资源  authc认证过滤器
        map.put("/main/main.jsp", "anon"); //将main.jsp设为匿名资源  anon匿名过滤器
        map.put("/admin/getImage", "anon");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        //设置登陆页面  不设置会默认到webapp下找login.jsp
        shiroFilterFactoryBean.setLoginUrl("/login/login.jsp");

        //创建安全管理器
        //DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //将安全过滤器交由工厂进行管理
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        return shiroFilterFactoryBean;
    }

    //创建安全管理器
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(MyRealm myRealm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //将myrealm交由 安全管理器
        defaultWebSecurityManager.setRealm(myRealm);
        return defaultWebSecurityManager;
    }

    @Bean
    public MyRealm getMyRealm() {
        //创建自定义realm
        MyRealm myRealm = new MyRealm();
        return myRealm;
    }
}
