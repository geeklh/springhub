package com.geekli.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhenzi.sms.ZhenziSmsClient;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Member;
import java.util.Random;

@Controller
@Api(value = "短信验证接口", tags = "登录验证")
@CrossOrigin(maxAge = 3600)
@RequestMapping(value = "/code")
public class CodeController {
    private static final long serialVersionUID = 1L;

    private String apiUrl = "https://sms_developer.zhenzikj.com";
    //榛子云系统上获取
//    private String appId = "100862";
//    private String appSecret = "62358d10-bc0e-4152-a52c-578a8debc9b9";
    private String appId = "";
    private String appSecret = "";

    @RequestMapping(value = "/fitnesscode", method = RequestMethod.POST)
    @ResponseBody
    public boolean getCode(@RequestParam("memPhone") String memphone, HttpSession httpSession) {
        try {
            JSONObject json = null;
            String code = String.valueOf(new Random().nextInt(999999));
            ZhenziSmsClient client = new ZhenziSmsClient(apiUrl, appId, appSecret);
            String result = client.send(memphone, "您的验证码为：" + code + "，该码有效期为5分钟，该码只能使用一次!");
            json = JSONObject.parseObject(result);
            if (json.getIntValue("code") != 0) {//发送短信失败
                return false;
            }
            //将验证码存到session中,同时存入创建时间
            json = new JSONObject();
            json.put("memphone", memphone);
            json.put("code", code);
            json.put("createTime", System.currentTimeMillis());
            httpSession.setAttribute("code", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
