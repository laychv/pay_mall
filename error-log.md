## 常见错误 - Mybatis

```log
org.apache.ibatis.binding.BindingException: Invalid bound statement (not found):
```

参考:
https://www.jianshu.com/p/2bd1e5313b66

- 暂未解决

```log

Failed to configure a DataSource: 'url' attribute is not specified and no embedded datasource could be configured.

Reason: Failed to determine a suitable driver class

Action:

Consider the following:
If you want an embedded database (H2, HSQL or Derby), please put it on the classpath. If you have database settings to
be loaded from a particular profile you may need to activate it (no profiles are currently active).
```

参考地址:
https://blog.csdn.net/nuomizhende45/article/details/84678976

- 这里是通过重启idea解决

SpringBoot 出现 Content type 'application/x-www-form-urlencoded;charset=UTF-8' not supported
https://blog.csdn.net/feiyst/article/details/88431621

### 请求错误 post man

Content type 'text/plain;charset=UTF-8' not supported

https://blog.csdn.net/qwdafedv/article/details/53005418

### 运行错误

sql语句缺少默认值

java.sql.SQLException: Field 'create_time' doesn't have a default value

在mysql中添加默认时间, 以Navicat为例,选中表 -> 菜单栏(设计表Ctrl + D) ->

- Navicat 中没有设置默认CURRENT_TIMESTAMP
    - https://blog.csdn.net/qq_43658218/article/details/107009034

- 举例: mall_user中的字段create_time/update_time
    - DEFAULT CURRENT_TIMESTAMP(0) 默认时间

```sql
DROP TABLE IF EXISTS `mall_user`;
CREATE TABLE `mall_user`
(
    `id`          int(11)                                                 NOT NULL AUTO_INCREMENT COMMENT '用户表id',
    `username`    varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '用户名',
    `password`    varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '用户密码，MD5加密',
    `email`       varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL,
    `phone`       varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL,
    `question`    varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '找回密码问题',
    `answer`      varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '找回密码答案',
    `role`        int(4)                                                  NOT NULL COMMENT '角色0-管理员,1-普通用户',
    `create_time` datetime                                                NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `update_time` datetime                                                NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '最后一次更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `user_name_unique` (`username`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

```

## mybatis-pagehelper

分页无效

添加依赖

```xml
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper-spring-boot-starter</artifactId>
    <version>1.2.13</version>
</dependency>
```

注意: 在查询语句上面

```java
PageHelper.startPage(pageNum, pageSize);
```