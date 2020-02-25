package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author ysl
 * @Date 2019/6/23 10:23
 * @Description: TODO 返回结果封装
 **/


public class Result implements Serializable {
    private boolean success; //定义是否成功
    private String message;  //定义相关的信息

    //用于存储 错误信息的格式为：[{field:,msg:},{}]
    private List<Error> errorsList = new ArrayList<>();


    public Result() {
    }

    public Result(boolean success, String message, List<Error> errorsList) {
        this.success = success;
        this.message = message;
        this.errorsList = errorsList;
    }

    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }


    public List<Error> getErrorsList() {
        return errorsList;
    }

    public void setErrorsList(List<Error> errorsList) {
        this.errorsList = errorsList;
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
