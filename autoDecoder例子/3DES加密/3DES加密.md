# 3DES加密

*使用python的flask框架实现了http接口*

明文为  
{"userName":"admin","userPwd":"123456"}

密文为  
cB9pLEou6hsxiVAEuKNQJ+LEoNY0A8BFgJIqwqkreQtP893kcB9OzQ==

CBC模式
偏移量为`11111111`
密钥为`onlysecurityonlysecurity`

flask代码如下：
```python
# -*- coding:utf-8 -*-  
# author:f0ngf0ng  
# @Date: 2022/5/15 下午10:25  
from Crypto.Cipher import DES3  
import pyDes,base64  
  
# 3des加密实现  
# 明文为  
# {'username':'admin'}  
#  
# 密文为  
# 5Pne6rhiOkxfngbJMpSc+aBCaNE/09HW  
  
class EncryptDate:  
    def __init__(self, key):  
        self.key = key  # 初始化密钥  
        self.iv = b'11111111'  # 偏移量  
        self.length = DES3.block_size  # 初始化数据块大小  
        self.des3 = DES3.new(self.key, DES3.MODE_CBC, self.iv)  # 初始化AES,CBC模式的实例  
        # 截断函数，去除填充的字符        self.unpad = lambda date: date[0:-ord(date[-1])]  
  
    def pad(self, text):  
        """  
        #填充函数，使被加密数据的字节码长度是block_size的整数倍        """        count = len(text.encode('utf-8'))  
        add = self.length - (count % self.length)  
        entext = text + (chr(add) * add)  
        return entext  
  
    def encrypt(self, encrData):  # 加密函数  
  
        res = self.des3.encrypt(self.pad(encrData).encode("utf8"))  
        msg = str(base64.b64encode(res), encoding="utf8")  
        # msg =  res.hex()  
        return msg  
  
    def decrypt(self, decrData):  # 解密函数  
        res = base64.decodebytes(decrData.encode("utf8"))  
        # res = bytes.fromhex(decrData)  
        msg = self.des3.decrypt(res).decode("utf8")  
        return self.unpad(msg)  
  
from flask import Flask,Response,request  
from pyDes import *  
import base64  
app = Flask(__name__)  
  
@app.route('/encode',methods=["POST"])  
def encrypt():  
    body = request.form.get('dataBody')  # 获取  post 参数 必需  
    headers = request.form.get('dataHeaders')  # 获取  post 参数  可选  
  
    if headers != None: # 开启了请求头加密  
        headers = headers + "aaaa:bbbb\r\n"  
        headers = headers + "f0ng:test"  
        print(headers + "\r\n\r\n\r\n\r\n" + body)  
        return headers + "\r\n\r\n\r\n\r\n" + body # 返回值为固定格式，不可更改  
    eg1 = EncryptDate('onlysecurityonlysecurity')  
    body = eg1.encrypt(body)  
    return  body  
  
@app.route('/decode',methods=["POST"])  
def decrypt():  
    body = request.form.get('dataBody')  # 获取  post 参数 必需  
    headers = request.form.get('dataHeaders')  # 获取  post 参数  可选  
    print(body)  
    if headers != None: # 开启了响应头加密  
        print(headers + "\r\n\r\n\r\n\r\n" + body)  
        headers = headers + "yyyy:zzzz\r\n"  
        headers = headers + "f0ng:onlysecurity"  
        return headers + "\r\n\r\n\r\n\r\n" + body # 返回值为固定格式，不可更改  
    if '"' in body:  
        body = body.replace('"',"")  
    eg = EncryptDate("onlysecurityonlysecurity")  
    body = eg.decrypt(body)  
    print(body)  
    return body  
  
if __name__ == '__main__':  
    app.debug = True # 设置调试模式，生产模式的时候要关掉debug  
    app.run(host="0.0.0.0",port="8888")
```

原始请求包：(明文)

![800](photo/Pasted%20image%2020220518204404.png)

实际请求包：
![800](photo/Pasted%20image%2020220518204423.png)

autoDecoder配置如下：
![800](photo/Pasted%20image%2020220518213605.png)