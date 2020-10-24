package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "yx_video")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Video implements Serializable {
    @Id
    private String id;
    private String title;   //标题
    private String biref;   //简介
    private String path;    //视频路径
    private String cover;   //封面图片路径
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "publish_date")
    private Date publishDate; //发布时间
    @Column(name = "category_id")
    private String categoryId; //所属二级类别id
    @Column(name = "user_id")
    private String userId;  //用户id
    @Column(name = "group_id")
    private String groupId; //组id

}
