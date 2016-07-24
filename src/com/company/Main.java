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

    public static void main(String[] args) {
        String source = "ANPR-EXIT";
        String vehicleNum = "SJ87654321";
        String subject = "VIP vehicle detected Alert";
        String body = String.format("VIP vehicle %s is detected at %s", vehicleNum, source);
        //SMSUtil.sendSMS(getHostContactNumbersByVehicleNumber(vehicleNum), body);

        try {
            List<String> tests = new ArrayList<>(Arrays.asList("90484658", "90332143"));
            List<String> builtURLs = buildURLs("VFBTQ1A6YVRtMjhyRHo5Vg%3D%3D", "Test Message", tests);
            //Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.0.0.1", 8080));

            for (String builtURL: builtURLs) {
                URL url = new URL("https://www.365smartsms.com/smsapi/httpsmstp.ashx?");
                System.out.println("Complete URL: " + "https://www.365smartsms.com/smsapi/httpsmstp.ashx?".concat(builtURL));
                HttpURLConnection httpsURLConnection = (HttpURLConnection) url.openConnection();

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
            }


        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private static List<String> buildURLs(String authCode, String message, List<String> contacts) {
        List<String> urls = new ArrayList<>();
        for (String contact : contacts) {
            try {
                String content = "authcode=".concat(authCode).concat("&sender=").concat("TemasekPoly").concat("&").concat("content=".concat(URLEncoder.encode(contact.concat("|").concat(message), "UTF-8")));
                System.out.println("Content: " + content);
                urls.add(content);
            } catch (UnsupportedEncodingException e) {
                System.out.println(e);
            }
        }
        return urls;
    }
}
