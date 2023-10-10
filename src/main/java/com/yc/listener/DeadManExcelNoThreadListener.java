package com.yc.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.yc.config.SpringJobBeanFactory;
import com.yc.mapper.DeadManMapper;
import com.yc.model.entity.DeadMan;
import com.yc.model.entity.DeadManExcelData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class DeadManExcelNoThreadListener extends AnalysisEventListener<DeadManExcelData> {

    /**
     *多线程保存集合，使用线程安全集合
     */
    private List<DeadManExcelData> list  = Collections.synchronizedList(new ArrayList<>());

    public List<DeadManExcelData> getData(){
        return list;
    }

    public DeadManExcelNoThreadListener(){}

    public void setData(List<DeadManExcelData> deadManExcelData){
        this.list = deadManExcelData;
    }

    @Override
    public void invoke(DeadManExcelData deadManExcelData, AnalysisContext analysisContext) {
        log.info("接受到："+deadManExcelData);
        if (deadManExcelData != null){
            list.add(deadManExcelData);
        }
    }

    /**
     * 单线程方式保存
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("解析结束，开始插入数据");

        //测试开始时间
        long startTime = System.currentTimeMillis();

        //将EasyExcel对象和实体类对象进行一个转换
        ArrayList<DeadMan> deadManList = new ArrayList<>();
        for (DeadManExcelData deadManExcelData:list){
            DeadMan deadMan = new DeadMan();
            BeanUtils.copyProperties(deadManExcelData,deadMan);
            deadManList.add(deadMan);
        }
        //批量新增
        DeadManMapper deadManMapper = SpringJobBeanFactory.getBean(DeadManMapper.class);
        deadManMapper.intsertBatchSomeColumn(deadManList);

        long endTime = System.currentTimeMillis();

        System.out.println("总耗时："+(endTime-startTime));
    }
}
