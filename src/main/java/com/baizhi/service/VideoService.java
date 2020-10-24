package com.baizhi.service;

import com.baizhi.entity.Video;
import com.baizhi.vo.VideoVo;

import java.util.List;
import java.util.Map;

public interface VideoService {
    //分页查询所有视频
    public Map<String, Object> queryVideoByPage(Integer page, Integer rows);

    //添加数据
    public String addVideo(Video video);

    //修改视频数据
    public void updateVideo(Video video);

    //删除视频
    public String deleteVideo(Video video);

    //查询单个视频
    public Video selectOneVideo(String id);

    //首页展示
    public List<VideoVo> queryFirst();

    //测试定时器
    public String dingshi();


}
