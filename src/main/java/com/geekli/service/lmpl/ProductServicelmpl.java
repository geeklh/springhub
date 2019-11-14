package com.geekli.service.lmpl;

import com.geekli.dao.ProductOperation;
import com.geekli.model.MyResult;
import com.geekli.model.Userpro;
import com.geekli.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service(value = "productService")

public class ProductServicelmpl implements ProductService {
    @Autowired
    private ProductOperation productOperation;

    @Override
    public MyResult addpro(Userpro userpro) {
        MyResult result = new MyResult();
        result.setCode(201);
        result.setObj(null);
        Long timeNum = new Date().getTime();
        String s = String.valueOf(timeNum);
        userpro.setId("s" + s);
        Userpro existpro = productOperation.findproductname(userpro.getId(), userpro.getSname());
        if (null != existpro) {
            result.setMsg("商品已存在");
        } else {
            productOperation.addpro(userpro);
            result.setMsg("添加商品成功");
            result.setCode(200);
            result.setObj(userpro);
        }
        return result;
    }

    @Override
    public MyResult deletepro(String sname, String id) {
        MyResult result = new MyResult();
        result.setCode(201);
        result.setObj(null);
        Userpro existpro = productOperation.findproductname(id, sname);
        if (null != existpro) {
            productOperation.deletepro(sname, id);
            result.setMsg("删除成功");
            result.setCode(200);
        } else {
            result.setMsg("删除失败,该商品不存在");
        }
        return result;
    }

    @Override
    public MyResult deleteProvider(List<Userpro> list) {
        MyResult result = new MyResult();
        result.setCode(200);
        result.setObj(productOperation.batchDelete(list));
        result.setMsg("批量删除成功");
        return result;
    }

    @Override
    public MyResult updatepro(Userpro userpro) {
        MyResult result = new MyResult();
        productOperation.updatepro(userpro);
        result.setCode(200);
        result.setMsg("更新成功");
        result.setObj(userpro);
        return result;
    }

    @Override
    public MyResult findByName(String sname, String id) {
        MyResult result = new MyResult();
        result.setObj(null);
        result.setMsg("查询成功");
        if (null == id || id.equals("")) {
            result.setCode(200);
            result.setObj(productOperation.findByName(sname));
        } else if (null == sname || sname.equals("")) {
            result.setCode(200);
            result.setObj(productOperation.findById(id));
        } else {
            Userpro existpro = productOperation.findproductname(id, sname);
            if (null != existpro) {
                result.setCode(200);
                result.setObj(productOperation.findByName(sname));
            } else {
                result.setCode(201);
                result.setMsg("查询失败，商品不存在");
            }
        }
        return result;
    }

    @Override
    public MyResult findAll() {
        MyResult result = new MyResult();
        result.setObj(productOperation.findAll());
        result.setCode(200);
        result.setMsg("返回所有商品");
        return result;
    }
}
