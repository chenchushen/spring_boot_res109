package com.yc.biz;

import com.alibaba.excel.EasyExcel;

import com.yc.listener.DeadManExcelListener;
import com.yc.listener.DeadManExcelNoThreadListener;
import com.yc.model.entity.DeadManExcelData;
import com.yc.util.MutipartFileToFileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImportServiceImpl implements ImportService {
    @Override
    public String importDeadManList(MultipartFile file) {
        //通过绑定DeadManExcelListener监听器方式导入数据
        //多线程导入
        EasyExcel.read(MutipartFileToFileUtils.MultipartFileToFile(file), DeadManExcelData.class,
                new DeadManExcelListener()).sheet().doRead();

        //单线程导入
//        EasyExcel.read(MutipartFileToFileUtils.MultipartFileToFile(file),
//                DeadManExcelData.class,new DeadManExcelNoThreadListener()).sheet().doRead();
        return "导入成功";
    }
}
