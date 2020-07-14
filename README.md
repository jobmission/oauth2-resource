## 接口采用Spring security OAuth2 Resource Server认证
## 创建数据库：持久层采用Mybatis框架
````
默认用Mysql数据库，如需用其他数据库请修改配置文件以及数据库驱动
创建数据库SQL：数据库名、数据库用户名、数据库密码需要和application.properties中的一致

CREATE DATABASE IF NOT EXISTS coupon_db DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
create user 'coupon'@'localhost' identified by 'password_dev!';
grant all privileges on coupon_db.* to 'coupon'@'localhost';

建表sql在src/main/resources/sql/create-database-table.sql
初始化sql在src/main/resources/sql/init.sql
````
## !!!启动Resource Server之前，须先确认OAUTH2 server运行中，并修改application.properties中issuer-uri指向OAUTH2 server，最后启动Resource Server应用

## 传递token三种方式
* header添加Authorization 
````
Authorization:Bearer a.b.c
````
* 参数添加access_token
````
/api/a?access_token=a.b.c
````
* cookie添加access_token
````
access_token=a.b.c
````

