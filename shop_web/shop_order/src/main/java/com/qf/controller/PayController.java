package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.qf.entity.Order;
import com.qf.service.IOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/24 14:39
 */
@Controller
@RequestMapping("/pay")
public class PayController {

    @Reference
    private IOrderService orderService;

    /**
     * 调用支付宝进行支付
     * @return
     */
    @RequestMapping("/alipay")
    public void aliPay(String orderid, HttpServletResponse response){

        //通过订单号，查询订单详情的总金额
        Order order = orderService.queryByOid(orderid);


        //创建一个阿里支付的客户端，所有的支付、退款、查询等等相关的请求都是由这个对象发送的
        AlipayClient alipayClient = new DefaultAlipayClient(
                "https://openapi.alipaydev.com/gateway.do",
                "2016073000127352",//APPID
                "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDq2OXWK7N4QiYMdrcTvSgRZXiqSIvY+ZnDB0Ab3fSqC7sgQTPGK4Txuf9iVPdgxGD/i26MDmUOTANa14ZXi5rqVGd4095vZYXyKfQB8SXFFmd3QtaqmeBlakphCyb4fEzPZZndIis0Hic6P9caXLg7DcQru/UZJxtvscsXQoRZFyqL8MSftHw+G60FgD0wHvml8WhPBkZcFLzQiuKKWA5RoB+ciuPPjkBkueb+oAdIGAMdYAaGnz+6ZTZxf8aUqot4PZEPYc0KmKHh37dzpq5tMX/EBPgQmGJxgFjHZTBNqMplO4kSw/pPrK2PY/JcRu0TAfqr1a144KIMhDEx0VWxAgMBAAECggEAZxz6tTGU6D1rBVfuhF0uRY0fEO0vcgINsCLz1Ah9T1g9PilCuUUMZzI6Xotd3n/68tjeneIIHuHjKzSsSK4EQ9TxZYzpAlI6IJypBV1DLyjqHuQa6ucLLpwfskI6RyoUc49xojVJcOW4ZYv4PxvszAj8VBN/xx5znnQa7siG4bJB48pQHDoqMXRDqMGoLB0S82oUYrQzXEfY2hv787DyhtxkQGkWzY1ySlRT92gbryq8+Fntaz84K6+VxfdP8Lj1GopkSGPutrU36qaV9dg9NgET9Nuz7byXiV6Eg3LHJcQK3io05kst9u5sGv3WNjn8DlIhi1cG0x7IlJrxyH/RrQKBgQD5c/syn5kwKGSRFcDdR4bb6s2gOf67Gw940xysysiVX8hYvbGg9SzvdNQOqZjnCsNFmosWgZr5a7x+9KUan6ikc4yiH908oYaHTe3guHSXnHSikqvevVzzi0odXIwFnAgwrK3z3WM14kkmKLVT6CqgSbU5Tij1E7gxsrluVFaF4wKBgQDxAsiUvqJO9jnhITW8XTxI5J2Rrmkl6tQOp7jVjVcHyBCrb2OOnGLmsh1AIZrRWdK3D2q9ovPbTv2Ktw4YTv9gmFEuQTan0hP5twmPhNgwONEPSAB5B+02Ndzbl59TGPEnVdgwcy6bHEZiHuDI/yCTot+qkZBEeJLoQ8SsJe2qWwKBgQCa64G8/ctIUj2qsSTXB/3kBF3xvTLLBtJECnBLi6CozBSDsENpn/ppOFgeRwuzqUZ+qqzorFMPxe4Jtmv3LeKxRyW25i0i43nF/ArYvhWCtuLkuExPB7F7eyzrMTewoCeh+wwJYJulskQVk8CRPKWEaGJJFZZwJfAFxWZN88jMWwKBgQCbUXroeQ5qqr3UOy3BLajbJRHyv56dw/9TTn2MimDK8ADmKovTbS9DkwfMu5tlTWjBdpsIiUp0TCyTR7M6sTZdDuxKTdamQnmDFZWAu1EDiqoYGgPL+/LAW+fwUH9p8wn+mpEPcDtZ1Nf7BgFBJ0HcQzu3VcXOipHKQ1oswuiuPwKBgQDV7G59mGDAEXCQVdN5gxCiQf4yVGHKBKTmEvbWZevo+Sku4bIsI7eHaSBDMscMTVQwelmootEZKxNGYInueAKDK0Q2ZKNNAY4ubjprHEXp+mQM9z+u657bE7X2NclR+hMo1SWcjh1P97jYIn5X0IhkgFufhVSXvFVZ6Cf7/LBP3w==",//开发者私钥
                "json",
                "UTF-8",
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAx+n+pEqTVi27mtj3CuUQXi2ixqeeTwE/0tGrW+sg6xtfajvJV67GYf2zzNxxBV0TYfhdbi70VI3DftEijg7GSNKoOilAu2DKQFqidnSxmN1Es1oRTaiaehqm1Uzs2uswpzBVR21iygLHujwthC8kNkMgxVFkjbE/qTn7z5wlsailtg6wF+hY3BcDCiaLyVLjEDngmrLyLXPLenjAuvXq20h9zV7CW9HXuhpPBDfsn4fv5TjgEl1smjJNr4O/VxICKDNPsvrCyNXhfroK9PEFFwH+4IWGeBUJAP2cSufNU0OA+UH+2xQnaR8Cz30QIgIslckBGuXQZvxqaY2mMMz14QIDAQAB",//阿里的公钥
                "RSA2"); //获得初始化的AlipayClient

        //申请支付页面的请求对象
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request

        //设置同步返回的url - 用户支付完成后跳转到哪里页面 GET
        alipayRequest.setReturnUrl("http://localhost:8081");

        //设置异步返回的url - 用户支付结果的通知url POST
        alipayRequest.setNotifyUrl("http://verygoodwlk.xicp.net/pay/paycompent");//在公共参数中设置回跳和通知地址

        //支付请求体
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\"" + orderid + "\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":" + order.getAllprice().doubleValue() + "," +
                "    \"subject\":\"" + orderid + "\"," +
                "    \"body\":\"" + orderid + "\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"2088511833207846\"" +
                "    }"+
                "  }");//填充业务参数

        //生成支付的表单页面 - 一串HTML文本
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=UTF-8");
        try {
            //将支付的页面写入用户的浏览器中
            response.getWriter().write(form);//直接将完整的表单html输出到页面
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 支付完成后的回调方法 --- 一定一定要验证下请求是否来自支付宝
     */
    @RequestMapping("/paycompent")
    @ResponseBody
    public void payCompent(String out_trade_no, String trade_status){
        //获得支付的结果
        if(trade_status.equals("TRADE_SUCCESS")){
            //交易成功
            //修改订单对应的状态
            orderService.updateOrderStatus(out_trade_no, 1);
        }
    }
}
