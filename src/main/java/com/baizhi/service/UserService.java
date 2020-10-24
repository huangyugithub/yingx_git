package com.baizhi.service;

import com.baizhi.entity.User;

import java.util.Map;

public interface UserService {
    //分页查询用户
    public Map<String, Object> queryUserByPage(Integer page, Integer rows);

    //添加用户
    public String add(User user);

    //修改头像路径  上传头像之后
    public void edit(String id, String filename);

    //修改用户状态
    public void updateStatus(User user);

    //导出用户数据
    public String easyPoiOut();

    //导入 假
    public String easyPoiIn();
}
