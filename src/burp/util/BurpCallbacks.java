///*
// * Copyright (C) 2013 DobinRutishauser@broken.ch
// *
// * This program is free software: you can redistribute it and/or modify
// * it under the terms of the GNU General Public License as published by
// * the Free Software Foundation, either version 3 of the License, or
// * (at your option) any later version.
// *
// * This program is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// * GNU General Public License for more details.
// *
// * You should have received a copy of the GNU General Public License
// * along with this program.  If not, see <http://www.gnu.org/licenses/>.
// */
//package burp.util;
//
//import burp.IBurpExtenderCallbacks;
//import burp.IHttpRequestResponse;
//import burp.IHttpService;
//import burp.IRequestInfo;
//import burp.IResponseInfo;
////import burp.model.SentinelHttpMessage;
////import gui.viewMessage.ExternalUpdater;
//import java.io.PrintWriter;
//import java.net.MalformedURLException;
//import java.net.URL;
////import model.SentinelHttpMessage;
////import model.SentinelHttpService;
//
///**
// *
// * @author unreal
// */
//public class BurpCallbacks {
//
//    static private BurpCallbacks burpCallbacks = null;
//    private IBurpExtenderCallbacks callback;
//    private PrintWriter stdout;
//
//    public void init(IBurpExtenderCallbacks callback) {
//        this.callback = callback;
//        stdout = new PrintWriter(callback.getStdout(), true);
//    }
//
//    public IBurpExtenderCallbacks getBurp() {
//        return callback;
//    }
//
//    public void print(String s) {
//        if (stdout != null) {
//            stdout.println(s);
//        }
//    }
//
//    public static BurpCallbacks getInstance() {
//        if (burpCallbacks == null) {
//            burpCallbacks = new BurpCallbacks();
//        }
//        return burpCallbacks;
//    }
//
//
//
//
//
//    private boolean isRedirect(byte[] response) {
//        IResponseInfo responseInfo = BurpCallbacks.getInstance().getBurp().getHelpers().analyzeResponse(response);
//        if (responseInfo.getStatusCode() == 302) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//
//
//    private URL followRedirectUrl(String redirStr, IHttpRequestResponse message) {
//        // get old url
//        System.out.println("FOLLOW!");
//        IRequestInfo requestInfo = BurpCallbacks.getInstance().getBurp().getHelpers().analyzeRequest(message);
//        URL origUrl = requestInfo.getUrl();
//
//        // create new url
//        URL url;
//        try {
//            url = new URL(origUrl, redirStr);
//        } catch (MalformedURLException ex) {
//            BurpCallbacks.getInstance().print("302 found, but could not convert location header!");
//            return null;
//        }
//
//        return url;
//    }
//
//
//
//
//}
