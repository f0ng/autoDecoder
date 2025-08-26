# autoDecoder

[![Repo stars](https://img.shields.io/github/stars/f0ng/autoDecoder)](https://github.com/f0ng/autoDecoder/stargazers)
[![Downloads total](https://img.shields.io/github/downloads/f0ng/autoDecoder/total?label=Downloads)](https://github.com/f0ng/autoDecoder/releases)
[![Repo tags](https://img.shields.io/github/v/tag/f0ng/autoDecoder?label=Latest)](https://github.com/f0ng/autoDecoder/tags)
[![Downloads latest total](https://img.shields.io/github/downloads/f0ng/autoDecoder/latest/total?label=Downloads@latest)](https://github.com/f0ng/autoDecoder/releases)



想维护成一个有很多用例、接口的项目，希望各位师傅有加解密之类的需求可以一起沟通，完善本项目。

工具针对人群：有一定代码基础的师傅、没有基础(但是愿意自主学习捣鼓自动加解密)的师傅

# 关于autoDecoder的案例移步[autoDecoder-usages](https://github.com/f0ng/autoDecoder-usages)，对很多想自行编写接口的师傅会有帮助
# 接口代码模板不要随便去改动获取参数和返回的格式，否则会出现乱码、无法正常运行等问题



交流群

<img width="183" alt="image" src="https://user-images.githubusercontent.com/48286013/220634169-ddefd4b2-d967-4a85-8b28-b626ba366742.png">

二维码失效请加微信`f-f0ng`、备注autoDecoder交流

关注主页公众号（only security），回复`autodecoder`获取下载地址】

# 捐赠 （如果项目有帮助到您，可以选择捐赠一些费用用于autoDecoder的后续版本维护，本项目长期维护）
<img width="251" alt="image" src="https://github.com/user-attachments/assets/f0308410-8094-47ec-9448-8ef1b8ec94d6" />


<img width="251" alt="image" src="https://github.com/f0ng/autoDecoder/assets/48286013/e9318b91-2521-4c14-93d8-9737fd7a4729">

## 有问题先到[FAQ](https://github.com/f0ng/autoDecoder/blob/main/FAQ.md)查看

## 简单流程图

<table rules="none" align="center">
	<tr>
		<td>
			<center>
   <font color="AAAAAA">正常流程图</font>
				<img src="https://user-images.githubusercontent.com/48286013/188769341-a5ad035b-2f0b-4fa1-87e1-342cd800dd46.jpg" width="110%" />
				<br/>
			</center>
		</td>
		<td>
			<center>
   <font color="AAAAAA">通过autoDecoder处理的流程之对于密文的处理</font>
				<img src="https://user-images.githubusercontent.com/48286013/188769399-5509ca25-9f23-4abc-8074-ff410534cf73.jpg" width="100%" />
				<br/>
			</center>
		</td>
  		<td>
			<center>
   <font color="AAAAAA">通过autoDecoder处理的流程之对于明文的处理</font>
				<img src="https://user-images.githubusercontent.com/48286013/188770030-b752aae9-dc8a-45a5-b1c6-b45b2995e8f1.jpg" width="100%" />
				<br/>
			</center>
		</td>
	</tr>
</table>

## 2025.7.24  更新0.55
1.增加自带加解密模式对header头的匹配[包含对get请求参数的匹配]

## 2025.7.8  更新0.54
1.修复自带加解密请求响应不同加解密的bug

## 2025.4.3  更新0.53
1.修复换行bug【写debug模块的时候，统一返回格式了，导致勾选header和不勾选header的换行是一样多的】

## 2024.12.30  更新0.52  预祝大家元旦快乐！
1. 接口调试模块增加关键字代入模式，防止关键字引发报错
2. 接口模式下加解密将报错回显，可以知道是具体是哪个问题导致加解密失败

当返回包格式出问题

<img width="500" alt="gagsjjcg o4n" src="https://github.com/user-attachments/assets/306ae42b-8079-48f8-8d28-b4359c5f06c7" />

当接口不通

<img width="400" alt="i3w34g2g f2x" src="https://github.com/user-attachments/assets/3578d919-140c-42ba-86ed-c26e2e54ef71" />

也可以在Extensions页面查看报错信息

<img width="400" alt="wtc30zsn qak" src="https://github.com/user-attachments/assets/7a226abd-0d57-4d41-9b3a-062d696a7720" />


## 2024.10.12  更新0.51
1. 修复替换模块host匹配问题
2. 优化替换模块，增加自动解unicode模式，需要为Literal模式，Replace需为`#unicode#`，响应包的unicode编码就会自动解码
<img width="995" alt="x1u2kzeg nao" src="https://github.com/user-attachments/assets/210e22e4-534c-42c8-8c4a-7c8dcf5f2411">

## 2024.9.24  更新0.50
1. 修复sm4加解密错误

## 2024.5.5 更新0.40
1. 修复sm4加解密bug(base64)
2. 增加密文关键字对响应体的判断

## 2024.3.17 更新0.39-beta1
1. 修复加密bug(SM4与AES)

## 2024.3.13 更新0.39
1. 修复SM4中的base64加解密bug
2. 修复AES/DES加密中base64解密的bug

## 2024.2.2 更新0.38
1. 优化SM4、SM2加解密
2. 优化正则匹配替换

## 2024.1.2 更新0.37 新年第一更，祝大家新年快乐！
1. 自带加解密增加sm2、sm4(CBC、ECB)加解密
2. 正则表达式保存进配置文件

## 2023.12.18 更新0.36
1. 修复了替换开关默认选中的问题
2. 接口加解密调试优化
3. 优化header头关键字判断

## 2023.11.22 更新0.35
1. 增加加载配置文件、保存配置文件的模块，后续使用可以根据配置文件的命名来进行加载配置
2. 增加请求、响应替换功能，类似burp的`Match and Replace`模块，增加提取模块，案例配置如下

<img width="1187" alt="image" src="https://github.com/f0ng/autoDecoder/assets/48286013/a5d82e7b-5db3-43e8-90d1-863f0d1629a5">

正常响应

<img src="https://github.com/f0ng/autoDecoder/assets/48286013/dacf44a6-3687-4eab-b716-2186a25f538d" width="450" />

修改后的响应

<img src="https://github.com/f0ng/autoDecoder/assets/48286013/c1a33b8a-9d2a-4469-ac8a-9cd709bab286" width="450" />

## 2023.11.11 更新0.34
1. 增加header头关键字判断

## 2023.10.23 更新0.33
1. 增加响应base64自动解码，当响应包返回的为base64时，可以自动解码，防止二进制数据包损坏
2. 增加选项保存、读取

## 2023.9.16 更新0.32
1. 明文关键字、密文关键字的设置优化

## 2023.9.5 更新0.31
1. 域名匹配模块中可以进行多域名匹配，修复了原版本无法在多个域名下显示选项卡问题
2. 将接口调试模块的两个换行符取消

## 2023.7.6 更新0.30
1. `Repeater、Intruder`模块中，增加右键加密、解密，当设置好相应的方法后可以对请求体的body进行加密、解密
2. 修复`Send to Repeater、Send to Intruder`不会带上端口号的问题
   
## 2023.5.22 更新0.27
1. autoDecoder扩展选项卡增加右键`Send to Repeater、Send to Intruder`，并且增加格式化，目前仅针对json格式

## 2023.5.17 更新0.26
1. 修复了勾选`对请求头处理`后，请求头缺失问题
2. 增加对密文URL解码读取、加密后的密文URL解码选项
3. 修复其他问题，如指定域名端口号不出现扩展选项卡、windows下中文解密乱码等问题

感谢微信群yosel 师傅反馈

## 2023.4.25 更新0.25
1. 增加二进制请求体、响应体处理，原理为将请求包的内容base64编码后传入接口，需要对burp做以下设置，否则获取到的编码为损坏的
	- 需要勾选User options->Character Sets中的`Use the platform default(UTF-8)`[MAC]，或者勾选`Display as raw bytes`[Windows]

## 2023.4.14 更新0.24-beta2
1. RSA加解密
2. 响应包正则模式加解密

## 2023.4.12 更新0.24-beta1
1. 根据加密方式对key进行截取
2. 修复只选中接口加解密无法进行加解密的问题

## 2023.4.8 更新0.24
1. 优化测试接口数据包显示
2. 增加zeropadding填充模式加密
3. 优化base64解码问题

## 2023.2.22 更新0.23
1. 优化了插件的一些问题
2. 案例移步[autoDecoder-usages](https://github.com/f0ng/autoDecoder-usages)

## 2023.2.16 更新0.22-beta1
1. 优化了读取密文的时候将`\u0000`去除的问题
2. 在自带算法中，将请求包加解密、响应包加解密分离开，可以选中加密算法`null`表示不进行加解密，返回原数据包


## 2023.2.15 更新0.22 重大更新
1. 重构UI页面，之前的UI太混乱，花了点时间重构了页面UI
<img width="583" alt="image" src="https://user-images.githubusercontent.com/48286013/219077310-7a17115b-885e-47ba-8aaf-427acb55db8a.png">

2. 增加自带算法加解密的正则提取，当正则表达式有内容时生效，针对两种情况: 
	1. 只有请求加密的情况
	2. 在爆破攻击中，对账号密码加密
	
	配置页面
	
	<img width="307" alt="image" src="https://user-images.githubusercontent.com/48286013/219077933-60e197ed-f940-473e-a394-242aedc1cc41.png">


	<img width="852" alt="image" src="https://user-images.githubusercontent.com/48286013/219077869-b894b59e-eed3-48ff-bda4-ebf4f231e2e4.png">

	
	原始请求
	
	<img width="696" alt="image" src="https://user-images.githubusercontent.com/48286013/219077437-c5e793ee-9061-49ca-aadc-8061f9843c21.png">
	
	真实请求
	
	<img width="611" alt="image" src="https://user-images.githubusercontent.com/48286013/219077506-f81555ef-5d8f-44c3-a659-41c6afc0a50f.png">
	
	解密后
	
	<img width="628" alt="image" src="https://user-images.githubusercontent.com/48286013/219077634-92366e38-5176-4ee1-b7ad-b7de7bee96cf.png">


3. 增加接口加解密时的调试页面，方便代码进一步编写

正常解密请求包

<img width="771" alt="image" src="https://user-images.githubusercontent.com/48286013/219079024-ef8fb2ce-0286-4a18-9e38-bdbe85ec742c.png">

正常解密响应包

<img width="747" alt="image" src="https://user-images.githubusercontent.com/48286013/219080446-5c135166-63cb-4c2e-ad66-0e57df1a39f2.png">


处理请求头

<img width="759" alt="image" src="https://user-images.githubusercontent.com/48286013/219079094-53e6f652-5038-4242-aa5f-0955310b6998.png">


## 2023.2.14 更新0.21
1. 增加burp模块按钮，防止与其他模块(如插件Extender)产生的请求冲突
2. 优化插件解密读取密文方式，进行URL解码后读取

## 2023.1.5 更新0.20
1. 增加`RSA解密` 一个例子
2. 优化选项卡数据包设置，不影响repeater本身的数据包内容
<table rules="none" align="center">
	<tr>
		<td>
			<center>
   <font color="AAAAAA">解密请求包</font>
				<img src="https://user-images.githubusercontent.com/48286013/210718969-8958c40d-cbee-47e7-8e97-97f6ba17b4e6.png" width="100%" />
				<br/>
			</center>
		</td>
		<td>
			<center>
   <font color="AAAAAA">解密请求包后不影响原请求包</font>
				<img src="https://user-images.githubusercontent.com/48286013/210718946-888dc52f-a882-4026-ba92-2845a7ac81f7.png" width="100%" />
				<br/>
			</center>
		</td>
	</tr>
</table>

## 2022.9.7 更新0.19

- 增加请求包、响应包不同加密算法按钮【仅针对接口加解密模式】，针对于请求包、响应包使用不同的加密算法（实现方式为：在请求解密接口同时，传入参数`requestorresponse`，表明是请求[`request`]还是响应[`response`]）

<img width="800" alt="image" src="https://user-images.githubusercontent.com/48286013/188768352-f7d33440-0f13-4abe-bc6c-4cdb7abfd8ee.png">

- 修改了ui，优化了在新版bp上`html`无法解析的问题

## 2022.5.18 更新0.18

- 增加`3DES加密`、 `AES加密`、 `JSON嵌套加密`三个例子

- 在明文发出请求的时候，如果响应包选中了默认选项卡，则无影响；但是当响应包选中了扩展选项卡，还会进行解密，导致请求包内的响应体报错，`0.18`修复该问题：明文发出请求，响应包只有明文。
- 增加密文关键字，出现该关键字则不进行加密，具体可以查看例json嵌套加密
## 2022.5.15 更新0.17
- 优化Desede(3DES)加密处理问题，3DES加密的密钥为24位，当输入超过24位将会报密钥长度错误，处理方式为对密钥长度进行了截取

## 2022.5.11 更新 0.16
1. 增加响应头处理，传入参数同样为`dataHeaders`
```
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
        headers = headers + "aaaa:bbbb\r\n"
        headers = headers + "f0ng:test"
        print(headers + "\r\n\r\n\r\n\r\n" + body)
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
```

原始请求响应

<img width="380" alt="image" src="https://user-images.githubusercontent.com/48286013/167889115-6b4a45ac-0acf-4220-8263-d8497c446a78.png">


经过autoDecoder处理后的响应

<img width="380" alt="image" src="https://user-images.githubusercontent.com/48286013/167889126-6f3d57cd-9ec9-4796-a442-a730b24865ac.png">


2. 修复当请求体为空时候的报错
3. 修复当关键词置空时造成多出一个换行符的问题
## 2022.5.7 更新 0.15

* 优化设置域名处的端口问题，两种模式：

①只输入域名，匹配域名与任意端口号
  
<img width="330" alt="image" src="https://user-images.githubusercontent.com/48286013/167258838-6a10e777-5253-4b21-be26-80acf7343592.png">
  
匹配所有`www.baidu.com:端口号`，如`www.baidu.com:8080`、`www.baidu.com:8088`
  
②输入域名与端口号，匹配唯一域名与端口号host
  
<img width="354" alt="image" src="https://user-images.githubusercontent.com/48286013/167258977-a897288a-61b0-4838-ae2c-3d8bb945925b.png">
  
  只匹配`www.baidu.com:8080`



## 2022.4.26更新 0.14

1. 增加对整个请求包的处理，具体怎么修改，根据个人需要不同进行自定义了，模板文件为`flasktestheader.py`

配置如下：

<img src="https://user-images.githubusercontent.com/48286013/165212779-88601efd-d11d-44ff-bd2a-74fc8efee915.png" width="700" />

原始请求包，捕捉整个请求如下，在请求包添加额外的请求头`aaaa:bbbb`、`f0ng:test`

<img src="https://user-images.githubusercontent.com/48286013/165212786-17579322-9c9d-411f-964e-931608184f72.png" width="700" />

实际请求包

<img src="https://user-images.githubusercontent.com/48286013/165212793-b267a7ea-b965-4ed8-80f7-3bf1ca428fa8.png" width="700" />

2. 增加自定义设置明文关键字，当请求体中出现了相应的关键字则不对数据包进行处理，取`contains`进行判断
## 2022.4.22更新 0.13

1. 对于`\r\n`的请求包处理不够完善，0.13版本修复该问题

2. 增加案例1`登录口爆破之ldap的md5加密`，案例2`sql注入绕过之sqlmap的数据包换行问题`，方便更好使用工具进行渗透测试
## 0x01 背景
- 当数据包里都是密文，我们无从下手；就算是获得了加解密的一些关键信息，能解密出来，但是每个数据包我们都需要慢慢解密，请求包需要解密，响应包也需要解密，比较麻烦
- 其实取auto这个名字并不是真正的auto，加解密算法还是需要自己去逆出来的，只是相对于数据包里的密文来说，可以算是半自动

## 0x02 优点
- 明文传，明文响应；密文传，密文响应，不影响原本通讯包的基础上，增加一个bp扩展页面查看明文信息
- 自定义加解密的接口，当存在复杂数据加密的时候，可以自行编写python代码对接口进行加解密， 自定义需要加解密域名，即开即用

## 0x03 插件的加解密方式
- 直接通过插件自带的算法去加解密数据包(较为简单，仅支持部分AES、DES、DESede加密)
- 通过python的flask接口去编写加解密数据包的api（不一定是flask框架，也可以起其他框架，只需要接口地址正确且加解密流程正确即可）

## 0x04 文件介绍:
- `users.sql` 为测试所用数据库

- `testsql.php` 为加密请求数据、加密响应数据且存在注入的页面

- `flasktest.py` 为测试文件编写的Python flask加解密接口。

详细举例可至公众号查看https://mp.weixin.qq.com/s/B-lBbVpJsPdCp1pjz2Rxdg

>## 通过自带加解密算法进行解密
![通过自带加解密算法进行解密](https://user-images.githubusercontent.com/48286013/160236779-9848060d-94ff-493d-ace5-44aa85e6e444.gif)

>## 通过flask接口进行解密
![通过flask接口进行解密](https://user-images.githubusercontent.com/48286013/160237163-cd57267e-7e11-49cb-8ec5-41a896f917bc.gif)

>## sqlmap进行加解密
![sqlmap进行加解密](https://user-images.githubusercontent.com/48286013/160238771-dd33b0ef-1007-47a0-9659-c8a20ed6d89e.gif)


