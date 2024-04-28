package burp.ui;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.security.Security;

import cn.hutool.core.codec.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class SM4 {
    private static final String name="SM4";                               //算法名字
    private static final String transformation="SM4/CBC/PKCS5Padding";    //加密模式以及短快填充方式
    private static final String Default_iv="0123456789abcdef";            //加密使用的初始向量

    /**
     * 加载指定文件，对其进行加密，并将加密结果写入指定输出文件中
     * @param inputFile 要加密的输入文件路径
     * @param outputFile 加密后的输出文件路径
     * @param key 加密所需的密钥
     * @throws Exception 如果文件读取、加密或写入时出现错误，则抛出异常
     */
    private static void encodeFile(String inputFile, String outputFile, String key) throws Exception {
        // 读取输入文件中的所有字节
        byte [] inputBytes = Files.readAllBytes(Paths.get(inputFile));
        // 对输入字节数组进行加密
        byte [] encodeByte = encode(inputBytes, key.getBytes(StandardCharsets.UTF_8));
        // 将加密后的字节数组写入指定输出文件中
        Files.write(Paths.get(outputFile),encodeByte);
        System.out.println("File encoded successfully.");
    }
    /**
     * 使用指定的加密算法和密钥对给定的字节数组进行加密
     * @param inputByte 要加密的字节数组
     * @param key 加密所需的密钥
     * @return 加密后的字节数组
     * @throws Exception 如果加密时发生错误，则抛出异常
     */
    public static byte [] encode(byte [] inputByte, byte [] key) throws Exception {
        // 获取加密实例
        Cipher c = Cipher.getInstance(transformation);
        // 根据密钥的字节数组创建 SecretKeySpec
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, name);
        // 创建 IvParameterSpec 对象，使用默认向量和字符集
        IvParameterSpec ivParameterSpec = new IvParameterSpec(Default_iv.getBytes(StandardCharsets.UTF_8));
        // 初始化加密实例
        c.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        // 返回加密后的字节数组
        return c.doFinal(inputByte);
    }

    public static void decodeFile(String inputFilePath, String outputFilePath, String key) throws Exception {
        byte[] inputBytes = Files.readAllBytes(Paths.get(inputFilePath));
        byte[] decodeBytes = decode(inputBytes, key.getBytes(StandardCharsets.UTF_8));
        Files.write(Paths.get(outputFilePath), decodeBytes);
        System.out.println("File decode successfully.");
    }



    private static byte[] decode(byte[] inputBytes, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance(transformation);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, name);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(Default_iv.getBytes(StandardCharsets.UTF_8));
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        return cipher.doFinal(inputBytes);
    }

    public static void main(String[] args) throws Exception {
//        Security.addProvider(new BouncyCastleProvider());
        System.out.println(Base64.encode(encode("ct".getBytes(), Base64.decode("8SvjKWLXHvGxZg4g/5Gztg=="))));
    }
}
