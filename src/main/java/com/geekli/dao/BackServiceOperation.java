package com.geekli.dao;

import com.geekli.model.Backmessage;
import com.geekli.model.SoftLink;
import com.geekli.model.Userbackform;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BackServiceOperation {
    //后端查询用户是否存在
    @Select(value = "select username,password from backuserfotm where username = #{username} and password = #{password}")
    @Results({@Result(property = "username", column = "username"), @Result(property = "password", column = "password")})
    Userbackform findback(@Param("username") String username, @Param("password") String password);

    //检查添加的app链接是否存在
    @Select(value = "select softname from softlink where softname = #{softname}")
    @Results({@Result(property = "softname", column = "softname")})
    SoftLink findsoftname(@Param("softname") String softname);

    //检查该用户是否有留言
    @Select(value = "select id,username from backmessage where id = #{id} and username = #{username}")
    @Results({@Result(property = "id", column = "id"), @Result(property = "username", column = "username")})
    Backmessage findusermessage(@Param("id") Long id, @Param("username") String username);

    //管理后台用户登录
    @Select("select username,password from backuserfotm u where u.username = #{username} and password = #{password} ")
    Userbackform backuserlogin(Userbackform userbackform);

    //管理后台用户注册
    @Insert("insert into backuserfotm values(#{username},#{function},#{password})")
    @Options(useGeneratedKeys = true, keyProperty = "username", keyColumn = "username")
    void backuserregist(Userbackform userbackform);

    //删除后台用户
    @Delete("delete from backuserfotm where username = #{username}")
    @Options(useGeneratedKeys = true, keyProperty = "username", keyColumn = "username")
    void backuserdelete(Userbackform userbackform);

    //更新后台用户数据
    @Update("update `backuserfotm` set `username` = #{username},`password` = #{password} where (`username` = #{username})")
    void backuserupdate(Userbackform userbackform);

    //用户反馈信息
    @Insert("insert into backmessage(id,username,usermessage) values(#{id},#{username},#{usermessage})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addusermessage(Backmessage backmessage);

    //更新用户反馈信息
    @Update("update `backmessage` set `usermessage` = #{usermessage} where (`id` = #{id} and `username` = #{username})")
    void updateusermessage(Backmessage backmessage);

    //后台回应用户反馈
    @Update("update `backmessage` set `backmessage` = #{backmessage} where (`id` = #{id} and `username` = #{username})")
    void updatebackmessage(Backmessage backmessage);

    //添加app链接
    @Insert("insert into softlink values(#{editionnum},#{softdis},#{link},#{softname})")
    @Options(useGeneratedKeys = true, keyProperty = "softname", keyColumn = "softname")
    void addsoftlink(SoftLink softLink);

    //更新app链接
    @Update("update `softlink` set `editionnum` = #{editionnum},`softdis` = #{softdis},`link` = #{link} where (`softname` = #{softname}) ")
    void updatesoftlink(SoftLink softLink);

    //删除app链接
    @Delete("delete from softlink where softname = #{softname}")
    @Options(useGeneratedKeys = true, keyProperty = "softname", keyColumn = "softname")
    void deletesoftlink(SoftLink softLink);
}
