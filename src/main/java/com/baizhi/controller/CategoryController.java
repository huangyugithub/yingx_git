package com.baizhi.controller;

import com.baizhi.entity.Category;
import com.baizhi.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @RequestMapping("/queryCategory")
    @ResponseBody
    public Map<String, Object> queryCategory(Integer page, Integer rows, String parentId) {
        System.out.println(parentId);
        Map<String, Object> map = categoryService.queryCategory(page, rows, parentId);
        return map;
    }

    @RequestMapping("/edit")
    @ResponseBody
    public Map<String, Object> edit(Category category, String oper) {
        Map<String, Object> map = new HashMap<>();
        System.out.println("-------");
        if ("add".equals(oper)) {
            System.out.println("父类别id： " + category.getParentId());
            if (category.getParentId() != null) {
                category.setLevels(2);
            } else {
                category.setLevels(1);
            }
            categoryService.addCategory(category);

        } else if ("del".equals(oper)) {
            System.out.println("删除类别操作： " + category);
            map = categoryService.delCategory(category);

        } else if ("edit".equals(oper)) {

        }
        return map;
    }
}
