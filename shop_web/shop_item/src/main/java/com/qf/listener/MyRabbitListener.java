package com.qf.listener;

import com.qf.controller.ItmeController;
import com.qf.entity.Goods;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/12 17:09
 */
@Component
public class MyRabbitListener {

    @Autowired
    private Configuration configuration;

    @RabbitListener(queues = "item_queue")
    public void msgHandler(Goods goods) throws IOException {
        //根据freemarker生成商品的静态页面
        Template template = configuration.getTemplate("goodsitem.ftl");

        Map<String, Object> map = new HashMap<>();
        map.put("goods", goods);
        map.put("images", goods.getGimage().split("\\|"));
        map.put("contextPath", "");

        //获得classpath路径
        String path = ItmeController.class.getResource("/static").getPath().replace("20%"," ");
        System.out.println("获得的classpath路径为：" + path);
        //如果路径不存在，则创建
        File file = new File(path + "/page");
        if(!file.exists()){
            file.mkdirs();
        }


        try (
                Writer writer = new FileWriter(file.getAbsolutePath() + "/" + goods.getId() + ".html")
        ){
            template.process(map, writer);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
