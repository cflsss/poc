package com.espressif.esptouch.android.main;

import android.os.AsyncTask;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class RequestTask extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... params) {
        String responseString = null;
        HttpURLConnection conn = null;


        String deviceIP =       params[0];
        String deviceAction =   params.length>1 ? params[1] :"";
        String deviceActionKey= params.length>2 ? params[2] :"";
        String deviceActionValue= params.length>3 ? params[3] :"";

        String urlParams = deviceActionKey != "" ? "?" + deviceActionKey +"="+ deviceActionValue :"";
        String urlStr = "http://"+ deviceIP +"/"+ deviceAction + urlParams;


        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            if (conn.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                //setTitle(R.string.main_title + " - Armada OK");
                //responseString = conn.getResponseMessage();
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line.replace("\"","'"));
                }
                br.close();
                responseString = sb.toString();
            } else {
                //response = "FAILED"; // See documentation for more info on response handling
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            responseString = "";
        } finally {
            conn.disconnect();
        }
        return responseString;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //Do anything with response..
    }
}