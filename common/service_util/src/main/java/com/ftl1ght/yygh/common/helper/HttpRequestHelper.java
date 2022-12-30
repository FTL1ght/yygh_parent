package com.ftl1ght.yygh.common.helper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author FTL1ght
 * @Description
 * @create 2022-12-14 20:58
 */
public class HttpRequestHelper {


    /**
     *
     * @param paramMap
     * @return
     */

    public static Map<String, Object> switchMap(Map<String,String[]> paramMap) {

        Map<String,Object> resultMap = new HashMap<>();

        for (Map.Entry<String,String[]> param:paramMap.entrySet()){
            resultMap.put(param.getKey(),param.getValue()[0]);
        }

        return resultMap;
    }
}
