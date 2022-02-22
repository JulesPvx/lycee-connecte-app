package fr.angel.lyceeconnecte.Utility;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class JsonUtility {

    public static JSONObject getJsonObject(String strUrl, String oneSessionId) throws IOException {
        URL url = new URL(strUrl);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("GET");

        httpConn.setRequestProperty("cookie", "oneSessionId=" + oneSessionId + ";");

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response = s.hasNext() ? s.next() : "";

        JSONObject jsonObject = ParseStringToJson.parseStringToJsonObject(response);

        responseStream.close();

        return jsonObject;
    }

    public static JSONArray getJsonArray(String strUrl, String oneSessionId) throws IOException {
        URL url = new URL(strUrl);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("GET");

        httpConn.setRequestProperty("cookie", "oneSessionId=" + oneSessionId + ";");

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response = s.hasNext() ? s.next() : "";

        JSONArray jsonArray = ParseStringToJson.parseStringToJsonArray(response);

        responseStream.close();

        return jsonArray;
    }

    public static void putJsonObject(String strUrl, String oneSessionId, String payload) throws IOException, JSONException {
        URL url = new URL(strUrl);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("PUT");

        httpConn.setRequestProperty("cookie", "oneSessionId=" + oneSessionId + ";");

        httpConn.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
        writer.write(payload);
        writer.flush();
        writer.close();
        httpConn.getOutputStream().close();

        // DO NOT DELETE: IT'S IMPORTANT FOR THE REQUEST
        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
    }
}
