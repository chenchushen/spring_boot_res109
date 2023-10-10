package com.yc.model.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@ContentRowHeight(15)
@HeadRowHeight(20)
@Builder    //自动生成builder()方法，可以通过链式调用的方式设置对象属性
@ApiModel(description = "excel导入模板类")
@NoArgsConstructor
@AllArgsConstructor
public class DeadManExcelData implements Serializable {
    private static final long serialVersionUID = 1L;


    @ExcelProperty(value = "身份证",index = 0)
    private String idCard;

    @ExcelProperty(value = "姓名",index = 1)
    private String userName;

    @ExcelProperty(value = "性别",index = 2)
    private String sex;

    @ExcelProperty(value = "年龄",index = 3)
    private String age;

    @ExcelProperty(value = "原因",index = 4)
    private String reason;

    @ExcelProperty(value = "地狱层数",index = 5)
    private String house;
}
