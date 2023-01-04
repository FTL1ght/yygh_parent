package com.ftl1ght.yygh.service;

import com.yygh.model.hosp.Hospital;
import com.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @author FTL1ght
 * @Description
 * @create 2022-12-14 20:29
 */
public interface HospitalService {

    /**
     * 上传医院信息
     * @param paramMap
     */
    void save(Map<String,Object> paramMap);

    Hospital getByHoscode(String hoscode);

    Page selectHospPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo);

    void updateStatus(String id, Integer status);


    Map<String, Object> getHospById(String id);

    String getHospName(String hoscode);

    List<Hospital> findByHosname(String hosname);

    Map<String, Object> item(String hoscode);
}
