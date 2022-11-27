package com.ftl1ght.yygh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yygh.model.cmn.Dict;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author FTL1ght
 * @Description
 * @create 2022-11-23 16:21
 */
public interface DictService extends IService<Dict> {
    //根据id查询子数据
    List<Dict> findChildData(Long id);

    void exportDictData(HttpServletResponse response);

    void importDict(MultipartFile file);
}
