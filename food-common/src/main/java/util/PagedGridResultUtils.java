package util;

import com.github.pagehelper.PageInfo;
import java.util.List;

/**
 * 公用的分页处理方法
 */
public class PagedGridResultUtils {

    public static PagedGridResult setterPagedGrid(List<?> list, Integer page) {
        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(pageList.getPages());
        grid.setRecords(pageList.getTotal());
        return grid;
    }

}
