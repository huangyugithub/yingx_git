package com.baizhi.service;

import com.baizhi.entity.Log;

import java.util.Map;

public interface LogService {

    public void insertLog(Log log);

    public Map<String, Object> queryLogByPage(Integer page, Integer rows);
}
