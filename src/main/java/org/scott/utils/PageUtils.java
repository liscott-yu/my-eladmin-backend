package org.scott.utils;

import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * project name  my-eladmin-backend-v2
 * filename  PageUtils
 * @author liscott
 * @date 2023/1/6 11:18
 * description  TODO
 */
public class PageUtils extends cn.hutool.core.util.PageUtil{

    /**
     * 分页
     * @param page 第几页
     * @param size 每页数据数
     * @param list 待分页数据列表
     * @return list 已完成分页数据列表
     */
    public static List toPage(int page, int size , List list) {
        // 起始页
        int fromIndex = page * size;
        // 尾页
        int toIndex = fromIndex + size;
        // 1. 待分页数据，不在此页内，返回空列表
        if(fromIndex > list.size()){
            return new ArrayList();
        }
        // 2. 待分页数据，完全在此页
        else if (toIndex >= list.size()) {
            return list.subList(fromIndex, list.size());
        }
        //3. 待分页数据，超出此页
        else {
            return list.subList(fromIndex, toIndex);
        }
    }

    /**
     * Page 数据处理，预防redis反序列化报错
     * @param page subList
     * @return Map
     */
    public static Map<String, Object> toPage(Page page) {
        Map<String, Object> map = new LinkedHashMap<String, Object>(2) {{
            put("content", page.getContent());
            put("totalElements", page.getTotalElements());
        }};
        return map;
    }

    public static Map<String,Object> toPage(Object object, Object totalElements) {
        Map<String, Object> map = new LinkedHashMap<String, Object>(2) {{
            put("content", object);
            put("totalElements", totalElements);
        }};
        return map;
    }

}
