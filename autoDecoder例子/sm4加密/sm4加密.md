# sm4加解密的例子
#autoDecoder例 
遇到了一个sm4加解密的，这里简单的实现一下
`JavaScript`代码如下：
```javascript
//sm4转换

const stringToByte = function (str) {
var len, c
len = str.length
var bytes = []
for (var i = 0; i < len; i++) {

	c = str.charCodeAt(i)

if (c >= 0x010000 && c <= 0x10FFFF) {

	bytes.push(((c >> 18) & 0x07) | 0xF0)
	
	bytes.push(((c >> 12) & 0x3F) | 0x80)
	
	bytes.push(((c >> 6) & 0x3F) | 0x80)
	
	bytes.push((c & 0x3F) | 0x80)

} else if (c >= 0x000800 && c <= 0x00FFFF) {

	bytes.push(((c >> 12) & 0x0F) | 0xE0)
	
	bytes.push(((c >> 6) & 0x3F) | 0x80)
	
	bytes.push((c & 0x3F) | 0x80)

} else if (c >= 0x000080 && c <= 0x0007FF) {

	bytes.push(((c >> 6) & 0x1F) | 0xC0)
	
	bytes.push((c & 0x3F) | 0x80)

} else {

	bytes.push(c & 0xFF)

}

}

return new Int8Array(bytes)

}

  
const sm4 = require('sm-crypto').sm4
const encryptData = '77327a37ff72f97ea72031861b20bbcd652284611571a0c06edfaeba4e405643' // 可以为 16 进制串或字节数组

const key = stringToByte("onlysecurityf0ng") // 可以为 16 进制串或字节数组，要求为 128 比特
let decryptData = sm4.decrypt(encryptData, key, {
output: 'array'
}) // 解密，默认输出 utf8 字符串，默认使用 pkcs#7 填充（传 pkcs#5 也会走 pkcs#7 填充）

decryptData = Buffer.from(decryptData)

decryptData = decryptData.toString('utf-8')

console.log(decryptData)

let Data = sm4.encrypt(decryptData,key)

console.log(Data)
```
运行代码：
![800](photo/Pasted%20image%2020220428215242.png)
`{"f0ng":"onlysecurity"}`为明文，`77327a37ff72f97ea72031861b20bbcd652284611571a0c06edfaeba4e405643`为密文
这里只是通过js实现了加解密，按照之前的经验，我们是通过flask进行加解密的，所以需要转换成python代码，这里用到了python的`execjs`模块，代码如下：
```Python
# -*- coding:utf-8 -*-  
# author:f0ngf0ng  
# @Date: 2022/4/28 下午5:14  
import execjs  
ctx = execjs.compile("""  
const sm4 = require('sm-crypto').sm4 //引入请求加密算法  
  
//sm4转换  
const stringToByte = function (str) {  
  var len, c  len = str.length  
  var bytes = []  
  for (var i = 0; 
  i < len; i++) {    
	  c = str.charCodeAt(i)    
	  if (c >= 0x010000 && c <= 0x10FFFF) {      
	  bytes.push(((c >> 18) & 0x07) | 0xF0)      
	  bytes.push(((c >> 12) & 0x3F) | 0x80)      
	  bytes.push(((c >> 6) & 0x3F) | 0x80)      
	  bytes.push((c & 0x3F) | 0x80)    
	  } else if (c >= 0x000800 && c <= 0x00FFFF) {      
	  bytes.push(((c >> 12) & 0x0F) | 0xE0)      
	  bytes.push(((c >> 6) & 0x3F) | 0x80)      
	  bytes.push((c & 0x3F) | 0x80)    
	  } else if (c >= 0x000080 && c <= 0x0007FF) {      
	  bytes.push(((c >> 6) & 0x1F) | 0xC0)      
	  bytes.push((c & 0x3F) | 0x80)    
	  } else {      
	  bytes.push(c & 0xFF)    
	  }  
	  }  
	  return new Int8Array(bytes)
	  }  
  
const decrypt = function ( str) {  
  let key = 'onlysecurityf0ng'  
  let decryptData = sm4.decrypt(str, stringToByte(key), {    
  output: 'array'  })  
  decryptData = Buffer.from(decryptData)  
  decryptData = decryptData.toString('utf-8')  
  console.log('解密-------：' + decryptData)  
  return decryptData}  
  
//sm4 加密 flag是否白名单  
const encrypt = function ( str) {  
  let key = 'onlysecurityf0ng'  
  let arrayData = JSON.stringify(str)  
  let encryptData = sm4.encrypt(arrayData, stringToByte(key))  
  //console.log('加密------：' + encryptData)  
  return encryptData}  
  
""")  
# print(ctx.call("stringToByte","onlysecurityf0ng"))  
  
print(ctx.call("decrypt" ,"77327a37ff72f97ea72031861b20bbcd652284611571a0c06edfaeba4e405643"))  
print(ctx.call("encrypt",{"f0ng":"onlysecurity"}))
```
![800](photo/Pasted%20image%2020220428215743.png)
这里就简单做好了，但是转念一想，`autoDecoder`是适配各种各样的接口的，那不如就用node写一个http请求进行加解密的接口呢
说干就干，直接码代码
nodejs服务代码如下：
```JavaScript
var http = require('http');

const url = require('url');

const querystring = require('querystring');

//sm4转换

const stringToByte = function (str) {

var len, c

len = str.length

var bytes = []

for (var i = 0; i < len; i++) {

c = str.charCodeAt(i)

if (c >= 0x010000 && c <= 0x10FFFF) {

bytes.push(((c >> 18) & 0x07) | 0xF0)

bytes.push(((c >> 12) & 0x3F) | 0x80)

bytes.push(((c >> 6) & 0x3F) | 0x80)

bytes.push((c & 0x3F) | 0x80)

} else if (c >= 0x000800 && c <= 0x00FFFF) {

bytes.push(((c >> 12) & 0x0F) | 0xE0)

bytes.push(((c >> 6) & 0x3F) | 0x80)

bytes.push((c & 0x3F) | 0x80)

} else if (c >= 0x000080 && c <= 0x0007FF) {

bytes.push(((c >> 6) & 0x1F) | 0xC0)

bytes.push((c & 0x3F) | 0x80)

} else {

bytes.push(c & 0xFF)

}

}

return new Int8Array(bytes)

}

const sm4 = require('sm-crypto').sm4

const key = stringToByte("onlysecurityf0ng") // 可以为 16 进制串或字节数组，要求为 128 比特

  
  

http.createServer(function (req, res) {

let path = url.parse(req.url);

let postparms = '';

if (path.pathname === '/encode') {

console.log("encode路由");

req.on('data', (parms) => {

postparms += parms;

})

req.on('end', () => {

postparms = querystring.parse(postparms);

console.log(postparms)

let dataBody = postparms.dataBody;

let Data = sm4.encrypt(dataBody,key)

console.log(Data)

res.end(Data);

})

} else if (path.pathname === '/decode') {

console.log("decode路由")

req.on('data', (parms) => {

postparms += parms;

})

req.on('end', () => {

postparms = querystring.parse(postparms);

console.log(postparms)

let dataBody = postparms.dataBody;

const encryptData = dataBody

let decryptData = sm4.decrypt(encryptData, key, {

output: 'array'

}) // 解密，默认输出 utf8 字符串，默认使用 pkcs#7 填充（传 pkcs#5 也会走 pkcs#7 填充）

decryptData = Buffer.from(decryptData)

// console.log(decryptData)

decryptData = decryptData.toString('utf-8')

console.log(decryptData);

res.end( decryptData );

})

  

} else{

res.write("end");

res.end()

  

}

}).listen(8888);
```
`autoDecoder`配置如下
![800](photo/Pasted%20image%2020220429154939.png)
原始请求如下：
![800](photo/Pasted%20image%2020220429154955.png)

当请求为明文：
![800](photo/Pasted%20image%2020220429155949.png)
node服务端日志：
![600](photo/Pasted%20image%2020220429160015.png)


