# WYYHttpServer

一个简单的HttpServer

主要实现了
+ 对单个站点的服务
+ 对Get请求的支持
+ 对静态文件的访问支持
+ 几个常见http状态码的支持
+ 在目前版本下（裸实现），使用Http_load本机简单测试性能如下（暂时找不来别的机器）
> 10000 fetches, 2 max parallel, 90000 bytes, in 30.0023 seconds  
  9 mean bytes/connection  
  333.308 fetches/sec, 2999.77 bytes/sec  
  msecs/connect: 0.751169 mean, 1.923 max, 0.701 min  
  msecs/first-response: 0.775515 mean, 2.957 max, 0.726 min  
  HTTP response codes:  
  code 200 -- 10000



##Bug
>1. <del>在快速刷新的时候出现File not found，在单线程的时候问题特别明显，加入多线程后好了点，但是还是会出现问题.</del>
>2. 等待发现更多

##Todo
>1. <del>修复上面的Bug</del>暂时已修复
>2. ***加入线程池的支持*** 暂时不知道使用那个线程池比较好
>3. 加入对post请求的支持
>4. 加入简单的日志功能
>4. 学会使用***epoll***
>5. 增加json或者xml的配置功能
>5. 加入多站点的支持
>6. 慢慢加入更多的功能
>7. 支持Https

##Tips
> 2017.2.17 刚开学好忙，感觉写代码时间都被压榨走了  
> 2017.2.15今天网易笔试没写好，acm做题水平下降了，不爽！
> <del>有点想重写一遍</del>  
