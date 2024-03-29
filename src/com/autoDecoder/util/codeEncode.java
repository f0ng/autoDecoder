package com.autoDecoder.util;

import burp.BurpExtender;

import static com.autoDecoder.util.RSAencode.encrypt;
import static com.autoDecoder.util.codeDecode.*;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
//import java.util.Base64;
import burp.IndexautoDecoder;
import org.apache.commons.codec.binary.Base64;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class codeEncode {
    public static void main(String[] args) throws Exception {

//        String a = encryptKeyivmode("{\"id\":\"1''\"}",
//                "f0ngtestf0ngtest","f0ngf0ng","DESede","CBC","PKCS5Padding","Base64","无");
//        System.out.println(a);
//
//        String aa = encryptKeyivmode("{\"id\":\"1''\"}",
//                "f0ngtestf0ngtest","f0ngf0ng","DESede","CBC","PKCS7Padding","Base64","无");
//        System.out.println(aa);

//        String aa = encryptKeyivmode("{\"mobile\":13812312355}\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000",
//                "1234567890123456","1234567890123456","AES","CBC","NoPadding","Base64","无");
//        System.out.println(aa);

//        String a = encryptKeyivmode("{\"loginstate\":null,\"data\":[],\"object\":null,\"token\":null,\"code\":null," +
//                "\"resultStatus\":\"success\",\"messageCode\":1009,\"devMessage\":\"\",\"message\":\"操作失败\"," +
//                "\"clientStyle\":1,\"dataCount\":0,\"key\":null,\"zip\":false,\"requestInfo\":{\"responseTime\":1}," +
//                "\"authStatus\":3,\"enabled\":1}", "12345645678iaweb","1234567812345678","AES","CBC","NoPadding","Base64","null");

//        String aa = encryptKeyivmode("{\"loginstate\":null,\"data\":[],\"object\":null,\"token\":null,\"code\":null,\"resultStatus\":\"success\",\"messageCode\":1009,\"devMessage\":\"\",\"message\":\"操作失败\",\"clientStyle\":1,\"dataCount\":0,\"key\":null,\"zip\":false,\"requestInfo\":{\"responseTime\":168022497401},\"authStatus\":3,\"enabled\":1}", "12345645678iaweb","1234567812345678","AES","CBC","NoPadding","Base64","null");
        String aa = encryptKeyivmode("123456", "1234567890123456","1234567890123456","AES","CBC","NoPadding","Base64","null");

//        String aa = decryptKeyivmode("QEwd/DWmy/4yGncCqBofQQ==", "1234567890123456","1234567890123456","AES","CBC","NoPadding","Base64","null");
        System.out.println(aa);
    }

    public static String byte2hex(final byte[] array) {
        String s = "";
        for (int i = 0; i < array.length; ++i) {
            final String hexString = Integer.toHexString(array[i] & 0xFF);
            if (hexString.length() == 1) {
                final StringBuilder sb = new StringBuilder();
                sb.append(s);
                sb.append("0");
                sb.append(hexString);
                s = sb.toString();
            }
            else {
                final StringBuilder sb2 = new StringBuilder();
                sb2.append(s);
                sb2.append(hexString);
                s = sb2.toString();
            }
        }
        return s.toUpperCase();
    }
    public static byte[] byteMerger(final byte[] array, final byte[] array2) {
        final byte[] array3 = new byte[array.length + array2.length];
        System.arraycopy(array, 0, array3, 0, array.length);
        System.arraycopy(array2, 0, array3, array.length, array2.length);
        return array3;
    }

    public static String encrypt2(byte[] array) {
        try {
            byte[] array2 = toByteArray(new String(array));

            String key_str = "f7dd8981859e6b2932c72eddf39aec144dd43896ca95252f30293188fd033abc";
            String iv_str = "dfb04ad776cc1ff90ea26e441a3949db";
            byte[] iv = toByteArray(iv_str);
            byte[] key = toByteArray(key_str);
            final IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            final SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            final Cipher instance = Cipher.getInstance("AES/CFB/NoPadding");
            instance.init(1, secretKeySpec, ivParameterSpec);
            String array3 = byte2hex(byteMerger(instance.getIV(), instance.doFinal(array)));
            return array3;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String encryptKeyivmode(String encryptedData,String sessionKey,String iv,String encodemode, String ivmode , String paddingmode ,String sSrcmode ,String keyivmode) throws Exception {
//        BurpExtender.stdout.println(encryptedData);
//        BurpExtender.stdout.println(sessionKey);
//        BurpExtender.stdout.println(iv);
//        BurpExtender.stdout.println(encodemode);
//        BurpExtender.stdout.println(ivmode);
//        BurpExtender.stdout.println(paddingmode);
//        BurpExtender.stdout.println(sSrcmode);
//        BurpExtender.stdout.println(keyivmode);

        Boolean zero = false;
        if  (paddingmode.equals("ZeroPadding")) {
            paddingmode = "NoPadding";
            zero = true;
        }

        if ( ivmode.equals("ECB") )
            iv = "";

//        BurpExtender.stdout.println(encodemode);
        if (encodemode.equals("null"))
            return encryptedData;
        else if (encodemode.equals("RSA")){
            try {
//                BurpExtender.stdout.println(sessionKey);
//                BurpExtender.stdout.println(encryptedData);
//                BurpExtender.stdout.println(encrypt(encryptedData, sessionKey));
                return encrypt(encryptedData, sessionKey);
            }catch (Exception e){
                BurpExtender.stdout.println(Arrays.toString(e.getStackTrace()));
                return "RSA加密错误";
            }
        } else {
            try {
                byte[] data = null;
                byte[] aseKey;
                byte[] ivData;

                if (keyivmode.equals("null")) {
                    if (encodemode.equals("DESede") ) {
                        if (sessionKey.length() > 24) {
                            sessionKey = sessionKey.substring(0, 24);
                        }
                        if ( ivmode.equals("ECB") ){
                            sessionKey = sessionKey.substring(0, 8);
                        }else {
                            iv = iv.substring(0, 8);
                        }
                    }
//                    else if(encodemode.equals("AES")){
//                        if (ivmode.equals("CBC"))
//                            iv = iv.substring(0,8);
//                    }
                    else if(encodemode.equals("DES")){
                        sessionKey = sessionKey.substring(0,8);
                        if ( ivmode.equals("ECB") ){
                            sessionKey = sessionKey.substring(0, 8);
                        }else {
                            iv = iv.substring(0, 8);
                        }
                    }
                }

                data = encryptedData.getBytes(StandardCharsets.UTF_8);
//                System.out.println(Arrays.toString(data));
//                BurpExtender.stdout.println(Arrays.toString(data));

                if (keyivmode.equals("Base64")) {
                    aseKey = Base64.decodeBase64(sessionKey);
                    ivData = Base64.decodeBase64(iv);
                } else if (keyivmode.equals("Hex")) {
                    aseKey = hexToByteArray(sessionKey);
                    ivData = hexToByteArray(iv);
                } else {
                    aseKey = sessionKey.getBytes(StandardCharsets.UTF_8);
                    ivData = iv.getBytes(StandardCharsets.UTF_8);
                }



                IvParameterSpec ivParameterSpec = new IvParameterSpec(ivData);
//                Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
                Cipher cipher = Cipher.getInstance(encodemode + "/" + ivmode + "/" + paddingmode);
                Key sKeySpec = new SecretKeySpec(aseKey, encodemode);
                //cipher.init(Cipher.ENCRYPT_MODE, sKeySpec,ivParameterSpec );// 初始化
                if (ivmode.equals("ECB") || ivmode.equals("GCM")) { // 如果为ECB模式，不进行偏移量加载
                    cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
                } else {
                    cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, ivParameterSpec);
                }

                byte[] result = null ;
                if (zero) {
                    result = cipher.doFinal(formatWithZeroPadding(data, cipher.getBlockSize()));
                }else{
                    System.out.println(Arrays.toString(aseKey));
                    result = cipher.doFinal(data);
                }
                String final_result = "";

                if (sSrcmode.equals("Base64") || sSrcmode.equals("null"))
                    final_result = new String(Base64.encodeBase64(result)).replace("\n", "");

                if (sSrcmode.equals("Hex"))
                    final_result = bytesToHex(result);
//                BurpExtender.stdout.println(final_result);


                if (IndexautoDecoder.getRadioButton12State())
                    final_result = URLEncoder.encode(final_result, "utf-8");

                return final_result;
            } catch (Exception e) {
                BurpExtender.stdout.println(e);
                return "加密错误，请确认选项无误!";
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



    // 字节数组转hex
    public static String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if(hex.length() < 2){
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }


}
