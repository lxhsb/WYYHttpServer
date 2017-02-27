# WYYHttpServer

一个简单的HttpServer

主要实现了
+ 单个站点的服务
+ 对Get请求的支持
+ 对静态文件的访问支持
+ 几个常见http状态码的支持


##Bug
>1. <del>在快速刷新的时候出现File not found，在单线程的时候问题特别明显，加入多线程后好了点，但是还是会出现问题.</del> [Solve](http://stackoverflow.com/questions/42221498/how-can-i-get-full-http-request-via-java)
>2. 等待发现更多

##Todo
>1. <del>修复上面的Bug</del>暂时已修复
>2. <del>加入线程池的支持</del>使用了CachedThreadPool
>3. 加入对post请求的支持
>4. 加入简单的日志功能
>4. 学会使用***epoll***
>5. 增加json或者xml的配置功能
>5. 加入多站点的支持
>6. 慢慢加入更多的功能
>7. 支持Https

##性能

##### 都是使用Http_load进行测试，暂时都是在本机测试（找不来别的机器）  
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

+ 使用Java自带的http实现:

      暂时没时间测试

  


##Tips
> 2017.2.17 刚开学好忙，感觉写代码时间都被压榨走了  
> 2017.2.15今天网易笔试没写好，acm做题水平下降了，不爽！  
> <del>有点想重写一遍</del>  
