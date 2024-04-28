package Utils;

import burp.IndexautoDecoder;

import java.io.IOException;

import static burp.BurpExtender.*;

/**
 * @DESCRIPTION:
 * @USER: f0ng
 * @DATE: 2023/2/15 下午3:15
 */
public class TestDecode {
    public static void main(String[] args) throws IOException {
        String Input = "POST /testsql.php HTTP/1.1\n" +
                "Host: 10.211.55.4\n" +
                "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:109.0) Gecko/20100101 Firefox/109.0\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8\n" +
                "Accept-Language: zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2\n" +
                "Accept-Encoding: gzip, deflate\n" +
                "Connection: close\n" +
                "Upgrade-Insecure-Requests: 1\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Content-Length: 24\n" +
                "\n" +
                "I9z1fsH5QQ2NUbJi/7a8lw==";
        System.out.println(TestDecode(Input, "request"));
    }
    public static String TestDecode(String Data,String reqorresp) throws IOException {
        String Output = "";
        String decodeTotal = "";
        String[] decodeTotallists = null;
//        Data = Data + "\n\n";
        String[] DataLists = Data.replace("\r", "").split("\n\n",2);
//        if (DataLists.length > 1) { // POST

            if (IndexautoDecoder.getRadioButtontestHeaderState() && IndexautoDecoder.getRadioButtontestdifferentState()) { // 选中对数据头处理、选中请求响应分开

                decodeTotallists = sendPostnewHeader(IndexautoDecoder.getDecodeApi(), DataLists[1], DataLists[0] + "\n", reqorresp);
                if (decodeTotallists.length == 1) {
                    Output = decodeTotallists[0];
                } else {
                    if (decodeTotallists[0].endsWith("\n"))
                        Output = decodeTotallists[0] + "\n" + decodeTotallists[1];
                    else{
                        Output = decodeTotallists[0] + "\n\n" + decodeTotallists[1];
                    }
                }

            } else {
                if (IndexautoDecoder.getRadioButtontestHeaderState()) { // 选中对数据头处理
                    decodeTotallists = sendPostnewHeader(IndexautoDecoder.getDecodeApi(), DataLists[1], DataLists[0] + "\n");
                    if (decodeTotallists.length == 1) {
                        Output = decodeTotallists[0];
                    } else {
                        if (decodeTotallists[0].endsWith("\n"))
                            Output = decodeTotallists[0] + "\n" + decodeTotallists[1];
                        else{
                            Output = decodeTotallists[0] + "\n\n" + decodeTotallists[1];
                        }
                    }
                }

                if (IndexautoDecoder.getRadioButtontestdifferentState()) { // 选中请求响应分开
                    decodeTotal = sendPostnew(IndexautoDecoder.getDecodeApi(), DataLists[1], reqorresp);
                    Output = Data.replace(DataLists[1], decodeTotal);
                }

                if (!IndexautoDecoder.getRadioButtontestdifferentState() && !IndexautoDecoder.getRadioButtontestHeaderState()) { // 不选中对数据头处理且不选中请求响应分开
                    decodeTotal = sendPostnew(IndexautoDecoder.getDecodeApi(), DataLists[1]);
                    Output = Data.replace(DataLists[1], decodeTotal);
                }
            }
            return Output.trim();
//        return Output.trim()+"\n\n";
//        }else{ // GET
//
//            if (IndexautoDecoder.getRadioButtontestHeaderState() && IndexautoDecoder.getRadioButtontestdifferentState()) { // 选中对数据头处理、选中请求响应分开
//
//                decodeTotallists = sendPostnewHeader(IndexautoDecoder.getDecodeApi(), "", DataLists[0] + "\n", reqorresp);
//                if (decodeTotallists.length == 1) {
//                    Output = decodeTotallists[0];
//                } else {
//                    Output = decodeTotallists[0] + "\n" + decodeTotallists[1];
//                }
//
//            } else {
//                if (IndexautoDecoder.getRadioButtontestHeaderState()) { // 选中对数据头处理
//                    decodeTotallists = sendPostnewHeader(IndexautoDecoder.getDecodeApi(), "", DataLists[0] + "\n");
//                    if (decodeTotallists.length == 1) {
//                        Output = decodeTotallists[0];
//                    } else {
//                        Output = decodeTotallists[0] + "\n" + decodeTotallists[1];
//                    }
//                }
//            }
//            return Output.trim();
//        }
    }

}
