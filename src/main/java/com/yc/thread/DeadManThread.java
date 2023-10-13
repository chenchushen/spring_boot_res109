package com.yc.thread;

import com.yc.mapper.DeadManMapper;
import com.yc.model.entity.DeadMan;
import com.yc.model.entity.DeadManExcelData;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Component
public class DeadManThread implements Runnable {

    //当前线程需要处理的总数据中的开始位置
    private int startPosition;

    //当前线程需要处理的总数居中的结束位置
    private int endPosition;

    //需要处理的未查拆分之前的全部数据
    private List<DeadManExcelData> list = Collections.synchronizedList(new ArrayList<>());

    private CountDownLatch count;

    private DeadManMapper deadManMapper;

    public DeadManThread() {
    }

    public DeadManThread(CountDownLatch count, DeadManMapper deadManMapper, List<DeadManExcelData> list
            , int startPosition, int endPosition) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.deadManMapper = deadManMapper;
        this.list = list;
        this.count = count;
    }

    @Override
    public void run() {
        try {
            ArrayList<DeadMan> deadManList = new ArrayList<>();
            List<DeadManExcelData> newList = list.subList(startPosition, endPosition);
            //将EasyExcel对象和实体类对象进行一个转换
            for (DeadManExcelData deadManExcelData :
                    newList) {
                DeadMan deadMan = new DeadMan();
                BeanUtils.copyProperties(deadManExcelData, deadMan);
                deadManList.add(deadMan);
            }
            //批量新增
            deadManMapper.intsertBatchSomeColumn(deadManList);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //当一个线程执行完了计数要减一不然这个程序会一直挂起
            count.countDown();
        }

    }
}
