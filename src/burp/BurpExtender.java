package burp;

import static Utils.Match.MatchReg;
import static burp.IndexautoDecoder.DecodeParams;
import static burp.IndexautoDecoder.getRadioButton1111State;
import static burp.IndexautoDecoder.getRadioButton2222State;
import static burp.IndexautoDecoder.getRadioButton3333State;
import static burp.IndexautoDecoder.getRadioButton4444State;
import static com.autoDecoder.util.codeDecode.decryptKeyivmode;
import static com.autoDecoder.util.codeEncode.encryptKeyivmode;

//import burp.network.Transmission;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.Arrays;

import burp.ui.iMessageEditorTab;
import org.apache.commons.codec.binary.Base64;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class BurpExtender extends AbstractTableModel  implements IBurpExtender, ITab, IMessageEditorController,IMessageEditorTabFactory ,IHttpListener ,IMessageEditor{

    public static IBurpExtenderCallbacks callbacks;

    public static boolean isDecoded;

    public static boolean isWords; // 请求体中是否出现了关键字

    public static boolean ishost;

    public static boolean flag;

    public static URL URL;

    public static Boolean usehttps = false;

    public static IExtensionHelpers helpers;

    public static PrintWriter stdout;

    private IndexautoDecoder indexautoDecoder;

    public static boolean isDarkTheme;

    public static List<String> DARK_THEMES = Arrays.asList("Darcula","FlatLaf - Burp Dark");

    public String version = "0.27";

//    public static IHttpRequestResponse ihttpreqresp ;

    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {
        this.callbacks = callbacks;
        this.helpers = callbacks.getHelpers();
        this.stdout = new PrintWriter(callbacks.getStdout(), true);
        callbacks.setExtensionName("autoDecoder");
        this.stdout.println("=======================================");
        this.stdout.println("[+]          load successful!          ");
        this.stdout.println("[+]     autoDecoder v" + version + "    ");
        this.stdout.println("[+]            code by f0ng            ");
        this.stdout.println("[+] https://github.com/f0ng/autoDecoder");
        this.stdout.println("=======================================");
        this.indexautoDecoder = new IndexautoDecoder(); // 创建GUI界面类

        BurpExtender.this.callbacks.addSuiteTab(BurpExtender.this); // 注册 ITab 接口
        BurpExtender.this.callbacks.registerHttpListener(BurpExtender.this); // 注册 HttpListener 接口
        BurpExtender.this.callbacks.registerMessageEditorTabFactory((IMessageEditorTabFactory) BurpExtender.this); // 注册 ITab 接口

    }


    @Override
    public String getTabCaption() {
        return "autoDecoder";
    }

    @Override
    public Component getUiComponent() {
        return indexautoDecoder.$$$getRootComponent$$$();
    }

    @Override
    public IHttpService getHttpService() {
        return null;
    }

    @Override
    public byte[] getRequest() {
        return new byte[0];
    }

    @Override
    public byte[] getResponse() {
        return new byte[0];
    }

    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }


    @Override
    public IMessageEditorTab createNewInstance(IMessageEditorController iMessageEditorController, boolean b) {
        return new iMessageEditorTab();
    }

    @Override
    public void processHttpMessage(int toolFlag, boolean messageIsRequest, IHttpRequestResponse iHttpRequestResponse) { //处理请求包
        usehttps = iHttpRequestResponse.getHttpService().getProtocol().contains("https");
        // 当proxy、intruder、repeater模块的时候调用加解密
//        this.ihttpreqresp = iHttpRequestResponse;
            if ( ( toolFlag == IBurpExtenderCallbacks.TOOL_PROXY && getRadioButton1111State() ) || ( toolFlag == IBurpExtenderCallbacks.TOOL_REPEATER && getRadioButton2222State() ) ||
                    ( toolFlag == IBurpExtenderCallbacks.TOOL_INTRUDER && getRadioButton3333State() )|| (toolFlag == IBurpExtenderCallbacks.TOOL_EXTENDER && getRadioButton4444State() )){

            if( (IndexautoDecoder.getRadioButton1State() || IndexautoDecoder.getRadioButton2State()) && messageIsRequest ) { // 请求的时候 todo
//                stdout.println(( toolFlag == IBurpExtenderCallbacks.TOOL_PROXY && getRadioButton1111State() ));
                isDecoded = false;
                ishost = false;
                isWords = false;
                // 如果是明文，就加密以后发出请求，然后响应包也为明文
                byte[] request = iHttpRequestResponse.getRequest();
                IRequestInfo iRequestInfo = helpers.analyzeRequest(iHttpRequestResponse);
                // 获取请求中的所有参数

                List<String> headersList = iRequestInfo.getHeaders();
                int bodyOffset = iRequestInfo.getBodyOffset();

                byte[] body = Arrays.copyOfRange(request, bodyOffset, request.length);

                String[] hosts = IndexautoDecoder.getEncryptHosts(); // 得到需要加密的域名
                for (String header : headersList) {
                    for (String host : hosts) {
                        String host_value = "";
                        String[] host_lists = header.split(":");
                        if (host_lists[0].toLowerCase().equals("host")) {
                            if (host_lists.length > 2)
                                host_value = host_lists[1] + ":" + host_lists[2];
                            else
                                host_value = host_lists[1];
                            host_value = host_value.trim();
                            if(host_value.replace("*", "").replaceAll(":(.*)", "").endsWith(host.replace("*", "").replaceAll(":(.*)", "")))
                                ishost = true;
                        }
                        if ( header.endsWith(host.replace("*", "")) || host_value.replace("*", "").replaceAll(":(.*)", "").endsWith(host.replace("*", "")) ||
                                header.replace("*", "").endsWith(host.replace("*", ""))) {

                            ishost = true; //返回true
                        }

                    }
                }


                byte[] back = messageIsRequest ? iHttpRequestResponse.getRequest() : iHttpRequestResponse.getResponse();

                String code_body = ""; //新增对二进制流传输的解决方案
                if (IndexautoDecoder.getRadioButton5State()){

                    code_body = Base64.encodeBase64String(body);

                }else{
                    code_body = new String(body);
                }


                String encodeWords = IndexautoDecoder.gettextArea2(); // 加密关键字
                String[] encodeWords_lists = encodeWords.split("\n");
                for (String encodeWords_single : encodeWords_lists) {
//                    stdout.println(encodeWords_single);
                    if ( (new String(body).contains(encodeWords_single)) ) {
                        isWords = true;
                    }
                }

                String not_encodeWords = IndexautoDecoder.gettextArea3(); // 不加密关键字
                String[] not_encodeWords_lists = not_encodeWords.split("\n");

                for (String not_encodeWords_single : not_encodeWords_lists) {
                    if ( (new String(body).contains(not_encodeWords_single)) && !not_encodeWords_single.equals("")  )
                        isWords = false;
                    //System.out.println(isWords);
                }

                if ( ishost && isWords) {
//                    System.out.println(this.isTextModified());
                    isDecoded = true;
                    String decodeBody = null;

                    // getRadioButton3State 请求头处理 todo
                    if (IndexautoDecoder.getRadioButton3State() && IndexautoDecoder.getRadioButton2State()) { // 当选中了接口加解密、对请求头进行处理
                        //System.out.println("134");
                        String totalHeaders = "";
                        for (String singleheader : headersList)
                            totalHeaders = totalHeaders + singleheader + "\r\n";
                        String[] decodeTotal = null;
                        try {
                            if (IndexautoDecoder.getRadioButton4State()){ // 如果选中了请求包、响应包分开解密
                                decodeTotal = sendPostnewHeader(IndexautoDecoder.getEncodeApi(), code_body,totalHeaders,"request");
                            }else {
                                decodeTotal = sendPostnewHeader(IndexautoDecoder.getEncodeApi(), code_body, totalHeaders);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        assert decodeTotal != null;

                        if (decodeTotal.length == 1) { // 判断decodeTotal的长度是不是为1，也就是请求体无参数

                            byte[] httpmsgresp = helpers.buildHttpMessage(Arrays.asList(decodeTotal[0].split("\r\n")), "".getBytes());
                            iHttpRequestResponse.setRequest(httpmsgresp);
                        }else {
                            byte[] httpmsgresp = helpers.buildHttpMessage(Arrays.asList(decodeTotal[0].split("\r\n")), decodeTotal[1].getBytes());
                            iHttpRequestResponse.setRequest(httpmsgresp);
                        }

                    } else if (IndexautoDecoder.getRadioButton2State() && IndexautoDecoder.getRadioButton4State()){ //获取是否通过接口进行加解密、         请求包、响应包的解密方式是否分开

                        try {
                            String decodeTotal = sendPostnew(IndexautoDecoder.getEncodeApi(), code_body, "request");
                            byte[] httpmsgresp = helpers.buildHttpMessage(headersList,decodeTotal.getBytes());
                            iHttpRequestResponse.setRequest(httpmsgresp);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }else if (IndexautoDecoder.getRadioButton2State() && !IndexautoDecoder.getRadioButton4State() && !IndexautoDecoder.getRadioButton3State()){

                        try {
                            String decodeTotal = sendPostnew(IndexautoDecoder.getEncodeApi(), code_body);
                            byte[] httpmsgresp = helpers.buildHttpMessage(headersList,decodeTotal.getBytes());
                            iHttpRequestResponse.setRequest(httpmsgresp);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }else if(IndexautoDecoder.getRadioButton1State()){

//                        if (IndexautoDecoder.getRadioButton1State()) { // 如果选中 通过加解密算法进行加解密
                            if (!IndexautoDecoder.regtextField.getText().trim().equals("")){
                                String reg = IndexautoDecoder.regtextField.getText();
                                String EncryText = MatchReg(code_body, reg);

                                try {
                                    decodeBody = encryptKeyivmode(EncryText, DecodeParams[5], DecodeParams[6], DecodeParams[0], DecodeParams[1], DecodeParams[2], DecodeParams[3], DecodeParams[4]);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                byte[] httpmsg = helpers.buildHttpMessage(headersList, code_body.replace(EncryText,decodeBody).getBytes());
                                iHttpRequestResponse.setRequest(httpmsg);


                            } else {
                                try {
                                    decodeBody = encryptKeyivmode( code_body, DecodeParams[5], DecodeParams[6], DecodeParams[0], DecodeParams[1], DecodeParams[2], DecodeParams[3], DecodeParams[4]);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                byte[] httpmsg = helpers.buildHttpMessage(headersList, decodeBody.getBytes());
                                iHttpRequestResponse.setRequest(httpmsg);
                            }
//                        }
//                        if (IndexautoDecoder.getRadioButton2State()) { // 如果选中 通过接口进行加解密
//                            //System.out.println(new String(body));
//                            String decodeTotal = null;
//                            try {
//                                decodeTotal = sendPostnew(IndexautoDecoder.getEncodeApi(), new String(body));
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//
//                            byte[] httpmsgresp = helpers.buildHttpMessage(headersList, decodeTotal.getBytes());
//                            iHttpRequestResponse.setRequest(httpmsgresp);
//                        }
                    }

            }
            }else if((IndexautoDecoder.getRadioButton2State() || IndexautoDecoder.getRadioButton1State()) && !messageIsRequest) { // 响应的时候，如果是以明文进行请求，那么响应包也为明文
//                System.out.println(isDecoded);

                if (isDecoded && ishost) {


                    //System.out.println(isDecoded);
                    String decodeBody = null;
                    byte[] response = iHttpRequestResponse.getResponse();
                    IResponseInfo iResponseInfo = helpers.analyzeResponse(response);

                    // 获取请求中的所有参数
                    List<String> headersList = iResponseInfo.getHeaders();
                    int bodyOffset = iResponseInfo.getBodyOffset();
                    byte[] body = Arrays.copyOfRange(response, bodyOffset, response.length);
                    String[] decodeTotal2 = null;

                    String code_body_resp = ""; //新增对二进制流传输的解决方案
                    if (IndexautoDecoder.getRadioButton6State()){

                        code_body_resp = Base64.encodeBase64String(body);

                    }else{
                        code_body_resp = new String(body);
                    }

                    //getRadioButton3State 接口加解密 todo
                    if (IndexautoDecoder.getRadioButton3State() && IndexautoDecoder.getRadioButton2State()) { // 如果选中 对header进行处理    通过接口进行加解密
                        String totalHeaders = ""; // 0.16更新
                        for (String singleheader : headersList)
                            totalHeaders = totalHeaders + singleheader + "\r\n";

                        try {
                            if (IndexautoDecoder.getRadioButton4State()){ // 如果选中了请求包、响应包分开解密
                                decodeTotal2 = sendPostnewHeader(IndexautoDecoder.getDecodeApi(), code_body_resp, totalHeaders ,"response");
                            }else {
                                decodeTotal2 = sendPostnewHeader(IndexautoDecoder.getDecodeApi(), code_body_resp, totalHeaders);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (decodeTotal2.length == 1) {  // 判断decodeTotal的长度是不是为1，也就是响应体无参数

                            byte[] httpmsgresp2 = helpers.buildHttpMessage(Arrays.asList(decodeTotal2[0].split("\r\n")), "".getBytes());
                            iHttpRequestResponse.setResponse(httpmsgresp2);
                        }else {
                            byte[] httpmsgresp2 = helpers.buildHttpMessage(Arrays.asList(decodeTotal2[0].split("\r\n")), decodeTotal2[1].getBytes());
                            iHttpRequestResponse.setResponse(httpmsgresp2);
                        }
                    } else {

                        if (IndexautoDecoder.getRadioButton1State()) { // 如果选中 通过加解密算法进行加解密
                            String[] respDecodeParams = IndexautoDecoder.getRespDecodeParams(); // 获取解密数组
                            if (!IndexautoDecoder.regtextField_resp.getText().trim().equals("")) { // 响应正则解密 0.24-beta2
                                String reg = IndexautoDecoder.regtextField_resp.getText();
                                String EncryText = MatchReg(new String(body), reg);
//                                stdout.println(EncryText);
                                try {
                                    decodeBody = decryptKeyivmode(EncryText, respDecodeParams[5], respDecodeParams[6], respDecodeParams[0], respDecodeParams[1], respDecodeParams[2], respDecodeParams[3], respDecodeParams[4]);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                byte[] httpmsgresp = helpers.buildHttpMessage(headersList, decodeBody.getBytes());
                                iHttpRequestResponse.setResponse(httpmsgresp);

                            } else {

                                try {
                                    decodeBody = decryptKeyivmode(code_body_resp, respDecodeParams[5], respDecodeParams[6], respDecodeParams[0], respDecodeParams[1], respDecodeParams[2], respDecodeParams[3], respDecodeParams[4]);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                byte[] httpmsgresp = helpers.buildHttpMessage(headersList, decodeBody.getBytes());
                                iHttpRequestResponse.setResponse(httpmsgresp);
                            }
                        }
                        if (IndexautoDecoder.getRadioButton2State()) {

                            String decodeTotal = null;
                            try {
                                if (IndexautoDecoder.getRadioButton4State()){ // 如果选中了请求包、响应包分开解密
                                    decodeTotal = sendPostnew(IndexautoDecoder.getDecodeApi(), code_body_resp ,"response");
                                }else {
                                    decodeTotal = sendPostnew(IndexautoDecoder.getDecodeApi(), code_body_resp );
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            byte[] httpmsgresp = helpers.buildHttpMessage(headersList, decodeTotal.getBytes());
                            iHttpRequestResponse.setResponse(httpmsgresp);
                        }
                    }
            }
            }
        }
    }

    @Override
    public Component getComponent() {
        stdout.println("1");
        return null;
    }

    @Override
    public void setMessage(byte[] bytes, boolean b) {
        stdout.println("2");
    }

    @Override
    public byte[] getMessage() {
        stdout.println("3");
        return new byte[0];
    }

    @Override
    public boolean isMessageModified() {
        stdout.println("4");
        return false;
    }

    @Override
    public byte[] getSelectedData() {
        stdout.println("5");
        return new byte[0];
    }

    @Override
    public int[] getSelectionBounds() {
        stdout.println("6");
        return new int[0];
    }


    public static String getPath(){
        String oss = System.getProperty("os.name");

        if ( oss.toLowerCase().startsWith("win") ) {
            //System.out.println(oss);
            return "autoDecoder.properties";
        }else {
            String jarPath = callbacks.getExtensionFilename(); // 获取当前jar的路径
            //System.out.println(jarPath);
            return jarPath.substring(0, jarPath.lastIndexOf("/")) + "/autoDecoder.properties";
        }
    }

    public static String sendPostnew(String url, String param) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody requestBody = new FormBody.Builder()
                .add("dataBody",param)
                .build();


        Request request = new Request.Builder()
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .url(url)
                .post(requestBody)
                .build();

        String result = okHttpClient.newCall(request).execute().body().string();
        return result;
    }

    /***
     * 增加是请求还是响应的参数
     * @param url
     * @param param
     * @param requestorresponse
     * @return
     * @throws IOException
     */
    public static String sendPostnew(String url, String param,String requestorresponse) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody requestBody = new FormBody.Builder()
                .add("dataBody",param)
                .add("requestorresponse",requestorresponse)
                .build();


        Request request = new Request.Builder()
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .url(url)
                .post(requestBody)
                .build();

        String result = okHttpClient.newCall(request).execute().body().string();
        return result;
    }

    public static String[] sendPostnewHeader(String url, String param,String headers) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody requestBody = new FormBody.Builder()
                .add("dataBody",param)
                .add("dataHeaders", headers)
                .build();


        Request request = new Request.Builder()
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .url(url)
                .post(requestBody)
                .build();

        String result = okHttpClient.newCall(request).execute().body().string();
        String[] result_list = result.split("\r\n\r\n\r\n\r\n");

        return result_list;
    }

    /**
     * 增加是请求还是响应的参数
     * @param url
     * @param param
     * @param headers
     * @param requestorresponse
     * @return
     * @throws IOException
     */
    public static String[] sendPostnewHeader(String url, String param,String headers,String requestorresponse) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody requestBody = new FormBody.Builder()
                .add("dataBody",param)
                .add("dataHeaders", headers)
                .add("requestorresponse",requestorresponse)
                .build();


        Request request = new Request.Builder()
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .url(url)
                .post(requestBody)
                .build();

        String result = okHttpClient.newCall(request).execute().body().string();
        String[] result_list = result.split("\r\n\r\n\r\n\r\n");

        return result_list;
    }




}
