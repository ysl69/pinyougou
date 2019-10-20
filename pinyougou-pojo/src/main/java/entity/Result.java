package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author ysl
 * @Date 2019/6/24 13:26
 * @Description: TODO 返回结果信息
 **/


public class Result implements Serializable {
    private boolean success;  //定义是否成功
    private String message;  //定义相关的信息

    //错误信息
    private List<Error> errorList = new ArrayList<>();

    public List<Error> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<Error> errorList) {
        this.errorList = errorList;
    }

    public Result() {
    }

    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
