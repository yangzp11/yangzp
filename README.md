SpringCouldAlibaba+nacos+gradle 简易项目
主要就是工作中遇到一些功能，我把觉得有用的一些分享出来供大家参考，如有不足欢迎指出。

项目结构

yzp-auth(oauth2示例)  
yzp-common(通用基础工具包，暂时没用只是为了做多项目管理)  
yzp-business(业务系统服务，示例都在这里面)  
yzp-gateway(网关服务，服务通过nacos全部注册到这)  
yzp-rocketmq(消息服务)

主要功能示例
1. 动态密码(类似令牌，到期自动刷新)
2. 设计模式示例(策略，建造)
3. stream lambda表达式示例
4. 自定义编号批量生成(锁，自增)
5. 多线程示例（ThreadTest）
6. 地图位置转坐标
7. Solution 一些简单算法示例，优惠金额算法
8. 二维码生成、短链接生成
9. 经典，复杂SQL示例(/yzp-business/sql)
10. socketIO示例（多nameSpace）