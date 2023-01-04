package com.ftl1ght.yygh.service;


import com.yygh.model.hosp.Department;
import com.yygh.vo.hosp.DepartmentQueryVo;
import com.yygh.vo.hosp.DepartmentVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @author FTL1ght
 * @Description
 * @create 2022-12-24 21:14
 */
public interface DepartmentService {

    void save(Map<String, Object> paramMap);

    Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo);

    void remove(String hoscode, String depcode);

    List<DepartmentVo> findDeptTree(String hoscode);

    String getDepName(String hoscode, String depcode);

    //Department getByHoscode(String hoscode);
}
