package com.baizhi;

import com.baizhi.dao.AdminDao;
import com.baizhi.dao.VideoDao;
import com.baizhi.entity.Admin;
import com.baizhi.entity.Log;
import com.baizhi.po.VideoPo;
import com.baizhi.service.LogService;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YingxHuangyApplicationTests {
    @Resource
    AdminDao adminDao;
    @Resource
    LogService logService;
    @Resource
    VideoDao videoDao;

    @Test
    public void contextLoads() {
        Admin admin = adminDao.queryByUsername("hy");
        System.out.println(admin);

    }

    @Test
    public void queryUserByPage() {
        Admin admin = new Admin(); //不可以注入admin
        System.out.println(admin);
        RowBounds rowBounds = new RowBounds(0, 2);
        List<Admin> admins = adminDao.selectByRowBounds(admin, rowBounds);
        System.out.println(admin);
        System.out.println(admins);
        int i = adminDao.selectCount(admin);
        System.out.println("数量是：  " + i);
    }

    @Test
    public void queryByExample() {

        //创建一个条件对象
        Example example = new Example(Admin.class);
        example.createCriteria().andEqualTo("username", "hy");
        int i = adminDao.selectCountByExample(example);
        System.out.println(i);
    }

    @Test
    public void insertLog() {
        //Log log = new Log("3","name","caozuo",new Date(),"状态");
        Log log = new Log();
        log.setId("9");
        log.setName("hehe");
        log.setOperation("查询");
        log.setDate(new Date());
        log.setStatus("成功");
        logService.insertLog(log);

    }

    @Test
    public void testSelect() {
        List<VideoPo> videoPos = videoDao.queryFirst();
        for (VideoPo videoPo : videoPos) {
            System.out.println(videoPo);
        }
    }

    @Test
    public void test22() {
        Admin admin = adminDao.queryAdminRAdnPByUsername("hy");
        System.out.println(admin);
    }

}
