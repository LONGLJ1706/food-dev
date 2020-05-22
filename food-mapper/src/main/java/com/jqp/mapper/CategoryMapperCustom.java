package com.jqp.mapper;

import com.jqp.vo.CategoryVO;
import com.jqp.vo.NewItemsVO;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface CategoryMapperCustom {
    List<CategoryVO> getSubCatList(@Param("rootCatId") Integer rootCatId);
    List<NewItemsVO> getSixNewItemsLazy(@Param("paramsMap") Map<String, Object> map);
}
