package com.jqp.mapper;

import com.jqp.pojo.ItemsSpec;
import com.jqp.vo.ItemCommentVO;
import com.jqp.vo.SearchItemsVO;
import com.jqp.vo.ShopcartVO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;
import java.util.Map;

public interface ItemsMapperCustom  {

    List<ItemCommentVO> queryItemComments(@Param("paramsMap") Map<String, Object> map);

    List<SearchItemsVO> searchItems(@Param("paramsMap") Map<String, Object> map);

    List<SearchItemsVO> searchItemsByThirdCat(@Param("paramsMap") Map<String, Object> map);

    List<ShopcartVO> queryItemsBySpecIds(@Param("paramsList") List specIdsList);

    int decreaseItemSpecStock(@Param("specId")String specId, @Param("pendingCounts")int pendingCounts);
}