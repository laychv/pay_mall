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

## user

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

## category

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