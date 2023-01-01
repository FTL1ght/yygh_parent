package com.yygh.controller;

import com.ftl1ght.yygh.common.result.Result;
import com.yygh.model.hosp.Hospital;
import com.yygh.service.HospitalService;
import com.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author FTL1ght
 * @Description
 * @create 2022-12-26 21:42
 */
@RestController
@RequestMapping("/admin/hosp/hospital")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @GetMapping("list/{page}/{limit}")
    public Result listHosp(@PathVariable Integer page,
                           @PathVariable Integer limit,
                           HospitalQueryVo hospitalQueryVo){

        Page pageModel = hospitalService.selectHospPage(page,limit,hospitalQueryVo);
        return Result.ok(pageModel);
    }

    //更新医院上线状态
    @GetMapping("updateHospStatus/{id}/{status}")
    public Result updateHospStatus(@PathVariable String id,
                                   @PathVariable Integer status){
        hospitalService.updateStatus(id,status);

        return Result.ok();
    }

    //获取医院详情信息
    @GetMapping("showHospDetail/{id}")
    public Result showHospDetail(@PathVariable String id){

        //为了后续取值方便，可以直接将hospital和bookingRule两种结果放进Map集合
        Map<String,Object> map = hospitalService.getHospById(id);

        return Result.ok(map);
    }
}
