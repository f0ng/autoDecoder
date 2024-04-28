package burp;

import static Utils.Match.MatchReg;
import static Utils.Match.MatchReg_code;
import static burp.IndexautoDecoder.*;
import static burp.util.TableRead.*;
import static com.autoDecoder.util.codeDecode.decryptKeyivmode;
import static com.autoDecoder.util.codeEncode.encryptKeyivmode;

//import burp.network.Transmission;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import Utils.ClickEncodeDecode;
import burp.ui.iMessageEditorTab;
import burp.util.autoDecoderutil;
import org.apache.commons.codec.binary.Base64;
import java.util.List;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class BurpExtender extends AbstractTableModel  implements IBurpExtender, ITab, IMessageEditorController,IMessageEditorTabFactory ,IHttpListener ,IMessageEditor,IContextMenuFactory,ITextEditor{

    public static IBurpExtenderCallbacks callbacks;

    public static boolean isDecoded;

    public static boolean isWords; // 请求体中是否出现了关键字

    public static boolean ishost;

    public static boolean ishostReqp;

    public static boolean flag;

    public static URL URL;

    public static Boolean usehttps = false;

    public static IExtensionHelpers helpers;

    public static PrintWriter stdout;

    private IndexautoDecoder indexautoDecoder;

    public static boolean isDarkTheme;

//    public static List<String> DARK_THEMES = Arrays.asList("Darcula","FlatLaf - Burp Dark");

    public String version = "0.39-beta1";

//    private IContextMenuInvocation currentInvocation;

//    public static IHttpRequestResponse ihttpreqresp ;

    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {
        this.callbacks = callbacks;
        this.helpers = callbacks.getHelpers();
//        this.callbacks.s
        this.stdout = new PrintWriter(callbacks.getStdout(), true);
        callbacks.setExtensionName("autoDecoder");
        this.stdout.println("=======================================");
        this.stdout.println("[+]          load successful!          ");
        this.stdout.println("[+]     autoDecoder v" + version + "    ");
        this.stdout.println("[+]            code by f0ng            ");
        this.stdout.println("[+] https://github.com/f0ng/autoDecoder");
        this.stdout.println("=======================================");
        this.indexautoDecoder = new IndexautoDecoder(); // 创建GUI界面类

        BurpExtender.this.callbacks.registerContextMenuFactory(this);
        BurpExtender.this.callbacks.addSuiteTab(BurpExtender.this); // 注册 ITab 接口
        BurpExtender.this.callbacks.registerHttpListener(BurpExtender.this); // 注册 HttpListener 接口
        BurpExtender.this.callbacks.registerMessageEditorTabFactory( BurpExtender.this); // 注册 ITab 接口

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
    public List<JMenuItem> createMenuItems ( IContextMenuInvocation invocation ) {
//        this.currentInvocation = invocation;
        JMenuItem jMenuItem = new JMenuItem("Decode-Autodecoder");
        JMenuItem jMenuItem2 = new JMenuItem("Encode-Autodecoder");
        List<JMenuItem> jMenuItemList = new ArrayList<>();
        jMenuItemList.add(jMenuItem);
        jMenuItemList.add(jMenuItem2);

        // 监听上下文菜单点击事件
        jMenuItem.addActionListener(a -> { // 解密
            int[] selectedIndex = invocation.getSelectionBounds();
            IHttpRequestResponse req = invocation.getSelectedMessages()[0];
            byte[] request = req.getRequest();
            byte[] param = new byte[selectedIndex[1] - selectedIndex[0]];
            System.arraycopy(request, selectedIndex[0], param, 0, selectedIndex[1] - selectedIndex[0]);
            String selectString = new String(param);
            try {
                ClickEncodeDecode.Decode(invocation,selectString);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            stdout.println(selectString);// 获取选中的内容
        });

        // 监听上下文菜单点击事件
        jMenuItem2.addActionListener(a -> { // 加密
            int[] selectedIndex = invocation.getSelectionBounds();
            IHttpRequestResponse req = invocation.getSelectedMessages()[0];
            byte[] request = req.getRequest();
            byte[] param = new byte[selectedIndex[1] - selectedIndex[0]];
            System.arraycopy(request, selectedIndex[0], param, 0, selectedIndex[1] - selectedIndex[0]);
            String selectString = new String(param);
            try {
                ClickEncodeDecode.Encode(invocation,selectString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return jMenuItemList;
    }

    @Override
    public void processHttpMessage(int toolFlag, boolean messageIsRequest, IHttpRequestResponse iHttpRequestResponse) { //处理请求包
        usehttps = iHttpRequestResponse.getHttpService().getProtocol().contains("https");

        // 启用替换
        if ( getRadioButtonUseReqpState() &&  ( ( toolFlag == IBurpExtenderCallbacks.TOOL_PROXY && getRadioButtonReqp111State() ) || ( toolFlag == IBurpExtenderCallbacks.TOOL_REPEATER && getRadioButtonReqp222State() ) ||
                ( toolFlag == IBurpExtenderCallbacks.TOOL_INTRUDER && getRadioButtonReqp333State() )|| (toolFlag == IBurpExtenderCallbacks.TOOL_EXTENDER && getRadioButtonReqp444State() ) )){
            List<String> row_use =  new ArrayList<>();
            //  处理request header头
            for ( int row = 0 ;row <= table.getRowCount()-1; row ++) {
                if ( (Boolean)table.getValueAt(row, 0) ) // 获取enable参数
                    row_use.add(String.valueOf(row));// 将enable参数为true的写入list里
            }

            //  请求有关的参数
            byte[] request = iHttpRequestResponse.getRequest();
            IRequestInfo iRequestInfo = helpers.analyzeRequest(iHttpRequestResponse);
            // 获取请求中的所有参数


            if(messageIsRequest && row_use.size() > 0) {


                List<String> headersList = iRequestInfo.getHeaders();

                String headers_total = "";
                String host_value = "";
                for (String header : headersList) {
                    headers_total = headers_total + header + ("\r\n");
                    String[] host_lists = header.split(":");
                    if (host_lists[0].toLowerCase().equals("host")) {
                        host_value = host_lists[1];
                    }
                }
                int bodyOffset = iRequestInfo.getBodyOffset();

                byte[] body = Arrays.copyOfRange(request, bodyOffset, request.length);
                String code_body = new String(body);

                for (String row : row_use){ //对启用的规则进行替换
                    ishostReqp = false;
                    if ( table.getValueAt(Integer.parseInt(row), 1).toString().equals("*") ){ //  Host
                        ishostReqp = true;
                    }else if(host_value.equals(table.getValueAt(Integer.parseInt(row), 1).toString())) ishostReqp = true;
                    else if (host_value.endsWith(table.getValueAt(Integer.parseInt(row), 1).toString().replace("*",""))) ishostReqp = true;
                    if (ishostReqp) {
                        String Item = table.getValueAt(Integer.parseInt(row), 2).toString(); //Item
                        String Match = table.getValueAt(Integer.parseInt(row), 3).toString(); //Match
                        String Replace = table.getValueAt(Integer.parseInt(row), 4).toString(); //Replace
                        String Type = table.getValueAt(Integer.parseInt(row), 5).toString(); //Type
                        String[] total = MatchandReplace(headers_total, code_body, Type, Item, Match, Replace, REQUEST);
                        headers_total = total[0];
                        code_body = total[1];
                    }
                }

                List<String> end_headerstotallist = Arrays.asList(headers_total.split("\r\n"));

                byte[] httpmsgresp = helpers.buildHttpMessage(end_headerstotallist, code_body.getBytes());
                iHttpRequestResponse.setRequest(httpmsgresp);

            }else if( !messageIsRequest && row_use.size() > 0) {
                // todo 响应有关的参数
                byte[] response = iHttpRequestResponse.getResponse();
                IResponseInfo iResponseInfo = helpers.analyzeResponse(response);

                // 获取请求中的所有参数
                List<String> headersList = iResponseInfo.getHeaders();


                int bodyOffset = iResponseInfo.getBodyOffset();
                byte[] body = Arrays.copyOfRange(response, bodyOffset, response.length);
                String code_body = new String(body);

                String headers_total = "";
                String host_value = "";
                for (String header : headersList) {
                    headers_total = headers_total + header + ("\r\n");
                    String[] host_lists = header.split(":");
                    if (host_lists[0].toLowerCase().equals("host")) {
                        host_value = host_lists[1];
                    }
                }

                for (String row : row_use){ //对启用的规则进行替换
                    ishostReqp = false;
                    if ( table.getValueAt(Integer.parseInt(row), 1).toString().equals("*") ){ //  Host
                        ishostReqp = true;
                    }else if(host_value.equals(table.getValueAt(Integer.parseInt(row), 1).toString())) ishostReqp = true;
                    else if (host_value.endsWith(table.getValueAt(Integer.parseInt(row), 1).toString().replace("*",""))) ishostReqp = true;
                    if (ishostReqp) {
                        String Item = table.getValueAt(Integer.parseInt(row), 2).toString(); //Item
                        String Match = table.getValueAt(Integer.parseInt(row), 3).toString(); //Match
                        String Replace = table.getValueAt(Integer.parseInt(row), 4).toString(); //Replace
                        String Type = table.getValueAt(Integer.parseInt(row), 5).toString(); //Type
                        String[] total = MatchandReplace(headers_total, code_body, Type, Item, Match, Replace, RESPONSE);
                        headers_total = total[0];
                        code_body = total[1];

                    }
                }
//                stdout.println(code_body);
                List<String> end_headerstotallist = Arrays.asList(headers_total.split("\r\n"));

                byte[] httpmsgresp = helpers.buildHttpMessage(end_headerstotallist, code_body.getBytes());
                iHttpRequestResponse.setResponse(httpmsgresp);


            }



        }


            if ( ( ( toolFlag == IBurpExtenderCallbacks.TOOL_PROXY && getRadioButton1111State() ) || ( toolFlag == IBurpExtenderCallbacks.TOOL_REPEATER && getRadioButton2222State() ) ||
                    ( toolFlag == IBurpExtenderCallbacks.TOOL_INTRUDER && getRadioButton3333State() )|| (toolFlag == IBurpExtenderCallbacks.TOOL_EXTENDER && getRadioButton4444State() ) ) && !getRadioButtonUseReqpState()  ){

            if( (IndexautoDecoder.getRadioButton1State() || IndexautoDecoder.getRadioButton2State()) && messageIsRequest ) { // 请求的时候
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
                    for (String host : hosts) { // 单个加密的域名
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


                String code_body = ""; //新增对二进制流传输的解决方案
                if (IndexautoDecoder.getRadioButton5State()){
                    code_body = Base64.encodeBase64String(body);

                }else{
                    code_body = new String(body);
                }


                String encodeWords = autoDecoderutil.getwords(); // 明文关键字
                String[] encodeWords_lists = encodeWords.split(",");
                for (String encodeWords_single : encodeWords_lists) {
//                    stdout.println("352");
//                    stdout.println(encodeWords_single);
                    if (autoDecoderutil.FileGetValue(new File( BurpExtender.getPath() ),"wordsbody").equals("1")) {// 明文关键字 body处理
                        if ((new String(body).contains(encodeWords_single.trim())) && !encodeWords_single.equals("")) {
                            isWords = true;
                        }
                    }

                    if (autoDecoderutil.FileGetValue(new File( BurpExtender.getPath() ),"wordsheader").equals("1") && IndexautoDecoder.getRadioButton2State() ){// 明文关键字 header处理
                        if (String.join("\n", headersList).contains(encodeWords_single.trim()) && !encodeWords_single.equals("")){
                            isWords = true;
                        }
                    }
                }

                String not_encodeWords = autoDecoderutil.getnotwords(); // 加密关键字
                String[] not_encodeWords_lists = not_encodeWords.split(",");

                for (String not_encodeWords_single : not_encodeWords_lists) {
                    if (autoDecoderutil.FileGetValue(new File( BurpExtender.getPath() ),"notwordsbody").equals("1")) {// 加密关键字 body处理
                        //  加密关键字
                        if ((new String(body).contains(not_encodeWords_single)) && !not_encodeWords_single.equals(""))
//                            stdout.println("372");
                            isWords = false;
//                            isDecoded = true;
                    }

                    if (autoDecoderutil.FileGetValue(new File( BurpExtender.getPath() ),"notwordsheader").equals("1") && IndexautoDecoder.getRadioButton2State() ){// 加密关键字 header处理
                        if (String.join("\n", headersList).contains(not_encodeWords_single.trim()) && !not_encodeWords_single.equals("")){
//                            stdout.println("378");
                            isWords = false;
//                            isDecoded = true;
                        }
                    }
                }
                stdout.println(isWords);
                stdout.println(ishost);

                if ( ishost && isWords) {
                    isDecoded = true;
                    String decodeBody = null;

                    // getRadioButton3State 请求头处理
                    if (IndexautoDecoder.getRadioButton3State() && IndexautoDecoder.getRadioButton2State()) { // 当选中了接口加解密、对请求头进行处理
//                        System.out.println("134");
                        String totalHeaders = "";
                        for (String singleheader : headersList)
                            totalHeaders = totalHeaders + singleheader + "\r\n";
                        String[] decodeTotal = null;
//                        System.out.println("399");
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

                        if (decodeTotal.length == 1) { // decodeTotal的长度是不是为1，也就是请求体无参数

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
                        //获取是否通过接口进行加解密、        请求包、响应包的解密方式不分开             不对header进行处理

                        try {
                            String decodeTotal = sendPostnew(IndexautoDecoder.getEncodeApi(), code_body);
                            byte[] httpmsgresp = helpers.buildHttpMessage(headersList,decodeTotal.getBytes());
                            iHttpRequestResponse.setRequest(httpmsgresp);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }else if(IndexautoDecoder.getRadioButton1State()){ // 获取是否通过加解密算法进行加解密

                            if (!IndexautoDecoder.regtextField.getText().trim().equals("")){ // 正则提取中有表达式
                                String reg = IndexautoDecoder.regtextField.getText();
                                String EncryText = MatchReg(code_body, reg); // 以admin=1111&password=1111举例 获取1111
                                String EncryText_code = MatchReg_code(code_body, reg); // 以admin=1111&password=1111举例 获取password=1111

                                try {
                                    decodeBody = encryptKeyivmode(EncryText, DecodeParams[5], DecodeParams[6], DecodeParams[0], DecodeParams[1], DecodeParams[2], DecodeParams[3], DecodeParams[4]);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                byte[] httpmsg = helpers.buildHttpMessage(headersList, code_body.replace(EncryText_code,EncryText_code.replace(EncryText,decodeBody)).getBytes());
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

                String decodeBody = null;
                byte[] response = iHttpRequestResponse.getResponse();
                IResponseInfo iResponseInfo = helpers.analyzeResponse(response);

                // 获取请求中的所有参数
                List<String> headersList = iResponseInfo.getHeaders();
                int bodyOffset = iResponseInfo.getBodyOffset();
                byte[] body = Arrays.copyOfRange(response, bodyOffset, response.length);


                // todo 0417
                String not_encodeWords = autoDecoderutil.getnotwords(); // 加密关键字
                String[] not_encodeWords_lists = not_encodeWords.split(",");
                stdout.println("resp before isWords:"+isWords);
                stdout.println("resp before isDecoded:"+isDecoded);
                stdout.println(new String(body));


                for (String not_encodeWords_single : not_encodeWords_lists) {
                    if (autoDecoderutil.FileGetValue(new File( BurpExtender.getPath() ),"notwordsbody").equals("1")) {// 密文关键字 body处理
                        if ((new String(body).contains(not_encodeWords_single)) && !not_encodeWords_single.equals("")) {
                            stdout.println("496");
                            isWords = false;
                            isDecoded = false;
                        }
                    }

                    if (autoDecoderutil.FileGetValue(new File( BurpExtender.getPath() ),"notwordsheader").equals("1") && IndexautoDecoder.getRadioButton2State() ){// 密文关键字 header处理
                        if (String.join("\n", headersList).contains(not_encodeWords_single.trim())){
                            stdout.println("505");
                            isWords = false;
                            isDecoded = false;
                        }
                    }
                }

                stdout.println( "resp isWords:" + isWords);
                stdout.println( "resp isDecoded:" + isDecoded);

                if ( isDecoded && ishost ) {

                    String[] decodeTotal2 = null;

                    String code_body_resp = ""; //新增对二进制流传输的解决方案
                    if (IndexautoDecoder.getRadioButton6State()){

                        code_body_resp = Base64.encodeBase64String(body);

                    }else{
                        code_body_resp = new String(body);
                    }

                    //getRadioButton3State 接口加解密
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


                            if (IndexautoDecoder.getRadioButtonbase64decodeState()) { //选中响应包进行base64解码，无损
                                //  选中对响应包进行base64解码，无损获取响应包，参考 <@autoDecode-base64>dGVzdA==</@autoDecode-base64> 案例
//                            stdout.println(decodeTotal2[1]);
                            byte[] httpmsgresp = helpers.buildHttpMessage(Arrays.asList(decodeTotal2[0].split("\r\n")), Base64.decodeBase64(decodeTotal2[1].trim()));
                            iHttpRequestResponse.setResponse(httpmsgresp);
                            }else{
                                byte[] httpmsgresp2 = helpers.buildHttpMessage(Arrays.asList(decodeTotal2[0].split("\r\n")), decodeTotal2[1].getBytes());
                                iHttpRequestResponse.setResponse(httpmsgresp2);
                            }

                        }
                    } else {

                        if (IndexautoDecoder.getRadioButton1State()) { // 如果选中 通过加解密算法进行加解密
                            String[] respDecodeParams = IndexautoDecoder.getRespDecodeParams(); // 获取解密数组
                            if (!IndexautoDecoder.regtextField_resp.getText().trim().equals("")) { // 响应正则解密 0.24-beta2
                                String reg = IndexautoDecoder.regtextField_resp.getText();
                                String EncryText = MatchReg(new String(body), reg);
                                String EncryText_code = MatchReg_code(new String(body), reg); // 以admin=1111&password=1111举例 获取password=1111
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
                        if (IndexautoDecoder.getRadioButton2State()) { // 获取是否通过接口进行加解密

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

                            if (IndexautoDecoder.getRadioButtonbase64decodeState()) { //选中响应包进行base64解码，无损
//                                stdout.println(decodeTotal);
                                byte[] httpmsgresp = helpers.buildHttpMessage(headersList, Base64.decodeBase64(decodeTotal.trim()));
                                iHttpRequestResponse.setResponse(httpmsgresp);
                            }else {
                                byte[] httpmsgresp = helpers.buildHttpMessage(headersList, decodeTotal.getBytes());
                                iHttpRequestResponse.setResponse(httpmsgresp);
                            }
                        }
                    }
            } else{
                stdout.println("618" + isDecoded);
                stdout.println("密文关键字优先，故");
            }

            }
        }
    }

    @Override
    public Component getComponent() {
        return null;
    }

    @Override
    public void setEditable(boolean b) {

    }

    @Override
    public void setText(byte[] bytes) {

    }

    @Override
    public byte[] getText() {
        return new byte[0];
    }

    @Override
    public boolean isTextModified() {
        return false;
    }

    @Override
    public byte[] getSelectedText() {

        return null;
    }

    @Override
    public void setMessage(byte[] bytes, boolean b) {

    }

    @Override
    public byte[] getMessage() {

        return new byte[0];
    }

    @Override
    public boolean isMessageModified() {

        return false;
    }

    @Override
    public byte[] getSelectedData() {

        return new byte[0];
    }

    @Override
    public int[] getSelectionBounds() {

        return new int[0];
    }

    @Override
    public void setSearchExpression(String s) {

    }


    public static String getPath(){
        String oss = System.getProperty("os.name");

        try {
            if (!callbacks.loadExtensionSetting("autoDecoderfilepath").equals("") && callbacks.loadExtensionSetting("autoDecoderfilepath") != null) {
                return callbacks.loadExtensionSetting("autoDecoderfilepath");
            }
        }catch (Exception e){

        }

        if ( oss.toLowerCase().startsWith("win") ) {
            //System.out.println(oss);
            return "autoDecoder.properties";
        } else {
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
