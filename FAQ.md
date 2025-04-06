## 0x01.第一次使用

1. 第一次使用建议使用样例进行加解密流程的https://mp.weixin.qq.com/s/B-lBbVpJsPdCp1pjz2Rxdg

2. 加解密的时候核对一下三个地方的参数:

【明文关键字与密文关键字不可重复，否则取密文关键字的判断优先】

`加解密的域名` 只有出现该域名的请求包会进行加解密操作(`autoDecoder`选项卡与此参数有关)

`明文关键字` 当请求体内出现该关键字即进行加解密操作(换行符为分隔符，支持多个关键字)

`密文关键字` 当请求体内出现该关键字即不进行加解密操作(换行符为分隔符，支持多个关键字)

<img width="380" alt="image" src="https://user-images.githubusercontent.com/48286013/187074998-90ddce7f-7b65-4721-8803-2c5e82e16295.png">

### 务必注意，密文关键字与明文关键字不可以重合，重合会出现很多问题
#### 修改任何配置以后都要去保存配置，否则不生效
#### 修改任何配置以后都要去保存配置，否则不生效
#### 修改任何配置以后都要去保存配置，否则不生效

3. 有代码基础的建议使用接口进行加解密，可以自定义请求的内容，自由替换；工具本身自带加解密满足不了现在大部分应用的加解密需求。

4. 可以参考下列文章进行使用，配合加解密靶场 https://github.com/SwagXz/encrypt-labs
   - 加密对抗靶场enctypt-labs通关  https://flowus.cn/share/a1d890c2-f72b-4534-93cf-37db6dab5d21
   - 加密对抗靶场enctypt-labs通关  https://mp.weixin.qq.com/s/ZgD7qAQAsNlZgLtdZVCoFw

## 0x02.在`repeater`模块出现解密错误问题

进入`Burp Suite`的`logger`模块(或者`logger++`模块)查看当前请求的实际数据包(在`repeater`内的数据包被autoDecoder处理过，所以会提示解密失败)，进而分析是什么原因

1. 可能使用了工具自带的加解密，目前工具仅支持请求包、响应包同时加解密，一旦响应包为非加密的，就会报解密的错误，而使用接口加解密，就可以很好解决此问题
2. 使用了接口加解密，考虑代码编写问题，如函数的入参、函数的返回值等错误



## 0x03.接口脚本参数

参考示例见[flasktestheader.py](https://github.com/f0ng/autoDecoder/blob/main/flasktestheader.py)(处理请求头)与[flasktest.py](https://github.com/f0ng/autoDecoder/blob/main/flasktest.py)(DES加解密示例)脚本

参数解释如下：

### 函数入参为

#### 1.`dataBody` 请求体，传参为flask框架传参，使用`body = request.form.get('dataBody')`获取，以下示例为整个flask对象[打印request.form]：

正常POST格式(application/x-www-form-urlencoded)

`ImmutableMultiDict([('dataBody', 'a=1&b=2&c=3')])`(原始数据为a=1&b=2&c=3)

JSON格式(application/json)

`ImmutableMultiDict([('dataBody', '{"f0ng":"onlysecurity"}')])`(原始数据为{"f0ng":"onlysecurity"})等



#### 2.`dataHeaders `请求头，使用`headers = request.form.get('dataHeaders')`获取，以下示例为整个flask对象[打印request.form]：

```java
ImmutableMultiDict([('dataBody', '{"id":"1"}'), ('dataHeaders', 'POST /0828/testsql.php HTTP/1.1\r\nHost: 10.211.55.4\r\nUser-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:104.0) Gecko/20100101 Firefox/104.0\r\nAccept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8\r\nAccept-Language: zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2\r\nAccept-Encoding: gzip, deflate\r\nConnection: keep-alive\r\nUpgrade-Insecure-Requests: 1\r\nContent-Type: application/x-www-form-urlencoded\r\nContent-Length: 39\r\n')])
```

原始数据为（数据包的换行分隔符为`\r\n`）

```
POST /0828/testsql.php HTTP/1.1
Host: 10.211.55.4
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:104.0) Gecko/20100101 Firefox/104.0
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8
Accept-Language: zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2
Accept-Encoding: gzip, deflate
Connection: keep-alive
Upgrade-Insecure-Requests: 1
Content-Type: application/x-www-form-urlencoded
Content-Length: 39

{"id":"1"}
```

获取相应参数的代码如下（flask框架的读取参数方法，也可以用其他框架，这里仅以flask进行举例）：

```
body = request.form.get('dataBody')  # 获取  post 参数 必需
headers = request.form.get('dataHeaders')  # 获取  post 参数  可选
```

#### 3.`requestorresponse`标识为请求包还是响应包(可选为request和response)，使用`reqresp = request.form.get('requestorresponse')`获取参数，以下示例为整个flask对象[打印request.form]：
```Java
ImmutableMultiDict([('dataBody', 'dCtLdlmk7wI='), ('requestorresponse', 'response')])
```


### 函数出参为

#### 4.1`body`(当未勾选对请求头加密时)

传入的为请求体，传出的为处理后的请求体

```
I9z1fsH5QQ2NUbJi/7a8lw==
```



#### 4.2.`headers + "\r\n\r\n\r\n\r\n" + body`(当勾选对请求头加密时)

传入的为请求头与请求体，传出的为处理后的请求头与请求体（这里未处理请求体，所以请求体是未处理过的）

这里的`\r\n\r\n\r\n\r\n`是为了区分开headers与body，是必需的

```
POST /0828/testsql.php HTTP/1.1
Host: 10.211.55.4
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:104.0) Gecko/20100101 Firefox/104.0
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8
Accept-Language: zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2
Accept-Encoding: gzip, deflate
Connection: keep-alive
Upgrade-Insecure-Requests: 1
Content-Type: application/x-www-form-urlencoded
Content-Length: 39
aaaa:bbbb
f0ng:test



{"id":"1"}
```


## 0x04 项目不支持`pkcs7`加密？

实际上是已经实现的，因为AES并没有64位的块, 如果采用PKCS5, 那么实质上就是采用PKCS7

## 0x05 在burp中不出现`autoDecoder`选项卡?

需要配置生效的域名

## 0x06 使用二进制base64后数据被损坏

需要修改一下burp的设置，具体修改如下：

- 勾选User options->Character Sets中的`Use the platform default(UTF-8)`[Mac]，或者勾选`Display as raw bytes`[Windows]


~~## 0x07 调试模块有换行存在~~ 0.31版本删除

~~这里是为了方便调试，自动加的换行，在repeater、intruder模块是没有换行的，所以不必担心~~


## 0x08 调试模块可以正常进行加解密，但是在repeater、proxy模块没有获取正常变量(没有正常进行加解密)

1. 调试模块是textarea数据包是字符串格式，而在repeater里是http包格式，所以在repeater、proxy模块中会有\r\n，在获取字符串的时候可以进行\r\n的替换即可
2. 关键字问题，调试模块不会受到关键字的影响，只会根据使用者的按钮进行加密、解密，而在实际运用中，在repeater、proxy模块，会受到关键字的影响

## 0x09 明文关键字、密文关键字如何理解

- 明文关键字

即解密后是明文的关键字，可以配合手工进行测试，当标识为明文关键字后，手动请求明文，会经过插件加密为密文再发送到服务端，服务端返回密文，插件自动解密至选项卡，做到明文请求，明文响应
，明文关键字可以是明文框里的任意一个字符，但是为了和字符区分开，一般取 { 或者 " 这种json数据的

- 密文关键字
  
即标示为密文的关键字，这里设置了密文关键字以后，任何标示为密文的请求体就不会进行加密，防止二次加密的产生，比如上图中的，就无法进行密文关键字标识，因为密文是字符，不可预测

两个关键字设置的初衷，就是为了在同一种环境中，既有加密的包，又有不加密的包，来进行区分；也为了在加密环境下，应用程序会取上一个数据包的密文作为下一个数据包的参数进行发送请求，如果自动解密成明文下一个数据包的参数就无法获取，导致应用程序无法正常运行，而设置了明文关键字、密文关键字，可以很好解决该问题

## 0x10 明文修改后进行发包，但是请求包返回原来的明文

问题出现于2021.10.1以后(可能往前几个版本也有)，目前不知道什么原因，解决方法是把数据包粘贴在repeater模块的`raw`中，进行发包，不要在`autodecoder`选项卡中发包



~~0x11 自带的sm4加解密有问题？~~  

#### 0.50版本修复，之前的原因在于 JCE cannot authenticate the provider BC ，故抛弃三方库进行实现，完美避免这个报错

~~sm4模块确实有问题，特别是一些不可见字符的，目前用的是原生的代码实现的，用不了现成的三方库，用sm4的建议还是用接口加解密~~


## 0x12 正则模块使用问题

正则模块只对请求体、响应体有效，不作用于数据头，且正则需要按照案例中正则进行参考编写


## 0x13 插件是不是不支持识别get参数?(插件是不是没办法获取数据包的header?)

勾选对数据头进行处理，python-flask代码中示例`dataHeaders `请求头，使用`headers = request.form.get('dataHeaders')`获取


## 0x14 插件是不是不支持并发？

理论上支持，但是由于代码处理密文数据、明文数据需要时间，所以高并发【burp默认线程这种】会导致加密/解密报错，建议用低并发进行处理

## 0x15 使用接口加解密得到的数据包会多一个换行

可以检查headers或者body是否使用`strip()【python语言】`这种去除首尾换行函数进行处理了，可以参考模板文件`flasktest.py`和`flasktestheader.py`

