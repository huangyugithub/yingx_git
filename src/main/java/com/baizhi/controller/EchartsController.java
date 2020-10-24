package com.baizhi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("echarts")
public class EchartsController {

    @RequestMapping("queryUserNum")
    @ResponseBody
    public Map<String, Object> queryUserNum() {
        /*
         * 新用户表  new_user
         * id name phone sex create_date
         * 查询每个月份用户注册量
         * month(create_date): 该函数获取当前日期的月份
         * select concat(month(create_date),'月'), count(id) from new_user
         *   where sex = '男'
         *   group by month(create_date)
         * select concat(month(create_date),'月'), count(id) from new_user
         *   where sex = '女'
         *   group by month(create_date)
         * */

        //根据月份、性别 查询数据
        /*
        * 浅谈Arrays.asList()方法的使用#
　           首先，该方法是将数组转化为list。有以下几点需要注意：
　　          （1）该方法不适用于基本数据类型（byte,short,int,long,float,double,boolean）
　　          （2）该方法将数组与列表链接起来，当更新其中之一时，另一个自动更新
              (3）不支持add和remove方法
        * */
        HashMap<String, Object> map = new HashMap<>();
        map.put("month", Arrays.asList("1月", "2月", "3月", "4月", "5月", "6月"));
        map.put("boys", Arrays.asList(40, 10, 26, 10, 10, 20));
        map.put("girls", Arrays.asList(50, 20, 16, 30, 20, 10));
        return map;
    }

    @RequestMapping("queryUserCity")
    @ResponseBody
    public List<Object> queryUserCity() {
        /*
         * 新用户表  new_user
         * id name phone sex city create_date
         * 查询每个省份用户注册量
         * select city,count(id) value from new_user where sex='男' group by city
         * select city,count(id) value from new_user where sex='女' group by city
         * */
        return null;
    }
}
