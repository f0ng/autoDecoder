# -*- coding:utf-8 -*-
# author:f0ngf0ng

from flask import Flask,Response,request
from pyDes import *
import base64

def des_encrypt(s):
    """
    DES 加密
    :param s: 原始字符串
    :return: 加密后字符串，16进制
    """
    secret_key = "f0ngtest"
    iv = "f0ngf0ng"
    k = des(secret_key, CBC, iv, pad=None, padmode=PAD_PKCS5)
    en = k.encrypt(s, padmode=PAD_PKCS5)
    return base64.encodebytes(en).decode()

def des_decrypt(s):
    """
    DES 解密
    :param s: 加密后的字符串，16进制
    :return:  解密后的字符串
    """
    secret_key = "f0ngtest"
    iv = "f0ngf0ng"
    k = des(secret_key, CBC, iv, pad=None, padmode=PAD_PKCS5)
    de = k.decrypt(base64.decodebytes(bytes(s,encoding="utf-8")), padmode=PAD_PKCS5)
    return de.decode()

app = Flask(__name__)

@app.route('/encode',methods=["POST"])
def encrypt():
    param = request.form.get('dataBody')  # 获取  post 参数
    encry_param = des_encrypt(param.strip("\n"))
    print(param)
    print(encry_param)
    return encry_param

@app.route('/decode',methods=["POST"])
def decrypt():
    param = request.form.get('dataBody')  # 获取  post 参数
    decrypt_param = des_decrypt(param.strip("\n"))
    print(param)
    print(decrypt_param)
    return decrypt_param

if __name__ == '__main__':
    app.debug = True # 设置调试模式，生产模式的时候要关掉debug
    app.run(host="0.0.0.0",port="8888")
