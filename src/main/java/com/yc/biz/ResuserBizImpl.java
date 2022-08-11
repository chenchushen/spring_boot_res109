package com.yc.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yc.bean.Resuser;
import com.yc.commons.Encrypt;
import com.yc.mapper.ResuserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ResuserBizImpl implements ResuserBiz{

    @Autowired
    private ResuserMapper resuserMapper;
    @Override
    public Resuser login(String uname, String pwd) {
        pwd = Encrypt.md5(pwd);   //加密
        QueryWrapper<Resuser> wrapper = new QueryWrapper<>();  //查询条件包装器
        wrapper.eq("username",uname).eq("pwd",pwd);
        return resuserMapper.selectOne(wrapper);
    }
}
