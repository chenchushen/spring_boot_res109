package com.yc.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.yc.config.SpringJobBeanFactory;
import com.yc.mapper.DeadManMapper;
import com.yc.model.entity.DeadManExcelData;
import com.yc.thread.DeadManThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

/**
 * 单线程的事件监听器
 */
@Service
@Slf4j
public class DeadManExcelListener extends AnalysisEventListener<DeadManExcelData> {

    /**
     *多线程保存集合，使用线程安全集合
     */
    private List<DeadManExcelData> list  = Collections.synchronizedList(new ArrayList<>());

    /**
     * 创建线程池必要的参数
     * @return
     */
    private static final int CORE_POOL_SIZE = 5;    //核心线程数
    private static final int MAX_POOL_SIZE = 10;    //最大线程数
    private static final int QUEUE_CAPACITY = 100;    //队列大小
    private static final Long KEEP_ALIVE_TIME = 1L;    //存活时间


    public List<DeadManExcelData> getData(){
        return list;
    }

    public DeadManExcelListener(){}

    public void setData(List<DeadManExcelData> deadManExcelData){
        this.list = deadManExcelData;
    }

    @Override
    public void invoke(DeadManExcelData deadManExcelData, AnalysisContext analysisContext) {
        log.info("接收到："+deadManExcelData);
        if (deadManExcelData!=null){
            list.add(deadManExcelData);
        }
    }

    /**
     * 多线程方式保存
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("解析结束，开始插入数据");

        //创建线程池
        ExecutorService executor = new ThreadPoolExecutor(CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                new ThreadPoolExecutor.CallerRunsPolicy());

        //指定每个线程需要处理的导入数据，假设每个线程处理1000条
        int singleThreadDealCount = 1000;

        //根据假设每个线程需要处理的数量以及总数，计算需要提交到线程池的线程数量
        int threadSize = (list.size()/singleThreadDealCount)+1;

        //计算需要导入的数据总数，用于拆分线程需要处理数据时使用
        int rowSize = list.size() + 1;

        //测试开始时间
        long startTime = System.currentTimeMillis();

        //申明该线程需要处理数据的开始位置
        int startPosition = 0;

        //申明该线程需要处理数据的结束位置
        int endPosition = 0;

        //为了让每个线程执行完后回到当前线程，使用CountDownLatch,值为线程数，每次线程执行完就会执行countDown方法减1，为0后回到主线程
        CountDownLatch count = new CountDownLatch(threadSize);

        //计算每个线程要处理的数据
        for (int i = 0; i < threadSize; i++) {
            //如果是最后一个线程，为保证程序不发生空指针异常，特殊判断结束位置
            if((i+1)==threadSize){
                //计算开始位置
                startTime = (i * singleThreadDealCount);
                //当前线程为划分的最后一个线程，则取总数据的最后为此线程的结束位置
                endPosition = rowSize-1;
            }else{
                //计算开始位置
                startPosition = (i * singleThreadDealCount);
                //计算结束位置
                endPosition = (i+1)*singleThreadDealCount;
            }
            DeadManMapper deadManMapper = SpringJobBeanFactory.getBean(DeadManMapper.class);
            DeadManThread thread = new DeadManThread(count,deadManMapper,list,startPosition,endPosition);
            executor.execute(thread);
        }
        try {
            count.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //逻辑处理完，关闭线程池
        executor.shutdown();
        long endTime = System.currentTimeMillis();
        System.out.println("总耗时："+(endTime-startTime));
    }
}
