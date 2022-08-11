package com.yc.controller;


import com.yc.bean.*;
import com.yc.biz.ResorderBiz;
import com.yc.commons.DataDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class ResorderController {
    @Autowired
    private ResorderBiz resorderBiz;

    @RequestMapping("/confirmOrder")
    public JsonModel confirmOrder( Resorder resorder,HttpSession session){
        JsonModel js = new JsonModel();
        try{
            Resuser resuser = (Resuser) session.getAttribute(DataDict.RESUSER);
            //获取用户id存到 resorder中
            resorder.setUserid(resuser.getUserid());
            resorder.setStatus(DataDict.ORDER_STATUS_ORDERED); //1表示已下单
            //取出购物车
            Map<Integer,CartItem> cart = (Map<Integer, CartItem>) session.getAttribute(DataDict.CART);
            resorderBiz.confirm(resorder,cart);
            //下单成功，则购物车清空
            session.removeAttribute(DataDict.CART);
            js.setCode(1);
        }catch (Exception ex){
            ex.printStackTrace();
            js.setCode(0);
            js.setMsg(ex.getMessage());
        }
        return js;
    }

    @RequestMapping("/order")
    public JsonModel order(HttpSession session ,int fid,int num){
        JsonModel js = new JsonModel();
        try{
            //判断是否登录
            if(session.getAttribute(DataDict.RESUSER)==null){
                js.setCode(-1);
                return js;
            }
            List<Resfood> list = (List<Resfood>) session.getAttribute("list");
            Resfood resfood = null;
            boolean isFound=false;
            for( Resfood rf: list){
                if (rf.getFid()==fid){
                    resfood=rf;   //要加入购物车的商品
                    isFound =true;
                    break;
                }
            }
            if (isFound==false){
                js.setCode(0);
                js.setMsg("查无此商品,"+fid);
                return js;
            }
            //加入购物车，如何设计
            //ConcurrentHashMap这是一个线程安全的Map
            //<>
            Map<Integer,CartItem> cart = null;
            if (session.getAttribute(DataDict.CART)!=null){
                cart = (Map<Integer, CartItem>) session.getAttribute(DataDict.CART);
            }else{
                cart = new ConcurrentHashMap<>();
            }
            //判断 cart中是否有这个fid的键，如果有，则取出fid对应的值cartItem,然后加数量
            //                           没有，则创建一个CartItem对象，以fid做键存到cart中
            CartItem ci = null;
            if (cart.containsKey(fid)){
                ci =cart.get(fid);
                ci.setNum(ci.getNum()+num);
            }else{
                ci=new CartItem();
                ci.setResfood(resfood);
                ci.setNum(num);
            }
            //计算小计
            ci.getSmallCount();
            cart.put(fid,ci);    //存入cart中
            //如果这个cartitem的数量少于等于0，则要从购物车删除这个商品
            if (ci.getNum()<=0){
                cart.remove(fid);
            }
            //将cart购物车存入到session中
            session.setAttribute(DataDict.CART,cart);
            //添加成功，回送  code=1
            js.setCode(1);
            js.setData(cart);
        }catch(Exception ex){
            ex.printStackTrace();
            js.setCode(0);
            js.setMsg(ex.getMessage());
        }
        return js;
    }
    @RequestMapping("/clearAll")
    public JsonModel clearAll(HttpSession session){
        JsonModel js = new JsonModel();
        if (session.getAttribute(DataDict.RESUSER)==null){
            js.setCode(-1);
            return js;
        }
        session.removeAttribute(DataDict.CART);
        js.setCode(1);
        return js;
    }
    @RequestMapping("/getCartInfo")
    public JsonModel getCartInfo(HttpSession session){
        JsonModel js = new JsonModel();
        if (session.getAttribute(DataDict.RESUSER)==null){
            js.setCode(-1);
            return js;
        }
        Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute(DataDict.CART);

        js.setCode(1);
        js.setData(cart);
        return js;
    }
}
