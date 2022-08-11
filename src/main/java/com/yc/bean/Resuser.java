package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
@Data
public class Resuser implements Serializable {
	@TableId(type = IdType.AUTO)
	private Integer userid;
	private String username;
	private String pwd;
	private String email;
}
