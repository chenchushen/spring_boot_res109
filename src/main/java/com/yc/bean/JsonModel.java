package com.yc.bean;


import lombok.Data;

import java.io.Serializable;

//最好实现序列化接口,实现缓存(tomcat)
//应用协议
@Data
public class JsonModel implements Serializable {
    private Integer code;
    /**如果访问出错的异常信息*/
    private String msg;
    private Object data;


}
