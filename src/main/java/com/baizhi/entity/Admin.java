package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

@Component
@Table(name = "yx_admin")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin implements Serializable {
    @Id
    private String id;      //写在属性上 表示这个属性对应的是数据库表的主键字段
    @Column(name = "username") //写在属性上 如果实体属性名与数据库字段不一致，表示这个属性映射的数据库字段名
    private String username;
    private String password;
    private String salt;
    @Transient     //写在属性上  表示该属性在数据库不存在
    private List<Role> roles;
}
