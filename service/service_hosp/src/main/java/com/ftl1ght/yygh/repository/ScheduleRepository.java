package com.ftl1ght.yygh.repository;

import com.yygh.model.hosp.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author FTL1ght
 * @Description
 * @create 2022-12-25 15:40
 */
@Repository
public interface ScheduleRepository extends MongoRepository<Schedule,String> {


    Schedule getScheduleByHoscodeAndHosScheduleId(String hoscode, String hosScheduleId);

    List<Schedule> findScheduleByHoscodeAndDepcodeAndWorkDate(String hoscode, String depcode, Date toDate);
}
