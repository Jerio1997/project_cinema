package com.stylefeng.guns.rest.modular.auth.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Author Jerio
 * CreateTime 2019/12/3 20:04
 **/
public class ConnectionUtils {

    public static String readFileToString(String uri){

        String s = null;
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type","application/json;charset-UTF-8");

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
            String line;
            StringBuilder sb = new StringBuilder();
            while((line = br.readLine()) != null){
                sb.append(line);
            }
            connection.disconnect();
            s = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return s;
    }
}
