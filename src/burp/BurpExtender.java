package burp;

import static burp.IndexautoDecoder.DecodeParams;
import static com.autoDecoder.util.codeDecode.decryptKeyivmode;
import static com.autoDecoder.util.codeEncode.encryptKeyivmode;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class BurpExtender extends AbstractTableModel  implements IBurpExtender, ITab, IMessageEditorController,IMessageEditorTabFactory ,IHttpListener{

    private static IBurpExtenderCallbacks callbacks;

    public static boolean isDecoded;

    public static boolean isWords; // 请求体中是否出现了关键字

    public static boolean ishost;

    public static boolean flag;

    private IExtensionHelpers helpers;

    private PrintWriter stdout;

    private IndexautoDecoder indexautoDecoder;

    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {
        this.callbacks = callbacks;
        this.helpers = callbacks.getHelpers();
        this.stdout = new PrintWriter(callbacks.getStdout(), true);
        callbacks.setExtensionName("autoDecoder");
        this.stdout.println("=======================================");
        this.stdout.println("[+]          load successful!          ");
        this.stdout.println("[+]         autoDecoder v0.16          ");
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
        // 当proxy、intruder、repeater模块的时候调用加解密
            if ( (toolFlag == IBurpExtenderCallbacks.TOOL_REPEATER ) || (toolFlag == IBurpExtenderCallbacks.TOOL_INTRUDER ) || (toolFlag == IBurpExtenderCallbacks.TOOL_PROXY )){
            if( (IndexautoDecoder.getRadioButton1State() || IndexautoDecoder.getRadioButton2State()) && messageIsRequest ) { // 请求的时候

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
                            if(host_value.replace("*", "").replaceAll(":(.*)", "").endsWith(host.replace("*", "")))
                                ishost = true;
                        }
                        // todo 端口设置问题
                        if ( header.endsWith(host.replace("*", "")) || host_value.replace("*", "").replaceAll(":(.*)", "").endsWith(host.replace("*", "")) ||
                                header.replace("*", "").endsWith(host.replace("*", ""))) {

                            ishost = true; //返回true
                        }

                    }
                }
                //System.out.println("124");
                String encodeWords = IndexautoDecoder.gettextArea2();
                String[] encodeWords_lists = encodeWords.split("\n");
                for (String encodeWords_single : encodeWords_lists) {
                    System.out.println((new String(body)));
                    System.out.println(encodeWords_single);

                    if ( (new String(body).contains(encodeWords_single)) )
                        isWords = true;
                    System.out.println(isWords);
                }

                if ( ishost && isWords) {
                //if ((new String(body).contains("\"") || new String(body).contains(":")) && ishost) {
                    // todo 当请求体 里有"或者:的时候默认为明文，但是可能不能兼容所有情况
                    isDecoded = true;
                    String decodeBody = null;
                    //System.out.println("131");

                    if (IndexautoDecoder.getRadioButton3State() && IndexautoDecoder.getRadioButton2State()) { // 当选中了对请求头进行处理
                        //System.out.println("134");
                        String totalHeaders = "";
                        // todo 对接口处理，传入参数为接口的数组、请求体
                        for (String singleheader : headersList)
                            totalHeaders = totalHeaders + singleheader + "\r\n";
                        String[] decodeTotal = null;
                        try {
                            //System.out.println("142");
                            //System.out.println(totalHeaders);
                            decodeTotal = sendPostnewHeader(IndexautoDecoder.getEncodeApi(), new String(body),totalHeaders);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        assert decodeTotal != null;
                        stdout.println(decodeTotal);
                        stdout.println(decodeTotal[0]);
                        if (decodeTotal.length == 1) { // 判断decodeTotal的长度是不是为1，也就是请求体无参数

                            byte[] httpmsgresp = helpers.buildHttpMessage(Arrays.asList(decodeTotal[0].split("\r\n")), "".getBytes());
                            iHttpRequestResponse.setRequest(httpmsgresp);
                        }else {
                            byte[] httpmsgresp = helpers.buildHttpMessage(Arrays.asList(decodeTotal[0].split("\r\n")), decodeTotal[1].getBytes());
                            iHttpRequestResponse.setRequest(httpmsgresp);
                        }

                    } else {

                        if (IndexautoDecoder.getRadioButton1State()) { // 如果选中 通过加解密算法进行加解密
                            try {
                                decodeBody = encryptKeyivmode(new String(body), DecodeParams[5], DecodeParams[6], DecodeParams[0], DecodeParams[1], DecodeParams[2], DecodeParams[3], DecodeParams[4]);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            byte[] httpmsg = helpers.buildHttpMessage(headersList, decodeBody.getBytes());
                            iHttpRequestResponse.setRequest(httpmsg);
                        }
                        if (IndexautoDecoder.getRadioButton2State()) { // 如果选中 通过接口进行加解密
                            //System.out.println(new String(body));
                            String decodeTotal = null;
                            try {
                                decodeTotal = sendPostnew(IndexautoDecoder.getEncodeApi(), new String(body));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            byte[] httpmsgresp = helpers.buildHttpMessage(headersList, decodeTotal.getBytes());
                            iHttpRequestResponse.setRequest(httpmsgresp);
                        }
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
                    if (IndexautoDecoder.getRadioButton3State() && IndexautoDecoder.getRadioButton2State()) { // 如果选中 通过接口进行加解密
                        String totalHeaders = ""; // 0.16更新
                        // todo 对接口处理，传入参数为接口的数组、请求体
                        for (String singleheader : headersList)
                            totalHeaders = totalHeaders + singleheader + "\r\n";

                        try {
                            decodeTotal2 = sendPostnewHeader(IndexautoDecoder.getDecodeApi(), new String(body),totalHeaders);
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
                            try {
                                decodeBody = decryptKeyivmode(new String(body), DecodeParams[5], DecodeParams[6], DecodeParams[0], DecodeParams[1], DecodeParams[2], DecodeParams[3], DecodeParams[4]);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            byte[] httpmsgresp = helpers.buildHttpMessage(headersList, decodeBody.getBytes());
                            iHttpRequestResponse.setResponse(httpmsgresp);
                        }
                        if (IndexautoDecoder.getRadioButton2State()) {

                            String decodeTotal = null;
                            try {
                                decodeTotal = sendPostnew(IndexautoDecoder.getDecodeApi(), new String(body));
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


    class iMessageEditorTab implements IMessageEditorTab{
        //实例化iTextEditor返回当前加密数据显示的组件包括加密数据内容
        public ITextEditor iTextEditor = callbacks.createTextEditor();

        //设置消息编辑器的标题
        public String getTabCaption(){
            return "autoDecoder";
        }
        //设置组件 这里直接设置为默认的iTextEditor组件
        public Component getUiComponent(){
            return iTextEditor.getComponent();
        }

        //过滤对特定的请求才生成消息编辑器 当burp中捕捉的请求不符合
        //我们需要的则不生成消息编辑器
        //比如 消息中包含“param”字段、host为www.test.com才生成消息编辑器
        //则如果请求包含它们返回true
        public boolean isEnabled(byte[] content,boolean isRequest){
            //参数content byte[]即是getMessage中获取的iTextEditor中的文本
            //参数isRequest boolean即表示当前文本是request请求 还是 response接收的数据
            //当isRequest true表示request false表示response

            try{
                if (!(IndexautoDecoder.getRadioButton2State() || IndexautoDecoder.getRadioButton1State()))
                    return false;

                if(isRequest){// 判断当请求为request
                    flag = false;
                    IRequestInfo requestInfo = helpers.analyzeRequest(content);
                    List<String> headersList = requestInfo.getHeaders();
                    String[] hosts = IndexautoDecoder.getEncryptHosts();
                    for(String header : headersList) {
//                        System.out.println(header);
                        //请求头中包含指定域名“applog.xx.cn”才设置我们生成的消息编辑器
                        for ( String host:hosts) {
                            String host_value = "";
                            String[] host_lists = header.split(":");
                            if (host_lists[0].toLowerCase().equals("host")) {
                                if (host_lists.length > 2)
                                    host_value = host_lists[1] + ":" + host_lists[2];
                                else
                                    host_value = host_lists[1];
                                host_value = host_value.trim();
                                if(host_value.replace("*", "").replaceAll(":(.*)", "").endsWith(host.replace("*", "")))
                                    flag = true;
                                break;
                            }
                            // todo 端口设置问题
                            if ( header.endsWith(host.replace("*", "")) || host_value.replace("*", "").replaceAll(":(.*)", "").endsWith(host.replace("*", "")) ||
                                    header.replace("*", "").endsWith(host.replace("*", ""))) {
                                flag = true; //返回true
                                break;
                            }

                        }
                    }
                }else{ // 响应包也需要解密模块
                    if (flag)
                        return true;
                }
            }catch(Exception e){
                e.printStackTrace();
                flag = false;
            }
            return flag;
        }

        //我们要在消息编辑器中显示的消息
        //比如对content解密、添加额外内容、或者替换掉再返回到消息编辑器中
        public void setMessage(byte[] content,boolean isRequest){
            //参数content byte[]即是getMessage中获取的iTextEditor中的文本
            //参数isRequest boolean即表示当前是request请求 还是 response接收的数据
            //当isRequest true表示request false表示response
            try{
                if(isRequest){// 判断当请求为request才处理数据
                    IRequestInfo requestInfo = helpers.analyzeRequest(content);
                    int bodyOffset = requestInfo.getBodyOffset();
                    byte[] body = Arrays.copyOfRange(content, bodyOffset, content.length);
                    List<String> headersList = requestInfo.getHeaders();

                    if (IndexautoDecoder.getRadioButton1State()) { // 如果选中 通过加解密算法进行加解密
                        //if (new String(body).contains("\"") || new String(body).contains("'")) {
                        if(isWords){ // 如果请求的是明文，则正常显示明文
                            iTextEditor.setText(content);
                        } else { // 如果请求的不是明文，则会通过加密
                            String[] DecodeParams = IndexautoDecoder.getDecodeParams(); // 获取解密数组
                            String decodeBody = decryptKeyivmode(new String(body), DecodeParams[5], DecodeParams[6], DecodeParams[0], DecodeParams[1], DecodeParams[2], DecodeParams[3], DecodeParams[4]);
                            String totalHeaders = "";
                            for (String singleheader : headersList)
                                totalHeaders = totalHeaders + singleheader + "\r\n";
                            iTextEditor.setText((totalHeaders + "\r\n" + decodeBody).getBytes());
                        }
                    }

                    if (IndexautoDecoder.getRadioButton2State()) { // 如果选中 通过接口进行加解密
                        //if (IndexautoDecoder.getRadioButton3State()){ // 当选中了对请求头进行处理
                        //    if ((new String(body).contains("\"") || new String(body).contains("'"))) {
                        //        iTextEditor.setText(content);
                        //    } else {
                        //        StringBuilder totalHeaders = new StringBuilder();
                        //        // todo 对接口处理，传入参数为接口的数组、请求体
                        //        for (String singleheader : headersList)
                        //            totalHeaders.append(singleheader).append("\r\n");
                        //
                        //        String decodeTotal = sendPostnewHeader(IndexautoDecoder.getDecodeApi(), new String(body),totalHeaders);
                        //        iTextEditor.setText((totalHeaders + "\r\n" + decodeTotal).getBytes());
                        //    }
                        //}else {
                        //    if ((new String(body).contains("\"") || new String(body).contains("'"))) {
                        if (isWords){
                                iTextEditor.setText(content);
                            } else {
                                StringBuilder totalHeaders = new StringBuilder();
                                for (String singleheader : headersList)
                                    totalHeaders.append(singleheader).append("\r\n");

                                String decodeTotal = sendPostnew(IndexautoDecoder.getDecodeApi(), new String(body));
                                iTextEditor.setText((totalHeaders + "\r\n" + decodeTotal).getBytes());
                            //}
                        }
                    }

                }else { // 处理响应包的数据
                    IRequestInfo requestInfo = helpers.analyzeRequest(content);
                    int bodyOffset = requestInfo.getBodyOffset();
                    byte[] body = Arrays.copyOfRange(content, bodyOffset, content.length);
                    List<String> headersList = requestInfo.getHeaders();
                    String[] DecodeParams = IndexautoDecoder.getDecodeParams(); // 获取解密数组
                    if (IndexautoDecoder.getRadioButton1State()) { // 如果选中 通过加解密算法进行加解密
                        String decodeBody = decryptKeyivmode(new String(body), DecodeParams[5], DecodeParams[6], DecodeParams[0], DecodeParams[1], DecodeParams[2], DecodeParams[3], DecodeParams[4]);
                        String totalHeaders = "";
                        for (String singleheader : headersList)
                            totalHeaders = totalHeaders + singleheader + "\r\n";
                        iTextEditor.setText((totalHeaders + "\r\n" + decodeBody).getBytes());
                    }

                    if (IndexautoDecoder.getRadioButton2State()) { // 如果选中 通过接口进行加解密
                        String totalHeaders = "";
                        for (String singleheader : headersList)
                            totalHeaders = totalHeaders + singleheader + "\r\n";

                        String decodeTotal = sendPostnew(IndexautoDecoder.getDecodeApi(),new String(body));
//                        System.out.println(decodeTotal);
                        iTextEditor.setText((totalHeaders + "\r\n" + decodeTotal).getBytes());

                    }

                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        //返回iTextEditor显示的文本
        public byte[] getMessage(){
            return iTextEditor.getText();
        }

        //允许修改消息
        public boolean isModified(){
            return true;
        }

        //返回iTextEditor中选定的文本 没有选择的话 则不返回数据
        public byte[] getSelectedData(){
            return iTextEditor.getSelectedText();
        }
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
        //System.out.println(result_list[0]);
        //System.out.println(result_list[1]);

        return result_list;
    }


}
