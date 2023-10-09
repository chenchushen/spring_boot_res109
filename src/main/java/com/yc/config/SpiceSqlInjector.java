package com.yc.config;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpiceSqlInjector extends DefaultSqlInjector {
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        //此SQL注入器继承了DefaultSqlInjector(默认注入器)，调用了父类中的getMethodList方法，保留了原来mybatis-plus中的方法
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        //注入InsertBatchSomeColumn方法
        //!t.isLogicDelete()表示不要逻辑删除的字段；!"update_time".equals(t.getColumn())表示不要update_time这个字段
        methodList.add(new InsertBatchSomeColumn(t->!t.isLogicDelete() && !"update_time".equals(t.getColumn())));
        return super.getMethodList(mapperClass);
    }
}
