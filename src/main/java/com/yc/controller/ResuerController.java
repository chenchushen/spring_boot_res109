package com.yc.controller;

import com.yc.bean.JsonModel;
import com.yc.bean.Resuser;
import com.yc.biz.ResuserBiz;
import com.yc.commons.DataDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class ResuerController {

    @Autowired
    private ResuserBiz resuserBiz;

    @RequestMapping("/login")
    public JsonModel logout(String username,String pwd, String valcode,HttpSession session){
        JsonModel js = new JsonModel();
        try{
            //2.运算：登录
            //从session中取出验证码
            String validateCode = session.getAttribute("validateCode").toString();
            if (!validateCode.equalsIgnoreCase(valcode)){
                js.setCode(0);
                js.setMsg("验证不正确，请重新输入");
                return js;
            }
            Resuser user = resuserBiz.login(username,pwd);
            if (user != null){
                session.setAttribute(DataDict.RESUSER,user);
                user.setPwd("");
                js.setCode(1);
                js.setData(user);
            }else{
                js.setCode(0);
                js.setMsg("用户名或密码错误");
            }
            return js;
        }catch (Exception ex){
            js.setCode(0);
            js.setMsg(ex.getMessage());
        }
        return js;
    }
    @RequestMapping("/checkLogin")
    public JsonModel checkLogin(HttpSession session){
        JsonModel js = new JsonModel();
       if( session.getAttribute(DataDict.RESUSER)!=null){
           js.setCode(1);
           Resuser resuer = (Resuser) session.getAttribute(DataDict.RESUSER);
           resuer.setPwd(" ");
           js.setData(resuer);
       }else{
           js.setCode(0);
       }
        return js;
    }
}
