package com.pinpougou.controller;

import com.pinpougou.model.Person;
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
 * @Date 2019/10/20 11:00
 * @Description:
 **/

@RequestMapping("/person")
@RestController
public class PersonController {

    @RequestMapping("/add")
    public Result add(@Valid @RequestBody Person person, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                System.out.println("错误信息>>>>>>>>>" + fieldError.getDefaultMessage());
            }
            return new Result(false, "error");
        }else {
            return new Result(true,"meicuo");
        }
    }
}
