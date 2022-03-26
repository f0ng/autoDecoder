package com.autoDecoder.util;

//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
//import org.apache.commons.codec.binary.Base64;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.Security;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;



public class codeDecode {
    public static void main(String[] args) throws Exception {

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

        try {
            byte[] data ;
            byte[] aseKey = new byte[0];
            byte[] ivData = new byte[0];
            if (sSrcmode.equals("Base64")) data = Base64.getDecoder().decode(encryptedData);
            else if (sSrcmode.equals("Hex")) {
                data = hexToByteArray(encryptedData);
            }
            else data = encryptedData.getBytes(StandardCharsets.UTF_8);

//            data = hex2byte(new String(data));

            if (keyivmode.equals("Base64")) {
                aseKey = Base64.getDecoder().decode(sessionKey);
                ivData = Base64.getDecoder().decode(iv);
            }else if(keyivmode.equals("Hex")) {
                aseKey = hexToByteArray(sessionKey);
                ivData = hexToByteArray(iv);
            }else {
                aseKey = sessionKey.getBytes(StandardCharsets.UTF_8);
                ivData = iv.getBytes(StandardCharsets.UTF_8);
            }
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivData);
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance(encodemode + "/" + ivmode + "/" + paddingmode);
            Key sKeySpec = new SecretKeySpec(aseKey, encodemode);
            cipher.init(Cipher.DECRYPT_MODE, sKeySpec, ivParameterSpec);// 初始化

            byte[] result = cipher.doFinal(data);
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
            return "解密错误，请确认选项无误!";
        }

    }




    /**
    * 字符串转字节数组
    * */
    public static byte[] toByteArray(String hexString) {
        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {// 因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }

    /**
     * 字节数组转16进制
     * @param bytes 需要转换的byte数组
     * @return  转换后的Hex字符串
     */
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

    public static AlgorithmParameters generateIv(byte[] iv) throws Exception{
        AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
        params.init(new IvParameterSpec(iv));
        return params;
    }
}
