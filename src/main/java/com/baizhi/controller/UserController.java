package com.baizhi.controller;

import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import com.baizhi.util.AliyunOssUtil;
import com.baizhi.util.AliyunSendMessageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private HttpSession session;

    @RequestMapping("/queryUserByPage")
    @ResponseBody
    public Map<String, Object> queryUserByPage(Integer page, Integer rows) {
        Map<String, Object> map = userService.queryUserByPage(page, rows);
        return map;
    }

    @RequestMapping("/edit")
    @ResponseBody
    public Map<String, Object> edit(User user, String oper) {
        System.out.println(user);
        System.out.println(oper);
        Map<String, Object> map = new HashMap<>();
        if ("add".equals(oper)) {
            String id = userService.add(user);
            map.put("id", id);

        } else if ("edit".equals(oper)) {

        } else if ("del".equals(oper)) {

        }
        return map;
    }

    @RequestMapping("/upload")
    public void upload(String id, MultipartFile headImg, HttpServletRequest request) {
        //文件上传   本地
        //  1. 获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/bootstrap/img");
        //  2. 文件上传
        //文件名 + 时间戳
        String filename = new Date().getTime() + "_" + headImg.getOriginalFilename();
        try {
            headImg.transferTo(new File(realPath, filename));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //修改当前用户的头像信息
        userService.edit(id, filename);
    }

    //上传Byte数组
    @RequestMapping("uploadFile")
    public void uploadFile(String id, MultipartFile headImg) {
        //文件名
        String name = new Date().getTime() + headImg.getOriginalFilename();
        //文件上传到阿里云
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        /*String endpoint = "https://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = "LTAI4G96jxNmwb9tdguvNKmz"; //密钥
        String accessKeySecret = "eVwrnyxTCtfKP5rBpouAeqriihSZL8";
        String bucketName = "yingx-huangy";*/
        //上传到阿里云上的 路径 与 文件名
        String fileName = "photo/" + name;
        AliyunOssUtil.uploadByteFile(headImg, fileName);
// 创建OSSClient实例。
        //OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId,accessKeySecret);

// 上传Byte数组。
        //ossClient.putObject(bucketName, fileName, new ByteArrayInputStream(content));

// 关闭OSSClient。
        //ossClient.shutdown();

        //修改用户数据
        String filename2 = "http://yingx-huangy.oss-cn-beijing.aliyuncs.com/" + fileName;
        userService.edit(id, filename2);
    }

    //修改用户状态
    @RequestMapping("updateStatus")
    @ResponseBody
    public void updateStatus(User user) {
        System.out.println(user);
        userService.updateStatus(user);
    }

    @RequestMapping("sendCode")
    public void sendCode(String phone) {
        //获取验证码
        String code = AliyunSendMessageUtil.getCode();
        //保存验证码  redis安全高效
        session.setAttribute("code", code);
        //发送短信
        String message = AliyunSendMessageUtil.sendCode(phone, code);
        System.out.println(message);
    }

    @RequestMapping("booleanCode")
    @ResponseBody
    public String booleanCode(String code) {
        String code1 = (String) session.getAttribute("code");
        String hint = "校验失败";
        if (code1.equals(code)) {
            hint = "校验成功";
        }
        return hint;
    }

    //easyPoi导出
    @RequestMapping("easyPoiOut")
    @ResponseBody
    public String easyPoiOut() {
        String s = userService.easyPoiOut();
        System.out.println(s);
        return s;
    }

    //easyPoi导入
    @RequestMapping("easyPoiIn")
    @ResponseBody
    public String easyPoiIn() {
        String s = userService.easyPoiIn();
        System.out.println(s);
        return s;
    }

}
