package com.qf.shop_item;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopItemApplicationTests {

    @Autowired
    private Configuration configuration;

    @Test
    public void contextLoads() throws IOException, TemplateException {

        //通过configuration对象获取ftl模板
        Template template = configuration.getTemplate("hello.ftl");

        //准备模板中数据
        Map<String, Object> map = new HashMap<>();
        map.put("key", "World!");

        map.put("age", 28);

        int[] arrays = {1,2,3,4,5,6,7};
        map.put("ages", arrays);

        map.put("date", new Date());

        //将数据和模板整合成为一个静态页
        template.process(map, new FileWriter("C:\\Users\\ken\\Desktop\\hello.html"));
    }

}
