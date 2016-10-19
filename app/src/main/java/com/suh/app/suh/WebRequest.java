package com.suh.app.suh;

/**
 * Created by rowankrishnan on 10/17/16.
 */

import java.util.HashMap;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class WebRequest {
    static String response = null;
    public final static int GETRequest = 1;
    public final static int POSTRequest = 2;

    public WebRequest() {
    }

    public String makeWebServiceCall(String url, int requestMethod) {
        return this.makeWebServiceCall(url, requestMethod, null);
    }

    public String makeWebServiceCall(String urladdress, int requestmethod,
                                     HashMap<String, String> params) {
        URL url;
        String response = "";

        try {
            url = new URL(urladdress);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15001);
            conn.setConnectTimeout(15001);
            conn.setDoInput(true);
            //conn.setDoOutput(true);

            if (requestmethod == POSTRequest) {
                conn.setRequestMethod("POST");
            } else if (requestmethod == GETRequest) {
                conn.setRequestMethod("GET");
            }

            if (params != null) {
                OutputStream ostream = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ostream, "UTF-8"));
                StringBuilder requestresult = new StringBuilder();
                boolean first = true;

                for (Map.Entry<String, String> entry : params.entrySet()) {
                    if (first) {
                        first = false;
                    } else {
                        requestresult.append("&");
                    }
                    requestresult.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                    requestresult.append("=");
                    requestresult.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                }
                writer.write(requestresult.toString());

                writer.flush();
                writer.close();
                ostream.close();
            }

            int reqresponseCode = conn.getResponseCode();

            if (reqresponseCode == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

}
