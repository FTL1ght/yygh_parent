package com.yygh.repository;

import com.yygh.model.hosp.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author FTL1ght
 * @Description
 * @create 2022-12-14 20:27
 */
public interface HospitalRepository extends MongoRepository<Hospital,String> {
    Hospital getHospitalByHoscode(String hoscode);
}
