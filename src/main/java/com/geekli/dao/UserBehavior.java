package com.geekli.dao;

import com.geekli.model.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserBehavior {
    //    查询用户名是否存在
    @Select(value = "select username from usermessage where username = #{username}")
    @Results({@Result(property = "username", column = "username")})
    User findUserByname(@Param("username") String username);

    //查询电话号码
    @Select(value = "select phone from usermessage where phone = #{phone}")
    @Results({@Result(property = "phone", column = "phone")})
    User findUserByphone(@Param("phone") Long phone);

    //查询用户名和电话
    @Select(value = "select username,phone from usermessage where username = #{username} and phone = #{phone}")
    @Results({@Result(property = "username", column = "username"), @Result(property = "phone", column = "phone")})
    User findUserBynameandPhone(@Param("username") String username, @Param("phone") Long phone);

    @Select(value = "select uid,cid from appsc where uid = #{uid} and cid = #{cid}")
    @Results({@Result(property = "uid", column = "uid"), @Result(property = "cid", column = "cid")})
    UserscKey finduid(@Param("uid") Long uid, @Param("cid") String cid);

    //    注册
    @Insert("insert into usermessage(username,password,phone) values(#{username},#{password},#{phone})")
    @Options(useGeneratedKeys = true, keyProperty = "username", keyColumn = "username")
    void regist(User user);

    //    登录
    @Select("select * from usermessage u where u.username = #{username} and password = #{password}")
    User login(User user);

    //    删除
    @Delete("delete from usermessage where id = #{id} and username = #{username}")
    @Options(useGeneratedKeys = true, keyProperty = "username", keyColumn = "username")
    void deleteUser(User user);

    //    更新电话、email、地址、身份证
    @Update("update `usermessage` set `phone` = #{phone},`email` = #{email},`personalid` = #{personalid},`address` = #{address} where (`username` = #{username})")
    void updateUser(User user);

    //修改密码
    @Update("update `usermessage` set `password` = #{password} where (`username` = #{username} and `phone` = #{phone})")
    void updtaeuserpassword(User user);

    //查询功能
    //按用户名查询
    @Select("select * from usermessage where username = #{username}")
    List<User> selectuserByName(@Param("username") String username);

    //按电话号码查询
    @Select("select * from usermessage where phone = #{phone}")
    List<User> selectuserByPhone(@Param("phone") Long phone);

    //    名字或者电话号码做判空处理
    @Select("select * from usermessage where username = #{username} and phone = #{phone}")
    List<User> selectuserByNameorPhone(@Param("username") String username, @Param("phone") Long phone);

    //查询当前用户拥有的机器的所有信息,暂定是使用用户名查找
    @Select("select productform.* from usermessage left join appsc on usermessage.id = appsc.uid left join productform on productform.id = appsc.cid where usermessage.username = #{username}")
    User selectUserProduct(User user);

    //获取所有用户信息
    @Select("select * from usermessage")
    List<User> findALLUser();

    //插入已购买商品列表
    @Select("insert into appsc values(#{uid},#{cid})")
    UserscKey buypro(UserscKey userscKey);

    //更新用户VIP信息
    @Update("update `usermessage` set `isvip` = #{isvip} where (`username` = #{username})")
    User buyvip(User user);

    //支付宝
    @Insert("insert into orderlist values (#{id},#{outtradeno},#{totalAmount},#{body},#{subjecy})")
    void insertalipayorder(OrderList orderList);

    @Insert("insert into alipaymentorder(outtradeno) values outtradeno = #{outtradeno}")
    void insertalipayment(String outtradeno);

    @Select("select * from alipaymentorder where outtradeno = #{outtradeno}")
    AliPaymentOrder selecttradeno(String outtradeno);

    @Update("update `alipaymentorder` set `Notifytime` = #{Notifytime},`Gmtcreate` = #{Gmtcreate},`Gmtpayment` = #{setGmtpayment},`Gmtrefund` = #{Gmtrefund},`Gmtclose` = #{setGmtclose},`Tradeno` = #{Tradeno},`Outtradeno` = #{Outtradeno},`Buyerlogonid` = #{Buyerlogonid},`Selleremail` = #{Selleremail},`Totalamount` = #{Totalamount},`Receiptamount` = #{Receiptamount},`Invoiceamount` = #{Invoiceamount},`Buyerpayamount` = #{Buyerpayamount} where (`outTradeNo` = #{outTradeNo})")
    int updateTrade(AliPaymentOrder aliPaymentOrder);

    //微信
    @Insert("insert into wxorderlist values (#{out_trade_no},#{spbill_create_ip},#{notify_url},#{total_fee},#{body},#{sign},#{productID})")
    void insertwxorder(String out_trade_no, String spbill_create_ip, String notify_url, String total_fee, String body, String sign, String productID);

    @Insert("insert into wxpaymentorder values(#{out_trade_no},#{mch_id},#{openid},#{is_subscribe},#{prepay_id},#{total_fee})")
    void insertwxpayment(String out_trade_no, String mch_id, String openid, String is_subscribe, String prepay_id, String total_fee);

    //银联
    @Insert("insert into unionproductlist values (#{productId},#{subject},#{body},#{totalFee},#{outTradeNo},#{spbillCreateIp},#{attach})")
    void insertunion(Product product);
}
