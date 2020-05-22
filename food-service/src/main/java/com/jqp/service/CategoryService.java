package com.jqp.service;

import com.jqp.pojo.Category;
import com.jqp.vo.CategoryVO;
import com.jqp.vo.NewItemsVO;

import java.util.List;

public interface CategoryService {

    /**
     * 查询所有一级分类
     */
    List<Category> queryAll();

    /**
     * 根据一级分类id查询二级，三级信息
     * @param rootCatId
     * @return
     */
    List<CategoryVO> getSubCatList(Integer rootCatId);

    /**
     * 查询首页每个一级分类的最新的6条记录
     * @param rootCatId
     * @return
     */
    List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId);
}
