package com.company;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static String buildURL(String authCode, String message, List<String> contacts) {
        List<String> contents = new ArrayList<>();
        for (String contact : contacts) {
            try {
                String content = "content=".concat(URLEncoder.encode(contact.concat("|").concat(message), "UTF-8"));
                contents.add(content);
            } catch (UnsupportedEncodingException e) {
                System.out.println(e);
            }
        }
        return "authcode=".concat(authCode).concat("&sender=").concat("TemasekPoly").concat("&").concat(contents.stream().collect(Collectors.joining("?")));
    }

    public static void main(String[] args) {
        try {
            List<String> tests = new ArrayList<>(Arrays.asList("90484658"));
            String builtURL = buildURL("VFBTQ1A6YVRtMjhyRHo5Vg%3D%3D", "Test Message", tests);
            URL url = new URL("https://www.365smartsms.com/smsapi/httpsmstp.ashx?");
            HttpURLConnection httpsURLConnection = (HttpURLConnection) url.openConnection();

            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.0.0.1", 8080));
            conn = new URL(urlString).openConnection(proxy);

            httpsURLConnection.setRequestMethod("POST");
            httpsURLConnection.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(httpsURLConnection.getOutputStream());
            wr.writeBytes(builtURL);
            wr.flush();
            wr.close();

            int responseCode = httpsURLConnection.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
