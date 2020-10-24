package com.baizhi.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baizhi.annotation.AddCache;
import com.baizhi.annotation.AddLog;
import com.baizhi.annotation.DelCache;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import com.baizhi.util.AliyunOssUtil;
import com.baizhi.util.UUIDUtil;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class UserServiceImple implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    @AddCache
    @Transactional(propagation = Propagation.SUPPORTS)
    //分页查询所有用户
    public Map<String, Object> queryUserByPage(Integer page, Integer rows) {
        User user = new User();
        Map<String, Object> map = new HashMap<>();
        //查询总条数 records
        Integer records = userDao.selectCount(user);
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
        List<User> users = userDao.selectByRowBounds(user, rowBounds);

        map.put("page", page);
        map.put("records", records);
        map.put("rows", users);
        map.put("total", total);

        return map;
    }

    //添加用户
    @Override
    @DelCache
    public String add(User user) {
        String id = UUIDUtil.getUUID();
        user.setId(id);
        user.setStatus("正常");
        user.setCreateDate(new Date());
        userDao.insertSelective(user);
        return user.getId();
    }

    //添加完用户后上传头像 后 修改 头像路径
    @Override
    public void edit(String id, String filename) {
        User user = new User();
        user.setId(id);
        user.setHeadImg(filename);
        userDao.updateByPrimaryKeySelective(user);
    }

    //修改用户状态
    @Override
    @DelCache
    public void updateStatus(User user) {
        if ("正常".equals(user.getStatus())) {
            user.setStatus("冻结");
        } else {
            user.setStatus("正常");
        }
        userDao.updateByPrimaryKeySelective(user);
    }

    //导出用户数据
    @Override
    @AddLog(value = "导出用户数据")
    public String easyPoiOut() {
        //查询所有用户
        try {
            List<User> users = userDao.selectAll();

            ArrayList<Object> list = new ArrayList<>();
            //yingx-huangy.oss-cn-beijing.aliyuncs.com/photo/1.jpg
            for (User user : users) {
                String[] split = user.getHeadImg().split("/");
                String fileName = split[4];
                System.out.println("fileName--- " + fileName);
                String filePath = "F:\\img\\" + fileName;
                AliyunOssUtil.testDownload("photo/" + fileName, filePath);
                user.setHeadImg(filePath);
                list.add(user);
            }
            //先将远程网络图片下载到本地  再导出
            //导出后的图片路径发生改变  再用此文件导入需处理图片路径
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户数据", "用户"),
                    User.class, list);
            workbook.write(new FileOutputStream(new File("F:/应学用户.xls")));
            workbook.close();
            return "导出成功！";
        } catch (IOException e) {
            e.printStackTrace();
            return "导出失败!";
        }
    }

    //导入 假
    @DelCache
    public String easyPoiIn() {
        try {
            //设置导入得相关参数
            ImportParams params = new ImportParams();
            params.setTitleRows(1); //表格的标题行数
            params.setHeadRows(1);  //表头所占行
            //long start = new Date().getTime();
            FileInputStream stream = new FileInputStream(new File("F:/应学用户.xls"));
            List<User> list = ExcelImportUtil.importExcel(stream, User.class, params);
            list.forEach(user -> System.out.println(user));
            //遍历后存入数据库，不过导出后的图片路径发生改变  需处理

            //Person(id=1, name=小黑, headImg=/excel/upload/img\Person\pic20374222194.JPG)
            return "导入成功！";
        } catch (Exception e) {
            e.printStackTrace();
            return "导入失败！";
        }
    }
}
