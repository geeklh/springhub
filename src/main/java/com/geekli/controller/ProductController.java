package com.geekli.controller;

import com.geekli.model.MyResult;
import com.geekli.model.Userpro;
import com.geekli.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Api(value = "商品信息配置", tags = "商品信息配置接口")
@CrossOrigin(maxAge = 3600)
@RequestMapping(value = "/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/addpro.do", method = RequestMethod.POST)
    @ApiOperation(value = "添加商品")
    @ResponseBody
    public MyResult addpro(@RequestBody Userpro userpro) {
        return productService.addpro(userpro);
    }

    @RequestMapping(value = "/deletepro.do", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除商品")
    @ResponseBody
    public MyResult deletepro(@RequestParam(value = "sname", required = true) String sname, @RequestParam(value = "id", required = true) String id) {
        return productService.deletepro(sname,id);
    }

    @RequestMapping(value = "/deleteProvider.do",method = RequestMethod.DELETE)
    @ApiOperation(value = "批量删除")
    @ResponseBody
    public MyResult deleteProvider(@RequestBody List<Userpro> list){
        return productService.deleteProvider(list);
    }

    @RequestMapping(value = "/updatepro.do", method = RequestMethod.PUT)
    @ApiOperation(value = "修改商品")
    @ResponseBody
    public MyResult updatepro(@RequestBody Userpro userpro) {
        return productService.updatepro(userpro);
    }

    @RequestMapping(value = "/findbyname.do", method = RequestMethod.GET)
    @ApiOperation(value = "查询商品")
    @ResponseBody
    public MyResult findByName(@RequestParam(value = "sname", required = true) String sname, @RequestParam(value = "id", required = true) String id) {
        return productService.findByName(sname, id);
    }

    @RequestMapping(value = "/findAllPro.do", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有")
    @ResponseBody
    public MyResult findAllPro() {
        return productService.findAll();
    }
}
