package Utils;

import burp.BurpExtender;
import burp.IHttpRequestResponse;
import burp.IRequestInfo;
import burp.ui.HackvertorInput;
//import burp.util.BurpCallbacks;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RTextArea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

/**
 * @DESCRIPTION:
 * @USER: f0ng
 * @DATE: 2023/5/21 下午1:57
 */
public class Utils {

    private static JPopupMenu menu;

    private static JMenuItem menuRepeater;
    private static JMenuItem menuReissue;
    private static JMenuItem menuCopy;
//    private static JMenuItem menuCopySmart;

    public static void applyThemeToRSyntaxTextArea(RSyntaxTextArea area, String themeName) {
        try {
            Theme theme = Theme.load(Utils.class.getResourceAsStream(
                    "/org/fife/ui/rsyntaxtextarea/themes/"+themeName+".xml"));
            theme.apply(area);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void configureRSyntaxArea(RSyntaxTextArea area) {
        area.setLineWrap(true);
        if(BurpExtender.isDarkTheme) {
            Utils.applyThemeToRSyntaxTextArea(area, "dark");
        }
        BurpExtender.callbacks.customizeUiComponent(area);
        area.setFont(new Font("Courier New", Font.PLAIN, area.getFont().getSize()));
    }

    public static JPopupMenu getPopup(RSyntaxTextArea hackvertorInput ) {
        menu = new JPopupMenu("Message");
        menuCopy = new JMenuItem(RSyntaxTextArea.getAction(RTextArea.COPY_ACTION));
        menu.add(menuCopy);

        menuRepeater = new JMenuItem("Send to Repeater");
        menuRepeater.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
//                BurpExtender.helpers.
                IRequestInfo requestInfo = BurpExtender.helpers.analyzeRequest(hackvertorInput.getText().getBytes());
                BurpExtender.stdout.println(requestInfo);
//                c_sendToRepeater(ihttpreqresp);
                try {
                    c_sendToRepeater(requestInfo, hackvertorInput.getText().getBytes());
                }catch (Exception e){
                    BurpExtender.stdout.println(e.getMessage());
                }
//                BurpExtender.stdout.println(hackvertorInput.getText());

            }
        });
        menu.add(menuRepeater);

        menuReissue = new JMenuItem("Send to Intruder");
        menuReissue.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                IRequestInfo requestInfo = BurpExtender.helpers.analyzeRequest(hackvertorInput.getText().getBytes());
                BurpExtender.stdout.println(requestInfo);
//                c_sendToRepeater(ihttpreqresp);
                try {
                    c_sendToIntruder(requestInfo, hackvertorInput.getText().getBytes());
                }catch (Exception e){
                    BurpExtender.stdout.println(e.getMessage());
                }
            }
        });
        menu.add(menuReissue);

//        menuCopySmart = new JMenuItem("Copy Smart");
//        menuCopySmart.addActionListener(new ActionListener(){
//            public void actionPerformed(ActionEvent arg0) {
//
//            }
//        });
//        menu.add(menuCopySmart);

        return menu;
    }


    /**
     *     void sendToRepeater(
     *             String host,
     *             int port,
     *             boolean useHttps,
     *             byte[] request,
     *             String tabCaption);
     */
    public static void c_sendToRepeater(IRequestInfo iRequestInfo,byte[] request) {
        String host = null;
        int port = 0;
        List<String> headersList = iRequestInfo.getHeaders();

//        BurpCallbacks.getInstance().sendToRepeater();

//        new StandaloneBurpRequestInfo(iRequestInfo.get);

//        if (iRequestInfo.getUrl().toString().contains("https"))
//            BurpExtender.usehttps = true;

        for (String header : headersList) {
            String[] host_lists = header.split(":",2);
            if (host_lists[0].toLowerCase().equals("host")) {
                host = headersTohost(host_lists[1]).trim();
                port = headersToport(host_lists[1],BurpExtender.usehttps);
            }
        }

        BurpExtender.stdout.println(BurpExtender.usehttps);
        BurpExtender.callbacks.sendToRepeater(
                host,
                port,
                BurpExtender.usehttps,
                request ,
                "autoDecoder");
    }

//    public void cc_sendToRepeater() {
//        BurpCallbacks.getInstance().sendToRepeater(httpMessage);
//    }

    public static void c_sendToIntruder(IRequestInfo iRequestInfo,byte[] request) {
        String host = null;
        int port = 0;
        List<String> headersList = iRequestInfo.getHeaders();
//        if (iRequestInfo.getUrl().getProtocol().contains("https"))
//            BurpExtender.usehttps = true;
        for (String header : headersList) {
            String[] host_lists = header.split(":",2);
            if (host_lists[0].toLowerCase().equals("host")) {
                host = headersTohost(host_lists[1]).trim();
                port = headersToport(host_lists[1],BurpExtender.usehttps);
            }
        }
        BurpExtender.callbacks.sendToIntruder(
                host,
                port,
                BurpExtender.usehttps,
                request );
    }

    public static String headersTohost(String hostport){
        String host = "";
        String[] hostports ;
        if (hostport.contains(":")) {
            hostports = hostport.split(":");
            host = hostports[0];
        }else{
            host = hostport;
        }
        return host;
    }

    public static int headersToport(String hostport,Boolean usehttps){
        int port ;
        String[] hostports ;
        if (hostport.contains(":")) {
            hostports = hostport.split(":");
            port = Integer.parseInt(hostports[1]);
        }else{
            if (usehttps)
                port = 443;
            else
                port =80;
        }
        return port;
    }

}
