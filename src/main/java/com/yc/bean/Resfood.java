package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
@Data
public class Resfood implements Serializable {

	@TableId(type = IdType.AUTO)
	private Integer fid;

	private String fname;
	private Double normprice;
	private Double realprice;
	private String detail;
	private String fphoto;

}
