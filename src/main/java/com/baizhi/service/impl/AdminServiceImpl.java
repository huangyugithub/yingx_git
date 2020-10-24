package com.baizhi.service.impl;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Resource
    private HttpSession session;
    @Resource
    private AdminDao adminDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> login(String username, String password, String vcode) {
        Map<String, Object> map = new HashMap<>();

        String code = (String) session.getAttribute("code");
        boolean b = false;
        if (code.equals(vcode)) {


            //创建一个条件对象
            Example example = new Example(Admin.class);
            example.createCriteria().andEqualTo("username", username);
            List<Admin> admins = adminDao.selectByExample(example);

            if (admins != null) {
                //账号正确  判断密码
                for (Admin admin : admins) {
                    if (admin.getPassword().equals(password)) {
                        b = true;
                        session.setAttribute("admin", admin);
                        map.put("status", b);
                        return map;
                    }
                }
                if (!b) {
                    map.put("status", b);
                    map.put("message", "密码错误");
                }
            } else {
                map.put("status", b);
                map.put("message", "账号错误");
            }
        } else {
            map.put("status", b);
            map.put("message", "验证码错误");
        }
        return map;
    }
}
