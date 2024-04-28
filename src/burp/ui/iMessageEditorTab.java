package burp.ui;

import burp.*;
import burp.util.autoDecoderutil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.commons.codec.binary.Base64;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static Utils.Match.MatchReg;
import static burp.BurpExtender.*;
import static com.autoDecoder.util.codeDecode.decryptKeyivmode;

/**
 * @DESCRIPTION:
 * @USER: f0ng
 * @DATE: 2023/5/21 上午12:48
 */
public class iMessageEditorTab implements IMessageEditorTab {

    private  JPanel hackvertorContainer = new JPanel(new BorderLayout());
//    private  JPanel hackvertorPanel = new JPanel(new BorderLayout());
    public  RSyntaxTextArea inputArea ;;
//    public static Boolean result;



    public iMessageEditorTab(){

//        BurpExtender.callbacks.customizeUiComponent(this.inputArea);
        JTextComponent.removeKeymap("RTextAreaKeymap");
        UIManager.put("RSyntaxTextAreaUI.actionMap", null);
        UIManager.put("RSyntaxTextAreaUI.inputMap", null);
        UIManager.put("RTextAreaUI.actionMap", null);
        UIManager.put("RTextAreaUI.inputMap", null);
//        inputArea = new RSyntaxTextArea(20, 60);
        this.inputArea = new HackvertorInput();
//        Utils.Utils.applyThemeToRSyntaxTextArea(inputArea, "dark"); // todo 设置黑暗主体
        inputArea.setWhitespaceVisible(true);
//        inputArea.setEOLMarkersVisible();
        SwingUtilities.invokeLater(() -> inputArea.setVisible(true));
        this.inputArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
        this.inputArea.setEditable(true);
        this.inputArea.setCodeFoldingEnabled(true);
        Utils.Utils.configureRSyntaxArea(this.inputArea);
        inputArea.setPopupMenu(Utils.Utils.getPopup(this.inputArea));
//        hackvertorPanel.add(this.inputArea);
        hackvertorContainer.add(new RTextScrollPane(this.inputArea));


    }


    //实例化this.inputArea返回当前加密数据显示的组件包括加密数据内容
//    public this.inputArea this.inputArea = BurpExtender.callbacks.createTextEditor();

    //设置消息编辑器的标题
    public String getTabCaption(){
        return "autoDecoder";
    }

    //设置组件 这里直接设置为默认的this.inputArea组件
    public Component getUiComponent(){
        return hackvertorContainer;
    }

    //过滤对特定的请求才生成消息编辑器 当burp中捕捉的请求不符合
    //我们需要的则不生成消息编辑器
    //比如 消息中包含“param”字段、host为www.test.com才生成消息编辑器
    //则如果请求包含它们返回true

    public boolean isEnabled(byte[] content,boolean isRequest){
        //参数content byte[]即是getMessage中获取的this.inputArea中的文本
        //参数isRequest boolean即表示当前文本是request请求 还是 response接收的数据
        //当isRequest true表示request false表示response

        try{
            if (!(IndexautoDecoder.getRadioButton2State() || IndexautoDecoder.getRadioButton1State()))
                return false;

            if(isRequest){// 判断当请求为request
                BurpExtender.flag = false;
                IRequestInfo requestInfo = BurpExtender.helpers.analyzeRequest(content);
                java.util.List<String> headersList = requestInfo.getHeaders();
                String[] hosts = IndexautoDecoder.getEncryptHosts();
                for(String header : headersList) {
//                        System.out.println(header);
                    //请求头中包含指定域名“applog.xx.cn”才设置我们生成的消息编辑器
//                    for ( String host:hosts) {
//                        String host_value = "";
//                        String[] host_lists = header.split(":");
//                        if (host_lists[0].toLowerCase().equals("host")) {
//                            if (host_lists.length > 2)
//                                host_value = host_lists[1] + ":" + host_lists[2];
//                            else
//                                host_value = host_lists[1];
//                            host_value = host_value.trim();
//                            if(host_value.replace("*", "").replaceAll(":(.*)", "").endsWith(host.replace("*", "").replaceAll(":(.*)", "")))
//                                BurpExtender.flag = true;
//                            break;
//                        }
//                        if ( header.endsWith(host.replace("*", "")) || host_value.replace("*", "").replaceAll(":(.*)", "").endsWith(host.replace("*", "")) ||
//                                header.replace("*", "").endsWith(host.replace("*", ""))) {
//                            BurpExtender.flag = true; //返回true
//                            break;
//                        }
//                    }
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
                                BurpExtender.flag = true;
                        }

                        if ( header.endsWith(host.replace("*", "")) || host_value.replace("*", "").replaceAll(":(.*)", "").endsWith(host.replace("*", "")) ||
                                header.replace("*", "").endsWith(host.replace("*", ""))) {

                            BurpExtender.flag = true; //返回true
                        }

                    }
                }
//                stdout.println(BurpExtender.flag);
            }else{ // 响应包也需要解密模块
                if (BurpExtender.flag)
                    return true;
            }
        }catch(Exception e){
            e.printStackTrace();
            BurpExtender.flag = false;
        }
        return BurpExtender.flag;
    }

    //我们要在消息编辑器中显示的消息，在额外的扩展栏
    //比如对content解密、添加额外内容、或者替换掉再返回到消息编辑器中
    public void setMessage(byte[] content,boolean isRequest){
        //参数content byte[]即是getMessage中获取的this.inputArea中的文本
        //参数isRequest boolean即表示当前是request请求 还是 response接收的数据
        //当isRequest true表示request false表示response
        String contentt = new String(content);


        try{
            IRequestInfo requestInfo = BurpExtender.helpers.analyzeRequest(content);
            int bodyOffset = requestInfo.getBodyOffset();
            byte[] body = Arrays.copyOfRange(content, bodyOffset, content.length);
            List<String> headersList = requestInfo.getHeaders();
            String encodeWords = autoDecoderutil.getwords(); // 加密关键字
            String[] encodeWords_lists = encodeWords.split(",");

            BurpExtender.isWords = false;
            for (String encodeWords_single : encodeWords_lists) {
                if ( (new String(body).contains(encodeWords_single)) && !encodeWords_single.equals("")  ) {
                    BurpExtender.isWords = true;
                }
            }
            String not_encodeWords = autoDecoderutil.getnotwords(); // 不加密关键字
            String[] not_encodeWords_lists = not_encodeWords.split(",");

            for (String not_encodeWords_single : not_encodeWords_lists) {
                if ( (new String(body).contains(not_encodeWords_single)) && !not_encodeWords_single.equals("")  )
                    BurpExtender.isWords = false;
            }

            if(isRequest){  // 判断当请求为request才处理数据 todo
                String code_body = ""; //新增对二进制流传输的解决方案
                if (IndexautoDecoder.getRadioButton5State()){

                    code_body = Base64.encodeBase64String(body);

                }else{
                    code_body = new String(body);
                }

                if (IndexautoDecoder.getRadioButton1State()) { // 如果选中 通过加解密算法进行加解密
                    if(BurpExtender.isWords && IndexautoDecoder.regtextField.getText().trim().equals("")){ // 如果请求的是明文，则正常显示明文
//                        BurpExtender.stdout.println(isJSON2(new String(body, StandardCharsets.UTF_8)));
                        if (isJSON2(new String(body, StandardCharsets.UTF_8)) ) {
                            inputArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JSON);
                        }

                        this.inputArea.setText(contentt);
                    } else { // 如果请求的不是明文，则会通过解密
                        if (!IndexautoDecoder.regtextField.getText().trim().equals("")){
                            String[] DecodeParams = IndexautoDecoder.getDecodeParams();
                            String reg = IndexautoDecoder.regtextField.getText();
                            String EncryText = MatchReg(code_body, reg);
                            String decodeBody = decryptKeyivmode(EncryText, DecodeParams[5], DecodeParams[6], DecodeParams[0], DecodeParams[1], DecodeParams[2], DecodeParams[3], DecodeParams[4]);

                            String totalHeaders = "";
                            for (String singleheader : headersList)
                                totalHeaders = totalHeaders + singleheader + "\r\n";

                            if (isJSON2(code_body.replace(EncryText,decodeBody)) ) {
                                inputArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JSON);
                            }

                            this.inputArea.setText((totalHeaders + "\r\n" + (code_body.replace(EncryText,decodeBody)) ));
                        }
                        else {
                            String[] DecodeParams = IndexautoDecoder.getDecodeParams(); // 获取解密数组
                            String decodeBody = decryptKeyivmode(code_body, DecodeParams[5], DecodeParams[6], DecodeParams[0], DecodeParams[1], DecodeParams[2], DecodeParams[3], DecodeParams[4]);
                            String totalHeaders = "";
                            for (String singleheader : headersList)
                                totalHeaders = totalHeaders + singleheader + "\r\n";
                            if (isJSON2(decodeBody) ) {
                                inputArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JSON);
                            }
                            this.inputArea.setText((totalHeaders + "\r\n" + (decodeBody)));
                        }
                    }
                }

                if (IndexautoDecoder.getRadioButton2State()) { // 如果选中 通过接口进行加解密


                    if (BurpExtender.isWords){
                        this.inputArea.setText(contentt);
                    } else {
                        String totalHeaders = "";
                        for (String singleheader : headersList)
                            totalHeaders = totalHeaders + singleheader + "\r\n";

                        // getRadioButton3State 请求头处理 todo
                        if ( IndexautoDecoder.getRadioButton3State() ) { // 对请求头进行加密

                            if (IndexautoDecoder.getRadioButton4State()) { // 如果选中了请求包、响应包分开解密
                                String[] decodeTotal = sendPostnewHeader(IndexautoDecoder.getDecodeApi(), code_body, totalHeaders, "request");
                                if (isJSON2(decodeTotal[1]) ) {
                                    inputArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JSON);
                                }
                                this.inputArea.setText((decodeTotal[0] + "\r\n" + (decodeTotal[1])));
                            } else {
                                String[] decodeTotal = sendPostnewHeader(IndexautoDecoder.getDecodeApi(), code_body , totalHeaders);
                                if (isJSON2(decodeTotal[1]) ) {
                                    inputArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JSON);
                                }
                                this.inputArea.setText((decodeTotal[0] + "\r\n" + (decodeTotal[1])));
                            }

                        }else if(!IndexautoDecoder.getRadioButton3State()){ // 不对请求头进行加密
                            if (IndexautoDecoder.getRadioButton4State()) { // 如果选中了请求包、响应包分开解密
                                String decodeTotal = sendPostnew(IndexautoDecoder.getDecodeApi(), code_body, "request");
                                if (isJSON2(decodeTotal) ) {
                                    inputArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JSON);
                                }
                                this.inputArea.setText((totalHeaders + "\r\n" + (decodeTotal)));
                            } else {
                                String decodeTotal = sendPostnew(IndexautoDecoder.getDecodeApi(), code_body);
                                if (isJSON2(decodeTotal) ) {
                                    inputArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JSON);
                                }
                                this.inputArea.setText((totalHeaders + "\r\n" + (decodeTotal)));

                            }
                        }
                    }
                }

            }else { // 处理响应包的数据 todo

                String code_body_resp = ""; //新增对二进制流传输的解决方案
                if (IndexautoDecoder.getRadioButton6State()){

                    code_body_resp = Base64.encodeBase64String(body);

                }else{
                    code_body_resp = new String(body);
                }

                String[] DecodeParams = IndexautoDecoder.getDecodeParams(); // 获取解密数组
                String totalHeaders = "";
                for (String singleheader : headersList)
                    totalHeaders = totalHeaders + singleheader + "\r\n";
                if(BurpExtender.isWords){
                    if (isJSON2(new String(body, StandardCharsets.UTF_8)) ) {
                        inputArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JSON);
                    }
                    this.inputArea.setText(contentt);
                }else {
                    String decodeBody = null;
                    if (IndexautoDecoder.getRadioButton1State()) { // 如果选中 通过加解密算法进行加解密
                        String[] respDecodeParams = IndexautoDecoder.getRespDecodeParams(); // 获取解密数组

                        if (!IndexautoDecoder.regtextField_resp.getText().trim().equals("")) { // 响应正则解密 0.24-beta2
                            String reg = IndexautoDecoder.regtextField_resp.getText();
                            String EncryText = MatchReg(code_body_resp , reg);
                                stdout.println(EncryText);
                            stdout.println(respDecodeParams[5]+"##"+ respDecodeParams[6]+"##"+ respDecodeParams[0]+"##"+ respDecodeParams[1]+"##"+ respDecodeParams[2]+"##"+ respDecodeParams[3]+"##"+ respDecodeParams[4]);
                            try {

                                decodeBody = decryptKeyivmode(EncryText, respDecodeParams[5], respDecodeParams[6], respDecodeParams[0], respDecodeParams[1], respDecodeParams[2], respDecodeParams[3], respDecodeParams[4]);
                                stdout.println(decodeBody);
                                stdout.println(totalHeaders);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
//                            if (isJSON2(decodeBody) ) {
//                                inputArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JSON);
//                            }
                            stdout.println((totalHeaders + "\r\n" + beauty(decodeBody)));
                            this.inputArea.setText((totalHeaders + "\r\n" + beauty(decodeBody)));

                        } else { // 不是相应正则
                            decodeBody = decryptKeyivmode( code_body_resp  , DecodeParams[5], DecodeParams[6], DecodeParams[0], DecodeParams[1], DecodeParams[2], DecodeParams[3], DecodeParams[4]);
//                            for (String singleheader : headersList)
//                                totalHeaders = totalHeaders + singleheader + "\r\n";
                            if (isJSON2(decodeBody) ) {
                                inputArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JSON);
                            }
//                            stdout.println(totalHeaders);
                            this.inputArea.setText((totalHeaders + "\r\n" + beauty(decodeBody)));
                        }
                    }
                    if (IndexautoDecoder.getRadioButton2State()) { // 如果选中 通过接口进行加解密
                        if(!IndexautoDecoder.getRadioButton3State()) { // 不对请求头进行加密
//                            String totalHeaders = "";
//                            for (String singleheader : headersList)
//                                totalHeaders = totalHeaders + singleheader + "\r\n";
                            if (IndexautoDecoder.getRadioButton4State()) { // 如果选中了请求包、响应包分开解密
                                String decodeTotal = sendPostnew(IndexautoDecoder.getDecodeApi(), code_body_resp, "response");
                                if (isJSON2(decodeTotal) ) {
                                    inputArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JSON);
                                }
                                this.inputArea.setText((totalHeaders + "\r\n" + beauty(decodeTotal)));
                            } else {
                                String decodeTotal = sendPostnew(IndexautoDecoder.getDecodeApi(), code_body_resp);
                                if (isJSON2(decodeTotal) ) {
                                    inputArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JSON);
                                }
                                this.inputArea.setText((totalHeaders + "\r\n" + beauty(decodeTotal)));
                            }
                        }else if (IndexautoDecoder.getRadioButton3State()){  // 对请求头进行加密
                            if (IndexautoDecoder.getRadioButton4State()) { // 如果选中了请求包、响应包分开解密
                                String[] decodeTotal = sendPostnewHeader(IndexautoDecoder.getDecodeApi(), code_body_resp, totalHeaders, "response");
                                if (isJSON2(decodeTotal[1]) ) {
                                    inputArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JSON);
                                }
                                this.inputArea.setText((decodeTotal[0] + "\r\n" + beauty(decodeTotal[1])));
                            } else {
                                String[] decodeTotal = sendPostnewHeader(IndexautoDecoder.getDecodeApi(), code_body_resp , totalHeaders);
                                if (isJSON2(decodeTotal[1]) ) {
                                    inputArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JSON);
                                }
                                this.inputArea.setText((decodeTotal[0] + "\r\n" + beauty(decodeTotal[1])));
                            }

                        }
                    }
                }

            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static boolean isJSON2(String str) {
        Boolean result = false;
        str = str.trim();
//        BurpExtender.stdout.println(str);
        try {
            Object obj=JSON.parse(str);
            result = true;
        } catch (Exception e) {
            result=false;
        }
        return result;
    }

    /**
     * JSON美化，会自动转换Unicode
     * @param inputJson
     * @return
     */
    public static String beauty(String inputJson) {
        // todo 有bug
//        if (isJSON2(inputJson)) {
//            //Take the input, determine request/response, parse as json, then print prettily.
//            Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().serializeNulls().create();
//            JsonParser jp = new JsonParser();
//            JsonElement je = jp.parse(inputJson);
//            return gson.toJson(je);
//        }else{
            return inputJson;
//        }
    }

    //返回this.inputArea显示的文本
    public byte[] getMessage(){
        return this.inputArea.getText().getBytes();
    }

    //允许修改消息
    public boolean isModified(){
        return false;
    }

    //返回this.inputArea中选定的文本 没有选择的话 则不返回数据
    public byte[] getSelectedData(){
        return this.inputArea.getSelectedText().getBytes();
    }
}
