
遇到了一个RSA的加密，直接找到私钥，然后通过私钥去解密
![](photo/Pasted%20image%2020230105143101.png)

python脚本如下：（这里只用到了解密，所以直接用解密了）
```python
# -*- coding: utf-8 -*-  
# @Time    : 2022/12/19 3:17 下午  
# @Software: f0ng  
  
from flask import Flask,Response,request  
import base64  
from Crypto.Cipher import PKCS1_v1_5  
from Crypto import Random  
from Crypto.PublicKey import RSA  
import json  

# 存放rsa私钥的
def read_private_key(file_path="crypto_private_key.pem") -> bytes:  
    with open(file_path, "rb") as x:  
        b = x.read()  
        return b  
  
def decryption(text_encrypted_base64: str, private_key: bytes):  
    # 字符串指定编码（转为bytes）  
    text_encrypted_base64 = text_encrypted_base64.encode('utf-8')  
    # base64解码  
    text_encrypted = base64.b64decode(text_encrypted_base64)  
    # 构建私钥对象  
    cipher_private = PKCS1_v1_5.new(RSA.importKey(private_key))  
    # 解密（bytes）  
    text_decrypted = cipher_private.decrypt(text_encrypted, Random.new().read)  
    # 解码为字符串  
    text_decrypted = text_decrypted.decode()  
    return text_decrypted  
  
def rsa_decrypt(s):  
    """  
    DES 解密    :param s: 加密后的字符串，16进制    :return:  解密后的字符串  
    """    
    private_key = read_private_key() # 私钥  
    text_decrypted = decryption(s, private_key)  
  
    return text_decrypted  
  
app = Flask(__name__)  
  
  
@app.route('/decode',methods=["POST"])  
def decrypt():  
  
    param = request.form.get('dataBody')  # 获取  post 参数  
    print(param)  
    data = json.loads(param)['data']  
    total = ""  
    
    # 这里的响应包里rsa加密后的字符串是分段的，所以这里是进行拼接，获取加密后完整的字段
    for _ in data:   
        total = total + rsa_decrypt(_.strip("\n"))  
    print("*****")  
    print(total)  
  
    # if param_requestorresponse == "reponse":  
    return total  
  
if __name__ == '__main__':  
    app.debug = True # 设置调试模式，生产模式的时候要关掉debug  
    app.run(host="0.0.0.0",port="8888")
```

crypto_private_key.pem文件如下：
```
-----BEGIN PRIVATE KEY-----  
enID8+C40UKx89EDq1B5z577tGCiWZrLHLI7Xh7i7dXJgj9ejGUrBeQYYoLMjx0J
l3UKUJCJO5tAbYsBoCa55vo8VdnkzMxahN5lbvXv9irh9vj7ZkanUfbMt/jvVCon
enID8+C40UKx89EDq1B5z577tGCiWZrLHLI7Xh7i7dXJgj9ejGUrBeQYYoLMjx0J
czWHm5EJG9cgUsh9XFW6V8QFoRe7AgMBAAECgYEAhLexscAGsssXlKCbng0ZroxT
enID8+C40UKx89EDq1B5z577tGCiWZrLHLI7Xh7i7dXJgj9ejGUrBeQYYoLMjx0J
AzA2m+9LscI6e6YY3FHgjC0ZVaYmVR7DGatjoUcLn15lsoTVtRp88S33ier3GTn1
BX/vC8v0gIIDr4rBAAECQQDmUA5aNz94SXZkZnCKkvvZu1M7TVaXyZ8dV6E0l20S
enID8+C40UKx89EDq1B5z577tGCiWZrLHLI7Xh7i7dXJgj9ejGUrBeQYYoLMjx0J
X4zUjBz+rGyiMgCeffCKHIlXa88kK7LrtiHUwCUuQEM3N+QjBIvNMAn8es83RuP0
fmHMAQJAEgrHdQ3K7RNkMO1Nd62YCTCBjq26UN28l0HcIRCTvNZUlqy9CpeMpZAg
vHirjBkBuhkauCVHFvzFxZO/nhScLQJAJ8YJ/W0YxNV765Eo23eIGLo9LAnf6cwJ
Ni9vhCvyYgic/uDfiGoZVf8oV8fn6yL/TAYVsMiTpbwcRg5ZsCyMAQJAPhyrOCTx
0ArmVJ/0Q3YAqNsThun7xdji1/7CqD3UNHpT297HQkyHX00WnjHP6vmD9mkAC8jK
JgVfAoEaK2ReDQ==
-----END PRIVATE KEY-----
```
原始响应包如下：
![500](photo/Pasted%20image%2020230105143858.png)

解密如下：
![500](photo/Pasted%20image%2020230105143931.png)
