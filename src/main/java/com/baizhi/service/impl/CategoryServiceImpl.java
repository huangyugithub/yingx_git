package com.baizhi.service.impl;

import com.baizhi.annotation.AddLog;
import com.baizhi.dao.CategoryDao;
import com.baizhi.dao.VideoDao;
import com.baizhi.entity.Category;
import com.baizhi.entity.Video;
import com.baizhi.service.CategoryService;
import com.baizhi.util.UUIDUtil;
import com.baizhi.vo.CategoryVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryDao categoryDao;
    @Resource
    private VideoDao videoDao;

    //查询所有类别
    @Override
    @AddLog(value = "查询类别")
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryCategory(Integer page, Integer rows, String parentId) {
        Map<String, Object> map = new HashMap<>();
        //查询一级分类
        //总条数
        //创建一个条件对象
        Example example = new Example(Category.class);
        if (parentId == null) {
            example.createCriteria().andEqualTo("levels", 1);
        } else {
            example.createCriteria().andEqualTo("parentId", parentId);
        }

        Integer records = categoryDao.selectCountByExample(example);
        //总页数
        Integer total = records % rows == 0 ? (records / rows) : (records / rows + 1);
        //起始条数
        Integer firstCount = (page - 1) * rows;
        //按条件分页查询的数据
        RowBounds rowBounds = new RowBounds(firstCount, rows);
        List<Category> categories = categoryDao.selectByExampleAndRowBounds(example, rowBounds);

        map.put("page", page);
        map.put("total", total);
        map.put("rows", categories);
        map.put("records", records);

        return map;
    }

    //添加类别
    @Override
    @AddLog(value = "添加类别")
    public String addCategory(Category category) {
        String id = UUIDUtil.getUUID();
        category.setId(id);
        categoryDao.insertSelective(category);
        return null;
    }

    //删除类别
    @Override
    public Map<String, Object> delCategory(Category category) {
        Map<String, Object> map = new HashMap<>();
        //判断当前要删除的 是1级类别 还是2级类别
        if (category.getParentId() == null) {
            //判断当前1级类别下有无2级类别
            Example example = new Example(Category.class);
            example.createCriteria().andEqualTo("parentId", category.getId());
            int i = categoryDao.selectCountByExample(example);
            System.out.println("当前1级类别下的2级类别有几个： " + i);
            if (i == 0) {
                //没有管理2级类别，直接删除
                categoryDao.deleteByPrimaryKey(category);
                map.put("message", "删除成功");
            } else {
                //有2级类别
                map.put("message", "当前一级类别下管理着二级类别，不能删！");
            }
        } else {
            //当前类别是2级类别
            //判断2级类别下有无 video
            Example example = new Example(Video.class);
            example.createCriteria().andEqualTo("categoryId", category.getId());
            int i = videoDao.selectCountByExample(example);
            if (i == 0) {
                //无视频  直接删
                categoryDao.deleteByPrimaryKey(category);
                map.put("message", "删除成功！");
            } else {
                map.put("mesage", "当前2级类别下管理着视频，无法删除");
            }
        }


        return map;
    }


    //类别页展示
    @Override
    public List<CategoryVo> queryAllCategory() {
        List<CategoryVo> list = new ArrayList<>();

        try {
            Example example = new Example(Category.class);
            example.createCriteria().andEqualTo("levels", 1);
            List<Category> categories = categoryDao.selectByExample(example);
            for (Category category : categories) {
                example = new Example(Category.class);
                example.createCriteria().andEqualTo("parentId", category.getId());
                List<Category> twoCategories = categoryDao.selectByExample(example);

                CategoryVo categoryVo = new CategoryVo(category.getId(), category.getName(),
                        1, category.getParentId(), twoCategories);
                list.add(categoryVo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
