package com.zaihua.controller; /**
 * @author : zaihua.chen
 * @version : 1.0.0
 * @since : 2016/7/1 11:46
 */

import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController {

    private static String TOKEN = "gFS3UYzlI5FP0Nn";
    private static String EncodingAESKey = "0jkCCyVX8auOOIiajEOMh4b3wJi4lHwcYl0nLjmDRI8";
    private static String CORP_ID = "wx3835914a6d509f9a";

    @RequestMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("greeting", "Hello Spring MVC");
        return"helloworld";
    }

    @RequestMapping("/wechat/heartbeat")
    public ModelAndView heartbeat(String msg_signature, String timestamp, String nonce, String echostr){
        ModelAndView modelAndView = new ModelAndView("heartbeat");
        try {
            WXBizMsgCrypt wxBizMsgCrypt = new WXBizMsgCrypt(TOKEN, EncodingAESKey, CORP_ID);
            modelAndView.addObject("echostr", wxBizMsgCrypt.VerifyURL(msg_signature, timestamp, nonce, echostr));
        } catch (AesException e) {
            e.printStackTrace();
            modelAndView.addObject("echostr", e.getMessage());
        }

        return modelAndView;
    }
}
