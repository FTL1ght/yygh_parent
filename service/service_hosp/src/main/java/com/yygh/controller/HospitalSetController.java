package com.yygh.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ftl1ght.yygh.common.result.Result;
import com.ftl1ght.yygh.common.util.MD5;
import com.yygh.exception.YyghException;
import com.yygh.model.hosp.HospitalSet;
import com.yygh.service.HospitalSetService;
import com.yygh.vo.hosp.HospitalSetQueryVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 * @author FTL1ght
 * @Description
 * @create 2022-11-17 14:51
 */
@RestController
@RequestMapping("/admin/hosp/hospSet")
public class HospitalSetController {

    @Autowired
    private HospitalSetService hospitalSetService;

    //1 查询医院设置表所有信息
    @GetMapping("findAllHosp")
    public Result findAllHospSet(){
        List<HospitalSet> list = hospitalSetService.list();
        return Result.ok(list);
    }

    //逻辑删除
    @DeleteMapping("delete/{id}")
    public Result removeHospSet(@PathVariable Long id){
        boolean flag = hospitalSetService.removeById(id);
        if (flag){
            return Result.ok();
        }else{

            return Result.fail();
        }
    }

    //Vo类封装了筛选条件
    //mybatisPlus封装了条件查询带分页
    //条件查询带分页
    //为了前端方便(前端提供json数据),将原get请求，改为post请求，并为hospitalSetQueryVo添加@RequestBody标签
    @PostMapping("findPageHospSet/{current}/{limit}")
    public Result findPageHospSet(@PathVariable Long current,
                                  @PathVariable Long limit,
                                  @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo){
        //对page和limit进行封装
        Page<HospitalSet> setPage = new Page<>(current, limit);

        //获取筛选条件
        String hosname = hospitalSetQueryVo.getHosname();
        String hoscode = hospitalSetQueryVo.getHoscode();

        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(hosname)){
            wrapper.like("hosname",hosname);
        }
        if (!StringUtils.isEmpty(hoscode)){
            wrapper.eq("hoscode",hoscode);
        }

        //调用方法实现分页筛选
        Page<HospitalSet> hospitalSetPage = hospitalSetService.page(setPage, wrapper);

        return Result.ok(hospitalSetPage);
    }

    //添加医院设置
    @PostMapping("saveHospSet")
    public Result saveHospSet(@RequestBody HospitalSet hospitalSet){

        //设置状态,1为可以使用,0表示不能使用
        hospitalSet.setStatus(1);

        //签名秘钥
        Random random = new Random();
        hospitalSet.setApiUrl(MD5.encrypt(System.currentTimeMillis()+""+ random.nextInt(1000)));

        boolean flag = hospitalSetService.save(hospitalSet);
        if (flag){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }

    //根据id获取医院设置
    @GetMapping("getHospSet/{id}")
    public Result getHospById(@PathVariable Long id){
//        try {
//            int i = 10/0;
//        } catch (Exception e) {
//            throw new YyghException("失败",201);
//        }
        HospitalSet hosp = hospitalSetService.getById(id);
        if (hosp!=null){
            return Result.ok(hosp);
        }else{
            return Result.fail();
        }
    }

    //修改医院设置
    @PostMapping("updateHospSet")
    public Result updateHospSet(@RequestBody HospitalSet hospitalSet){
        boolean flag = hospitalSetService.updateById(hospitalSet);

        if (flag){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }


    //批量删除医院设置
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList){
        boolean flag = hospitalSetService.removeByIds(idList);
        if (flag){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }

    //8.医院设置锁定和解锁
    @PutMapping("lockHospSet/{id}/{status}")
    public Result lockHospSet(@PathVariable Long id,
                              @PathVariable Integer status){
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        hospitalSet.setStatus(status);

        //更新数据
        hospitalSetService.updateById(hospitalSet);

        return Result.ok();
    }


    //9.发送签名秘钥
    @PutMapping("sendKey/{id}")
    public Result sendKey(@PathVariable Long id){
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        hospitalSet.getSignKey();
        hospitalSet.getHoscode();
        //TODO 发送短信
        return Result.ok();
    }


}
