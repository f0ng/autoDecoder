# sqlmap的osshell遇到中文字符情况
#autoDecoder例 
sqlmap的os-shell经常遇到会有中文字符的目录，我们如果要写webshell、查看路径可能会遇到一些阻碍，可以通过`bitsadmin`、`certutil`等等来进行下载文件，然后再存储之类的，这里也可以用`autoDecoder`来进行处理，如下：

`sqlmap`的结果：
![800](photo/Pasted%20image%2020220426143153.png)
直接是不行的，原因在于将中文进行了hex编码，导致mssql识别不出来

但是如果我们用`burp`去重放，将hex编码直接替换为编码前的内容，是直接可以的：

sqlmap 的数据包

![800](photo/Pasted%20image%2020220426143526.png)

结果是返回不了我们想要的结果的

手动修改为中文

![800](photo/Pasted%20image%2020220426143617.png)

获得结果

![800](photo/Pasted%20image%2020220426144210.png)

配合`autoDecoder`，可以写flask代码如下：
```python
# -*- coding:utf-8 -*-  
# author:f0ngf0ng  
  
from flask import Flask,Response,request  
from pyDes import *  
import base64  
import re,binascii  
from urllib.parse import unquote,quote  
  
# 解决sqlmap的中文问题  
  
app = Flask(__name__)  
  
@app.route('/encode',methods=["POST"])  
def encrypt():  
    param = request.form.get('dataBody')  # 获取  post 参数  
    print(param)  
    if "INSERT" in param:  
        try :  
            print(unquote(param))  
            b = re.findall("0x(.*?);", unquote(param) )  
            # print(b)  
            c = binascii.a2b_hex(b[0])  
            total_param = unquote(param).replace("0x" + b[0], "'" +c.decode() + "'").replace(" ","%20")  
            print(b[0])  
            print(c.decode())  
        except:  
            pass  
        return total_param  
  
    else :  
        return param  
  
@app.route('/decode',methods=["POST"]) # 不解密  
def decrypt():  
    param = request.form.get('dataBody')  # 获取  post 参数  
    return param  
  
if __name__ == '__main__':  
    app.debug = True # 设置调试模式，生产模式的时候要关掉debug  
    app.run(host="0.0.0.0",port="8888")
```

`autoDecoder`配置如下：

![800](photo/Pasted%20image%2020220426144941.png)

sqlmap结果如下

![800](photo/Pasted%20image%2020220426145224.png)
