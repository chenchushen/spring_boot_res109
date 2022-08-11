package com.yc.bean;

import java.io.Serializable;

/**
 * 购物车项
 */
public class CartItem implements Serializable {
    private Resfood resfood;
    private Integer num;

    //下面的小计只是一个计算的方法，     gson包生成json时，只对属性生成json字符串，不会对方法生成，所以要增加一个属性
    private double smallCount;

    /*
    增加了一个业务处理方法，它没有对应一个属性，完全是计算出来的.   -> vue计算属性
     */
    public double getSmallCount(){
        if(  resfood==null ){
            return 0;
        }
        smallCount= resfood.getRealprice()*num;
        return smallCount;
    }

    public Resfood getResfood() {
        return resfood;
    }

    public Integer getNum() {
        return num;
    }

    public void setResfood(Resfood resfood) {
        this.resfood = resfood;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
