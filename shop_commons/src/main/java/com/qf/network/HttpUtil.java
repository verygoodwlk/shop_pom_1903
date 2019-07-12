package com.qf.network;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Http请求的工具方法
 *
 * @version 1.0
 * @user ken
 * @date 2019/7/12 11:47
 */
public class HttpUtil {

    /**
     * 模拟浏览器发送一个Get请求到指定的url服务器
     * @param urlStr
     * @return
     */
    public static String sendGet(String urlStr){

        try (
                ByteArrayOutputStream out = new ByteArrayOutputStream();
        ) {
            URL url = new URL(urlStr);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(10000);

            //发送请求到指定的服务器
            conn.connect();

            //获取服务器返回的结果
            InputStream in = conn.getInputStream();
            byte[] bytes = new byte[1024 * 10];
            int len;
            while((len = in.read(bytes)) != -1){
                out.write(bytes, 0, len);
            }

            byte[] buffer = out.toByteArray();

            return new String(buffer);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        sendGet("http://localhost:8083/item/createhtml?gid=13");
    }
}
