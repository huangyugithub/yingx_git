package com.baizhi.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoPo {
    private String id;
    private String title;
    private String cover;
    private String path;
    private Date publishDate;
    private String brief;
    private String cateName;
    private String headImg;

}
