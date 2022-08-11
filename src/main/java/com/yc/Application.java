package com.yc;



import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Hashtable;

//boot入口程序
@SpringBootApplication

@MapperScan("com.yc.mapper")
@EnableTransactionManagement   //开启事务管理器
public class Application {

    //Laucher类
    public static void main(String[] args) {
        //  args：命令行参数
        SpringApplication.run(Application.class,args);
        HashMap m = new HashMap();
        Hashtable mm = new Hashtable();


    }

    @Bean
    public DataSourceTransactionManager jdbcTransactionManager(DataSource ds){
        DataSourceTransactionManager jdbcTransactionManage = new DataSourceTransactionManager();
        jdbcTransactionManage.setDataSource(ds);
        return jdbcTransactionManage;
    }
}

