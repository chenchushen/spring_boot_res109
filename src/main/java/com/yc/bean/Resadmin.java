package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class Resadmin implements Serializable {
	@TableId(type = IdType.AUTO)
	private int raid;
	private String raname;
	private String rapwd;


}
