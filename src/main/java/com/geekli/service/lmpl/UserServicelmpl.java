package com.geekli.service.lmpl;


import com.alibaba.fastjson.JSONObject;
import com.alipay.api.CertAlipayRequest;
import com.geekli.dao.BackServiceOperation;
import com.geekli.dao.UserBehavior;
import com.geekli.model.*;
import com.geekli.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;


@Service(value = "userService")
public class UserServicelmpl implements UserService {
    @Autowired
    private UserBehavior userBehavior;

    @Autowired
    private BackServiceOperation backServiceOperation;

    @Override
    public MyResult deleteUser(User user) {
        MyResult result = new MyResult();
        result.setCode(201);
        result.setObj(null);
        User existUser = userBehavior.findUserBynameandPhone(user.getUsername(), user.getPhone());
        if (null != existUser) {
            userBehavior.deleteUser(user);
            result.setMsg("删除成功");
            result.setCode(200);
            result.setObj(user);
        } else {
            result.setMsg("该用户不存在");
        }
        return result;
    }

    public Object getSession(String code) {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
        String testStr = (String) request.getSession(true).getAttribute(code);
        return testStr;
    }

    @Override
    public MyResult regist(User user) {//这里需要插上短信验证
        MyResult result = new MyResult();
        result.setCode(201);
        result.setObj(null);
        try {
//            JSONObject userCode = (JSONObject)session.getAttribute("code");
//            JSONObject userCode = (JSONObject) getSession("code");
//            userCode.get("code");//获取的验证码
//            userCode.get("memphone");//进行验证的电话号码
            User existUser = userBehavior.findUserByname(user.getUsername());
            User existPhone = userBehavior.findUserByphone(user.getPhone());
            if (null != existUser) {
                result.setMsg("该用户名已被注册");
            } else if (null != existPhone) {
                result.setMsg("该电话已被注册,去登录处找回密码");
            } else {
                userBehavior.regist(user);
                result.setMsg("注册成功");
                result.setCode(200);
                result.setObj(user);
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public MyResult updateUser(User user) {
        MyResult result = new MyResult();
        userBehavior.updateUser(user);
        result.setCode(200);
        result.setMsg("更新成功");
        result.setObj(user);
        return result;
    }

    @Override
    public MyResult updateUserPassword(User user) {//这里需要插上短信验证
        MyResult result = new MyResult();
        User existPhone = userBehavior.findUserByphone(user.getPhone());
        if (null == existPhone) {
            result.setMsg("该号码还没有被注册，无法执行找回密码操作");
        } else {
            userBehavior.updtaeuserpassword(user);
            result.setMsg("修改密码成功");
        }
        return result;
    }

    @Override
    public MyResult login(User user) {
        User u = null;
        List<User> list = new ArrayList<>();
        u = userBehavior.login(user);
        MyResult result = new MyResult();
        if (null != u) {
            result.setCode(200);
            result.setMsg("登录成功");
            list.add(u);
            result.setObj(list);
        } else {
            result.setCode(201);
            result.setMsg("登录失败");
        }
        return result;
    }

    @Override
    public List<User> finduserByName(String username) {
        return userBehavior.selectuserByName(username);
    }

    @Override
    public MyResult findAllUser() {
//        List<MyResult> list = new ArrayList<>();
        MyResult result = new MyResult();
//        List u = null;
//        u = userBehavior.findALLUser();
        result.setObj(userBehavior.findALLUser());
        result.setCode(200);
        result.setMsg("查询成功");
//        list.add(result);
        return result;
    }

    @Override
    public MyResult findUserNameorPhone(String username, Long phone) {
        MyResult result = new MyResult();
        System.out.println(username + phone);
        result.setCode(200);
        result.setMsg("查询成功");
        if (null == username || username.equals("")) {
            result.setObj(userBehavior.selectuserByPhone(phone));
        } else if (null == phone) {
            result.setObj(userBehavior.selectuserByName(username));
        } else {
            result.setObj(userBehavior.selectuserByNameorPhone(username, phone));
        }
        return result;
    }

    @Override
    public MyResult selectUserProduct(User user) {
        MyResult result = new MyResult();
        User existUser = userBehavior.findUserByname(user.getUsername());
        if (null != existUser) {
            result.setMsg("查找成功");
            result.setCode(200);
            result.setObj(userBehavior.selectUserProduct(user));
        } else {
            result.setMsg("该用户不存在");
        }
        return result;
    }

    @Override
    public MyResult buypro(UserscKey userscKey) {
        MyResult result = new MyResult();
        result.setCode(201);
        result.setObj(null);
        try {
            UserscKey existuid = userBehavior.finduid(userscKey.getUid(), userscKey.getCid());
            if (null != existuid) {
                result.setMsg("已经购买");
            } else {
                userBehavior.buypro(userscKey);
                result.setMsg("购买成功");
                result.setCode(200);
                result.setObj(userscKey);
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public MyResult buyvip(User user) {
        MyResult result = new MyResult();
        userBehavior.buyvip(user);
        result.setCode(200);
        result.setObj(user);
        return result;
    }

    @Override
    public MyResult backuserlogin(Userbackform userbackform) {
        Userbackform u = null;
        MyResult result = new MyResult();
        List<Userbackform> list = new ArrayList<>();
        u = backServiceOperation.backuserlogin(userbackform);
        if (null != u) {
            result.setCode(200);
            result.setMsg("登录成功");
            list.add(u);
            result.setObj(list);
        } else {
            result.setCode(201);
            result.setMsg("登录失败");
        }
        return result;
    }

    @Override
    public MyResult backuserregist(Userbackform userbackform) {
        MyResult result = new MyResult();
        result.setCode(201);
        result.setObj(null);
        try {
            Userbackform existBackUser = backServiceOperation.findback(userbackform.getUsername(), userbackform.getPassword());
            if (null != existBackUser) {
                result.setMsg("用户已存在");
            } else {
                backServiceOperation.backuserregist(userbackform);
                result.setMsg("注册成功");
                result.setObj(userbackform);
                result.setCode(200);
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public MyResult backuserdelete(Userbackform userbackform) {
        MyResult result = new MyResult();
        result.setCode(201);
        result.setObj(null);
        Userbackform existBackUser = backServiceOperation.findback(userbackform.getUsername(), userbackform.getPassword());
        if (null != existBackUser) {
            backServiceOperation.backuserdelete(userbackform);
            result.setMsg("删除成功");
            result.setCode(200);
            result.setObj(userbackform);
        } else {
            result.setMsg("该用户不存在");
        }
        return result;
    }

    @Override
    public MyResult backupdateuser(Userbackform userbackform) {
        MyResult result = new MyResult();
        backServiceOperation.backuserupdate(userbackform);
        result.setCode(0);
        result.setMsg("更新成功");
        result.setObj(userbackform);
        return result;
    }

    @Override
    public MyResult addusermessage(Backmessage backmessage) {
        MyResult result = new MyResult();
        result.setCode(201);
        result.setObj(null);
        Backmessage existmessage = backServiceOperation.findusermessage(backmessage.getId(), backmessage.getUsername());
        if (null != existmessage) {
            result.setMsg("该用户已有留言记录,无法新增");
        } else {
            backServiceOperation.addusermessage(backmessage);
            result.setMsg("反馈成功");
            result.setCode(200);
            result.setObj(backmessage);
        }
        return result;
    }

    @Override
    public MyResult updateusermessage(Backmessage backmessage) {
        MyResult result = new MyResult();
        backServiceOperation.updateusermessage(backmessage);
        result.setCode(200);
        result.setMsg("更新反馈成功");
        result.setObj(backmessage);
        return result;
    }

    @Override
    public MyResult updatebackmessage(Backmessage backmessage) {
        MyResult result = new MyResult();
        backServiceOperation.updatebackmessage(backmessage);
        result.setCode(200);
        result.setMsg("回应成功");
        result.setObj(backmessage);
        return result;
    }

    @Override
    public MyResult addsoftlink(SoftLink softLink) {
        MyResult result = new MyResult();
        result.setCode(201);
        result.setObj(null);
        SoftLink existsoftlink = backServiceOperation.findsoftname(softLink.getSoftname());
        if (null != existsoftlink) {
            result.setMsg("已存在该app,无法新增");
        } else {
            backServiceOperation.addsoftlink(softLink);
            result.setMsg("新增成功");
            result.setCode(200);
            result.setObj(softLink);
        }
        return result;
    }

    @Override
    public MyResult updatesoftlink(SoftLink softLink) {
        MyResult result = new MyResult();
        backServiceOperation.updatesoftlink(softLink);
        result.setCode(200);
        result.setMsg("更新成功");
        result.setObj(softLink);
        return result;
    }

    @Override
    public MyResult deletesoftlink(SoftLink softLink) {
        MyResult result = new MyResult();
        result.setCode(201);
        result.setObj(null);
        SoftLink existsoftlink = backServiceOperation.findsoftname(softLink.getSoftname());
        if (null != existsoftlink) {
            backServiceOperation.deletesoftlink(softLink);
            result.setMsg("删除成功");
            result.setCode(200);
            result.setObj(softLink);
        } else {
            result.setMsg("删除失败");
        }
        return result;
    }
}
