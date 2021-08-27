package com.laychv.pay_mall.service.impl;

import com.google.gson.Gson;
import com.laychv.pay_mall.consts.MallConst;
import com.laychv.pay_mall.dao.ProductMapper;
import com.laychv.pay_mall.enums.ProductStatusEnum;
import com.laychv.pay_mall.enums.ResponseEnum;
import com.laychv.pay_mall.form.CartAddForm;
import com.laychv.pay_mall.form.CartUpdateForm;
import com.laychv.pay_mall.pojo.Cart;
import com.laychv.pay_mall.pojo.Product;
import com.laychv.pay_mall.service.ICartService;
import com.laychv.pay_mall.vo.CartProductVo;
import com.laychv.pay_mall.vo.CartVo;
import com.laychv.pay_mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements ICartService {

    Gson gson = new Gson();
    @Autowired(required = false)
    private ProductMapper productMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public ResponseVo<CartVo> add(Integer uid, CartAddForm form) {
        Integer quantity = 1;
        Cart cart = null;
        final Product product = productMapper.selectByPrimaryKey(form.getProductId());
        // 商品是否存在
        if (product == null) {
            return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
        }
        // 是否在售
        if (!product.getStatus().equals(ProductStatusEnum.ON_SALE.getCode())) {
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }
        // 库存是否足够
        if (product.getStock() <= 0) {
            return ResponseVo.error(ResponseEnum.PRODUCT_STOCK_ERROR);
        }
        // redis
        // 对象方式
//        redisTemplate.opsForValue().set(String.format(MallConst.CART_REDIS_KEY_TEMPLATE, uid),
//                gson.toJson(new Cart(product.getId(), quantity, form.getSelected())));
        // hash方式
        final HashOperations<String, String, String> hash = redisTemplate.opsForHash();
        final String key = String.format(MallConst.CART_REDIS_KEY_TEMPLATE, uid);
        final String value = hash.get(key, String.valueOf(product.getId()));

        if (StringUtils.isEmpty(value)) {
            // 没有该商品, 新增
            cart = new Cart(product.getId(), quantity, form.getSelected());
        } else {
            // 存在,数量 + 1
            cart = gson.fromJson(value, Cart.class);
            cart.setQuantity(cart.getQuantity() + quantity);
        }

        hash.put(key, String.valueOf(product.getId()), gson.toJson(cart));
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> list(Integer uid) {
        final HashOperations<String, String, String> hash = redisTemplate.opsForHash();
        final String key = String.format(MallConst.CART_REDIS_KEY_TEMPLATE, uid);
        final Map<String, String> entries = hash.entries(key);

        boolean selectAll = true;
        CartVo cartVo = new CartVo();
        Integer cartTotalQuantity = 0;
        BigDecimal cartTotalPrice = BigDecimal.ZERO;
        List<CartProductVo> cartProductVoList = new ArrayList<>();

        for (Map.Entry<String, String> entry : entries.entrySet()) {
            final Integer productId = Integer.valueOf(entry.getKey());
            final Cart cart = gson.fromJson(entry.getValue(), Cart.class);
            // todo 循环查询数据库 x
            final Product product = productMapper.selectByPrimaryKey(productId);
            if (product != null) {
                CartProductVo cartProductVo = new CartProductVo(productId,
                        cart.getQuantity(),
                        product.getName(),
                        product.getSubtitle(),
                        product.getMainImage(),
                        product.getPrice(),
                        product.getStatus(),
                        product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())),
                        product.getStock(),
                        cart.getProductSelected());
                cartProductVoList.add(cartProductVo);

                if (!cart.getProductSelected()) {
                    selectAll = false;
                }

                if (cart.getProductSelected()) {
                    cartTotalPrice = cartTotalPrice.add(cartProductVo.getProductTotalPrice());
                }
            }
            cartTotalQuantity += cart.getQuantity();
        }

        cartVo.setSelectedAll(selectAll);
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setCartTotalQuantity(cartTotalQuantity);
        cartVo.setList(cartProductVoList);
        return ResponseVo.success(cartVo);
    }

    @Override
    public ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm form) {
        final HashOperations<String, String, String> hash = redisTemplate.opsForHash();
        final String key = String.format(MallConst.CART_REDIS_KEY_TEMPLATE, uid);
        final String value = hash.get(key, String.valueOf(productId));

        Cart cart = gson.fromJson(value, Cart.class);

        if (StringUtils.isEmpty(value)) {
            return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
        }
        // 存在, 修改
        if (form.getQuantity() != null && form.getQuantity() >= 0) {
            cart.setQuantity(form.getQuantity());
        }
        if (form.getSelected() != null) {
            cart.setProductSelected(form.getSelected());
        }
        hash.put(key, String.valueOf(productId), gson.toJson(cart));
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> delete(Integer uid, Integer productId) {
        final HashOperations<String, String, String> hash = redisTemplate.opsForHash();
        String key = String.format(MallConst.CART_REDIS_KEY_TEMPLATE, uid);
        final String value = hash.get(key, String.valueOf(productId));

        if (StringUtils.isEmpty(value)) {
            return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
        }
        hash.delete(key, String.valueOf(productId));
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> selectAll(Integer uid) {
        final HashOperations<String, String, String> hash = redisTemplate.opsForHash();
        final String key = String.format(MallConst.CART_REDIS_KEY_TEMPLATE, uid);

        for (Cart cart : listForCart(uid)) {
            cart.setProductSelected(true);
            hash.put(key,
                    String.valueOf(cart.getProductId()),
                    gson.toJson(cart));
        }
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> UnSelectAll(Integer uid) {
        final HashOperations<String, String, String> hash = redisTemplate.opsForHash();
        final String key = String.format(MallConst.CART_REDIS_KEY_TEMPLATE, uid);

        for (Cart cart : listForCart(uid)) {
            cart.setProductSelected(false);
            hash.put(key,
                    String.valueOf(cart.getProductId()),
                    gson.toJson(cart));
        }
        return list(uid);
    }

    @Override
    public ResponseVo<Integer> sum(Integer uid) {
        final Integer sum = listForCart(uid).stream().map(Cart::getQuantity).reduce(0, Integer::sum);
        return ResponseVo.success(sum);
    }

    public List<Cart> listForCart(Integer uid) {
        final HashOperations<String, String, String> hash = redisTemplate.opsForHash();
        final String key = String.format(MallConst.CART_REDIS_KEY_TEMPLATE, uid);
        final Map<String, String> entries = hash.entries(key);

        List<Cart> cartList = new ArrayList<>();
        for (Map.Entry<String, String> entry : entries.entrySet()) {
            cartList.add(gson.fromJson(entry.getValue(), Cart.class));
        }
        return cartList;
    }
}
