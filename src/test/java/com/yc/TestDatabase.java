package com.yc;

import com.yc.Application;
import com.yc.biz.ResfoodBiz;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
//@ContextConfiguration(classes = {Application.class})
@ActiveProfiles("init")   //激活注解 激活application-init.yml这个注解
@RunWith(SpringRunner.class)
public class TestDatabase {
    @Autowired
    private ResfoodBiz resfoodBiz;

    @Test
    public void testFindAll() {
        resfoodBiz.findAll();
    }
}