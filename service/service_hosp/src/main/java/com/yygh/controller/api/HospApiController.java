package com.yygh.controller.api;

import com.ftl1ght.yygh.common.result.Result;
import com.yygh.model.hosp.Hospital;
import com.yygh.service.DepartmentService;
import com.yygh.service.HospitalService;
import com.yygh.vo.hosp.DepartmentVo;
import com.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author FTL1ght
 * @Description
 * @create 2022-12-31 15:51
 */
@RestController
@RequestMapping("/api/hosp/hospital")
public class HospApiController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private DepartmentService departmentService;

    //查询医院列表
    @GetMapping("findHospList/{page}/{limit}")
    public Result findHospList(@PathVariable Integer page,
                               @PathVariable Integer limit,
                               HospitalQueryVo hospitalQueryVo){
        Page<Hospital> hospPage = hospitalService.selectHospPage(page, limit, hospitalQueryVo);

        List<Hospital> content = hospPage.getContent();
        int totalPages = hospPage.getTotalPages();

        return Result.ok(hospPage);
    }

    //根据医院名称查询
    @GetMapping("findByHosName/{hosname}")
    public Result findByHosName(@PathVariable String hosname){

        List<Hospital> list = hospitalService.findByHosname(hosname);

        return Result.ok(list);
    }

    //根据医院编号获取科室
    @GetMapping("department/{hoscode}")
    public Result index(@PathVariable String hoscode){

        List<DepartmentVo> list = departmentService.findDeptTree(hoscode);

        return Result.ok(list);
    }

    //根据医院编号获取医院预约挂号详情
    @GetMapping("findHospDetail/{hoscode}")
    public Result item(@PathVariable String hoscode){

        Map<String,Object> map = hospitalService.item(hoscode);

        return Result.ok(map);
    }
}
