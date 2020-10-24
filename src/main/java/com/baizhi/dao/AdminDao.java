package com.baizhi.dao;

import com.baizhi.entity.Admin;
import tk.mybatis.mapper.common.Mapper;

public interface AdminDao extends Mapper<Admin> {
    //后i台管理员登陆方法
    public Admin queryByUsername(String username);

    //查询管理员的 角色 与 权限
    public Admin queryAdminRAdnPByUsername(String username);
}
