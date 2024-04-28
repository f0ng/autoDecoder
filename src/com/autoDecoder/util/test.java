import java.util.Base64;

public class test {

    // Base64 编码的字符串转换为十六进制字符串
    public static String base64ToHex(String base64String) {
        // 解码 Base64 字符串得到原始字节数据
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);

        // 使用 StringBuilder 存储结果
        StringBuilder hexBuilder = new StringBuilder();

        // 遍历字节数组，将每个字节转换为十六进制表示并拼接
        for (byte decodedByte : decodedBytes) {
            String hex = Integer.toHexString(0xff & decodedByte);
            if (hex.length() == 1) {
                hexBuilder.append('0'); // 如果是单个字符，前面补零
            }
            hexBuilder.append(hex);
        }

        // 返回最终的十六进制字符串
        return hexBuilder.toString();
    }

    public static void main(String[] args) {
        // Base64 编码的字符串示例
        String base64String = "SGVsbG8gV29ybGQ="; // 等于 "Hello World" 的 Base64 编码

        // 转换为十六进制字符串
        String hexString = base64ToHex(base64String);

        // 打印结果
        System.out.println("Hex: " + hexString);
    }
}