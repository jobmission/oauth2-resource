## 接口采用Spring security OAuth2 ResourceServer认证
## 创建数据库：持久层采用Mybatis框架
````
#默认用Mysql数据库，如需用其他数据库请修改配置文件以及数据库驱动
#创建数据库SQL：数据库名、数据库用户名、数据库密码需要和application.properties中的一致

CREATE DATABASE IF NOT EXISTS coupon_db DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
create user 'coupon'@'localhost' identified by 'password_dev!';
grant all privileges on coupon_db.* to 'coupon'@'localhost';

#初始化数据sql在src/main/resources/sql/create-database-table.sql
````

## 传递token三种方式
* 请求时添加Authorization header
````
Authorization:Bearer a.b.c
````
* 请求地址添加参数access_token
````
/api/a?access_token=a.b.c
````
* cookie方式 添加access_token
````
access_token=a.b.c
````

### 当client,resource和server在一台主机时，请用域名访问，否则cookies会相互覆盖，影响测试，以下配置仅供参考
* hosts文件
````hosts
127.0.0.1 client.sso.com
127.0.0.1 server.sso.com
127.0.0.1 api.sso.com
````
* nginx配置
````nginx

server {
         server_name server.sso.com;
         listen 80;
         listen [::]:80;
 
         proxy_set_header Host $host;
         proxy_set_header X-Real-IP $remote_addr;
         proxy_set_header REMOTE-HOST $remote_addr;
         proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
 
 
         index index.html;
 
         location / {
                 proxy_pass http://localhost:10380/;
         }
 }
 
server {
        server_name client.sso.com;
        listen 80;
        listen [::]:80;

        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header REMOTE-HOST $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;


        index index.html;

        location / {
                proxy_pass http://localhost:10480/;
        }
}

 server {
         server_name api.sso.com;
         listen 80;
         listen [::]:80;
 
         proxy_set_header Host $host;
         proxy_set_header X-Real-IP $remote_addr;
         proxy_set_header REMOTE-HOST $remote_addr;
         proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
 
 
         index index.html;
 
         location / {
                 proxy_pass http://localhost:10580/;
         }
 }
````