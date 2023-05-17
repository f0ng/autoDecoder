package com.autoDecoder.util;

//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;
import org.apache.commons.codec.binary.Base64;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;
import java.util.HashMap;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.KeyPair;

public class RSAencode {
    /**
     * 密钥长度 于原文长度对应 以及越长速度越慢
     */
    private final static int KEY_SIZE = 1024;
    /**
     * 用于封装随机产生的公钥与私钥
     */
    private static Map<Integer, String> keyMap = new HashMap<Integer, String>();

    public static void main(String[] args) {

        //解密数据
        try {
            //生成公钥和私钥
            String publicKeyString = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCYCJiMg0LijRpYLwj+j2VoESWhX8+WJDqlv2hoamqWGouqR2jdBP7IIXtbCyb1bPO2qbkyzYkyrQRMg6dqIXMZz8OPHgdSipWcJrqBNhMT20g6KREMakCN2HRswhFfpTmPAXHv5woFJWxYoPKE+aF5LKBjWtWcv8SmTkB5DQ+5iQIDAQAB";
            keyMap.put(0,publicKeyString );
//            System.out.println(publicKeyString);
            //1表示私钥
            String privateKeyString = "MIICXgIBAAKBgQCYCJiMg0LijRpYLwj+j2VoESWhX8+WJDqlv2hoamqWGouqR2jdBP7IIXtbCyb1bPO2qbkyzYkyrQRMg6dqIXMZz8OPHgdSipWcJrqBNhMT20g6KREMakCN2HRswhFfpTmPAXHv5woFJWxYoPKE+aF5LKBjWtWcv8SmTkB5DQ+5iQIDAQABAoGBAJBK2D3D9p7+PJqlSWwQvLXgUE6wmFxvVhopZ/ZxyWddGmEqFSRvsUBQRrtKAle8aDJdMyA8YpJuEz5rVWEtDgdfvc4+I8B+BKD2jI5lQ2yCtTsSy5CohE8XFEKovxdgWEergbwIUtfdd730avM1Pc/kwTs/b7bAJPVH/ukkEznhAkEA4JvXMljsxFNPo7XfWkj94gDYJnGqOqB1xgTt6ilbSFxXSo9GI8frJVHhzd3+br19SB87oSDpOSP3lWL4rc+gGwJBAK1IID89RPCdHPQYqfNsPUMf8lMyvw25WZS1yRItn0g4je6fmDzTZ0bMFgmq91islMpjuCLkc5WHJHprkpnOzysCQQCsUsRbC2C0C5sZZkszcLbgc3din2hUTJGvWE7UjeBL9xS9zoiooRRm8JiGouA3REhfUh8ksyRcQ50LTwCuEZKrAkA5QabeHomkE9YYVfn6JB7OCkVQ9mioyUDvYW3SIt8Jxx4m5fcwyg3LF+6EPUjDtSrpATA7307N7ry/8sGbLvnHAkEAj7PH1hPyxUCzBFYLt3+Yu7F2S5Z+jct6jEgBGZsGHBeaEtzPibrsEfNpy3JUr2z2JKYR8NK/x0666NMrggLCKw==";
            keyMap.put(1, privateKeyString );
//            System.out.println(privateKeyString);
//            genKeyPair();
            String publicKey = keyMap.get(0);
            System.out.println("公钥:" + publicKey);
            String privateKey = keyMap.get(1);
            System.out.println("私钥:" + privateKey);

            String orgData = "123456781668745014120123D";
            System.out.println("原数据：" + orgData);
            String encryptStr = encrypt(orgData,publicKey);
            System.out.println("加密结果：" + encryptStr);

            String decryptStr = decrypt(encryptStr,privateKey);
            System.out.println("解密结果：" + decryptStr);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * RSA公钥加密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String str,String publicKey) throws Exception {
        //base64编码的公钥
        byte[] decoded = decryptBASE64(publicKey);
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = encryptBASE64(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str        加密字符串
     * @param privateKey 私钥
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) throws Exception {
        //64位解码加密后的字符串
        byte[] inputByte = decryptBASE64(str);
        //base64编码的私钥
        byte[] decoded = decryptBASE64(privateKey);
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }

    //编码返回字符串
    public static String encryptBASE64(byte[] key) throws Exception {
        return Base64.encodeBase64String(key);
    }

    //解码返回byte
    public static byte[] decryptBASE64(String key) throws Exception {
        return Base64.decodeBase64(key);
    }



//    /**
//     * 随机生成密钥对
//     * @throws Exception
//     */
//    public static void genKeyPair() throws Exception {
//        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
//        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
//        // 初始化密钥对生成器
//        keyPairGen.initialize(KEY_SIZE, new SecureRandom());
//        // 生成一个密钥对，保存在keyPair中
//        KeyPair keyPair = keyPairGen.generateKeyPair();
//        // 得到私钥
//        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
//        // 得到公钥
//        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
//        String publicKeyString = encryptBASE64(publicKey.getEncoded());
//        // 得到私钥字符串
//        String privateKeyString = encryptBASE64(privateKey.getEncoded());
//        // 将公钥和私钥保存到Map
//        //0表示公钥
//        keyMap.put(0, publicKeyString);
//        System.out.println(publicKeyString);
//        //1表示私钥
//        keyMap.put(1, privateKeyString);
//        System.out.println(privateKeyString);
//    }
}