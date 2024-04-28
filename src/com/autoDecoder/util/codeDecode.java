package com.autoDecoder.util;

//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
//import org.apache.commons.codec.binary.Base64;
import burp.BurpExtender;
import burp.IndexautoDecoder;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.ArrayUtils;

import java.net.URLDecoder;
//import java.util.Base64;
import org.apache.commons.codec.binary.Base64;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.Security;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static com.autoDecoder.util.RSAencode.decrypt;
import static com.autoDecoder.util.RSAencode.encrypt;


public class codeDecode {
    public static void main(String[] args) throws Exception {

//        String a = decryptKeyivmode("DL10Kvw9TGp/it/qR93PAIeTJhMnzp4gk2dfGYhnqxniTH1LVtWDaWwT8lQkqFWz",
//                "f0ngonlysecurity","","AES","ECB","PKCS5Padding","Base64","null");

//        String a = decryptKeyivmode("x2QJBmXFPlMwuLZyYPsDhQ==",
//                "1234123412341234","","AES","ECB","PKCS5Padding","Base64","Hex");


//        String a = decryptKeyivmode("0YeSXVqHwjal5pnnIi/CHas8iaQ04XNyjdECwFpkwyqQ7mukdE3FGp+fbqMPxzIDC3kH/TPCCQ8zuwlrvD8k98myBjYFzaNGgSSavL5dpRBAIJ8MQM49UAtNFxlY8Yyvdwxv+zjz2pPY3SqqXSXVhHICcQyfq8VFrxnZvlL45stGOUFmumiJkZYlVgm3V71t0DMG71FSKjHRmrYuxMiT5s7ms1MUayFqCpXN1PfzF5rtzPASXqpocyC/aNJcv14OzSgWzOTbq4q7XhOa6V+JrssGDS2RCZSHZfN5MmefOcxblzr2yi2Px7aUOhxGiVc/rvbGRSaFi+KMWaUnVbVDg/0EhYH7fvYBIMxZ95XHkz7xxBPOkCOHlxaasSsq+msr", "hj7x89H$yuBI0456","1234567812345678","AES","CBC","NoPadding","Base64","null");

//        String a = decryptKeyivmode("0424190E0E84283AAE491633AF803FFA77089B605D6498263ECC12D70FB57B113954D3C75BD98719B02459B11A17A0BACA9E5809EA9DFF2ED1D9783D23FF1FF08054413D27C79163E7965D1E4EE76CE6E6B6C0108F9B3241223B5524C141DEEE8762B3570697CB3B6F8905B9AB5FA131A4F85645192D9767B1CD1EA41EE88DD844154154CFBF1B2B4DDD85D7923CF66F0DF8ACEDAEB77A4BF679BC145D3DEBD87F4D56881D85E2729D27511AFCA317659948C88775C978295E5565464F5A32E72E83AB4285603DFC1D614E64508BD5120BAF04B2EDC87F90731C5B1CA53D57BB6A35FECD4C4E9A1D92BA548E9803F16EBF245F8C4D20025583060B69ABFCF6AA71E5C22091B4BA97A98B1D20C586BA03B8F1D2AEAEB11ED1611C1EC0FFA15EDBDF11DF021133B29C315BFD0AA745C286FD705D86468A5FAA51251DE62BD13819C9B518F80C971496A62D3D506CFB216DB6255081252758C448701331029AE17627F8F754B3AD1E065416F5A272CB2056E5C578153FCCA57085E5CDDE23C0A3A54CBF7DE68CEA6515711046D8FDE185C0FF2231BD7BD47F67AE88CB5F5437C1BF30B9C5411E3A2E297FC306861CEFB5BB1F655411EC825F40727ECEDA1448249145410E177EE79804",
//                "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEGjh3yHFUvIQNC0rz6Y5/2/nYS+6jtkfXqnzKVcjrL+h5nRBhWJ1T/xg1lhnTE367iaJ3xD31CDIf/wpVJj+IgQ==","MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQgAUtTjVTWPaE/pB27JTTN5Tdfe9IpEznGEy8uLmDDdXSgCgYIKoEcz1UBgi2hRANCAAQaOHfIcVS8hA0LSvPpjn/b+dhL7qO2R9eqfMpVyOsv6HmdEGFYnVP/GDWWGdMTfruJonfEPfUIMh//ClUmP4iB","SM2","CBC","PKCS5Padding","null","null");
        // sm4 解密
//        String a = decryptKeyivmode("YIfWxtJT+QFlRz81tog0fA==",
//                "HYyV5BxylpxmuIxJEdN2yg==","l/TbRpry3xWV9MgWZGZogw==","SM4","CBC","PKCS5Padding","Base64","Base64");
//
//        System.out.println("aaaaaaa  " + a);
//
//
//        System.out.println("hex加解密  " );
//        String aa = decryptKeyivmode("6087d6c6d253f90165473f35b688347c",
//                "HYyV5BxylpxmuIxJEdN2yg==","l/TbRpry3xWV9MgWZGZogw==","SM4","CBC","PKCS5Padding","Hex","Base64");
//
//        System.out.println("aaaaaaa  " + aa);



//        String b = decryptKeyivmode("43c31cd129034f2f23fc420809aebdd5f0ece482bf501e06fcdb5597198f6274",
//                "3d7bdda07326bb086de449838ec5c590","12345678","SM4","ECB","PKCS5Padding","Hex","Hex");
//
//        System.out.println("aaaaaaa  " + b);

//        String a = decryptKeyivmode("9Si6iHoRLv7B4Umd5SIpJxoAroDcHat8ZkbHfI/G3f8RuTCN9UWx3KXXdJI2CJQ9jS5gCS8/6Zo/+42v5XUnim9UCDKlcZ5Q28SLKjaCsao=",
//                "1234123412341234","1234123412341234","AES","CBC","PKCS5Padding","Base64","null");

        String a = decryptKeyivmode("02aa51879463f0e1b6ad85113a90e03582f9dfdc034f91bc65e7f7e43613f808fcb3c49768853c6d3f5ac8a34ef917f2c93d130bd443cd3762b4f283cf4e1bd2573dd0e5d39302d24a176db9c40396aaf7d843387def4db5b04bea6a877697de",
                "j6slh8xin3yfhqbxmx46n53p","j6slh8xin3yfhqbxmx46n53p","DESede","ECB","PKCS5Padding","Hex","null");

        System.out.println(a);

        System.out.println(a.length());

//        System.out.println(Arrays.toString(base64ToHex("ZmRkNzkyODAwY2JiZmY3NzE3ZmEzYjFiZGE1MTIwNTFhMTY0ZGY5MjZhZjRhYzZhZmYwNzIyNmZlYmY3YmJmY2MwNjNmZjRiNGY4NGM3NjlhMjZhN2Q5YmRkMjhlZDA0NTJhNjgzMjNkM2I4NzJhODIzNWU1MGEyYzRjN2Y5NDhmYzRkNTIwMzAxNWJiYzViNzQ0OWNjYThhMTg2ODM1Nw==")));

    }

//    public static byte[] decrypt2(byte[] array) {
//        try {
//            for (byte a : array)
//                System.out.println(a);
//            array = hex2byte(new String(array));
//
//            String key_str = "adDRX4Fo1G7C0m3bWqihLwZS2xzpK4sr";
//            String iv_str = "12101754";
////            byte[] iv = {-33, -80, 74, -41, 118, -52, 31, -7, 14, -94, 110, 68, 26, 57, 73, -37};
////            byte[] key = {-9, -35, -119, -127, -123, -98, 107, 41, 50, -57, 46, -35, -13, -102, -20, 20, 77, -44, 56, -106, -54, -107, 37, 47, 48, 41, 49, -120, -3, 3, 58, -68};
//            byte[] iv = toByteArray(iv_str);
//            byte[] key = toByteArray(key_str);
//            final IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
//            final SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
//            final Cipher instance = Cipher.getInstance("AES/CFB/NoPadding");
//            instance.init(2, secretKeySpec, ivParameterSpec);
////            return instance.doFinal(Arrays.copyOfRange(array, 16, array.length));
//            return instance.doFinal(array);
//        }
//        catch (Exception ex) {
//            ex.printStackTrace();
//            return null;
//        }
//    }

    public static byte[] base64ToHex(String base64String) {
        // 解码 Base64 字符串得到原始字节数据
        byte[] decodedBytes = Base64.decodeBase64(base64String);

        // 返回解码后的字节数据
        return decodedBytes;
    }

    public static byte[] hex2byte(final String s) {
        if (s == null) {
            return null;
        }
        final int length = s.length();
        if (length % 2 == 1) {
            return null;
        }
        final int n = length / 2;
        final byte[] array = new byte[n];
        for (int i = 0; i != n; ++i) {
            final int n2 = i * 2;
            array[i] = (byte)Integer.parseInt(s.substring(n2, n2 + 2), 16);
        }
        return array;
    }

    public static String decryptKeyivmode(String encryptedData,String sessionKey,String iv,String encodemode, String ivmode , String paddingmode ,String sSrcmode,String keyivmode) throws Exception {
        encryptedData = encryptedData.trim();
//        BurpExtender.stdout.println(encryptedData);
//        BurpExtender.stdout.println(sessionKey);
//        BurpExtender.stdout.println(iv);
//        BurpExtender.stdout.println(encodemode);
//        BurpExtender.stdout.println(ivmode);
//        BurpExtender.stdout.println(paddingmode);
//        BurpExtender.stdout.println(sSrcmode);
//        BurpExtender.stdout.println(keyivmode);
//
////        encryptedData = URLDecoder.decode(encryptedData, "utf-8");
//        if (IndexautoDecoder.getRadioButton11State())
//            encryptedData = URLDecoder.decode(encryptedData, "utf-8");

        Boolean zero = false;
        if  (paddingmode.equals("ZeroPadding")) {
            paddingmode = "NoPadding";
            zero = true;
        }

        if (encodemode.equals("null"))
            return encryptedData;
        else if (encodemode.equals("RSA")){
            try {

                return decrypt(encryptedData, iv);

            }catch (Exception e){
                return "RSA解密错误";
            }
        } else if (encodemode.equals("SM2")){
            try {
                SM2 sm2 = SmUtil.sm2(Base64.decodeBase64(iv), Base64.decodeBase64("MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEGjh3yHFUvIQNC0rz6Y5/2/nYS+6jtkfXqnzKVcjrL+h5nRBhWJ1T/xg1lhnTE367iaJ3xD31CDIf/wpVJj+IgQ=="));

                return StrUtil.utf8Str(sm2.decryptFromBcd(encryptedData, KeyType.PrivateKey));

            }catch (Exception e){
                e.printStackTrace();
                return "SM2解密错误";
            }
        } else if(encodemode.equals("SM4")){ // SM4
            try {
                byte[] data = new byte[0];
                byte[] aseKey = new byte[0];
                byte[] ivData = new byte[0];

                if (sSrcmode.equals("Base64")  ) {
//                    System.out.println(URLDecoder.decode(encryptedData, "utf-8").replace(" ", "+"));
//                    System.out.println(base64ToHex(URLDecoder.decode(encryptedData, "utf-8").replace(" ", "+")));
                    data = base64ToHex(URLDecoder.decode(encryptedData, "utf-8").replace(" ", "+"));
//                    System.out.println(Arrays.toString(data));
                }
                if ( sSrcmode.equals("null") || sSrcmode.equals("Hex")) {
                    data = (URLDecoder.decode(encryptedData, "utf-8").replace(" ", "+")).getBytes(StandardCharsets.UTF_8);
                }

//                if (sSrcmode.equals("Hex")) {
//                    data = hexToByteArray(encryptedData);
//                }
                if (keyivmode.equals("Base64")) {
                    aseKey = Base64.decodeBase64(sessionKey);
                    ivData = Base64.decodeBase64(iv);
                } else if (keyivmode.equals("Hex")) {

                    aseKey = hexToByteArray(sessionKey);
//                    System.out.println( "Hex " + aseKey);
                    if (ivmode.equals("ECB")) {}
                    else {
                        ivData = hexToByteArray(iv);
                    }
//                    if (ivmode.equals("ECB")) {
//
//                    } else {
//                        ivData = hexToByteArray(iv);
//                    }

                } else { // null
                    aseKey = sessionKey.getBytes(StandardCharsets.UTF_8);
                    if (ivmode.equals("ECB")) {

                    } else {
                        ivData = iv.getBytes(StandardCharsets.UTF_8);
                    }
                }
                if ( ivmode.equals("ECB") ) {
//                    String s = Util.byteToHex(data.getBytes());
//                System.out.println("原文" + s);
                    SM4Utils sm42 = new SM4Utils();
                    //sm4.secretKey = "JeF8U9wHFOMfs2Y8";
                    sm42.secretKey = Util.byteToHex(aseKey);
//                    System.out.println( "key  203  " + sm42.secretKey);
//                    System.out.println("204  " + new String(data,StandardCharsets.UTF_8)  );
                    sm42.hexString = true;
//                    System.out.println( "206 "+Util.byteToHex(data));
                    byte[] result ;
                    try {
                        result = sm42.decryptData_ECB(new String(data, StandardCharsets.UTF_8)).getBytes();
                    }catch (Exception e){
                        result = sm42.decryptData_ECB(Util.byteToHex(data)).getBytes();
                        return new String(result, StandardCharsets.UTF_8);
                    }
                    return new String(result, StandardCharsets.UTF_8);

                }else{ // CBC
                    String s = Util.byteToHex(encryptedData.getBytes());
//                System.out.println("原文" + s);
                    SM4Utils sm42 = new SM4Utils();
                    //sm4.secretKey = "JeF8U9wHFOMfs2Y8";
                    sm42.secretKey = Util.byteToHex(aseKey);
//                    System.out.println(sm42.secretKey);
                    sm42.hexString = true;
                    sm42.iv = Util.byteToHex(ivData);
//                    System.out.println("237  " +  s);
                    byte[] result ;
                    if (sSrcmode.equals("Base64")  ) {
                        result = sm42.decryptData_CBC(Util.byteToHex(data)).getBytes();
                    }else{
                        result = sm42.decryptData_CBC(new String(data, StandardCharsets.UTF_8)).getBytes();
                    }
                    return new String(result, StandardCharsets.UTF_8);
                }

            }catch (Exception e ){
                e.printStackTrace();
                return "SM4解密错误"+ Arrays.toString(e.getStackTrace());
            }






        } else { //aes des desede

            try {
                byte[] data = new byte[0];
                byte[] aseKey = new byte[0];
                byte[] ivData = new byte[0];

                if (sSrcmode.equals("Base64") || sSrcmode.equals("null")) {
//                    BurpExtender.stdout.println(URLDecoder.decode(encryptedData, "utf-8"));
                    data = Base64.decodeBase64(URLDecoder.decode(encryptedData, "utf-8").replace(" ", "+"));
//                    BurpExtender.stdout.println(Arrays.toString(data));
//                    data = Util.byteToHex(data).getBytes();
                }
                 if (sSrcmode.equals("Hex")) {
                    data = hexToByteArray(encryptedData);
                }
//                else data = encryptedData.getBytes(StandardCharsets.UTF_8);

                if (keyivmode.equals("Base64")) {
                    aseKey = Base64.decodeBase64(sessionKey);
                    ivData = Base64.decodeBase64(iv);


                } else if (keyivmode.equals("Hex")) {

                    aseKey = hexToByteArray(sessionKey);
//                    sessionKey = new String(aseKey);
//                    System.out.println(Arrays.toString(aseKey));
                    if ( ivmode.equals("ECB") ){

                    }else {
                        ivData = hexToByteArray(iv);
//                        iv = new String(ivData);
                    }

                } else{ // keyivmode为null的时候
                    if (encodemode.equals("DESede") ) {
                        if (sessionKey.length() >= 24) {
                            aseKey = sessionKey.substring(0, 24).getBytes();
                            ivData = iv.substring(0, 8).getBytes();
                        }else {
                            if (ivmode.equals("ECB")) {
                                aseKey = sessionKey.substring(0, 8).getBytes();
                            } else {
                                aseKey = sessionKey.substring(0, 8).getBytes();
                                ivData = iv.substring(0, 8).getBytes();
                            }
                        }
                    } else if(encodemode.equals("DES")){
                        if ( ivmode.equals("ECB") ){
                            aseKey = sessionKey.substring(0, 8).getBytes();
                            iv = "";
                        }else {
                            aseKey = sessionKey.substring(0, 8).getBytes();
                            ivData = iv.substring(0, 8).getBytes();
                        }
                    }else{ // AES
                        if ( ivmode.equals("ECB") ) {
                            aseKey = sessionKey.getBytes();
                        }else {
                            aseKey = sessionKey.getBytes();
                            ivData = iv.getBytes();
                        }
                    }
                }

                IvParameterSpec ivParameterSpec = new IvParameterSpec(ivData);
//                System.out.println(ivParameterSpec.toString());
                Cipher cipher = Cipher.getInstance(encodemode + "/" + ivmode + "/" + paddingmode);
                System.out.println(Arrays.toString(aseKey));
                Key sKeySpec = new SecretKeySpec(aseKey, encodemode);
                //cipher.init(Cipher.DECRYPT_MODE, sKeySpec, ivParameterSpec);// 初始化
                if (ivmode.equals("ECB") || ivmode.equals("GCM")) { // 如果为ECB、GCM模式，不进行偏移量加载
                    cipher.init(Cipher.DECRYPT_MODE, sKeySpec);
                } else {
                    System.out.println("311 "+ encodemode + "/" + ivmode + "/" + paddingmode);
                    cipher.init(Cipher.DECRYPT_MODE, sKeySpec, ivParameterSpec);
                }

                byte[] result = null ;
                if (zero) {
                    result = cipher.doFinal(formatWithZeroPadding(data, cipher.getBlockSize()));
                }else{
                    result = cipher.doFinal(data);
                }
//                BurpExtender.stdout.println(Arrays.toString(result));

                return new String(result,StandardCharsets.UTF_8);
            } catch (Exception e) {
                e.printStackTrace();
//                BurpExtender.stdout.println(Arrays.toString(e.getStackTrace()));
                return "解密错误，请确认选项无误!\n" + Arrays.toString(e.getStackTrace());
            }
        }

    }
    private static byte[] formatWithZeroPadding(byte[] data, final int blockSize) {
        final int length = data.length;
        final int remainLength = length % blockSize;

        if (remainLength > 0) {
            byte[] inputData = new byte[length + blockSize - remainLength];
            System.arraycopy(data, 0, inputData, 0, length);
            return inputData;
        }
        return data;
    }

//    /**
//     * 字符串转字节数组
//     * */
//    public static byte[] toByteArray(String hexString) {
//        hexString = hexString.toLowerCase();
//        final byte[] byteArray = new byte[hexString.length() / 2];
//        int k = 0;
//        for (int i = 0; i < byteArray.length; i++) {// 因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
//            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
//            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
//            byteArray[i] = (byte) (high << 4 | low);
//            k += 2;
//        }
//        return byteArray;
//    }

//    /**
//     * 字节数组转16进制
//     * @param bytes 需要转换的byte数组
//     * @return  转换后的Hex字符串
//     */
//    public static String bytesToHex(byte[] bytes) {
//        StringBuffer sb = new StringBuffer();
//        for(int i = 0; i < bytes.length; i++) {
//            String hex = Integer.toHexString(bytes[i] & 0xFF);
//            if(hex.length() < 2){
//                sb.append(0);
//            }
//            sb.append(hex);
//        }
//        return sb.toString();
//    }

    //hex 转字节数组
    public static byte[] hexToByteArray(String inHex){
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1){
//奇数
            hexlen++;
            result = new byte[(hexlen/2)];
            inHex="0"+inHex;
        }else {
//偶数
            result = new byte[(hexlen/2)];
        }
        int j=0;
        for (int i = 0; i < hexlen; i+=2){
            result[j]=hexToByte(inHex.substring(i,i+2));
            j++;
        }
        return result;
    }

    public static byte hexToByte(String inHex){
        return (byte)Integer.parseInt(inHex,16);
    }

//    public static AlgorithmParameters generateIv(byte[] iv) throws Exception{
//        AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
//        params.init(new IvParameterSpec(iv));
//        return params;
//    }
}
