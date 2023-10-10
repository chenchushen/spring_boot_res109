package com.yc.controller;

import com.yc.biz.ImportService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNullApi;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/importController")
public class ImportController {
    //让lombok在生成setter方法时，在这个setter方法上进行@Autowired注入
    //@Setter(onMethod = @Autowired)
    @Resource
    private ImportService importService;

    @ApiOperation("名单导入接口")
    @PostMapping("importDeadManList")
    @ApiImplicitParams(@ApiImplicitParam(name = "file", value = "导入excel文件", required = true))
    public String importDeadManList(@RequestParam("file") MultipartFile file) {
        return importService.importDeadManList(file);
    }


}
