package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
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

@Table(name = "yx_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @Excel(name = "id")
    private String id;
    @Excel(name = "用户名")
    private String username;
    @Excel(name = "手机")
    private String phone;
    @Excel(name = "微信")
    private String wechat;
    @Column(name = "head_img")
    @Excel(name = "头像", type = 2, width = 40, height = 20)
    private String headImg;
    @Excel(name = "签名")
    private String sign;
    @Excel(name = "状态")
    private String status;
    @Column(name = "create_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", format = "yyyy-MM-dd")
    private Date createDate;
}
