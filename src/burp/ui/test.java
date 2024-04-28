package burp.ui;

import burp.BurpExtender;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static burp.util.autoDecoderutil.FileGetValue;

/**
 * @DESCRIPTION:
 * @USER: f0ng
 * @DATE: 2023/6/26 下午6:34
 */
public class test {
    public static void main(String[] args) {
//        File f = new File("/Users/f0ng/BURP/BurpUnlimited/autoDecoder/target/autoDecoder.properties");
//        String encodemethod = FileGetValue(f,"encodemethod").trim();; // encodemethod
//        System.out.println(encodemethod.trim());
//        System.out.println("2");
//        System.out.println(encodemethod.trim().equals("2"));
        // 读取reqp数组

        String jsonStr = FileGetValue(new File( "autoDecoder.properties" ),"reqp");
        System.out.println(jsonStr);
        JSONObject json = JSON.parseObject(jsonStr);
        JSONArray reqpArray = json.getJSONArray("reqp");

        Object[][] data11199 = new Object[reqpArray.size()][];
        // 遍历reqp数组中的每个对象
        for (int i = 0; i < reqpArray.size(); i++) {
            JSONObject reqpObj = reqpArray.getJSONObject(i);

            // 读取每个对象的属性值
            String Enable = reqpObj.getString("Enable");
            String item = reqpObj.getString("Item");
            String match = reqpObj.getString("Match");
            String replace = reqpObj.getString("Replace");
            String type = reqpObj.getString("Type");
            String comment = reqpObj.getString("Comment");

            data11199[i] = new Object[]{Enable,item,match,replace,type,comment};
            // 打印读取到的属性值
            System.out.println("Enable: " + Enable);
            System.out.println("Item: " + item);
            System.out.println("Replace: " + replace);
            System.out.println("Comment: " + comment);
            System.out.println("Type: " + type);
            System.out.println("Match: " + match);
        }
            System.out.println(Arrays.deepToString(data11199));

            Object[][] data1119 = {
            {true, "Response body", "Connection: .*", "Replace 22222", "Regex", "Comment 2"},
            {true, "Response body", "<center><h1>30(.*)ound", "ngi</@auto>nx", "Extract", "Comment 444444"}
            };
        data1119[0] = new Object[] {"aaaa"};
        System.out.println(Arrays.deepToString(data1119));
    }
}
