package com.baizhi.realm;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;

public class MyRealm extends AuthorizingRealm {
    @Resource
    private AdminDao adminDao;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String primaryPrincipal = (String) principals.getPrimaryPrincipal(); //获取主身份
        //查询数据库  根据主身份查询对应角色
        //根据 角色 查询对应 权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole("admin");
        simpleAuthorizationInfo.addStringPermission("user:select:*");
        return simpleAuthorizationInfo;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String principal = (String) token.getPrincipal(); //拿到身份信息 username

        Admin admin = adminDao.queryAdminRAdnPByUsername(principal);

        SimpleAuthenticationInfo simpleAuthenticationInfo = null;
        if (admin != null) {
            simpleAuthenticationInfo = new SimpleAuthenticationInfo(admin.getUsername(), admin.getPassword(), ByteSource.Util.bytes(admin.getSalt()), this.getName());
        }
        return simpleAuthenticationInfo;
    }
}
