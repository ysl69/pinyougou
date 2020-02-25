package com.pinyougou.user.controller;

import com.pinyougou.user.pojo.Person;
import entity.Error;
import entity.Result;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author ysl
 * @Date 2019/7/10 15:46
 * @Description:
 **/

@RequestMapping("/person")
@RestController
public class PersonController {


    @RequestMapping("/add")
    public Result add(@Valid @RequestBody Person person, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            Result result = new Result(false, "error");

            for (FieldError fieldError : fieldErrors) {
                Error error = new Error(fieldError.getField(), fieldError.getDefaultMessage());

                result.getErrorsList().add(error);
                System.out.println("错误信息>>>>>>>>"+fieldError.getDefaultMessage());
            }
            return result;
        }else {
            return new Result(true,"meicuo");
        }
    }
}
