# 说明 #

自己作为技术学习的CMS微服务系统，取名elf **（精灵）** ，前后分离。前端主要用bootstrap、layui，后端用springboot、springcloud。

2020-06-21更新说明：
更新springboot版本为2.3.1， 更新springcloud版本为Hoxton.SR5

调整架构为

* elf-eureka: 注册中心，8070端口
* elf-config: 配置中心，8071端口
* elf-gateway: 网关，8080端口
* elf-common: 通用jar包
* elf-auth-client: 权限认证client端jar包，采用jwt方式实现
* elf-logging: 日志jar包，aop方式通过注解保存核心操作日志到数据库
* elf-auth-server: 授权服务，采用jwt，springboot服务，8081端口
* elf-system: 系统管理服务，8082端口

* elf-page-admin: 后台静态页面
* elf-page-web: 前台静态页面


目前只完成了登录和系统管理部分.
在线演示地址: http://www.sususky.com/admin/login.html
用户名/密码: admin/admin


