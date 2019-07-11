package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.entity.Goods;
import com.qf.service.ISearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/11 10:55
 */
@Service
public class SearchService implements ISearchService {

    @Autowired
    private SolrClient solrClient;

    //limit ？，？
    @Override
    public List<Goods> searchByKey(String keyword) {

        SolrQuery solrQuery = null;
        if(keyword == null || keyword.trim().equals("")){
            solrQuery = new SolrQuery("*:*");
        } else {
            String str = "gname:%s || ginfo:%s";
            String s = String.format(str, keyword, keyword);
            solrQuery = new SolrQuery(s);
        }

        //设置查询的高亮
        solrQuery.setHighlight(true);//开启高亮
        solrQuery.setHighlightSimplePre("<font color='red'>");//设置关键字的前缀
        solrQuery.setHighlightSimplePost("</font>");//设置关键字的后缀
        solrQuery.addHighlightField("gname");//设置高亮的字段

        //设置高亮的折叠
//        solrQuery.setHighlightSnippets(3);//折叠几次高亮内容
//        solrQuery.setHighlightFragsize(10);//每次折叠的内容长度

        //设置分页
//        solrQuery.setStart();//?,x
//        solrQuery.setRows();//x,?

        //处理排序
//        solrQuery.setSort();


        List<Goods> goods = new ArrayList<>();
        try {
            //查询获得返回的响应对象
            QueryResponse queryResponse = solrClient.query(solrQuery);

            //通过响应对象获得查询结果 - 无高亮
            SolrDocumentList results = queryResponse.getResults();

            //获得高亮的结果
            //Map<有高亮的商品id号, Map<有高亮的字段, List<多个高亮的内容>>>
            Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();


            //遍历所有结果
            for (SolrDocument document : results) {
                Goods good = new Goods();

                int id = Integer.parseInt(document.getFieldValue("id") + "");
                String gname = document.getFieldValue("gname") + "";
                String gimage = document.getFieldValue("gimage") + "";
                BigDecimal gprice = new BigDecimal(document.getFieldValue("gprice") + "");
                int gsave = (int)document.getFieldValue("gsave");

                good.setId(id);
                good.setGname(gname);
                good.setGprice(gprice);
                good.setGimage(gimage);
                good.setGsave(gsave);

                //处理结果的高亮
                if(highlighting.containsKey(id + "")){
                    //说明当前id对应的商品是有高亮的结果的
                    Map<String, List<String>> stringListMap = highlighting.get(id + "");

                    if(stringListMap.containsKey("gname")) {
                        //从高亮结果中获得gname字段的结果
                        String highlightGname = stringListMap.get("gname").get(0);
                        good.setGname(highlightGname);
                    }
                }

                goods.add(good);
            }

        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return goods;
    }

    @Override
    public int addGoods(Goods goods) {
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id", goods.getId());
        document.setField("gname", goods.getGname());
        document.setField("ginfo", goods.getGinfo());
        document.setField("gimage", goods.getGimage());
        document.setField("gprice", goods.getGprice().floatValue());
        document.setField("gsave", goods.getGsave());

        try {
            solrClient.add(document);
            solrClient.commit();
            return 1;
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
