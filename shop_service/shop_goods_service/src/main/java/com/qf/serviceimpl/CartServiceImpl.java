package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.CartMapper;
import com.qf.entity.Goods;
import com.qf.entity.ShopCart;
import com.qf.entity.User;
import com.qf.service.ICartService;
import com.qf.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/22 10:32
 */
@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IGoodsService goodsService;

    /**
     * 添加购物车
     * @param shopCart
     * @return
     */
    @Override
    public int insertCart(ShopCart shopCart, User user, String cartToken) {

        //计算购物车小计
        Goods goods = goodsService.queryById(shopCart.getGid());
        //商品数量
        int gnumber = shopCart.getGnumber();

        //计算小计
        BigDecimal sprice = goods.getGprice().multiply(BigDecimal.valueOf(gnumber));
        shopCart.setSprice(sprice);

        //创建时间
        shopCart.setCreatetime(new Date());

        if(user != null){
            //说明已经登录
            shopCart.setUid(user.getId());

            //保存到数据库即可
            cartMapper.insert(shopCart);

        } else {
            //说明未登录，将购物车信息放入redis中
            redisTemplate.opsForList().leftPush(cartToken, shopCart);
        }

        return 1;
    }

    /**
     * 查询购物车列表
     * @param user
     * @param cartToken
     * @return
     */
    @Override
    public List<ShopCart> queryCartList(User user, String cartToken) {

        List<ShopCart> cartList = null;

        if(user != null){
            //去数据库中查询购物车的信息
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("uid", user.getId());
            cartList = cartMapper.selectList(queryWrapper);

        } else {
            //去redis中查询购物车的信息
            if(cartToken != null){
                //获得购物车的长度
                long len = redisTemplate.opsForList().size(cartToken);
                //获取指定链表的范围值
                cartList = redisTemplate.opsForList().range(cartToken, 0, len);
            }
        }

        //根据购物车的信息查询商品信息
        if(cartList != null){
            for (ShopCart shopCart : cartList) {
                //查询购物车对应的商品信息
                Goods goods = goodsService.queryById(shopCart.getGid());
                shopCart.setGoods(goods);
            }
        }


        return cartList;
    }
}
