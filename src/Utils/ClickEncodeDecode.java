package Utils;

import burp.*;

import java.util.Arrays;
import java.util.List;

import static burp.BurpExtender.sendPostnew;
import static com.autoDecoder.util.codeDecode.decryptKeyivmode;
import static com.autoDecoder.util.codeEncode.encryptKeyivmode;

/**
 * @DESCRIPTION:
 * @USER: f0ng
 * @DATE: 2023/7/2 下午10:38
 */
public class ClickEncodeDecode {

    public static void Decode(IContextMenuInvocation iContextMenuInvocation, String selectString) throws Exception {
        //获取原请求信息
        IHttpRequestResponse currentRequest = iContextMenuInvocation.getSelectedMessages()[0];
        IRequestInfo requestInfo = BurpExtender.helpers.analyzeRequest(currentRequest);
        List<String> headers = requestInfo.getHeaders();
        String[] respDecodeParams = IndexautoDecoder.getRespDecodeParams(); // 获取解密数组
        if (IndexautoDecoder.getRadioButton1State()) { // 自带加解密
            //更新header
            String decode_text = decryptKeyivmode(selectString, respDecodeParams[5], respDecodeParams[6], respDecodeParams[0], respDecodeParams[1], respDecodeParams[2], respDecodeParams[3], respDecodeParams[4]);
            byte[] newMessage = BurpExtender.helpers.buildHttpMessage(headers, getHttpRequestBody(currentRequest,selectString, decode_text));

            currentRequest.setRequest(newMessage);
        }else if(IndexautoDecoder.getRadioButton2State()){ // 接口加解密
            //更新header
            String decodeTotal = sendPostnew(IndexautoDecoder.getDecodeApi(), selectString);
            byte[] newMessage = BurpExtender.helpers.buildHttpMessage(headers, getHttpRequestBody(currentRequest, selectString ,decodeTotal));
            currentRequest.setRequest(newMessage);
        }
    }

    public static void Encode(IContextMenuInvocation iContextMenuInvocation, String selectString) throws Exception {
        //获取原请求信息
        IHttpRequestResponse currentRequest = iContextMenuInvocation.getSelectedMessages()[0];
        IRequestInfo requestInfo = BurpExtender.helpers.analyzeRequest(currentRequest);
        List<String> headers = requestInfo.getHeaders();
        String[] respDecodeParams = IndexautoDecoder.getRespDecodeParams(); // 获取解密数组

        if (IndexautoDecoder.getRadioButton1State()) { // 自带加解密
            //更新header
            String decode_text = encryptKeyivmode(selectString, respDecodeParams[5], respDecodeParams[6], respDecodeParams[0], respDecodeParams[1], respDecodeParams[2], respDecodeParams[3], respDecodeParams[4]);
            byte[] newMessage = BurpExtender.helpers.buildHttpMessage(headers, getHttpRequestBody(currentRequest,selectString, decode_text));

            currentRequest.setRequest(newMessage);
        }else if(IndexautoDecoder.getRadioButton2State()){ // 接口加解密
            //更新header
            String decodeTotal = sendPostnew(IndexautoDecoder.getEncodeApi(), selectString);
            byte[] newMessage = BurpExtender.helpers.buildHttpMessage(headers, getHttpRequestBody(currentRequest, selectString ,decodeTotal));
            currentRequest.setRequest(newMessage);
        }
    }

    private static byte[] getHttpRequestBody(IHttpRequestResponse httpRequestResponse, String codeString , String selectString) {
        byte[] request = httpRequestResponse.getRequest();
        IRequestInfo requestInfo = BurpExtender.helpers.analyzeRequest(request);

        int bodyOffset = requestInfo.getBodyOffset();
        byte[] httpBody = Arrays.copyOfRange(request, bodyOffset, request.length);

        BurpExtender.stdout.println(new String(httpBody));
        String code_body = new String(httpBody);
        code_body = code_body.replace(codeString,selectString);

        return code_body.getBytes();
    }
}
