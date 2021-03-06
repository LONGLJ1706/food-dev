package com.jqp.controller;

import com.jqp.pojo.Carousel;
import com.jqp.pojo.Category;
import com.jqp.service.CarouselService;
import com.jqp.service.CategoryService;
import com.jqp.vo.CategoryVO;
import com.jqp.vo.NewItemsVO;
import enums.YesOrNo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.JSONResult;
import java.util.List;

@RestController
@RequestMapping("/index")
@Api(value = "首页", tags = "首页相关接口")
public class IndexController {

    @Autowired
    private CarouselService carouselService;

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "获取首页轮播图片")
    @GetMapping("/carousel")
    public JSONResult carousel(){
        List<Carousel> list = carouselService.queryAll(YesOrNo.YES.type);
        return JSONResult.ok(list);
    }


    /**
     * 首页分类展示需求：
     * 1.第一次刷新主页查询大分类，渲染展示到首页
     * 2.如果鼠标上移到大分类，则加载其子类的内容，如果已经存在子分类，则不需要加载（懒加载）
     */
    @ApiOperation(value = "查询所有一级分类")
    @GetMapping("/cats")
    public JSONResult cats(){
        List<Category> list = categoryService.queryAll();
        return JSONResult.ok(list);
    }

    /**
     * rootCatId: 一级分类id
     * @return
     */
    @ApiOperation(value = "一级分类查询二级三级信息")
    @GetMapping("/subCat/{rootCatId}")
    public JSONResult subCat(@ApiParam(name = "rootCatId", value = "一级分类id", required = true)
                             @PathVariable Integer rootCatId){
        if(rootCatId == null){
            return null;
        }
        List<CategoryVO> list = categoryService.getSubCatList(rootCatId);
        return JSONResult.ok(list);
    }


    /**
     * rootCatId: 查询每个一级分类的最新6条商品信息
     * @return
     */
    @ApiOperation(value = "查询每个一级分类的最新6条商品信息")
    @GetMapping("/sixNewItems/{rootCatId}")
    public JSONResult sixNewItems(@ApiParam(name = "rootCatId", value = "一级分类id", required = true)
                             @PathVariable Integer rootCatId){
        if(rootCatId == null){
            return null;
        }
        List<NewItemsVO> list = categoryService.getSixNewItemsLazy(rootCatId);
        return JSONResult.ok(list);
    }


}
