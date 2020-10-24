package com.baizhi.controller;

import com.baizhi.entity.Video;
import com.baizhi.service.VideoService;
import com.baizhi.util.AliyunOssUtil;
import com.baizhi.util.InterceptorPhotoUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/video")
public class VideoController {
    @Resource
    private VideoService videoService;

    @RequestMapping("/queryVideoByPage")
    @ResponseBody
    public Map<String, Object> queryVideoByPage(Integer page, Integer rows) {
        Map<String, Object> map = videoService.queryVideoByPage(page, rows);
        return map;
    }

    @RequestMapping("/edit")
    @ResponseBody
    public Map<String, Object> edit(String adminId, Video video, String oper) {
        Map<String, Object> map = new HashMap<>();
        if ("add".equals(oper)) {
            video.setUserId(adminId);
            String id = videoService.addVideo(video);
            map.put("id", id);
        } else if ("edit".equals(oper)) {

        } else if ("del".equals(oper)) {
            System.out.println("删除的id:  " + video);
            String id = videoService.deleteVideo(video);
            map.put("id", id);
        }
        return map;
    }

    @RequestMapping("uploadVideo")
    public void uploadVideo(String id, MultipartFile path, HttpServletRequest request) {
        System.out.println("---------" + path);
        //上传视频文件
        String name = new Date().getTime() + path.getOriginalFilename();
        //阿里云的 远程视频 文件路径 和文件名
        String fileName = "http://yingx-huangy.oss-cn-beijing.aliyuncs.com/video/" + name;

        AliyunOssUtil.uploadByteFile(path, "video/" + name);

        //获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/bootstrap/img");
        //获取封面的文件名
        String[] split = name.split("\\.");
        String photoName = split[0] + ".jpg";
        System.out.println("00000000000000");
        System.out.println(photoName);
        //本地图片路径
        String photoPath = realPath + "\\" + photoName;
        System.out.println("photoPath:  " + photoPath);
        //截取视频封面
        try {
            InterceptorPhotoUtil.fetchFrame(fileName, photoPath);
            System.out.println("11111111111");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //上传本地封面文件
        AliyunOssUtil.uploadLocalFile("photo/" + photoName, photoPath);
        //修改存储路径
        Video video = new Video();
        video.setId(id);
        video.setPath(fileName);
        video.setCover("http://yingx-huangy.oss-cn-beijing.aliyuncs.com/photo/" + photoName);
        videoService.updateVideo(video);
    }

    @RequestMapping("/deleteVideo")
    public void deleteVideo(String id) {
        Video video = videoService.selectOneVideo(id);
        System.out.println("video---------" + video);
        String path = video.getPath();
        int begin = path.indexOf("/", 3);

        String objectName = path.substring(begin);
        System.out.println("++++++ " + objectName);
        AliyunOssUtil.deleteOne(objectName);
    }
}
