package com.ftl1ght.yygh.controller.api;

import com.ftl1ght.yygh.common.exception.YyghException;
import com.ftl1ght.yygh.common.helper.HttpRequestHelper;
import com.ftl1ght.yygh.common.result.Result;
import com.ftl1ght.yygh.common.result.ResultCodeEnum;
import com.ftl1ght.yygh.common.util.MD5;
import com.ftl1ght.yygh.service.HospitalService;
import com.ftl1ght.yygh.service.ScheduleService;
import com.yygh.model.hosp.Department;
import com.yygh.model.hosp.Hospital;
import com.yygh.model.hosp.Schedule;
import com.ftl1ght.yygh.service.DepartmentService;
import com.ftl1ght.yygh.service.HospitalSetService;
import com.yygh.vo.hosp.DepartmentQueryVo;
import com.yygh.vo.hosp.ScheduleQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author FTL1ght
 * @Description
 * @create 2022-12-14 20:31
 */
@Api(tags="医院管理API接口")
@RestController
@RequestMapping("/api/hosp")
public class ApiController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private HospitalSetService hospitalSetService;

    //删除排班接口
    @PostMapping("schedule/remove")
    public Result remove(HttpServletRequest request){
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());

        //获取医院编号和科室编号
        String hoscode = (String)paramMap.get("hoscode");
        String hosScheduleId = (String)paramMap.get("hosScheduleId");

        //签名校验
        String signKey = hospitalSetService.getSignKey(hoscode);
        String signKeyMD5 = MD5.encrypt(signKey);
        String sign = (String)paramMap.get("sign");

        if (!sign.equals(signKeyMD5)){
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        scheduleService.remove(hoscode,hosScheduleId);
        return Result.ok();
    }

    //获取排班接口
    @PostMapping("schedule/list")
    public Result findSchedule(HttpServletRequest request){
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());

        String sign = (String) paramMap.get("sign");

        //医院编号
        String hoscode =(String) paramMap.get("hoscode");
        //科室编号
        String depcode = (String) paramMap.get("depcode");
        String signKey = hospitalSetService.getSignKey(hoscode);
        String signKeyMD5 = MD5.encrypt(signKey);

        //当前页和每页记录数
        int page = StringUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt((String)paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit")) ? 1:Integer.parseInt((String)paramMap.get("limit"));

        //签名校验
        if (!sign.equals(signKeyMD5)){
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        //DepartmentQueryVo departmentQueryVo = new DepartmentQueryVo();
        ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
        //departmentQueryVo.setHoscode(hoscode);
        scheduleQueryVo.setHoscode(hoscode);
        scheduleQueryVo.setDepcode(depcode);

        //Page<Department> pageModel = departmentService.findPageDepartment(page,limit,departmentQueryVo);
        Page<Schedule> pageModel = scheduleService.findPageSchedule(page,limit,scheduleQueryVo);

        return Result.ok(pageModel);
    }


    //上传排班接口
    @PostMapping("saveSchedule")
    public Result saveSchedule(HttpServletRequest request){

        //获取医院信息
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        String hoscode = (String)paramMap.get("hoscode");
        String sign = (String)paramMap.get("sign");
        String signKey = hospitalSetService.getSignKey(hoscode);
        String signKeyMD5 = MD5.encrypt(signKey);

        if (!sign.equals(signKeyMD5)){
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        scheduleService.save(paramMap);

        return Result.ok();
    }

    //删除科室信息
    @PostMapping("department/remove")
    public Result removeDepartment(HttpServletRequest request){
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());

        //获取医院编号和科室编号
        String hoscode = (String)paramMap.get("hoscode");
        String depcode = (String)paramMap.get("depcode");

        //签名校验
        String signKey = hospitalSetService.getSignKey(hoscode);
        String signKeyMD5 = MD5.encrypt(signKey);
        String sign = (String)paramMap.get("sign");

        if (!sign.equals(signKeyMD5)){
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        departmentService.remove(hoscode,depcode);
        return Result.ok();
    }


    //展示科室信息
    @PostMapping("department/list")
    public Result findDepartment(HttpServletRequest request){
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());

        String sign = (String) paramMap.get("sign");

        String hoscode =(String) paramMap.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hoscode);
        String signKeyMD5 = MD5.encrypt(signKey);

        //当前页和每页记录数
        int page = StringUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt((String)paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit")) ? 1:Integer.parseInt((String)paramMap.get("limit"));

        //签名校验
        if (!sign.equals(signKeyMD5)){
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        DepartmentQueryVo departmentQueryVo = new DepartmentQueryVo();
        departmentQueryVo.setHoscode(hoscode);

        Page<Department> pageModel = departmentService.findPageDepartment(page,limit,departmentQueryVo);

        return Result.ok(pageModel);
    }

    //上传科室
    @PostMapping("saveDepartment")
    public Result saveDepartment(HttpServletRequest request){
        //获取页面传递的科室信息
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());

        //获取传来科室的签名
        String sign = (String) paramMap.get("sign");

        //根据科室编码，获取数据库中对应的签名
        String hoscode = (String) paramMap.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hoscode);

        //再将数据库汇总的signKey通过MD5加密后与网页获取的sign比对
        String signKeyMd5 = MD5.encrypt(signKey);

        if (!sign.equals(signKeyMd5)){
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        departmentService.save(paramMap);

        return Result.ok();
    }

    //查询医院
    @PostMapping("hospital/show")
    public Result getHospital(HttpServletRequest request){
        //获取传递过来的医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //1 获取医院系统传递过来的签名，签名进行MD5加密
        String hospSign = (String) paramMap.get("sign");

        //2 根据传递过来的医院编码，查询数据库，查询签名
        String hoscode = (String) paramMap.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hoscode);

        //3 把数据库查询签名进行MD5加密
        String signKeyMD5 = MD5.encrypt(signKey);

        //4 判断签名是否一致
        if (!hospSign.equals(signKeyMD5)){
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        //调用service方法实现根据医院编号查询
        Hospital hospital = hospitalService.getByHoscode(hoscode);

        return Result.ok(hospital);

    }


    @ApiOperation(value="上传医院")
    @PostMapping("saveHospital")
    public Result saveHospital(HttpServletRequest request){
        //获取传递过来的医院信息
        Map<String,Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());

        //1 获取医院系统传递过来的签名，签名进行MD5加密
        String hospSign = (String) paramMap.get("sign");

        //2 根据传递过来的医院编码，查询数据库，查询签名
        String hoscode = (String) paramMap.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hoscode);

        //3 把数据库查询签名进行MD5加密
        String signKeyMD5 = MD5.encrypt(signKey);

        //4 判断签名是否一致
        if (!hospSign.equals(signKeyMD5)){
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        //传输过程中图片base64 "+"转换成了" "，因此需要转换回来
        String logoData = (String) paramMap.get("logoData");
        logoData=logoData.replaceAll(" ","+");
        paramMap.put("logoData",logoData);


        hospitalService.save(paramMap);
        return Result.ok();
    }
}
