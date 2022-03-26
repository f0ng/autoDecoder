# autoDecoder
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


