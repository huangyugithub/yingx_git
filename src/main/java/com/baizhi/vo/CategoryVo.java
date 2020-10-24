package com.baizhi.vo;

import com.baizhi.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryVo {

    private String id;
    private String cateName;
    private Integer levels;
    private String parentId;
    private List<Category> categoryList;
}
