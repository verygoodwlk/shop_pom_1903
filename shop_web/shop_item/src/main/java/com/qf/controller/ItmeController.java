package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/12 11:13
 */
@Controller
@RequestMapping("/item")
public class ItmeController {

    @Reference
    private IGoodsService goodsService;

    @Autowired
    private Configuration configuration;

    /**
     * 生成商品静态页
     */
    @RequestMapping("/createhtml")
    @ResponseBody
    public void createHtml(Integer gid, HttpServletRequest request) throws IOException {

        //根据商品id，查询商品的详细信息
        Goods goods = goodsService.queryById(gid);

        //根据freemarker生成商品的静态页面
        Template template = configuration.getTemplate("goodsitem.ftl");

        Map<String, Object> map = new HashMap<>();
        map.put("goods", goods);
        map.put("images", goods.getGimage().split("\\|"));
        map.put("contextPath", request.getContextPath());

        //获得classpath路径
        String path = ItmeController.class.getResource("/static").getPath().replace("20%"," ");
        System.out.println("获得的classpath路径为：" + path);
        //如果路径不存在，则创建
        File file = new File(path + "/page");
        if(!file.exists()){
            file.mkdirs();
        }


        try (
                Writer writer = new FileWriter(file.getAbsolutePath() + "/" + gid + ".html")
        ){
            template.process(map, writer);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
