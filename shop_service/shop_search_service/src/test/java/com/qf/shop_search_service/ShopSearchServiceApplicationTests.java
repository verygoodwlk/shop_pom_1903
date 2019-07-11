package com.qf.shop_search_service;

import com.qf.entity.Goods;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopSearchServiceApplicationTests {

    @Autowired
    private SolrClient solrClient;

    /**
     * 添加索引库
     */
    @Test
    public void add() throws IOException, SolrServerException {
        //创建一个solr文档 - 搜索的一个条目 -> 数据库的一条记录
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", 1);
        document.addField("gname", "滚筒洗衣机洗衣机洗衣机洗衣机洗衣机洗衣机洗衣机");
        document.addField("ginfo", "非常省水的洗衣机");
        document.addField("gimage", "http://www.baidu.com");
        document.addField("gprice", 99.99);
        document.addField("gsave", 100);

        solrClient.add(document);
        solrClient.commit();
    }

    @Test
    public void update(){

    }

    @Test
    public void delete() throws IOException, SolrServerException {
//        solrClient.deleteById("55764593-dfe3-4691-8507-b46e0e87d22c");
        solrClient.deleteByQuery("*:*");
        solrClient.commit();
    }

    public void query() throws IOException, SolrServerException {

        SolrQuery query = new SolrQuery("*:*");

        QueryResponse response = solrClient.query(query);

        SolrDocumentList results = response.getResults();

        for (SolrDocument result : results) {
            Goods goods = new Goods();
            goods.setId(Integer.parseInt(result.getFieldValue("id") + ""));
            result.getFieldValue("gname");
            result.getFieldValue("gprice");
            result.getFieldValue("gsave");
            result.getFieldValue("gimage");
        }
    }

}
