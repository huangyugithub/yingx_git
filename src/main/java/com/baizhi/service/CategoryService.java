package com.baizhi.service;

import com.baizhi.entity.Category;
import com.baizhi.vo.CategoryVo;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    //查询所有类别
    public Map<String, Object> queryCategory(Integer page, Integer rows, String parentId);

    //添加类别
    public String addCategory(Category category);

    //删除类别
    public Map<String, Object> delCategory(Category category);

    //类别页展示
    public List<CategoryVo> queryAllCategory();

}
