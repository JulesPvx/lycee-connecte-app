package fr.angel.lyceeconnecte.Utility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class JsonFromUrl {

    private static final String TAG = "JsonFromUrl";

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
}
