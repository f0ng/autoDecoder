# JSON嵌套加密

*使用python的flask框架实现了http接口*

明文为：
{"userName":"admin","userPwd":"123456"}

密文为：
DL10Kvw9TGp/it/qR93PAIeTJhMnzp4gk2dfGYhnqxniTH1LVtWDaWwT8lQkqFWz

密文还有一层base64编码，所以实际上的数据包的入参为：
{"data":"`REwxMEt2dzlUR3AvaXQvcVI5M1BBSWVUSmhNbnpwNGdrMmRmR1lobnF4bmlUSDFMVnRXRGFXd1Q4bFFrcUZXeg==`"}

特殊点在于，json数据中传入了加密数据，而加密数据也是json的，这就导致了当加密关键字设置了`"`时，无法分辨出来什么是密文、什么是明文，所以在`0.18`版本中更新了密文关键字，出现该关键字则不进行加密

flask代码如下：
```python
# -*- coding:utf-8 -*-  
# author:f0ngf0ng  
# @Date: 2022/5/17 下午9:08  
# aes、base64  
# aes加密后，外面套了一层base64  
# 明文为  
# {"userName":"admin","userPwd":"123456"}  
#  
# 加密后的数据为  
# DL10Kvw9TGp/it/qR93PAIeTJhMnzp4gk2dfGYhnqxniTH1LVtWDaWwT8lQkqFWz  
#  
# 数据包的入参为  
# {"data":"DL10Kvw9TGp/it/qR93PAIeTJhMnzp4gk2dfGYhnqxniTH1LVtWDaWwT8lQkqFWz"}  
  
from Crypto.Cipher import AES  
import base64,json  
  
from Crypto.Util.Padding import pad  
  
def aes_encrypt(text):  
    password = b'f0ngonlysecurity' #秘钥，b就是表示为bytes类型  
    text = text.encode() #需要加密的内容，bytes类型  
    aes = AES.new(password,AES.MODE_ECB) #创建一个aes对象  
    # AES.MODE_ECB 表示模式是ECB模式    text = pad(text, 16)  
    en_text = aes.encrypt(text) #加密明文  
    out = base64.b64encode(en_text)  
    return out.decode() #加密明文，bytes类型  
  
  
def aes_decrypt(text):  
    password = b'f0ngonlysecurity' #秘钥，b就是表示为bytes类型  
    text = base64.b64decode(text) #需要加密的内容，bytes类型  
    aes = AES.new(password,AES.MODE_ECB) #创建一个aes对象  
    # AES.MODE_ECB 表示模式是ECB模式    en_text = aes.decrypt(text) #加密明文  
    return en_text.decode()  
  
  
from flask import Flask,Response,request  
import base64  
app = Flask(__name__)  
  
@app.route('/encode',methods=["POST"])  
def encrypt():  
    body = request.form.get('dataBody')  # 获取  post 参数 必需  
    headers = request.form.get('dataHeaders')  # 获取  post 参数  可选  
    print(body)  
    if headers != None: # 开启了请求头加密  
        headers = headers + "aaaa:bbbb\r\n"  
        headers = headers + "f0ng:test"  
        print(headers + "\r\n\r\n\r\n\r\n" + body)  
        return headers + "\r\n\r\n\r\n\r\n" + body # 返回值为固定格式，不可更改  
    body = aes_encrypt(body)  
    body = base64.b64encode(body.encode())  
    body = '{"data":"' + body.decode() + '"}'  
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
  
    if "data" in body:  
        body = json.loads(body)['data']  
        body = base64.b64decode(body)  
        body = aes_decrypt(body.decode())  
        print(body)  
        return body.strip()  
    else:  
        return body.strip()  
  
# print(aes_encrypt('{"userName":"admin","userPwd":"123456"}'))  
if __name__ == '__main__':  
    app.debug = True # 设置调试模式，生产模式的时候要关掉debug  
    app.run(host="0.0.0.0",port="8888")
```

原始请求包：(明文)
![800](photo/Pasted%20image%2020220518215855.png)


实际请求包：
![800](photo/Pasted%20image%2020220518215911.png)





`autoDecoder`配置如下：
![800](photo/Pasted%20image%2020220518214621.png)
