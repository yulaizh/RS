# 基于推荐系统的资讯网站  
document editted by SmacUL

> 文档中出现的所有API、SDK的版本号，均是项目创建时下载、使用的版本号，如无特殊说明，不作版本要求

## 运行说明
- 此为IDEA工程项目
- JDK  
    - version 1.8.0_171 64-Bit
- Tomcat
    - version 9.0.8
- mysql
    - version 5.7.18
- 下载并导入mysql-connector包
    - [mysql-connector 下载链接](https://dev.mysql.com/downloads/connector/j/)  version 5.1.46  
    - 下载完成后将mysql-connector-java-5.1.46-bin.jar放入Tomcat目录下的lib文件中
- 下载并导入JSON包
    - [org.json 下载链接](https://search.maven.org/#search%7Cgav%7C1%7Cg:%22org.json%22%20AND%20a:%22json%22)
    version 20180130
    - json-lib的正常使用需要其他的jar包支持，所以直接提供集合包的下载地址  
    [json-lib jar 包集合（已包括json-lib）](https://download.csdn.net/download/u012990533/8004181)
- 下载并导入Apache IOUtils包
    - [IOUtils 下载链接](https://commons.apache.org/proper/commons-io/download_io.cgi)
    version 2.6
- 下载并导入百度语言处理技术基础的Java SDK
    - [SDK 下载链接](http://ai.baidu.com/sdk#nlp) version 4.4.1
- 需要构建mysql数据库RS，具体见[数据库说明](#database)
- 导入文章基本数据到article表  
    - 打开[src/otherAPI/ReadURL.java](./src/otherAPI/ReadURL.java)
    - 修改当中的数据库用户名和密码
    - 运行
        > 运行过程中如果有报错是正常的，只要数据库中有内容写入即可
- 导入文章的文字内容数据到article表
    - 打开[src/otherAPI/GetContent.java](./src/otherAPI/GetContent.java)
    - 运行
        > 运行过程中如果有报错是正常的，只要数据库中有内容写入即可



## 关于命题单位提供的数据接口

> 此接口由第三方提供，可能会失效
- 接口类型  
    JSON
- 接口地址  
    [http://www.textvalve.com/htdatasub/subscribe/articles/toPublish/v2?userId=82&size=100&rnd0.456121920803368](http://www.textvalve.com/htdatasub/subscribe/articles/toPublish/v2?userId=82&size=100&rnd0.456121920803368)
    > 点击此链接可以获得最新的100条文章的基本信息，包括文章作者、发表时间、文章引用的图片URL列表等信息，但是不含文章的内容
- 获得不同页文章的基本数据  
    在上述接口地址的末尾添加 &page=[页数] 即可获得更多的文章基本数据   
    例如：[http://www.textvalve.com/htdatasub/subscribe/articles/toPublish/v2?userId=82&size=100&rnd0.456121920803368&page=1](http://www.textvalve.com/htdatasub/subscribe/articles/toPublish/v2?userId=82&size=100&rnd0.456121920803368&page=1)
- 获得文章原文内容  
    URL格式：http://www.textvalve.com/htdatasub/subscribe/articles/v2/article=[文章编号]  
    例如： 
    [http://www.textvalve.com/htdatasub/subscribe/articles/v2/article-1137993](http://www.textvalve.com/htdatasub/subscribe/articles/v2/article-1137993)
    > 点击此链接获得的是文章的内容（HTML格式）


<h2 id="database">关于数据库</h2>

1. 需要先建立名为RS的mysql数据库  
2. 运行 资讯网站.sql文件，创建库中的表
    > mysql workbench不支持.sql脚本中出现的check语句，如果使用workbench，需要把check语句部分删除或将其注释掉  
    
    > 资讯网站.sql文件中第一部分新建article表的语句，因为reads是sql的关键字-保留字之一，所以需要将reads修改为read_s（可能已经修改）
3. 凡是运行涉及到数据库操作的源码，在运行前都需要修改数据库用户名和密码


## 已知BUG
- 登录&注册页在不同分辨率下表现不同
- 首页瀑布流效果必须放大网页之后才能实现
- 页面左侧的热点新闻不能获取
- 首页回到顶部和举报的按钮是残废的
- 首页发文和了解吃惊没加进去
- 评论页评论数量功能是假的
- 评论发不了
- 资讯网站的mysql脚本有问题
- 发文

## 未完成的部分
- 页面改进一下  
    后台
- 轮播图动态  
    后台
- canvas模型  
    页面  
    后台  
- 浏览记录按日期搜索  
    后台   
- 文章的模糊搜索  
    后台  
- 推荐  
    算法  
- 头像上传
