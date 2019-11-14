package com.geekli.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class TESTOKHttp {
    public static String interfaceUtil(String path, String data) {
        String str = "";
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((str = br.readLine()) != null) {
                str = new String(str.getBytes(), "UTF-8");
//                System.out.println(str);
                return str;
            }
            is.close();
            conn.disconnect();
            System.out.println("结束");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
}
