package com.yc.controller;


import com.yc.bean.JsonModel;
import com.yc.bean.Resfood;
import com.yc.biz.ResfoodBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@ResponseBody
//上面两个注解相当与一个@RestController
public class RestController {
    @Autowired
    private ResfoodBiz resfoodBiz;

    @RequestMapping("/findAllFoods")
    public JsonModel findAll(HttpSession session){
        JsonModel js = new JsonModel();
        try{
            List<Resfood> list = resfoodBiz.findAll();
            js.setCode(1);
            js.setData(list);
            session.setAttribute("list",list);  //界面商品全部存到session中
        }catch (Exception ex){
            js.setCode(0);
        }
        return js;
    }
}
