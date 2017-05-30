package com.zaihua.quanjingbiao;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : zaihua.chen
 * @version : 1.0.0
 * @since : 2016/12/28 18:03
 */
@Controller
public class MainController {
    @RequestMapping("/home")
    public String getQuanjb(Model model) {
        return "quanjb";
    }

    @RequestMapping("/search/{code}")
    public String getCodes(@PathVariable("code") String code, Model model) {
        model.addAttribute("code", code);
        return "companies";
    }
}
