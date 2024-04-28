package com.autoDecoder.util;

/**
 * Created by $(USER) on $(DATE)
 */

import com.autoDecoder.util.Util;
import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SM4Utils {
//	private String secretKey = "";
//    private String iv = "";
//    private boolean hexString = false;

    public String secretKey = "";
    public String iv = "";
    public boolean hexString = false;

    public SM4Utils() {
    }


    public String encryptData_ECB(String plainText) {
        try {
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_ENCRYPT;

            byte[] keyBytes;
            if (hexString) {
                keyBytes = Util.hexStringToBytes(secretKey);
            } else {
                keyBytes = Util.hexStringToBytes(secretKey);
            }

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_enc(ctx, keyBytes);
            byte[] encrypted = sm4.sm4_crypt_ecb(ctx, plainText.getBytes("UTF-8"));
            System.out.println("SM4Utils 44  " + Util.byteToHex(encrypted));
            return Util.byteToHex(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String decryptData_ECB(String cipherText) {
        try {
            byte[] encrypted = Util.hexToByte(cipherText);
            cipherText=Base64.encodeBase64String(encrypted);;
            //cipherText = new BASE64Encoder().encode(encrypted);
            if (cipherText != null && cipherText.trim().length() > 0) {
                Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                Matcher m = p.matcher(cipherText);
                cipherText = m.replaceAll("");
            }
            System.out.println(secretKey);

            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_DECRYPT;

            byte[] keyBytes;
            if (hexString) {
                keyBytes = Util.hexStringToBytes(secretKey);
            } else {
                keyBytes = secretKey.getBytes();
            }

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_dec(ctx, keyBytes);
            System.out.println("SM4Utils 77 " + cipherText);
            byte[] decrypted = sm4.sm4_crypt_ecb(ctx, Base64.decodeBase64(cipherText));
            //byte[] decrypted = sm4.sm4_crypt_ecb(ctx, new BASE64Decoder().decodeBuffer(cipherText));
            return new String(decrypted, "UTF-8");
        } catch (Exception e) {
//            e.printStackTrace();
            return null;
        }
    }

    public String encryptData_CBC(String plainText) {
        try {
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_ENCRYPT;

            byte[] keyBytes;
            byte[] ivBytes;
            if (hexString) {
                keyBytes = Util.hexStringToBytes(secretKey);
                ivBytes = Util.hexStringToBytes(iv);
            } else {
                keyBytes = secretKey.getBytes();
                ivBytes = iv.getBytes();
            }

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_enc(ctx, keyBytes);
            byte[] encrypted = sm4.sm4_crypt_cbc(ctx, ivBytes, plainText.getBytes("UTF-8"));
            return Util.byteToHex(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String decryptData_CBC(String cipherText) {
        try {
            byte[] encrypted = Util.hexToByte(cipherText);
            cipherText=Base64.encodeBase64String(encrypted);;
            //cipherText = new BASE64Encoder().encode(encrypted);
            if (cipherText != null && cipherText.trim().length() > 0) {
                Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                Matcher m = p.matcher(cipherText);
                cipherText = m.replaceAll("");
            }
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_DECRYPT;

            byte[] keyBytes;
            byte[] ivBytes;
            if (hexString) {
                keyBytes = Util.hexStringToBytes(secretKey);
                ivBytes = Util.hexStringToBytes(iv);
            } else {
                keyBytes = secretKey.getBytes();
                ivBytes = iv.getBytes();
            }

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_dec(ctx, keyBytes);
            //byte[] decrypted = sm4.sm4_crypt_cbc(ctx, ivBytes, new BASE64Decoder().decodeBuffer(cipherText));
            byte[] decrypted = sm4.sm4_crypt_cbc(ctx, ivBytes, Base64.decodeBase64(cipherText));
            /*String text = new String(decrypted, "UTF-8");
            return text.substring(0,text.length()-1);*/
            return new String(decrypted, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws IOException {
        String plainText = "I Love You Every Day";
        String s = Util.byteToHex(plainText.getBytes());
        System.out.println("原文" + s);
        SM4Utils sm4 = new SM4Utils();
        //sm4.secretKey = "JeF8U9wHFOMfs2Y8";
        sm4.secretKey = "1d8c95e41c72969c66b88c4911d376ca";
        sm4.iv = "97f4db469af2df1595f4c81664666883";
        sm4.hexString = true;

        System.out.println("ECB模式加密");
        String cipherText = sm4.encryptData_CBC(plainText);
        System.out.println("密文: " + cipherText);
        System.out.println("");

        String plainText2 = sm4.decryptData_ECB("9a2eb12038cf8e85873c63fb45d2a0424e8a2ac8d2ed0a84271a9a5ee63a8e07ab11015e14e1741d51bde64c00240e16f126b322575cb68999de33d4daf6a8c36b0d25f9dc074a560d95b562d5b9fc640c0d8fe4f0461f1663fb01c76effee06040000436413590dc7677dd13f98f3d45115c817852292a0ed9716bd6d286358eb908b46c41a96e7f261a2e5bfb34bf71dfa4773d84be350628773c4dc2e82eacb1cdcb1f3e216ec3a2ec8544b78010febeb99697afc37f8352c9099490d8e13");
        System.out.println("明文: " + plainText2);

//        System.out.println("CBC模式加密");
//        sm4.iv = "31313131313131313131313131313131";
//        String cipherText2 = sm4.encryptData_CBC(plainText);
//        System.out.println("加密密文: " + cipherText2);
//        System.out.println("");
//
//        String plainText3 = sm4.decryptData_CBC(cipherText2);
//        System.out.println("解密明文: " + plainText3);

    }
}
