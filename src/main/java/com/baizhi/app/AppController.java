package com.baizhi.app;

import com.baizhi.common.ResultCommon;
import com.baizhi.service.CategoryService;
import com.baizhi.service.VideoService;
import com.baizhi.util.AliyunSendMessageUtil;
import com.baizhi.vo.CategoryVo;
import com.baizhi.vo.VideoVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("app")
public class AppController {
    @Resource
    private VideoService videoService;
    @Resource
    private CategoryService categoryService;

    //获取手机验证码
    @RequestMapping("getPhoneCode")
    @ResponseBody
    public ResultCommon getPhoneCode(String phone) {
        //获取验证码
        String code = AliyunSendMessageUtil.getCode();
        //保存验证码  redis里保存
        //发送短信
        String message = AliyunSendMessageUtil.sendCode(phone, code);
        if (message.equals("发送成功")) {

            return new ResultCommon(phone, "验证码发送成功", "100");
        } else {
            return new ResultCommon(phone, "验证码发送失败", "104");
        }
    }

    //展示首页数据
    @RequestMapping("queryByReleaseTime")
    @ResponseBody
    public ResultCommon queryByReleaseTime() {
        List<VideoVo> videoVos = videoService.queryFirst();
        if (videoVos.size() != 0) {
            return new ResultCommon(videoVos, "查询成功", "100");

        } else {
            return new ResultCommon(null, "查询失败", "104");
        }

    }

    //分类页面数据
    @RequestMapping("queryAllCategory")
    @ResponseBody
    public ResultCommon queryAllCategory() {
        List<CategoryVo> list = categoryService.queryAllCategory();
        if (list.size() != 0) {
            return new ResultCommon(list, "查询成功", "100");
        } else {
            return new ResultCommon(null, "查询失败", "104");
        }
    }
}
