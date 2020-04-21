# PROTECT Agent
一个java agent来防止XXE、s2-032、s2-037攻击。
# 支持版本
* JAVA 5-8
* openJDK
# 部署说明
1. 下载Agent
```shell script
git clone https://github.com/ziizhuwy/ProtectAgent.git
cd ProtectAgent
mvn clean package
```
或者直接下载release包(https://github.com/ziizhuwy/XXEAgent/releases/download/v1.0/XXE-Agent.jar).

2. 安装Agent
```shell script
-javaagent:/路径/ProtectAgent.jar
```
路径为XXE-Agent.jar的目录
3. 安装成功
启动应用时可以看到如下标志表明安装成功
```text
********************************************************************
*                     Agent for  attacks                        *
********************************************************************
```
# 测试DEMO
demo下载地址：https://github.com/ziizhuwy/XXEDemo
测试结果如下，出现`[XXE ATTACK] entity:http://localhost:10000`，表明拦截成功。
![xxe](/img/xxe.png)





