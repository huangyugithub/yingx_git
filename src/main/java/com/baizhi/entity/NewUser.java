package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "yx_newuser")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUser implements Serializable {
    @Id
    private String id;
    private String name;
    private String sex;
    @Column(name = "province_id")
    private String provinceId;
    @Column(name = "create_date")
    private Date createDate;
}
