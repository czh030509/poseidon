package com.zaihua.controller; /**
 * @author : zaihua.chen
 * @version : 1.0.0
 * @since : 2016/7/1 11:46
 */

import com.zaihua.dao.entity.KDay;
import com.zaihua.dao.entity.KDayDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWorldController {
    @RequestMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("greeting", "Hello Spring MVC");
        return"helloworld";
    }
}
