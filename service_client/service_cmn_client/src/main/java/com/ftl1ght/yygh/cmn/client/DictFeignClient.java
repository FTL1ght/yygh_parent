package com.ftl1ght.yygh.cmn.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author FTL1ght
 * @Description
 * @create 2022-12-27 16:05
 */
@FeignClient("service-cmn")
@Repository
public interface DictFeignClient {

    //1 添加FeignClient注解,直接把需要调用的方法复制过来
    //2 并把controller中的访问路径补充完整
    //3 指定变量名称

    //根据dictCode和value进行查询
    @GetMapping("/admin/cmn/dict/getName/{dictCode}/{value}")
    public String getName(@PathVariable("dictCode") String dictCode,
                          @PathVariable("value") String value);

    //根据value进行查询
    @GetMapping("/admin/cmn/dict/getName/{value}")
    public String getName(@PathVariable("value") String value);
}
