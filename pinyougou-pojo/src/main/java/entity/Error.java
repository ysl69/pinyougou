package entity;

import java.io.Serializable;

/**
 * @Author ysl
 * @Date 2019/7/10 16:18
 * @Description:
 **/


public class Error implements Serializable {
    private String field;//发生错误的属性字段名 （必须要填）
    private Integer id;//发生错误的属性的ID 可选值
    private String msg;//错误的信息（必须要要写）


    public Error() {
    }


    public Error(String field, String msg) {
        this.field = field;
        this.msg = msg;
    }

    public Error(String field, Integer id, String msg) {
        this.field = field;
        this.id = id;
        this.msg = msg;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
