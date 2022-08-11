package com.yc.biz;

import com.yc.bean.Resfood;
import com.yc.mapper.ResfoodMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ResfoodBizImpl implements ResfoodBiz{
    @Autowired
    private ResfoodMapper resfoodMapper;

    @Override
    public List<Resfood> findAll(){
        return resfoodMapper.selectList(null);
    }
}
