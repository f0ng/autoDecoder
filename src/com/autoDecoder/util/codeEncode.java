package com.autoDecoder.util;

//import com.sun.org.apache.xml.internal.security.utils.Base64;
import static com.autoDecoder.util.codeDecode.*;
import java.util.Arrays;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Encoder;

public class codeEncode {
    public static void main(String[] args) throws Exception {

        String a = encryptKeyivmode("{\"id\":\"1''\"}","f0ngtest","f0ngf0ng","DES","CBC","PKCS5Padding","Base64","无");
        System.out.println(a);
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



    public static String encryptKeyivmode(String encryptedData,String sessionKey,String iv,String encodemode, String ivmode , String paddingmode ,String sSrcmode ,String keyivmode) throws Exception {
        try {
            byte[] data = null;
            byte[] aseKey ;
            byte[] ivData ;

            data = encryptedData.getBytes(StandardCharsets.UTF_8);
            if (keyivmode.equals("Base64")) {
                aseKey = Base64.getDecoder().decode(sessionKey);
                ivData = Base64.getDecoder().decode(iv);
            } else if(keyivmode.equals("Hex")) {
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
            cipher.init(Cipher.ENCRYPT_MODE, sKeySpec,ivParameterSpec );// 初始化
            byte[] result = cipher.doFinal(data);
            String final_result = "";
            if (sSrcmode.equals("Base64") || sSrcmode.equals("无"))
                final_result = new String(Base64.getEncoder().encode(result)).replace("\n","");

            if (sSrcmode.equals("Hex"))
                final_result = bytesToHex(result);

            return final_result;
        } catch (Exception e) {
            return "加密错误，请确认选项无误!"; }
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
