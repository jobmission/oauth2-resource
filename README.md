### 接口采用Spring security OAuth2 ResourceServer认证，请求时添加header
````
Authorization : Bearer xxxxx
````

##当client和server在一台主机时，请用域名访问，否则cookies会相互覆盖，影响测试。