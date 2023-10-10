package com.yc.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "deadMan")
public class DeadMan implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "uid",type = IdType.ASSIGN_UUID)
    private String uid;

    @TableField("idCard")
    private String idCard;

    @TableField("userName")
    private String userName;

    @TableField("sex")
    private String sex;

    @TableField("age")
    private String age;

    @TableField("reason")
    private String reason;

    @TableField("house")
    private String house;


}
