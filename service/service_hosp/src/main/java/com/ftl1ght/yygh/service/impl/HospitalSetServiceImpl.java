package com.ftl1ght.yygh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ftl1ght.yygh.mapper.HospitalSetMapper;
import com.yygh.model.hosp.HospitalSet;
import com.ftl1ght.yygh.service.HospitalSetService;
import org.springframework.stereotype.Service;

/**
 * @author FTL1ght
 * @Description
 * @create 2022-11-17 14:48
 */
@Service
public class HospitalSetServiceImpl extends ServiceImpl<HospitalSetMapper, HospitalSet> implements HospitalSetService {

    //2 根据传递过来的医院编码，查询数据库，查询签名
    @Override
    public String getSignKey(String hoscode) {
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        wrapper.eq("hoscode",hoscode);
        HospitalSet hospitalSet = baseMapper.selectOne(wrapper);
        return hospitalSet.getSignKey();
    }
}
