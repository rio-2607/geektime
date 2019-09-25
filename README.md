## 把极客时间的文章转换成PDF

## 注意：本程序仅供技术交流用，误做其他用途。

生成PDF的过程包括两步：

1. 通过调用程序接口把文章生成PDF。

2. 手动用chrome打开第一步中生成的html，右键 打印，保存为PDF即可


#### 1. 在GeekTimeCall类中填写自己的Cookie和UA。登录极客时间web端之后任何一个页面抓包均可。

#### 2. 调用接口.

接口有两个：

1. `/api/geektime/all`。该接口会把登录用户所有买的类型为"专栏"的文章全部生成HTML。
2. `/api/geektime/column`。该接口会把指定的`cid`(专栏ID)的文章转换成HTML。可以在任意文章内容页面抓包`articles`接口获取到。

![geektime.png](https://i.loli.net/2019/09/25/JBYlaTeZOjuvd5V.png)

#### 3. 报错。

如果接口返回451错误码，一般有两个原因：
1. 接口参数或者header参数变化。重新抓包填入你抓到的参数。
2. 调用接口频率太高。可以适当放缓速度。



