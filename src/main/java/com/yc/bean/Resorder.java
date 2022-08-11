package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
@Data
public class Resorder implements Serializable {
	@TableId(type = IdType.AUTO)
	private Integer roid;
	private Integer userid;
	private String address;
	private String tel;

	private String ordertime;
	private String deliverytime;

	private String ps;
	private Integer status=0;




}
