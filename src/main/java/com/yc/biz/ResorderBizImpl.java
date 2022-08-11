package com.yc.biz;

import com.yc.bean.CartItem;
import com.yc.bean.Resfood;
import com.yc.bean.Resorder;
import com.yc.bean.Resorderitem;
import com.yc.mapper.ResorderMapper;
import com.yc.mapper.ResorderitemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service
@Transactional
public class ResorderBizImpl implements ResorderBiz{
    @Autowired
    private ResorderMapper resorderMapper;
    @Autowired
    private ResorderitemMapper resorderitemMapper;
    @Override
    public void confirm(Resorder order, Map<Integer, CartItem> cart) {
        Date d = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        order.setOrdertime(df.format(d));

        resorderMapper.insert(order);
        int oid = order.getRoid();  //新增的订单号    ？？？
        //3.循环添加订单详情
        for(Map.Entry<Integer,CartItem> entry : cart.entrySet()){
            int fid = entry.getKey();
            CartItem ci = entry.getValue();
            Resfood resfood = ci.getResfood();
            int num = ci.getNum();
            double smallCount = ci.getSmallCount();
            Resorderitem ri = new Resorderitem();
            ri.setFid(fid);
            ri.setNum(num);
            ri.setRoid(oid);
            ri.setDealprice( resfood.getRealprice());

            resorderitemMapper.insert(ri);
        }
    }
}
