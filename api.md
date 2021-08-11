# 接口文档

返回统一样式

```json
{
  "status": 2,
  "msg": "用户名已存在"
}
```

```json
{
  "status": 2,
  "data": {}
}
```

请求方法

Get/Post

json

---

## 用户模块

**1. 用户注册**

POST/user/register

request

```json
{
  "username": "admin",
  "password": "123456",
  "email": "123@qq.com"
}
```

response

```json
{
  "status": 0,
  "data": {
    "id": 1,
    "username": "admin",
    "password": "",
    "email": "admin@qq.com",
    "phone": null,
    "question": null,
    "answer": null,
    "role": 0,
    "createTime": "2000-08-04T16:14:23.000+0000",
    "updateTime": "2000-08-06T15:12:00.000+0000"
  }
}
```

**2. 用户登陆**

POST/user/login

request

```json
{
  "username": "admin",
  "password": "admin"
}
```

response

略

**3. 用户登出**

GET/user/logout

request

response

略

**4. 用户信息**

GET/user/info

request

response

```json
{
  "status": 0,
  "data": {
    "id": 1,
    "username": "admin",
    "password": "",
    "email": "admin@qq.com",
    "phone": null,
    "question": null,
    "answer": null,
    "role": 0,
    "createTime": "2000-08-04T16:14:23.000+0000",
    "updateTime": "2000-08-06T15:12:00.000+0000"
  }
}
```

---

## 商品模块

商品分类

## product

**1. 商品列表**

GET/product/list

request

```
categoryId(非必传)
pageNum(default=1)
pageSize(default=10)
```

response

```json
{
  "status": 0,
  "data": {
    "pageNum": 1,
    "pageSize": 10,
    "size": 2,
    "orderBy": null,
    "endRow": 2,
    "total": 2,
    "pages": 1,
    "list": [
      {
        "id": 1,
        "categoryId": 3,
        "name": "iPhone7",
        "subtitle": "双十一促销"
      }
    ]
  }
}
```

**2.商品详情**

GET/product/{productId}

request

```
productId
```

response

```json
{
  "status": 0,
  "data": {
    "id": 2,
    "category": 2,
    "name": "oppo",
    "subtitle": "oppo促销",
    "mainImage": "mainImage.jpg",
    "subImages": "",
    "detail": "rechtext",
    "price": 2999,
    "stock": 71,
    "status": 1,
    "createTime": "2016-11-21 12:12:21",
    "updateTime": "2016-12-12 12:12:12"
  }
}
```

---

## 购物车模块

**1. 购物车添加商品**

POST/cart/add

request
```
productId
selected:true
```

response

```
```

**2. 购物车列表**

GET/cart/list

request

略

response

```json
{
  "status": 0,
  "data": {
    "selectedAll": true,
    "cartTotalPrice": 15596.00,
    "cartTotalQuantity": 4,
    "list": [
      {
        "productId": 26,
        "quantity": 1,
        "productName": "Apple iPhone 7 Plus (A1661) 128G 玫瑰金色 移动联通电信4G手机",
        "productSubtitle": "iPhone 7，现更以红色呈现。",
        "productMainImage": "241997c4-9e62-4824-b7f0-7425c3c28917.jpeg",
        "productStock": 96,
        "productTotalPrice": 6999.00,
        "productPrice": 6999.00,
        "productStatus": 1,
        "productSelected": true
      }
    ]
  }
}

```

**3. 更新购物车**

PUT/cart/update/{productId}

request

```
quantity 非必填
selected:true 非必填
```

response

```
同列表
```

**4. 购物车删除某个商品**

DELETE/cart/{productId}

request

```
productId
```

response

```
同列表
```

**5. 全选**

PUT/cart/selectAll

request

response


**6. 全不选**

PUT/cart/unSelectAll

request

response

**7. 购物车中商品数量总和**

GET/cart/products/sum

request

response

---

