package Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @DESCRIPTION:
 * @USER: f0ng
 * @DATE: 2023/2/15 下午10:01
 */
public class Match {
    public static void main(String[] args) {
//        System.out.println(MatchReg("----------856810576\n" +
//                "Content-Disposition: form-data; name=\"id\"\n" +
//                "\n" +
//                "111\n" +
//                "----------856810576--","\"id\"\\r\\n\\r\\n([^\\n]*)\\r\\n".replace("\\r\\n","\n")));

        System.out.println(MatchReg("admin=1111&password=1111","password=(.*)"));

//        String total = "";
//        String data = "GET /6ONWsjip0QIZ8tyhnq/ps_default.gif?_t=1700485217628 HTTP/1.1\r\n" +
//                "Host: ss3.baidu.com\r\n" +
//                "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:109.0) Gecko/20100101 Firefox/119.0\r\n" +
//                "Accept: image/avif,image/webp,*/*\r\n" +
//                "Accept-Language: zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2\r\n" +
//                "Referer: https://www.baidu.com/\r\n" +
//                "Connection: close\r\n" +
//                "Cookie: BAIDUID=B2CF572646927252617315D108A6056E:FG=1; BIDUPSID=B2CF572646927252BD630617148DF75A; PSTM=1699857307; ZFY=9yhuR:AiB2wvMy6AZ0pNw8l4RGVr1AiKn85hc1XhJoT0:C; BDRCVFR[Fc9oatPmwxn]=aeXf-1x8UdYcs; BDRCVFR[S4-dAuiWMmn]=6ILBVOWw0XbfAdEUhkGUhNxndqbus; delPer=0; PSINO=3; H_PS_PSSID=39634_39673_39663_39676_39679_39713_39736_39779_39787_39703; BA_HECTOR=000l0h8i8k0g048kag248l8i1ilmm1t1r; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598\r\n" +
//                "Sec-Fetch-Dest: image\r\n" +
//                "Sec-Fetch-Mode: no-cors\r\n" +
//                "Sec-Fetch-Site: same-site\r\n" +
//                "\r\n" +
//                "a=111111&b=222222";
//        String pattern = "a=.*&" ;
//        Pattern r = Pattern.compile(pattern);
//        Matcher m = r.matcher(data);
//        if (m.find()) {
//            total = m.group(0);
//        }
//        System.out.println(total);
    }

    public static String MatchReg(String data, String reg){
        String total = "";
        String pattern = reg ;
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(data);
        if (m.find()) {
            total = m.group(1);
        }
        System.out.println(m.group(0));

        return total;

    }

    public static String MatchReg_code(String data, String reg){
        String total = "";
        String pattern = reg ;
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(data);
        if (m.find()) {
            total = m.group(0);
        }

        return total;

    }
}
