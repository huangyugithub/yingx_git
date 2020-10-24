package com.baizhi.controller;

import com.baizhi.service.LogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/log")
public class LogController {
    @Resource
    private LogService logService;

    @RequestMapping("/queryLogByPage")
    @ResponseBody
    public Map<String, Object> queryLogByPage(Integer page, Integer rows) {
        Map<String, Object> map = logService.queryLogByPage(page, rows);
        return map;
    }

}
