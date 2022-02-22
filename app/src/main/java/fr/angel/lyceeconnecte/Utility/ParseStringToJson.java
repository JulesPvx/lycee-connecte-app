package fr.angel.lyceeconnecte.Utility;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseStringToJson {

    private static final String TAG = "ParseStringToJson";

    public static JSONObject parseStringToJsonObject(String str) {
        try {
            return new JSONObject(str);
        }catch (JSONException err){
            Log.e(TAG, err + ": " + str);
        }

        return null;
    }

    public static JSONArray parseStringToJsonArray(String str) {
        try {
            return new JSONArray(str);
        }catch (JSONException err){
            Log.e(TAG, err + ": " + str);
        }

        return null;
    }
}
