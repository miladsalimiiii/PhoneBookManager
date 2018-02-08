package com.phonebookmanager.miladsi.phonebookmanager.Utils;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

public class Utils {

    public static String sendData(String Address, HashMap hashMap) {
        try {
            Object[] keys = hashMap.keySet().toArray();
            Object[] values = hashMap.values().toArray();

            String data = Utils.Encoder(keys[0].toString(), values[0].toString());
            for (int i = 1; i < hashMap.size(); i++) {
                data += "&" + Utils.Encoder(keys[i].toString(), values[i].toString());
            }


            URL url = new URL(Address);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();


            connection.setRequestMethod("POST");
            connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
            connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
            connection.setDoOutput(true);
            DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
            dStream.writeBytes(data);
            dStream.flush();
            dStream.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            StringBuilder responseOutput = new StringBuilder();

            while ((line = br.readLine()) != null) {
                responseOutput.append(line);
            }
            br.close();

            return responseOutput.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getData(String Address) {
        URL url = null;
        try {
            url = new URL(Address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
            connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");

            InputStream in = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = "";
            StringBuilder responseOutput = new StringBuilder();
            while ((line = br.readLine()) != null) {
                responseOutput.append(line);
            }
            br.close();
            return responseOutput.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String Encoder(String key, String value) {
        try {
            return URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "";
    }
}
