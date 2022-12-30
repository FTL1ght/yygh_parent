package com.ftl1ght.yygh.controller;

import com.ftl1ght.yygh.common.result.Result;
import com.ftl1ght.yygh.service.DictService;
import com.yygh.model.cmn.Dict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author FTL1ght
 * @Description
 * @create 2022-11-23 16:27
 */
@RestController
@CrossOrigin
@RequestMapping("/admin/cmn/dict")
public class DictController {

    @Autowired
    private DictService dictService;

    //根据dictCode查询下级节点
    @GetMapping("findByDictCode/{dictCode}")
    public Result findByDictCode(@PathVariable String dictCode){
        List<Dict> list = dictService.findByDictCode(dictCode);

        return Result.ok(list);
    }

    //导入数据字典
    @PostMapping("importData")
    public Result importDict(MultipartFile file){
        dictService.importDict(file);

        return Result.ok();

    }

    //导出数据字典
    @GetMapping("exportData")
    public void exportDict(HttpServletResponse response){
        dictService.exportDictData(response);
    }


    //根据id查询子数据
    @GetMapping("findChildData/{id}")
    public Result findChildData(@PathVariable Long id){
        List<Dict> list = dictService.findChildData(id);
        return Result.ok(list);
    }

    //根据dictCode和value进行查询
    @GetMapping("getName/{dictCode}/{value}")
    public String getName(@PathVariable String dictCode,
                          @PathVariable String value){

        String dictName = dictService.getDictName(dictCode,value);
        return dictName;
    }


    //根据value进行查询
    @GetMapping("getName/{value}")
    public String getName(@PathVariable String value){

        String dictName = dictService.getDictName("",value);
        return dictName;
    }

}
