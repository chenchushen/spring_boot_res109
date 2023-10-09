package com.yc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.poi.ss.formula.functions.T;
import org.assertj.core.util.introspection.Introspection;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public interface SpiceBaseMapper<T> extends BaseMapper<T> {
    /**
     * 批量插入，此方法的名称必须与SpiceSqlInjector类中的保持一致
     * @param entityList
     * @return
     */
    int intsertBatchSomeColumn(List<T> entityList);
}
