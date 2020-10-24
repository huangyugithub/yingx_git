package com.baizhi.service.impl;

import com.baizhi.dao.LogDao;
import com.baizhi.entity.Log;
import com.baizhi.service.LogService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class LogServiceImpl implements LogService {
    @Resource
    private LogDao logDao;

    @Override
    public void insertLog(Log log) {
        logDao.insertSelective(log);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryLogByPage(Integer page, Integer rows) {
        Log log = new Log();
        Map<String, Object> map = new HashMap<>();
        //查询总条数 records
        Integer records = logDao.selectCount(log);
        System.out.println("总条数： " + records);
        //总页数
        Integer total = null;
        if (records % rows == 0) {
            total = records / rows;
        } else {
            total = records / rows + 1;
        }
        //起始条数
        Integer firstCount = (page - 1) * rows;

        //分页查询
        RowBounds rowBounds = new RowBounds(firstCount, rows);
        Example example = new Example(Log.class);
        //注意：排序使用的是列名
        example.setOrderByClause("date DESC");
        List<Log> logs = logDao.selectByExampleAndRowBounds(example, rowBounds);

        map.put("page", page);
        map.put("records", records);
        map.put("rows", logs);
        map.put("total", total);

        return map;
    }
}
