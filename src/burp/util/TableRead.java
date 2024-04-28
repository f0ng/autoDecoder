package burp.util;

import burp.BurpExtender;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Utils.Match.MatchReg;

/**
 * @DESCRIPTION:
 * @USER: f0ng
 * @DATE: 2023/11/20 上午12:48
 */
public class TableRead {
    public static String REQUEST = "request";
    public static String RESPONSE = "response";
    public static void main(String[] args) {
        String header = "GET /6ONWsjip0QIZ8tyhnq/ps_default.gif?_t=1700485217628 HTTP/1.1\r\n" +
                "Host: ss3.baidu.com\r\n" +
                "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:109.0) Gecko/20100101 Firefox/119.0\r\n" +
                "Accept: image/avif,image/webp,*/*\r\n" +
                "Accept-Language: zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2\r\n" +
                "Referer: https://www.baidu.com/\r\n" +
                "Connection: close\r\n" +
                "Cookie: BAIDUID=B2CF572646927252617315D108A6056E:\r\n" +
                "Sec-Fetch-Dest: image\r\n" +
                "Sec-Fetch-Mode: no-cors\r\n" +
                "Sec-Fetch-Site: same-site\r\n" ;

        String body = "<html>\r\n" +
                "<head><title>302 Found</title></head>\r\n" +
                "<body bgcolor=\"white\">\r\n" +
                "<center><h1>302 Found</h1></center>\r\n" +
                "<hr><center>nginx</center>\r\n" +
                "</body>\r\n" +
                "</html>\r\n";

        String Item = "Response body";
        String Match = "<center><h1>302(.*)ound";
        String Replace = "ngi</@auto>nx";
        String Type = "Extract";

        String[] total = MatchandReplace(header ,body , Type, Item , Match ,Replace,RESPONSE);
        System.out.println(total[0]); //header
        System.out.println(total[1]); //body

//        System.out.println(MatchRegReqpEx("a: aaaaa,b:aabbaa,c:dddd\r\n","a: (.*),b")); // 使用extract模式请加括号

    }

    public static String[] MatchandReplace(String header, String body , String Type , String Item, String Match, String Replace ,String requestResponse){
        String[] total = new String[2];
//        BurpExtender.stdout.println("Item");
//        BurpExtender.stdout.println(Item);
        switch (Item) {
            case "Request header":
                if (requestResponse.equals(REQUEST)) {
                    if (Type.equals("Literal")) {
                        header = header.replace(Match, Replace).replace("\r\n\r\n","\r\n");
                    } else if (Type.equals("Regex")) {
                        String regexmatch = MatchRegReqp(header, Match);
                        if (!regexmatch.equals("null"))
                        header = header.replace(regexmatch, Replace).replace("\r\n\r\n","\r\n");
                    } else if (Type.equals("Extract")) { //提取模式
                        String regexmatch = MatchRegReqpEx(header, Match);// 获取提取的数据
                        if (!regexmatch.equals("null")) {
                            header = header.replace( regexmatch ,""); // 替换为空
                            String[] to = Replace.split("</@auto>"); // 数组拼接
                            String r = Replace.replace("</@auto>",""); // 原始位置
                            BurpExtender.stdout.println(header);
                            header = header.replace( r ,to[0] + regexmatch + to[1] ).replace("\r\n\r\n","\r\n");// 在替换后的位置进行替换
                        }
                    }
                    break;
                }
            case "Request body":
                if (requestResponse.equals(REQUEST)) {
                    if (Type.equals("Literal")) {
                        body = body.replace(Match, Replace);
                    } else if (Type.equals("Regex")) {
                        String regexmatch = MatchRegReqp(body, Match);
                        if (!regexmatch.equals("null"))
                        body = body.replace(regexmatch, Replace);
                    } else if (Type.equals("Extract")) { //提取模式
                        String regexmatch = MatchRegReqpEx(body, Match);// 获取提取的数据
                        if (!regexmatch.equals("null")) {
                            body = body.replace( regexmatch ,""); // 替换为空
                            String[] to = Replace.split("</@auto>"); // 数组拼接
                            String r = Replace.replace("</@auto>",""); // 原始位置
                            body = body.replace( r ,to[0] + regexmatch + to[1] );// 在替换后的位置进行替换
                        }
                    }
                    break;
                }
            case "Response header":
                if (requestResponse.equals(RESPONSE)) {
                    if (Type.equals("Literal")) {
                        header = header.replace(Match, Replace).replace("\r\n\r\n","\r\n");
                    } else if (Type.equals("Regex")) {
                        String regexmatch = MatchRegReqp(header, Match);
                        if (!regexmatch.equals("null"))
                        header = header.replace(regexmatch, Replace).replace("\r\n\r\n","\r\n");
                    } else if (Type.equals("Extract")) { //提取模式
                        String regexmatch = MatchRegReqpEx(header, Match);// 获取提取的数据
                        if (!regexmatch.equals("null")) {
                            header = header.replace( regexmatch ,""); // 替换为空
                            String[] to = Replace.split("</@auto>"); // 数组拼接
                            String r = Replace.replace("</@auto>",""); // 原始位置
                            BurpExtender.stdout.println(header);
                            header = header.replace( r ,to[0] + regexmatch + to[1] ).replace("\r\n\r\n","\r\n");// 在替换后的位置进行替换
                        }
                    }
                    break;
                }
            case "Response body":
                if (requestResponse.equals(RESPONSE)) {
                    if (Type.equals("Literal")) {
                        body = body.replace(Match, Replace);
                    } else if (Type.equals("Regex")) {
                        String regexmatch = MatchRegReqp(body, Match);
                        if (!regexmatch.equals("null"))
                        body = body.replace(regexmatch, Replace);
                    }else if (Type.equals("Extract")) { //提取模式
                        String regexmatch = MatchRegReqpEx(body, Match);// 获取提取的数据
                        BurpExtender.stdout.println("TableRead");
                        BurpExtender.stdout.println(body);
                        if (!regexmatch.equals("null")) {
                            body = body.replace( regexmatch ,""); // 替换为空
                            String[] to = Replace.split("</@auto>"); // 数组拼接
                            String r = Replace.replace("</@auto>",""); // 原始位置
                            body = body.replace( r ,to[0] + regexmatch + to[1] );// 在替换后的位置进行替换
                        }
                    }
                    break;
                }
        }

        total = new String[]{header, body};

        return total;
    }

    public static String MatchRegReqp(String data, String reg){
        String total = "";
        String pattern = reg ;
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(data);
        if (m.find()) {
            total = m.group(0);
        }
        if (total.equals("")){
            return "null";
        }

        return total;

    }

    public static String MatchRegReqpEx(String data, String reg){
        String total = "";
        Pattern r = Pattern.compile(reg);
        Matcher m = r.matcher(data);
        if (m.find()) {
            total = m.group(1);
        }
        if (total.equals("")){
            return "null";
        }

        return total;

    }

}
