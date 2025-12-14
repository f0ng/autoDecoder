# autoDecoder

[![Repo stars](https://img.shields.io/github/stars/f0ng/autoDecoder)](https://github.com/f0ng/autoDecoder/stargazers)
[![Downloads total](https://img.shields.io/github/downloads/f0ng/autoDecoder/total?label=Downloads)](https://github.com/f0ng/autoDecoder/releases)
[![Repo tags](https://img.shields.io/github/v/tag/f0ng/autoDecoder?label=Latest)](https://github.com/f0ng/autoDecoder/tags)
[![Downloads latest total](https://img.shields.io/github/downloads/f0ng/autoDecoder/latest/total?label=Downloads@latest)](https://github.com/f0ng/autoDecoder/releases)

I want to maintain this project with many use cases and interfaces. I hope everyone with encryption/decryption needs can communicate together to improve this project.

----



Target audience: Masters with some coding foundation, or those without foundation but willing to learn and tinker with automatic encryption/decryption.



----- 

# For autoDecoder examples, please refer to [autoDecoder-usages](https://github.com/f0ng/autoDecoder-usages), which will be helpful for many who want to write interfaces themselves.


----- 

### AutoDecoder English/Chinese Language Option

This project is primarily used in Chinese as the main language of the plugin. I've edited this project in both Chinese and English. The core code structure hasn't been changed.

https://github.com/f0ng/autoDecoder

#### English
<img width="1785" height="946" alt="image" src="https://github.com/user-attachments/assets/4ea44e96-5cbe-4956-9300-a3af0a79ce38" />


#### Chinese 
<img width="1602" height="915" alt="image" src="https://github.com/user-attachments/assets/58995b49-e331-4f0c-809e-2f79d7d4e059" />

----- 

##### Plugin Compilation Command

```mvn -DskipTests clean package```

-----

# Do not casually modify the interface code template for obtaining parameters and return formats, otherwise garbled characters or inability to run normally may occur.

Communication Group

<img width="183" alt="image" src="https://user-images.githubusercontent.com/48286013/220634169-ddefd4b2-d967-4a85-8b28-b626ba366742.png">

If the QR code expires, please add WeChat `f-f0ng`, note autoDecoder communication.

Follow the main WeChat official account (only security), reply `autodecoder` to get the download address]

# Donation (If the project helps you, you can choose to donate some funds for the maintenance of subsequent versions of autoDecoder, this project is maintained long-term)

<img width="251" alt="image" src="https://github.com/user-attachments/assets/f0308410-8094-47ec-9448-8ef1b8ec94d6" />

<img width="251" alt="image" src="https://github.com/f0ng/autoDecoder/assets/48286013/e9318b91-2521-4c14-93d8-9737fd7a4729">

## If you have problems, first check [FAQ](https://github.com/f0ng/autoDecoder/blob/main/FAQ.md)

## Simple Flowchart

<table rules="none" align="center">
	<tr>
		<td>
			<center>
    <font color="AAAAAA">Normal Flowchart</font>
				<img src="https://user-images.githubusercontent.com/48286013/188769341-a5ad035b-2f0b-4fa1-87e1-342cd800dd46.jpg" width="110%" />
				<br/>
			</center>
		</td>
		<td>
			<center>
    <font color="AAAAAA">Process handled by autoDecoder for ciphertext</font>
				<img src="https://user-images.githubusercontent.com/48286013/188769399-5509ca25-9f23-4abc-8074-ff410534cf73.jpg" width="100%" />
				<br/>
			</center>
		</td>
   		<td>
			<center>
    <font color="AAAAAA">Process handled by autoDecoder for plaintext</font>
				<img src="https://user-images.githubusercontent.com/48286013/188770030-b752aae9-dc8a-45a5-b1c6-b45b2995e8f1.jpg" width="100%" />
				<br/>
			</center>
		</td>
	</tr>
</table>

## 2025.11.19  update 0.57
1. Modify the request header keywords to add support for built-in encryption and decryption
2. Optimize the extension tab to adapt to the request header method with built-in encryption and decryption for decryption
3. Add confirmation for encryption/decryption method selection when saving configurations
4. Fixed the issue where the unknown version could not display the save configuration interface
5. Optimize the arrangement of plugin buttons to prevent mixed usage

## 2025.11.19 Update 0.56
1. Update the English version, thank you very much @secfb # 48
2. Fix sm2 decryption issue # 47
3. Add request body base64 automatic decoding, adapt encrypted content to binary data, thank you to WeChat group master @满溢

## 2025.7.24 Update 0.55

1. Added matching for header in built-in encryption/decryption mode [including matching for GET request parameters]

## 2025.7.8 Update 0.54

1. Fixed bug where built-in encryption/decryption requests and responses used different encryption/decryption methods

## 2025.4.3 Update 0.53

1. Fixed newline bug [When writing debug module, unified return format, causing the number of newlines to be the same whether header is checked or not]

## 2024.12.30 Update 0.52 Happy New Year to everyone!

1. Interface debugging module adds keyword substitution mode to prevent errors caused by keywords
2. Interface mode encryption/decryption will echo errors, so you can know what specific problem caused the encryption/decryption to fail

When the return packet format is wrong

<img width="500" alt="gagsjjcg o4n" src="https://github.com/user-attachments/assets/306ae42b-8079-48f8-8d28-b4359c5f06c7" />

When the interface is not connected

<img width="400" alt="i3w34g2g f2x" src="https://github.com/user-attachments/assets/3578d919-140c-42ba-86ed-c26a2e54ef71" />

You can also check error information on the Extensions page

<img width="400" alt="wtc30zsn qak" src="https://github.com/user-attachments/assets/7a226abd-0d57-4d41-9b3a-062d696a7720" />

## 2024.10.12 Update 0.51

1. Fixed host matching issue in replacement module
2. Optimized replacement module, added automatic unicode decoding mode, needs to be Literal mode, Replace needs to be `#unicode#`, unicode encoded content in response packet will be automatically decoded
   <img width="995" alt="x1u2kzeg nao" src="https://github.com/user-attachments/assets/210e22e4-534c-42c8-8c4a-7c8dcf5f2411">

## 2024.9.24 Update 0.50

1. Fixed sm4 encryption/decryption error

## 2024.5.5 Update 0.40

1. Fixed sm4 encryption/decryption bug (base64)
2. Added ciphertext keyword judgment for response body

## 2024.3.17 Update 0.39-beta1

1. Fixed encryption bug (SM4 and AES)

## 2024.3.13 Update 0.39

1. Fixed base64 encryption/decryption bug in SM4
2. Fixed base64 decryption bug in AES/DES encryption

## 2024.2.2 Update 0.38

1. Optimized SM4, SM2 encryption/decryption
2. Optimized regular expression matching and replacement

## 2024.1.2 Update 0.37 First update of the new year, wish everyone a happy new year!

1. Built-in encryption/decryption adds sm2, sm4 (CBC, ECB) encryption/decryption
2. Regular expressions are saved to configuration file

## 2023.12.18 Update 0.36

1. Fixed the problem that the replacement switch was selected by default
2. Interface encryption/decryption debugging optimization
3. Optimized header keyword judgment

## 2023.11.22 Update 0.35

1. Added load configuration file and save configuration file module, subsequent use can load configuration according to the naming of the configuration file
2. Added request and response replacement function, similar to burp's `Match and Replace` module, added extraction module, configuration example as follows

<img width="1187" alt="image" src="https://github.com/f0ng/autoDecoder/assets/48286013/a5d82e7b-5db3-43e8-90d1-863f0d1629a5">

Normal response

<img src="https://github.com/f0ng/autoDecoder/assets/48286013/dacf44a6-3687-4eab-b716-2186a25f538d" width="450" />

Modified response

<img src="https://github.com/f0ng/autoDecoder/assets/48286013/c1a33b8a-9d2a-4469-ac8a-9cd709bab286" width="450" />

## 2023.11.11 Update 0.34

1. Added header keyword judgment

## 2023.10.23 Update 0.33

1. Added automatic base64 decoding for response, when the response packet returns base64, it can be automatically decoded to prevent binary data packet corruption
2. Added option saving and reading

## 2023.9.16 Update 0.32

1. Optimized settings for plaintext keywords and ciphertext keywords

## 2023.9.5 Update 0.31

1. In the domain name matching module, multi-domain matching is possible, fixed the problem that the extension tab could not be displayed under multiple domains in the original version
2. Removed two newline characters in the interface debugging module

## 2023.7.6 Update 0.30

1. In `Repeater, Intruder` modules, added right-click encryption and decryption, when the corresponding method is set, the body of the request can be encrypted and decrypted
2. Fixed the problem that `Send to Repeater, Send to Intruder` would not bring the port number

## 2023.5.22 Update 0.27

1. autoDecoder extension tab adds right-click `Send to Repeater, Send to Intruder`, and adds formatting, currently only supports json format

## 2023.5.17 Update 0.26

1. Fixed the problem that request headers were missing after checking `Process request headers`
2. Added ciphertext URL decoding reading and encrypted ciphertext URL decoding options
3. Fixed other issues, such as extension tabs not appearing when specifying domain ports, Chinese decryption garbled on windows, etc.

Thanks to WeChat group yosel master for feedback

## 2023.4.25 Update 0.25

1. Added binary request body and response body processing, the principle is to base64 encode the content of the request packet and pass it to the interface, you need to set burp as follows, otherwise the encoding obtained will be damaged
   - You need to check User options->Character Sets `Use the platform default(UTF-8)` [MAC], or check `Display as raw bytes` [Windows]

## 2023.4.14 Update 0.24-beta2

1. RSA encryption/decryption
2. Response packet regular mode encryption/decryption

## 2023.4.12 Update 0.24-beta1

1. Truncate key according to encryption method
2. Fixed the problem that only selecting interface encryption/decryption could not perform encryption/decryption

## 2023.4.8 Update 0.24

1. Optimized test interface data packet display
2. Added zeropadding padding mode encryption
3. Optimized base64 decoding problem

## 2023.2.22 Update 0.23

1. Optimized some problems with the plugin
2. Cases moved to [autoDecoder-usages](https://github.com/f0ng/autoDecoder-usages)

## 2023.2.16 Update 0.22-beta1

1. Optimized the problem of removing `\u0000` when reading ciphertext
2. In the built-in algorithm, request packet encryption/decryption and response packet encryption/decryption are separated, you can select encryption algorithm `null` to indicate no encryption/decryption, return original data packet

## 2023.2.15 Update 0.22 Major update

1. Refactored UI page, the previous UI was too messy, spent some time refactoring the page UI
   <img width="583" alt="image" src="https://user-images.githubusercontent.com/48286013/219077310-7a17115b-885e-47ba-8aaf-427acb55db8a.png">
2. Added regular expression extraction for built-in algorithm encryption/decryption, effective when regular expression has content, for two situations:

   1. Only request encryption
   2. Encrypting username and password in brute force attacks

   Configuration page

   <img width="307" alt="image" src="https://user-images.githubusercontent.com/48286013/219077933-60e197ed-940-473e-a394-242aedc1cc41.png">

   <img width="852" alt="image" src="https://user-images.githubusercontent.com/48286013/219077869-b894b59e-eed3-48ff-bda4-ebf4f231e2e4.png">

   Original request

   <img width="696" alt="image" src="https://user-images.githubusercontent.com/48286013/219077437-c5e793ee-9061-49ca-aadc-8061f9843c21.png">

   Real request

   <img width="611" alt="image" src="https://user-images.githubusercontent.com/48286013/219077506-f81555ef-5d8f-44c3-a659-41c6afc0a50f.png">

   After decryption

   <img width="628" alt="image" src="https://user-images.githubusercontent.com/48286013/219077634-92366e38-5176-4ee1-b7ad-b7de7bee96cf.png">
3. Added debugging page when interface encryption/decryption, convenient for further code writing

Normal decryption request packet

<img width="771" alt="image" src="https://user-images.githubusercontent.com/48286013/219079024-ef8fb2ce-0286-4a18-9e38-bdbe85ec742c.png">

Normal decryption response packet

<img width="747" alt="image" src="https://user-images.githubusercontent.com/48286013/219080446-5c135166-63cb-4c2e-ad66-0e57df1a39f2.png">

Process request headers

<img width="759" alt="image" src="https://user-images.githubusercontent.com/48286013/219079094-53e6f652-5038-4242-aa5f-0955310b6998.png">

## 2023.2.14 Update 0.21

1. Added burp module button to prevent conflicts with requests generated by other modules (such as plugin Extender)
2. Optimized plugin decryption reading ciphertext method, read after URL decoding

## 2023.1.5 Update 0.20

1. Added `RSA decryption` an example
2. Optimized tab data packet settings, does not affect the data packet content of repeater itself

<table rules="none" align="center">
	<tr>
		<td>
			<center>
    <font color="AAAAAA">Decrypt request packet</font>
				<img src="https://user-images.githubusercontent.com/48286013/210718969-8958c40d-cbee-47e7-8e97-97f6ba17b4e6.png" width="100%" />
				<br/>
			</center>
		</td>
		<td>
			<center>
    <font color="AAAAAA">Decrypt request packet does not affect original request packet</font>
				<img src="https://user-images.githubusercontent.com/48286013/210718946-888dc52f-a882-4026-ba92-2845a7ac81f7.png" width="100%" />
				<br/>
			</center>
		</td>
	</tr>
</table>

## 2022.9.7 Update 0.19

- Added different encryption algorithm buttons for request packet and response packet [Only for interface encryption/decryption mode], for request packet and response packet using different encryption algorithms (implementation method: when requesting decryption interface, pass parameter `requestorresponse` at the same time, indicating request [`request`] or response [`response`])

<img width="800" alt="image" src="https://user-images.githubusercontent.com/48286013/188768352-f7d33440-0f13-4abe-bc6c-4cdb7abfd8ee.png">

- Modified ui, optimized the problem that `html` could not be parsed on the new version of bp

## 2022.5.18 Update 0.18

- Added `3DES encryption`, `AES encryption`, `JSON nested encryption` three examples
- When sending plaintext request, if the response packet selects the default tab, it has no effect; but when the response packet selects the extension tab, it will also decrypt, causing the response body in the request packet to report an error, `0.18` fixes this problem: plaintext request, response packet only plaintext.
- Added ciphertext keyword, if this keyword appears, do not encrypt, you can check the example json nested encryption

## 2022.5.15 Update 0.17

- Optimized Desede (3DES) encryption processing problem, 3DES encryption key is 24 bits, when input exceeds 24 bits, it will report key length error, the processing method is to truncate the key length

## 2022.5.11 Update 0.16

1. Added response header processing, the incoming parameter is also `dataHeaders`

```
# -*- coding:utf-8 -*-
# author:f0ngf0ng

from flask import Flask,Response,request
from pyDes import *
import base64
app = Flask(__name__)

@app.route('/encode',methods=["POST"])
def encrypt():
    body = request.form.get('dataBody')  # Get post parameter required
    headers = request.form.get('dataHeaders')  # Get post parameter optional

    if headers != None: # Request header encryption enabled
        headers = headers + "aaaa:bbbb\r\n"
        headers = headers + "f0ng:test"
        print(headers + "\r\n\r\n\r\n\r\n" + body)
        return headers + "\r\n\r\n\r\n\r\n" + body # Return value is fixed format, cannot be changed

    return  body

@app.route('/decode',methods=["POST"]) # No decryption
def decrypt():
    body = request.form.get('dataBody')  # Get post parameter required
    headers = request.form.get('dataHeaders')  # Get post parameter optional
    if headers != None: # Response header encryption enabled
        print(headers + "\r\n\r\n\r\n\r\n" + body)
        headers = headers + "yyyy:zzzz\r\n"
        headers = headers + "f0ng:onlysecurity"
        return headers + "\r\n\r\n\r\n\r\n" + body # Return value is fixed format, cannot be changed

    return body

if __name__ == '__main__':
    app.debug = True # Set debug mode, turn off debug in production mode
    app.run(host="0.0.0.0",port="8888")
```

Original request response

<img width="380" alt="image" src="https://user-images.githubusercontent.com/48286013/167889115-6b4a45ac-0acf-4220-8263-d8497c446a78.png">

Response after autoDecoder processing

<img width="380" alt="image" src="https://user-images.githubusercontent.com/48286013/167889126-6f3d57cd-9ec9-4796-a442-a730b24865ac.png">

2. Fixed error when request body is empty
3. Fixed the problem of adding an extra newline when keyword is empty

## 2022.5.7 Update 0.15

* Optimized port problem in setting domain, two modes:

① Only enter domain name, match domain name and any port number

<img width="330" alt="image" src="https://user-images.githubusercontent.com/48286013/167258838-6a10e777-5253-4b21-be26-80acf7343592.png">

Match all `www.baidu.com:port number`, such as `www.baidu.com:8080`, `www.baidu.com:8088`

② Enter domain name and port number, match unique domain name and port number host

<img width="354" alt="image" src="https://user-images.githubusercontent.com/48286013/167258977-a897288a-61b0-4838-ae2c-3d8bb945925b.png">

Only match `www.baidu.com:8080`

## 2022.4.26 Update 0.14

1. Added processing for the entire request packet, how to modify specifically, customized according to different personal needs, template file is `flasktestheader.py`

Configuration as follows:

<img src="https://user-images.githubusercontent.com/48286013/165212779-88601efd-d11d-44ff-bd2a-74fc8efee915.png" width="700" />

Original request packet, capture the entire request as follows, add additional request headers `aaaa:bbbb`, `f0ng:test` to the request packet

<img src="https://user-images.githubusercontent.com/48286013/165212786-17579322-9c9d-411f-964e-931608184f72.png" width="700" />

Actual request packet

<img src="https://user-images.githubusercontent.com/48286013/165212793-b267a7ea-b965-4ed8-80f7-3bf1ca428fa8.png" width="700" />

2. Added custom setting plaintext keyword, when the corresponding keyword appears in the request body, the data packet will not be processed, judged by `contains`

## 2022.4.22 Update 0.13

1. The processing of `\r\n` request packets was not perfect, version 0.13 fixes this problem
2. Added case 1 `login brute force ldap md5 encryption`, case 2 `sql injection bypass sqlmap data packet newline problem`, to better use the tool for penetration testing

## 0x01 Background

- When all data packets are ciphertext, we have no way to start; even if we obtain some key information about encryption/decryption, we can decrypt it, but we need to decrypt each data packet slowly, request packets need to decrypt, response packets also need to decrypt, quite troublesome
- Actually, taking the name auto is not really auto, the encryption/decryption algorithm still needs to be reversed by yourself, it's just semi-automatic relative to the ciphertext in the data packet

## 0x02 Advantages

- Plaintext send, plaintext response; ciphertext send, ciphertext response, without affecting the original communication packet, add a bp extension page to view plaintext information
- Custom encryption/decryption interface, when there is complex data encryption, you can write python code to encrypt/decrypt the interface, custom domain that needs encryption/decryption, ready to use

## 0x03 Plugin encryption/decryption method

- Directly use the plugin's built-in algorithm to encrypt/decrypt data packets (relatively simple, only supports some AES, DES, DESede encryption)
- Write api for encrypting/decrypting data packets through python's flask interface (not necessarily flask framework, you can also use other frameworks, as long as the interface address is correct and the encryption/decryption process is correct)

## 0x04 File introduction:

- `users.sql` is the database used for testing
- `testsql.php` is the page that encrypts request data, encrypts response data and has injection
- `flasktest.py` is the Python flask encryption/decryption interface written for the test file.

For detailed examples, please check the WeChat official account https://mp.weixin.qq.com/s/B-lBbVpJsPdCp1pjz2Rxdg

> ## Decrypt through built-in encryption/decryption algorithm

![Decrypt through built-in encryption/decryption algorithm](https://user-images.githubusercontent.com/48286013/160236779-9848060d-94ff-493d-ace5-44aa85e6e444.gif)

> ## Decrypt through flask interface

![Decrypt through flask interface](https://user-images.githubusercontent.com/48286013/160237163-cd57267e-7e11-49cb-8ec5-41a896f917bc.gif)

> ## sqlmap encryption/decryption

![sqlmap encryption/decryption](https://user-images.githubusercontent.com/48286013/160238771-dd33b0ef-1007-47a0-9659-c8a20ed6d89e.gif)
