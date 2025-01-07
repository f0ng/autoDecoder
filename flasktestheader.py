# -*- coding:utf-8 -*-
# author:f0ngf0ng

from flask import Flask,Response,request
from pyDes import *
import base64,re
app = Flask(__name__)

@app.route('/encode',methods=["POST"]) # 不要修改 不要修改 不要修改 永远都是POST获取参数，不管源数据包GET方法还是POST方法
def encrypt():
    body = request.form.get('dataBody')  # 获取  post 参数 必需必需必需   获取数据包内body的内容
    headers = request.form.get('dataHeaders')  # 获取  post 参数  可选   获取数据包内的数据头，需要勾选<对数据头进行处理>按钮
    reqresp = request.form.get('requestorresponse')  # 获取  requestorresponse 参数  可选   获取是请求还是响应包，需要勾选<请求响应不同加解密>按钮
    print(body)
    print(headers)
    print(reqresp)

#被#隔开的部分是处理header头的部分
################################################################
################################################################
    if headers != None: # 开启了请求头加密
        print(headers)
        try:
            param_uri = re.findall(r'[POST|GET|PUT] /(.*?)[ ]', headers)[0] # 提取请求的uri及参数
            print(param_uri)
            param_uri_total = param_uri + "&b=22222222"
            headers = headers.replace(param_uri, param_uri_total, 1) # 用修改后的uri替换原参数中的uri
        except:
            pass

        headers = headers + "aaaa:bbbb\r\n"
        headers = headers + "f0ng:test"
        print(headers + "\r\n\r\n\r\n\r\n" + body)
        return headers.strip() + "\r\n\r\n\r\n\r\n" + body # 返回值为固定格式，不可更改 必需必需必需，共四个\r\n
################################################################
################################################################

    return  body  # 返回值为固定格式，不可更改 必需必需必需

@app.route('/decode',methods=["POST"]) # 不要修改 不要修改 不要修改 永远都是POST获取参数，不管源数据包GET方法还是POST方法
def decrypt():
    body = request.form.get('dataBody')  # 获取  post 参数 必需必需必需   获取数据包内body的内容
    headers = request.form.get('dataHeaders')  # 获取  post 参数  可选   获取数据包内的数据头，需要勾选<对数据头进行处理>按钮
    reqresp = request.form.get('requestorresponse')  # 获取  requestorresponse 参数  可选   获取是请求还是响应包，需要勾选<请求响应不同加解密>按钮
    print(body)
    print(headers)
    print(reqresp)
# 被#隔开的部分是处理header头的部分，需要勾选<对数据头进行处理>按钮
################################################################
################################################################
    if headers != None: # 开启了响应头加密
        print(headers)
        try:
            param_uri = re.findall(r'[POST|GET|PUT] /(.*?)[ ]', headers)[0] # 提取请求的uri及参数
            print(param_uri)
            param_uri_total = param_uri + "&b=22222222"
            headers = headers.replace(param_uri, param_uri_total, 1) # 用修改后的uri替换原参数中的uri
        except:
            pass
        headers = headers + "yyyy:zzzz\r\n"
        headers = headers + "f0ng:onlysecurity"
        return headers.strip() + "\r\n\r\n\r\n\r\n" + body # 返回值为固定格式，不可更改 必需必需必需，共四个\r\n
################################################################
################################################################

    return body # 返回值为固定格式，不可更改 必需必需必需

if __name__ == '__main__':
    app.debug = True # 设置调试模式，生产模式的时候要关掉debug
    app.run(host="0.0.0.0",port="8888")
