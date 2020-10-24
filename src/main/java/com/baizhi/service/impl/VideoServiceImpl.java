package com.baizhi.service.impl;

import com.baizhi.dao.VideoDao;
import com.baizhi.entity.Video;
import com.baizhi.po.VideoPo;
import com.baizhi.service.VideoService;
import com.baizhi.util.AliyunOssUtil;
import com.baizhi.util.UUIDUtil;
import com.baizhi.vo.VideoVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class VideoServiceImpl implements VideoService {
    @Resource
    private VideoDao videoDao;
    @Resource
    private HttpSession session;

    //分页查询所有视频
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryVideoByPage(Integer page, Integer rows) {
        Video video = new Video();
        Map<String, Object> map = new HashMap<>();
        //查询总条数 records
        Integer records = videoDao.selectCount(video);
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
        List<Video> videos = videoDao.selectByRowBounds(video, rowBounds);

        map.put("page", page);
        map.put("records", records);
        map.put("rows", videos);
        map.put("total", total);

        return map;
    }

    @Scheduled(cron = "* * * * * *")
    public String dingshi() {
        Thread thread = Thread.currentThread();
        String name = thread.getName();
        System.out.println("测试1： " + name + "  " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
        return null;
    }

    //添加数据
    @Override
    public String addVideo(Video video) {
        String id = UUIDUtil.getUUID();
        video.setId(id);
        videoDao.insertSelective(video);

        return video.getId();
    }

    //修改视频数据
    @Override
    public void updateVideo(Video video) {
        video.setPublishDate(new Date());
        //Admin admin = (Admin) session.getAttribute("admin");
        //String id = admin.getId();
        //video.setUserId(id);
        video.setCategoryId("1");
        videoDao.updateByPrimaryKeySelective(video);
    }

    //删除视频
    @Override
    public String deleteVideo(Video video) {

        //先删除 阿里云存储的数据
        Video video1 = videoDao.selectByPrimaryKey(video);
        //http://yingx-huangy.oss-cn-beijing.aliyuncs.com/video/1.mp4
        String path = video1.getPath();
        String[] split = path.split("/");
        String videoPath = "video/" + split[4];
        System.out.println("删除视频云路径： " + videoPath);

        //http://yingx-huangy.oss-cn-beijing.aliyuncs.com/photo/1.jpg
        String cover = video1.getCover();
        String[] split2 = cover.split("/");
        String coverPath = "photo/" + split2[4];
        System.out.println("删除视频 的 封面云路径： " + coverPath);

        AliyunOssUtil.deleteOne(videoPath);
        AliyunOssUtil.deleteOne(coverPath);

        //后删除数据库 的数据
        videoDao.deleteByPrimaryKey(video.getId());
        return video.getId();
    }

    //查询单个视频
    @Override
    public Video selectOneVideo(String id) {
        Video video = videoDao.selectByPrimaryKey(id);
        return video;
    }

    @Override
    public List<VideoVo> queryFirst() {

        ArrayList<VideoVo> list = new ArrayList<>();

        try {
            //查询连接的数据
            List<VideoPo> videoPos = videoDao.queryFirst();
            //转换日期格式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            //将vo对象的值赋值给po对象
            for (VideoPo p : videoPos) {
                String format = null;
                if (p.getPublishDate() != null) {
                    format = sdf.format(p.getPublishDate());
                }
                VideoVo videoVo = new VideoVo(p.getId(), p.getTitle(), p.getCover(), p.getPath(),
                        format, p.getBrief(), 0, p.getCateName(), p.getHeadImg());
                list.add(videoVo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
