package com.yc.biz;

import com.yc.bean.CartItem;
import com.yc.bean.Resorder;

import java.util.Map;

public interface ResorderBiz {
    public void confirm(Resorder order, Map<Integer, CartItem>cart);
}
