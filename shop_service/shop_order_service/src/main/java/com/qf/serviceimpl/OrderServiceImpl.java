package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.OrderDetilsMapper;
import com.qf.dao.OrderMapper;
import com.qf.dataconfig.DynamicDataSource;
import com.qf.entity.*;
import com.qf.service.IAddressService;
import com.qf.service.ICartService;
import com.qf.service.IOrderService;
import com.qf.util.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/23 14:21
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetilsMapper orderDetilsMapper;

    @Reference
    private IAddressService addressService;

    @Reference
    private ICartService cartService;

    @Autowired
    private OrderUtil orderUtil;

    /**
     * 添加订单
     * @param aid
     * @param user
     * @return
     */
    @Override
//    @Transactional
    public int insertOrder(Integer aid, User user) {

        //确定数据源
        //获得用户id
        //确定库，使用用户id的后4位
        int uids = Integer.parseInt(orderUtil.getUid(user.getId()));
        int dbIndex = uids % 2 + 1; //orderdb{dbIndex}
        System.out.println("定位到库的id：" + dbIndex);
        DynamicDataSource.set("orderdb"+dbIndex);

        //确定表，用户id后4位 / 2 % 2
        int tableIndex = uids / 2 % 2 + 1;//2
        System.out.println("定位到表的id: " + tableIndex);

        //通过收货地址查询地址详情
        Address address = addressService.queryByAid(aid);

        //通过用户信息查询所有的购物车
        List<ShopCart> shopCarts = cartService.queryCartList(user, null);

        //通过所有购物车计算总价
        BigDecimal bigDecimal = BigDecimal.valueOf(0.0);
        for (ShopCart shopCart : shopCarts) {
            bigDecimal = bigDecimal.add(shopCart.getSprice());
        }

        //手动创建订单对象
        Order order = new Order(
                orderUtil.createOrderId(user.getId()),
                address.getPerson(),
                address.getAddress(),
                address.getPhone(),
                bigDecimal,
                new Date(),
                0,
                null
        );

        System.out.println("生成的订单号：" + order.getOrderid());

        //根据购物车列表生成订单详情
        int i = 0;
        List<OrderDetils> orderDetilList = new ArrayList<>();
        for (ShopCart shopCart : shopCarts) {
            OrderDetils orderDetils = new OrderDetils(
                    null,
                    order.getOrderid(),
                    shopCart.getGid(),
                    shopCart.getGoods().getGname(),
                    shopCart.getGoods().getGprice(),
                    shopCart.getGoods().getGimage(),
                    shopCart.getGnumber(),
                    shopCart.getSprice()
            );
            orderDetilList.add(orderDetils);
            i++;

            if(i % 1000 == 0 || i == shopCarts.size()){
                //批量插入
                orderDetilsMapper.insertDetils(orderDetilList, tableIndex);
                orderDetilList.clear();
            }
        }

        //插入到订单表和订单详情表中
        orderMapper.insertOrder(order, tableIndex);

        return 1;
    }

    @Override
    public List<Order> queryByUid(Integer uid) {
        return null;
    }

    @Override
    public Order queryByOid(String orderid) {
        return null;
    }
}
