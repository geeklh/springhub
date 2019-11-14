package com.geekli.controller;

import com.geekli.model.MyResult;
import com.geekli.model.User;
import com.geekli.model.Userbackform;
import com.geekli.model.UserscKey;
import com.geekli.service.UserService;
import com.geekli.utils.TESTOKHttp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin(maxAge = 3600)
@Api(value = "用户配置", tags = "用户接口")
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/regist.do", method = RequestMethod.POST)
    @ApiOperation(value = "注册接口")
    @ResponseBody
    public MyResult regist(@RequestBody User user) {
        return userService.regist(user);
    }

    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    @ApiOperation(value = "登录接口")
    @ResponseBody
    public MyResult login(@RequestBody User user) {
        return userService.login(user);
    }

    @RequestMapping(value = "/updateUser.do", method = RequestMethod.PUT)
    @ApiOperation(value = "更新用户数据")
    @ResponseBody
    public MyResult updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @RequestMapping(value = "/updateUserPassword.do", method = RequestMethod.PUT)
    @ApiOperation(value = "找回密码接口")
    @ResponseBody
    public MyResult updateUserPassword(@RequestBody User user) {
        return userService.updateUserPassword(user);
    }

    @RequestMapping(value = "/selectuserProduct.do", method = RequestMethod.PUT)
    @ApiOperation(value = "查询当前用户拥有的机器")
    @ResponseBody
    public MyResult selectUserProduct(@RequestBody User user) {
        return userService.selectUserProduct(user);
    }

    @RequestMapping(value = "/buypro.do", method = RequestMethod.POST)
    @ApiOperation(value = "购买商品成功")
    @ResponseBody
    public MyResult buypro(@RequestBody UserscKey userscKey) {
        return userService.buypro(userscKey);
    }

    @RequestMapping(value = "/bugvip.do", method = RequestMethod.PUT)
    @ApiOperation(value = "购买VIP")
    @ResponseBody
    public MyResult buyvip(@RequestBody User user) {
        return userService.buyvip(user);
    }

    @ResponseBody
    @ApiOperation(value = "Fmos平台接口")
    @RequestMapping("/fmosconnect.do")
    public String Fmosconnect() {
        String connectString = TESTOKHttp.interfaceUtil("http://39.108.231.58:8083/ApiWithOutLogin/TsAdjust?DataString=1,1,1;sp2,993.1402,997.8317;sp3,993.4448,996.5921;sp4,993.6914,995.5993;ts;sp2,L,0.0000;sp2,S,7.1933;sp3,L,9.5537;sp3,S,7.3880;sp4,L,17.2117;sp4,S,7.6916;sp5,L,86.2910;sp5,S,6.9132;sp1,L,356.5306;sp1,S,7.2007;sp2,11.3387;sp3,11.3380;sp4,11.3454;ts,sp3,1.3379,7.3880,1;ts,sp4,1.3455,7.6916,1;ts,sp5,0.9312,6.9132,1;ts,sp1,0.8919,7.2007,1;ts,sp2,1.3386,7.1933,1;", "");
        return connectString;
    }

    @ResponseBody
    @RequestMapping("/test.do")
    public String test() {
        System.out.println("123456");
        return "123";
    }
}
