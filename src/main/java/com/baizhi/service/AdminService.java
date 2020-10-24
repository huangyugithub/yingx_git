package com.baizhi.service;


import java.util.Map;

public interface AdminService {
    public Map<String, Object> login(String username, String password, String vcode);
}
