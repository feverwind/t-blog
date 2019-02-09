### 来源说明
本项目fork 自 [t-blog](https://github.com/xieshuang/t-blog)，继承了核心框架。
t-blog 参考了 [My blog](https://github.com/ZHENFENG13/My-Blog)
我主要做了这些事情：
1. 升级到springboot 2+
2. 打开Druid监控界面
3. 使用了LOMBOK

### 1.涉及技术及工具

- 核心框架：SpringBoot
- ORM 框架：MyBatis
- MyBatis 工具：MyBatis通用Mapper插件
- MVC 框架：Spring MVC
- 模板引擎：Thymeleaf
- 数据库：MySQL、Liquibase、redis、Druid

### 2.部署事宜

- 1、服务器安装mysql并创建空的数据库blog
- 2、安装redis数据库
- 3、项目打成jar包上传到服务器上
- 4、执行 `nohup java -jar blog.jar &`
- 5、浏览器访问 ip:8090即可访问
- 6、可以配置nginx进行代理访问

### 3.我的网站
欢迎访问！http://118.24.197.75:8090/
