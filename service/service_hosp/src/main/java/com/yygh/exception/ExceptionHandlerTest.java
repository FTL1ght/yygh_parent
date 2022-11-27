package com.yygh.exception;

import com.ftl1ght.yygh.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author FTL1ght
 * @Description 由于common_util中的globalExceptionHandler无法生效，暂时用此代替
 * @create 2022-11-19 15:34
 */
@ControllerAdvice
public class ExceptionHandlerTest {


    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.fail();
    }

    @ResponseBody
    @ExceptionHandler(YyghException.class)
    public Result error(YyghException e) {
        e.printStackTrace();
        return Result.fail();
    }
}
