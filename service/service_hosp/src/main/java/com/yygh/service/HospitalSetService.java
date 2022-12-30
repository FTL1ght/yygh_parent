package com.yygh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yygh.model.hosp.HospitalSet;

/**
 * @author FTL1ght
 * @Description
 * @create 2022-11-17 14:46
 */
public interface HospitalSetService extends IService<HospitalSet> {

    //2 根据传递过来的医院编码，查询数据库，查询签名
    String getSignKey(String hoscode);
}
