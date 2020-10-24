package com.baizhi.controller;


import com.baizhi.service.AdminService;
import com.baizhi.util.ImageCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    @RequestMapping("/getImage")
    public void code(HttpServletResponse response, HttpSession session) throws IOException {
        String code = ImageCodeUtil.getSecurityCode();
        BufferedImage image = ImageCodeUtil.createImage(code);
        session.setAttribute("code", code);

        OutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, "png", outputStream);

        outputStream.flush();
        outputStream.close();
    }

    @RequestMapping("/login")
    @ResponseBody
    public Map<String, Object> login(String username, String password, String vcode) {
        System.out.println(username + password);
        //创建令牌
        /*UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username,password);

        //获取主体
        Subject subject = SecurityUtils.getSubject();
        String message = null;
        //调用认证方法
        try {
            subject.login(usernamePasswordToken);
        } catch (UnknownAccountException e) {
            message = "用户名输入错误";
            e.printStackTrace();
        }catch (IncorrectCredentialsException e){
            message = "密码输入错误";
            e.printStackTrace();
        }*/

        Map<String, Object> map = adminService.login(username, password, vcode);
        return map;
    }


}
