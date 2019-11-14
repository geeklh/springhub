package com.geekli.controller;

import com.geekli.model.*;
import com.geekli.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin(maxAge = 3600)
@Api(value = "后台管理接口",tags = "后台管理接口")
@RequestMapping(value = "/admin")
public class adminController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/findAllUser.do", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有用户")
    @ResponseBody
    public MyResult findalluser() {
        return userService.findAllUser();
    }

    @RequestMapping(value = "/findUserByNameOrPhone.do", method = RequestMethod.GET)
    @ApiOperation(value = "用户名和电话查询")
    @ResponseBody
    public MyResult findalluserBynameandphone(@RequestParam(value = "username", required = true) String username, @RequestParam(value = "phone", required = true) Long phone) {
        System.out.println(username + phone + "...进行条件查询");
        return userService.findUserNameorPhone(username, phone);
    }

    @RequestMapping(value = "/finduserByName.do", method = RequestMethod.GET)
    @ApiOperation(value = "查询用户，按用户名查询")
    @ResponseBody
    public List<User> finduserByName(@RequestParam(value = "username", required = true) String username) {
        return userService.finduserByName(username);
    }

    @RequestMapping(value = "/deleteUser.do", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除用户")
    @ResponseBody
    public MyResult deleteUser(@RequestBody User user) {
        return userService.deleteUser(user);
    }

    @RequestMapping(value = "/backuserlogin.do", method = RequestMethod.POST)
    @ApiOperation(value = "后台用户登录")
    @ResponseBody
    public MyResult backuserlogin(@RequestBody Userbackform userbackform) {
        return userService.backuserlogin(userbackform);
    }

    @RequestMapping(value = "/backuserregist.do", method = RequestMethod.POST)
    @ApiOperation(value = "后台注册")
    @ResponseBody
    public MyResult backuserregist(@RequestBody Userbackform userbackform) {
        return userService.backuserregist(userbackform);
    }

    @RequestMapping(value = "/backdelete.do", method = RequestMethod.DELETE)
    @ApiOperation(value = "后台删除用户")
    @ResponseBody
    public MyResult backdelete(@RequestBody Userbackform userbackform) {
        return userService.backuserdelete(userbackform);
    }

    @RequestMapping(value = "/backupdateuser.do", method = RequestMethod.PUT)
    @ApiOperation(value = "后台更新用户")
    @ResponseBody
    public MyResult backupdateuser(@RequestBody Userbackform userbackform) {
        return userService.backupdateuser(userbackform);
    }

    @RequestMapping(value = "/addusermessage.do", method = RequestMethod.POST)
    @ApiOperation(value = "用户反馈信息")
    @ResponseBody
    public MyResult addusermessage(@RequestBody Backmessage backmessage) {
        return userService.addusermessage(backmessage);
    }

    @RequestMapping(value = "/updateusermessage.do", method = RequestMethod.PUT)
    @ApiOperation(value = "更新用户反馈")
    @ResponseBody
    public MyResult updateusermessage(@RequestBody Backmessage backmessage) {
        return userService.updateusermessage(backmessage);
    }

    @RequestMapping(value = "/updatebackmessage.do", method = RequestMethod.PUT)
    @ApiOperation(value = "后台回应反馈")
    @ResponseBody
    public MyResult updatebackmessage(@RequestBody Backmessage backmessage) {
        return userService.updatebackmessage(backmessage);
    }

    @RequestMapping(value = "/addsoftlink.do", method = RequestMethod.POST)
    @ApiOperation(value = "添加版本更新链接")
    @ResponseBody
    public MyResult addsoftlink(@RequestBody SoftLink softLink) {
        return userService.addsoftlink(softLink);
    }

    @RequestMapping(value = "/updatesoftlink.do", method = RequestMethod.PUT)
    @ApiOperation(value = "更新版本链接")
    @ResponseBody
    public MyResult c(@RequestBody SoftLink softLink) {
        return userService.updatesoftlink(softLink);
    }

    @RequestMapping(value = "/deletesoftlink.do", method = RequestMethod.PUT)
    @ApiOperation(value = "删除版本链接")
    @ResponseBody
    public MyResult deletesoftlink(@RequestBody SoftLink softLink) {
        return userService.deletesoftlink(softLink);
    }
}
