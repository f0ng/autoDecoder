package Utils;

import burp.IndexautoDecoder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;

import static burp.BurpExtender.sendPostnew;
import static burp.BurpExtender.sendPostnewHeader;

/**
 * @DESCRIPTION:
 * @USER: f0ng
 * @DATE: 2023/2/15 下午3:15
 */
public class TestEncode {
    public static void main(String[] args) {
        System.out.println(beauty("{\"aaaa\":\"axxx\"}"));
    }

    public static String beauty(String inputJson) {
        //Take the input, determine request/response, parse as json, then print prettily.
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().serializeNulls().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(inputJson);
        return gson.toJson(je);
    }

    public static String TestEncode(String Data,String reqorresp) throws IOException {
        String Output = "";
        String decodeTotal = "";
        String[] decodeTotallists = null;
//        Data = Data + "\n\n";
        String[] DataLists = Data.replace("\r", "").split("\n\n",2);
//        if (DataLists.length > 1) { // POST

            if (IndexautoDecoder.getRadioButtontestHeaderState() && IndexautoDecoder.getRadioButtontestdifferentState()) { // 选中对数据头处理、选中请求响应分开

                decodeTotallists = sendPostnewHeader(IndexautoDecoder.getEncodeApi(), DataLists[1], DataLists[0] + "\n", reqorresp);
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
                    decodeTotallists = sendPostnewHeader(IndexautoDecoder.getEncodeApi(), DataLists[1], DataLists[0] + "\n");
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
                    decodeTotal = sendPostnew(IndexautoDecoder.getEncodeApi(), DataLists[1], reqorresp);
                    Output = Data.replace(DataLists[1], decodeTotal);
                }

                if (!IndexautoDecoder.getRadioButtontestdifferentState() && !IndexautoDecoder.getRadioButtontestHeaderState()) { // 不选中对数据头处理且不选中请求响应分开
                    decodeTotal = sendPostnew(IndexautoDecoder.getEncodeApi(), DataLists[1]);
                    Output = Data.replace(DataLists[1], decodeTotal);
                }
            }
            return Output.trim();
//        return Output.trim()+"\n\n";
//    }else{
//
//            if (IndexautoDecoder.getRadioButtontestHeaderState() && IndexautoDecoder.getRadioButtontestdifferentState()) { // 选中对数据头处理、选中请求响应分开
//
//                decodeTotallists = sendPostnewHeader(IndexautoDecoder.getEncodeApi(), DataLists[1], DataLists[0] + "\n", reqorresp);
//                if (decodeTotallists.length == 1) {
//                    Output = decodeTotallists[0];
//                } else {
//                    Output = decodeTotallists[0] + "\n" + decodeTotallists[1];
//                }
//
//            } else {
//                if (IndexautoDecoder.getRadioButtontestHeaderState()) { // 选中对数据头处理
//                    decodeTotallists = sendPostnewHeader(IndexautoDecoder.getEncodeApi(), DataLists[1], DataLists[0] + "\n");
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
