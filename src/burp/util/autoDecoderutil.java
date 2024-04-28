package burp.util;

import burp.BurpExtender;

import java.io.*;

/**
 * @DESCRIPTION:
 * @USER: f0ng
 * @DATE: 2023/10/23 下午2:07
 */
public class autoDecoderutil {
    public static void main(String[] args) {
        String regrex_req = FileGetValue(new File("target/test.properties"),"regrex_req").trim(); // regrex_req
        System.out.println(regrex_req);
    }
    public static String FileGetValue(File f, String key) { // 读取properties文件，根据key取出value
        BufferedReader reader = null;
        String value = "";
        StringBuffer sbf = new StringBuffer();
        String output = "";
        try {
            reader = new BufferedReader(new FileReader(f));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr + '\n');
            }
            reader.close();
            output = sbf.toString();
        } catch (IOException e) {
        }
        String[] properties_lists = output.split("\n");
        for (String str : properties_lists) {
            String[] str_lists = str.split("=", 2);
            if (str_lists[0].equals(key))
                value = str_lists[1];
        }
        return value.trim();
    }

    public static String getwords() { // 明文关键字
        File f = new File( BurpExtender.getPath() );
        String encodemode = FileGetValue(f,"words");
        return encodemode;
    }

    public static String getnotwords() { // 加密关键字

        File f = new File( BurpExtender.getPath() );
        String encodemode = FileGetValue(f,"notwords");
        return encodemode;
    }

    public static void createProperties(File f){
        if (!f.exists())
        {
            try {
                f.createNewFile();
                try (FileWriter fileWriter = new FileWriter(f)) {
                    fileWriter.append("encodemethod=0");
                    fileWriter.append("\n");
                    fileWriter.append("encodeapi=http://127.0.0.1:8888/encode");
                    fileWriter.append("\n");
                    fileWriter.append("decodeapi=http://127.0.0.1:8888/decode");
                    fileWriter.append("\n");
                    fileWriter.append("encodeheaders=2");
                    fileWriter.append("\n");
                    fileWriter.append("reqencodemode=DES");
                    fileWriter.append("\n");
                    fileWriter.append("reqivmode=CBC");
                    fileWriter.append("\n");
                    fileWriter.append("reqpaddingmode=PKCS5Padding");
                    fileWriter.append("\n");
                    fileWriter.append("reqsSrcmode=Base64");
                    fileWriter.append("\n");
                    fileWriter.append("reqkeyivmode=null");
                    fileWriter.append("\n");
                    fileWriter.append("reqskey=f0ngtest");
                    fileWriter.append("\n");
                    fileWriter.append("reqiv=f0ngf0ng");
                    fileWriter.append("\n");

                    fileWriter.append("respencodemode=DES");
                    fileWriter.append("\n");
                    fileWriter.append("respivmode=CBC");
                    fileWriter.append("\n");
                    fileWriter.append("resppaddingmode=PKCS5Padding");
                    fileWriter.append("\n");
                    fileWriter.append("respsSrcmode=Base64");
                    fileWriter.append("\n");
                    fileWriter.append("respkeyivmode=null");
                    fileWriter.append("\n");
                    fileWriter.append("respskey=f0ngtest");
                    fileWriter.append("\n");
                    fileWriter.append("respiv=f0ngf0ng");
                    fileWriter.append("\n");

                    fileWriter.append("hosts=10.211.55.4");
                    fileWriter.append("\n");
                    fileWriter.append("words=\",:");
                    fileWriter.append("\n");
                    fileWriter.append("wordsheader=0");
                    fileWriter.append("\n");
                    fileWriter.append("wordsbody=1");
                    fileWriter.append("\n");
                    fileWriter.append("notwords=");
                    fileWriter.append("\n");
                    fileWriter.append("notwordsheader=0");
                    fileWriter.append("\n");
                    fileWriter.append("notwordsbody=1");
                    fileWriter.append("\n");
                    fileWriter.append("regrex_req=");
                    fileWriter.append("\n");
                    fileWriter.append("regrex_resp=");
                    fileWriter.append("\n");

                    fileWriter.append("Proxy=1");
                    fileWriter.append("\n");
                    fileWriter.append("Repeater=1");
                    fileWriter.append("\n");
                    fileWriter.append("Intruder=1");
                    fileWriter.append("\n");
                    fileWriter.append("Extender=0");
                    fileWriter.append("\n");

                    fileWriter.append("selfurl=0");
                    fileWriter.append("\n");
                    fileWriter.append("selfencodeurl=0");
                    fileWriter.append("\n");
                    fileWriter.append("headerencode=0");
                    fileWriter.append("\n");
                    fileWriter.append("reqresp=0");
                    fileWriter.append("\n");
                    fileWriter.append("reqbase64=0");
                    fileWriter.append("\n");
                    fileWriter.append("respbase64=0");
                    fileWriter.append("\n");
                    fileWriter.append("autobase64resp=0");

                    fileWriter.flush();
                } catch (IOException e) { e.printStackTrace(); }
            } catch (IOException e) { e.printStackTrace(); }
        }
    }
}
