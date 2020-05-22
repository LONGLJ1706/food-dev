package com.jqp.service;

import com.jqp.pojo.Carousel;
import java.util.List;

public interface CarouselService {

    //查询所有的轮播图片
    List<Carousel> queryAll(Integer isShow);
}
