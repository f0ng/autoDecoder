# AES加密

*使用nodejs实现了http接口*

明文为：
{"username":"f0ng","password":"onlysecurity"}

密文为:
1vwZCmsFRAq5njm+82Pq8sMud5TG0RD8Up2GKhpHIB5cdoDOLu2SPZ94uArMa+7b

这里还有一个小难点，账号登录的时候数据包为`DATA=1vwZCmsFRAq5njm+82Pq8sMud5TG0RD8Up2GKhpHIB5cdoDOLu2SPZ94uArMa+7b`
所以我们在代码处增加逻辑判断：
加密的时候，识别登录的明文关键字，这里为`password`，当存在`password`关键字的时候，自动加上`DATA=`
解密的时候，我们直接全局替换`DATA=`，有的话就会被替换，没有也不影响结果

ECB模式
密钥为`onlysecurityonlysecurityf0ngf0ng`

```Javascript
// 明文为 {"username":"f0ng","password":"onlysecurity"}  
// 密文为 1vwZCmsFRAq5njm+82Pq8sMud5TG0RD8Up2GKhpHIB5cdoDOLu2SPZ94uArMa+7b  
var http = require('http');  
const url = require('url');  
const querystring = require('querystring');  
var CryptoJS = require("./crypto-js/crypto-js");  
  
var key = CryptoJS.enc.Hex.parse("onlysecurityonlysecurityf0ngf0ng");  
  
  
function Encrypt(word) {  
    var srcs = CryptoJS.enc.Utf8.parse(word);  
    var encrypted = CryptoJS.AES.encrypt(srcs, key, {  
        mode : CryptoJS.mode.ECB,  
        padding : CryptoJS.pad.Pkcs7  
    });  
    return encrypted.toString();  
}  
  
function Decrypt(word) {  
    var decrypt = CryptoJS.AES.decrypt(word, key, {  
        mode : CryptoJS.mode.ECB,  
        padding : CryptoJS.pad.Pkcs7  
    });  
    return CryptoJS.enc.Utf8.stringify(decrypt).toString();  
}  
  
  
http.createServer(function (req, res) {  
  let path = url.parse(req.url);  
  let postparms = '';  
  if (path.pathname === '/encode') {  
      console.log("encode路由");  
    req.on('data', (parms) => {  
        postparms += parms;  
    });  
    req.on('end', () => {  
  
        postparms = querystring.parse(postparms);  
        console.log(postparms);  
        let dataBody = postparms.dataBody;  
        if (dataBody.indexOf("password") > 0) {  
            let Data = querystring.escape(Encrypt(dataBody)); //  querystring.escape url编码  
            console.log(Data);  
            Data = "DATA=" + Data;  
            res.end(Data);  
        } else {  
            // let Data = escape(Encrypt(dataBody));  
            let Data = Encrypt(dataBody);  
            console.log(Data);  
            // Data = "X-BASE-DATA=" + Data;  
            res.end(Data);  
    }  
    })  
} else if (path.pathname === '/decode') {  
    console.log("decode路由")  
    req.on('data', (parms) => {  
        postparms += parms;  
    })  
    req.on('end', () => {  
  
        postparms = querystring.parse(postparms);  
        let dataBody = postparms.dataBody;  
        dataBody = dataBody.replace("DATA=","");  
        console.log(dataBody);  
        dataBody = unescape(dataBody)  
        let decryptData = Decrypt(dataBody); // 解密，默认输出 utf8 字符串，默认使用 pkcs#7 填充（传 pkcs#5 也会走 pkcs#7 填充）  
        // decryptData = Buffer.from(decryptData)        // console.log(decryptData)        // decryptData = decryptData.toString('utf-8')  
        console.log(decryptData);  
        res.end( decryptData );  
    })  
} else{  
    res.write("end");  
    res.end()  
  
}  
}).listen(8888);
```

## 登录数据包
原始请求包：(明文)
![800](photo/Pasted%20image%2020220518225410.png)

实际请求包：(添加了`DATA=`关键字)
![800](photo/Pasted%20image%2020220518225429.png)

## 正常交互数据包
原始请求包：(明文)
![800](photo/Pasted%20image%2020220518225520.png)

实际请求包：
![800](photo/Pasted%20image%2020220518225533.png)


autoDecoder配置
![800](photo/Pasted%20image%2020220518225315.png)