# WYYHttpServer

一个简单的HttpServer  
写这个是为了学习网络编程，目前正在阅读《HTTP权威指南》，看一点实现一点。

主要实现了
+ 单站点静态文件(UTF8)访问支持
+ 阻塞/非阻塞模式
+ 长连接(非阻塞模式)
+ 简单日志功能


##Bug
>1. <del>长连接有问题</del>Java中中文只占1个字节导致ContentLength不对
>1. <del>在快速刷新的时候出现File not found，在单线程的时候问题特别明显，加入多线程后好了点，但是还是会出现问题.</del> [Solve](http://stackoverflow.com/questions/42221498/how-can-i-get-full-http-request-via-java)
>2. 等待发现更多

##Todo
>1. 加入对post请求的支持
>2. 增加json或者xml的配置功能
>3. 加入多站点的支持
>4. 支持Https
>5. 反向代理(主要是php)

##性能

##### 都是使用Http_load进行测试，暂时都是在本机测试（找不来别的机器） 
 
+ 在V3版本下：（非阻塞模式/Ubuntu16.04/epoll）  


         暂时没时间测试
         
+ 在V2版本下：（CachedThreadPool，阻塞io）  


       10000 fetches, 12 max parallel, 90000 bytes, in 20.0024 seconds  
       9 mean bytes/connection  
       499.94 fetches/sec, 4499.46 bytes/sec  
       msecs/connect: 0.803044 mean, 2.556 max, 0.728 min  
       msecs/first-response: 0.841625 mean, 24.015 max, 0.735 min  
       HTTP response codes:  
       code 200 -- 10000  


+ 在V1版本下:（最基本的，无线程池，阻塞io，来一个连接新建一个线程）  


       10000 fetches, 2 max parallel, 90000 bytes, in 30.0023 seconds  
       9 mean bytes/connection  
       333.308 fetches/sec, 2999.77 bytes/sec  
       msecs/connect: 0.751169 mean, 1.923 max, 0.701 min  
       msecs/first-response: 0.775515 mean, 2.957 max, 0.726 min  
       HTTP response codes:  
       code 200 -- 10000


  


##Tips
> 2017.3.10基本的功能差不多都完成了，填完坑就开始写别的  
> 2017.2.17 刚开学好忙，感觉写代码时间都被压榨走了  
> 2017.2.15今天网易笔试没写好，acm做题水平下降了，不爽！  
> <del>有点想重写一遍</del>  
