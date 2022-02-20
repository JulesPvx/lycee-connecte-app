package fr.angel.lyceeconnecte.Utility;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseStringToJson {

    private static final String TAG = "ParseStringToJson";

    public static JSONObject parseStringToJsonObject(String str) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            Log.d(TAG, jsonObject.toString());
            return jsonObject;
        }catch (JSONException err){
            Log.e(TAG, err + ": " + str);
        }

        return null;
    }

    public static JSONArray parseStringToJsonArray(String str) {
        try {
            JSONArray jsonArray = new JSONArray(str);
            Log.d(TAG, jsonArray.toString());
            return jsonArray;
        }catch (JSONException err){
            Log.e(TAG, err + ": " + str);
        }

        return null;
    }
}
