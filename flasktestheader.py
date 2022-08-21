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

    if headers != None: # 开启了请求头加密
        print(headers + "\r\n\r\n\r\n\r\n" + body)
        headers = headers + "aaaa:bbbb\r\n"
        headers = headers + "f0ng:test"
        return headers + "\r\n\r\n\r\n\r\n" + body # 返回值为固定格式，不可更改

    return  body

@app.route('/decode',methods=["POST"]) # 不解密
def decrypt():
    body = request.form.get('dataBody')  # 获取  post 参数 必需
    headers = request.form.get('dataHeaders')  # 获取  post 参数  可选
    if headers != None: # 开启了响应头加密
        print(headers + "\r\n\r\n\r\n\r\n" + body)
        headers = headers + "yyyy:zzzz\r\n"
        headers = headers + "f0ng:onlysecurity"
        return headers + "\r\n\r\n\r\n\r\n" + body # 返回值为固定格式，不可更改

    return body

if __name__ == '__main__':
    app.debug = True # 设置调试模式，生产模式的时候要关掉debug
    app.run(host="0.0.0.0",port="8888")
