package com.geekli.dao;

import com.geekli.model.Userpro;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface ProductOperation {
    @Select(value = "select id,sname from productform where id = #{id} and sname = #{sname}")
    @Results({@Result(property = "id", column = "id"), @Result(property = "sname", column = "sname")})
    Userpro findproductname(@Param("id") String id, @Param("sname") String sname);

    @Insert("insert into productform values(#{id},#{sname},#{describe},#{classification},#{dayprice},#{monthprice},#{yearprice},#{istry},#{vipdis},#{trytime})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addpro(Userpro userpro);

    //删除
    @Delete("delete from productform where id = #{id} and sname = #{sname}")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void deletepro(@Param("sname") String sname, @Param("id") String id);

    //批量删除
    @DeleteProvider(type =Provider.class,method = "batchDelete")
    int batchDelete(List<Userpro> userpros);

    @Update("update `productform` set `describe` = #{describe},`classification` = #{classification},`dayprice` = #{dayprice},`monthprice` = #{monthprice},`yearprice` = #{yearprice},`istry` = #{istry},`vipdis` = #{vipdis},`trytime` = #{trytime} where (`id` = #{id} and `sname` = #{sname})")
    void updatepro(Userpro userpro);

    //查询
    @Select("select * from productform where sname = #{sname}")
    List<Userpro> findByName(@Param("sname") String sname);

    @Select("select * from productform where id = #{id}")
    List<Userpro> findById(@Param("id") String id);

    @Select("select * from productform where sname = #{sname} and id = #{id}")
    List<Userpro> findByNameAndId(@Param("sname") String sname, @Param("id") String id);

    @Select("select * from productform")
    List<Userpro> findAll();

    class Provider {
        public String batchDelete(Map map) {
            List<Userpro> list = (List<Userpro>) map.get("list");
            StringBuilder sb = new StringBuilder();
            sb.append("delete from productform where id in (");
            for (int i = 0; i < list.size(); i++) {
                sb.append("'").append(list.get(i).getId()).append("'");
                if (i < list.size() - 1) {
                    sb.append(",");
                }
            }
            sb.append(")");
            return sb.toString();
        }
    }
}
