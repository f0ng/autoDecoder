# sql注入绕过之sqlmap的数据包换行问题
#autoDecoder例

碰到个SQL注入，但是网站有防护，如图：
![800](photo/Pasted%20image%2020220422125448.png)
	![800](photo/Pasted%20image%2020220422125419.png)
可以通过`change body encoding`解决
![800](photo/Pasted%20image%2020220422125605.png)
但是当我们放到sqlmap中跑注入，发现报500错误，后续可以通过autoDecoder来解决，详细如下：

数据包是`Content-Type: multipart/form-data;` 的注入
![800](photo/Pasted%20image%2020220422122119.png)
但是sqlmap不识别`\r\n`的符号，所以无法跑出注入
使用`sqlmap --proxy=http://127.0.0.1:8080`代理到burp查看请求
![800](photo/Pasted%20image%2020220422122211.png)
![800](photo/Pasted%20image%2020220422122413.png)
原因就在于`\n`符号，sqlmap不知道什么原因无法识别请求体的换行为`\r\n`，导致请求全是500，报错了
http数据包里的换行应该为`\r\n`，可以编写autoDecoder的flask脚本解决问题
flask脚本如下：
```python
# -*- coding:utf-8 -*-  
# author:f0ngf0ng  
  
# 解决sqlmap中的\n无法识别为\r\n问题  
  
from flask import Flask,request   
  
app = Flask(__name__)  
  
@app.route('/encode',methods=["POST"])  
def encrypt():  
	param = request.form.get('dataBody')  # 获取  post 参数  
	param = param.replace("\r\n","\n")  
	data = param.replace("\n","\r\n")  
	print(bytes(data,encoding="utf-8"))  
	return data  
  
@app.route('/decode',methods=["POST"]) # 不解密  
def decrypt():  
    param = request.form.get('dataBody')  # 获取  post 参数  
    return param  
  
if __name__ == '__main__':  
    app.debug = True # 设置调试模式，生产模式的时候要关掉debug  
    app.run(host="0.0.0.0",port="8888")
```
`autoDecoder`插件配置如下：
![800](photo/Pasted%20image%2020220422125105.png)

即可识别
![800](photo/Pasted%20image%2020220422122239.png)
数据库正常跑出
![800](photo/Pasted%20image%2020220422122507.png)
