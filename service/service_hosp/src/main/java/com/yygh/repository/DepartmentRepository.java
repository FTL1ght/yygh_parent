package com.yygh.repository;

import com.yygh.model.hosp.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author FTL1ght
 * @Description
 * @create 2022-12-24 21:12
 */
@Repository
public interface DepartmentRepository extends MongoRepository<Department,String> {
    Department getDepartmentByHoscodeAndDepcode(String hoscode, String depcode);

    Department getDepartmentByHoscode();
}
