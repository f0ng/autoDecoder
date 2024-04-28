package burp.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JSONConverter {
    public static void main(String[] args) {
        // 创建一个JSONObject对象
        JSONObject innerJson = new JSONObject();
        innerJson.put("id", "1");
        innerJson.put("value", "first");

        // 创建一个包含innerJson的JSONArray对象
        JSONArray reqpArray = new JSONArray();
        reqpArray.add(innerJson);

        // 创建包含reqpArray的JSONObject对象
        JSONObject outerJson = new JSONObject();
        outerJson.put("reqp", reqpArray);

        // 打印JSON表示
        System.out.println("JSON: " + outerJson.toJSONString());
    }
}