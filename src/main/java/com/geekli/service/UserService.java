package com.geekli.service;

import com.geekli.model.*;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService {
    MyResult login(User user);

    MyResult regist(User user);

    MyResult deleteUser(User user);

    MyResult updateUser(User user);

    MyResult updateUserPassword(User user);

    List<User> finduserByName(String username);

    MyResult findAllUser();

    MyResult selectUserProduct(User user);

    MyResult findUserNameorPhone(String username, Long phone);

    MyResult buypro(UserscKey userscKey);

    MyResult buyvip(User user);

    MyResult backuserlogin(Userbackform userbackform);

    MyResult backuserregist(Userbackform userbackform);

    MyResult backuserdelete(Userbackform userbackform);

    MyResult backupdateuser(Userbackform userbackform);

    MyResult addusermessage(Backmessage backmessage);

    MyResult updateusermessage(Backmessage backmessage);

    MyResult updatebackmessage(Backmessage backmessage);

    MyResult addsoftlink(SoftLink softLink);

    MyResult updatesoftlink(SoftLink softLink);

    MyResult deletesoftlink(SoftLink softLink);
}
