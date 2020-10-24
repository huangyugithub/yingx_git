package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
    @ExcelIgnore  //跳过 不展示
    private String id;
    @Excel(name = "姓名", needMerge = true)
    private String name;
    @ExcelCollection(name = "学生们")
    private List<Student> students;
}
