## 项目架构

SpringBoot + Mybatis + MySQL5.7

## idea创建项目

maven 2.1.7

## 配置环境

maven

## 添加依赖

mysql

mybatis

数据库中字段是下划线格式的, Java中命名是驼峰的, 可能会找不到映射

```yaml
mybatis:
  configuration:
  map-underscore-to-camel-case: true
```  

lombok(减少手动创建get,set,toString方法的)

logging

```yaml
logging:
  pattern:
    console: "[%thread] %-5level %logger{36} - %m%n"
```

## 配置数据库地址

修改application.properties为application.yml

## 注意:

1. dao中不用每个都写@Mapper 通过全局配置

```
@MapperScan(basePackages = "com.laychv.pay_mall.dao")
```   

## 数据库设计

- 表关系
- 表结构
- 唯一索引
- 单索引及组合索引
- 时间戳

用户 分类 商品 订单详情 订单 支付 收获地址

## 数据库软件配置

- MySQL版本5.7.34
    - 安装过程及注意事项, 参考
- Navicat Premium 15
    - 激活,安装
    - 创建新库,导入数据库my.sql(默认数据库不要删除)
    - 测试数据库链接是否成功

## mybatis 三剑客

- xml 使用

- generator 使用
    - 新建generatorConfig.xml
    - mvn mybatis-generator:generate
    - 在dao,pojo,resources/mappers目录下生成文件
    - xml中文件的覆盖,Java中的覆盖

- plugin 使用
- 仓库中下载 mybatis plugin

---

### SpringBoot 参数校验

https://mp.weixin.qq.com/s/VOO4QaQF8mWz0l33_G7u-A

```
<!--校验组件-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
        <!--web组件-->
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

springboot-2.3之前的版本只需要引入 web 依赖就可以了。

## 用户模块

- Content-Type: application/json
- 开发顺序: Dao -> Service -> Controller
- Mybatis打印SQL语句
- 测试代码, 不插入数据库
    - 在测试代码类中添加注解 `@Transactional`

## 架构

### Controller层

Controller层主要和前端交互,通过接口方式

```
请求参数写法区别?
第一种: 写一行
@PostMapping(value = "/user/register")
第二种: 写两行
@RequestMapping("/user")
public class UserController{}
@PostMapping(value = "/register")
public ResponseVo<User> register(){}
```

- @RequestBody
    - 使用form表单形式, 前端json传参
    - raw + json

- @RequestParam("username")
    - 使用参数形式, 前端单独传参
    - urlencoded

- 返回给前端数据, 包含data=null的, 通过配置@JsonInclude(value = JsonInclude.Include.NON_NULL), 空的数据不返回

```
// 未添加的时候
{"status": 0,"msg": "注册成功!","data": null}
// 添加后的效果
{"status": 0,"msg": "注册成功!"}
```

- 数据校验

### Service层

业务逻辑

### Mapper层

数据库映射层

## SpringBoot 跨域

- cookie跨域
- 127.0.1:8080 / localhost:8080 IP / 域名:跨域

前段 -> Java

cookie(SessionId) -> Session(过期)

## 拦截器

Interceptor Url

AOP 包名

## 商品分类模块

查询一级目录 -> 子级目录

### 商品列表

如果不想通过继承的方式创建2个类,配置columnOverride的方式,即可生成到一个类中

```xml

<table tableName="mall_product" domainObjectName="Product"
       enableCountByExample="false"
       enableDeleteByExample="false"
       enableSelectByExample="false"
       enableUpdateByExample="false">
    <columnOverride column="detail" jdbcType="VARCHAR"/>
    <columnOverride column="sub_images" jdbcType="VARCHAR"/>
</table>
```

### mybatis 报错

参数类型是Set时候

```
org.mybatis.spring.MyBatisSystemException: nested exception is org.apache.ibatis.binding.BindingException: Parameter 'categoryIdSet' not found. Available parameters are [collection]
```

```
<foreach collection="categoryIdSet" item="item" index="index" open="(" separator="=" close=")">
                #{item}
</foreach>
List<Product> selectByCategoryIdSet(@Param("categoryIdSet") Set<Integer> categoryIdSet);
```

### 商品列表 - 分页

mybatis 分页插件

https://github.com/pagehelper/Mybatis-PageHelper/blob/master/README_zh.md

```xml
<dependency>
  <groupId>com.github.pagehelper</groupId>
  <artifactId>pagehelper-spring-boot-starter</artifactId>
  <version>1.3.1</version>
</dependency>
```

---

## 购物车模块

表单统一验证

