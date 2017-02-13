# WYYHttpServer

一个简单的HttpServer

##Bug
>1. 在快速刷新的时候出现File not found，在单线程的时候问题特别明显，加入多线程后好了点，但是还是会出现问题.

##Todo
>1. 修复上面的Bug
>2. 加入线程池的支持
>3. 加入对post请求的支持
>4. 使用***epoll***

##Tips
> 有点想重写一遍