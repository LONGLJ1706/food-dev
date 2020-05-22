package com.jqp.service.impl;

import com.jqp.mapper.CategoryMapper;
import com.jqp.mapper.CategoryMapperCustom;
import com.jqp.pojo.Category;
import com.jqp.service.CategoryService;
import com.jqp.vo.CategoryVO;
import com.jqp.vo.NewItemsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryMapperCustom categoryMapperCustom;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Category> queryAll() {
        Example example = new Example(Category.class);
        example.orderBy("id").asc();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type", 1);
        List<Category> list = categoryMapper.selectByExample(example);
        return list;
    }


    /**
     * 根据一级分类id查询二级，三级信息
     * @param rootCatId
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<CategoryVO> getSubCatList(Integer rootCatId) {
        List<CategoryVO> list = categoryMapperCustom.getSubCatList(rootCatId);
        return list;
    }


    /**
     * 查询首页每个一级分类的最新的6条商品记录
     * @param rootCatId
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId) {
        Map<String, Object> map = new HashMap<>();
        map.put("rootCatId", rootCatId);
        return categoryMapperCustom.getSixNewItemsLazy(map);
    }
}
