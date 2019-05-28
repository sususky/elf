package com.su.elf.common.utils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.net.URL;
import java.util.HashSet;

/**
 * Created by cloud on 16/3/24.
 */
public class HttpUtil {

    final static String CRLF = "\r\n";

    /**
     * http get
     * @param url
     * @return String
     * @throws IOException
     */
    public static String httpGet(String url) throws IOException {
        return sendHttp(url, "GET", null, null);
    }

    /**
     * http post
     * @param url
     * @param params
     * @return String
     * @throws IOException
     */
    public static String httpPost(String url, String params, String type) throws IOException{
        return sendHttp(url, "POST", params, type);
    }

    /**
     * http put
     * @param url
     * @param params
     * @return String
     * @throws IOException
     */
    public static String httpPut(String url, String params, String type) throws IOException{
        return sendHttp(url, "PUT", params, type);
    }

    /**
     * http delete
     * @param url
     * @return String
     * @throws IOException
     */
    public static String httpDelete(String url) throws IOException{
        return sendHttp(url, "DELETE", null, null);
    }

    /**
     * https get
     * @param url
     * @return String
     * @throws Exception
     */
    public static String httpsGet(String url) throws Exception{
        return sendHttps(url, "GET", null, null);
    }

    /**
     * https post
     * @param url
     * @param params
     * @return String
     * @throws Exception
     */
    public static String httpsPost(String url, String params, String type) throws Exception{
        return sendHttps(url, "POST", params, type);
    }

    /**
     * https put
     * @param url
     * @param params
     * @return String
     * @throws Exception
     */
    public static String httpsPut(String url, String params, String type) throws Exception{
        return sendHttps(url, "PUT", params, type);
    }

    /**
     * https delete
     * @param url
     * @return String
     * @throws Exception
     */
    public static String httpsDelete(String url) throws Exception{
        return sendHttps(url, "DELETE", null, null);
    }

    /**
     * 发送请求
     *
     * @param url
     * @param method
     * @param param
     * @return String
     * @throws IOException
     */
    private static String sendHttp(String url, String method, String param, String type) throws IOException {
        HashSet<String> methods = new HashSet();
        methods.add("POST");
        methods.add("GET");
        methods.add("PUT");
        methods.add("DELETE");

        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();

        try {
            HttpURLConnection conn = getHttpConnection(url);
            if(method!=null && methods.contains(method)){
                conn.setRequestMethod(method);
            }else{
                conn.setRequestMethod("GET");
            }
            if(type!=null && "json".equalsIgnoreCase(type)){
                conn.setRequestProperty("content-type", "application/json; charset=utf-8");
            }else {
                conn.setRequestProperty("content-type", "text/xml; charset=utf-8");
            }
            conn.connect();

            if(param != null && param.trim().length()>0){
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数
                out.print(param);
                // flush输出流的缓冲
                out.flush();
            }

            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = null;

            while ((line = in.readLine()) != null) {
                result.append(line).append(CRLF);
            }

            conn.disconnect();

        } finally {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        }

        return result.toString();
    }

    /**
     * 发送请求
     *
     * @param url
     * @param method
     * @param param
     * @return String
     * @throws Exception
     */
    private static String sendHttps(String url, String method, String param, String type) throws Exception {
        HashSet<String> methods = new HashSet();
        methods.add("POST");
        methods.add("GET");
        methods.add("PUT");
        methods.add("DELETE");

        PrintWriter out = null;
        BufferedReader reader = null;
        StringBuilder result = new StringBuilder("");
        try {
            HttpsURLConnection conn = getHttpsConnection(url);
            if(method!=null && methods.contains(method)){
                conn.setRequestMethod(method);
            }else{
                conn.setRequestMethod("GET");
            }
            if(type!=null && "json".equalsIgnoreCase(type)){
                conn.setRequestProperty("content-type", "application/json; charset=utf-8");
            }else {
                conn.setRequestProperty("content-type", "text/xml; charset=utf-8");
            }
            conn.connect();
            if(param != null){
                out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数
                out.print(param);
                // flush输出流的缓冲
                out.flush();
            }
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = null;
            while ((line = reader.readLine()) != null) {
                result.append(line).append(CRLF);
            }

            conn.disconnect();
        } finally {
            if(out!=null){
                out.close();
            }
            if(reader!=null){
                reader.close();
            }
        }
        return result.toString();
    }

    /**
     * get http connection
     *
     * @param url
     * @return HttpURLConnection
     * @throws IOException
     */
    private static HttpURLConnection getHttpConnection(String url) throws IOException {
        URL realUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.setRequestProperty("content-type", "text/xml; charset=utf-8");
        return conn;
    }

    /**
     * get https connection
     *
     * @param url
     * @return HttpsURLConnection
     * @throws Exception
     */
    private static HttpsURLConnection getHttpsConnection(String url) throws Exception {
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());
        URL console = new URL(url);
        HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
        conn.setSSLSocketFactory(sc.getSocketFactory());
        conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
        conn.setDoOutput(true);
        return conn;
    }

    private static class TrustAnyTrustManager implements X509TrustManager {
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[] {};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

}
