package fr.angel.lyceeconnecte;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import fr.angel.lyceeconnecte.Models.PrivateUser;
import fr.angel.lyceeconnecte.Models.User;
import fr.angel.lyceeconnecte.Utility.JsonUtility;
import fr.angel.lyceeconnecte.Utility.ParseStringToJson;
import fr.angel.lyceeconnecte.Utility.ProfilePicture;

public class UserActivity extends AppCompatActivity {

    private String userId, oneSessionId;
    private Integer status;
    private PrivateUser user;

    private MaterialToolbar topAppBar;
    private MaterialTextView typeTv, loginTv;
    private ShapeableImageView iconImg;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        userId = getIntent().getExtras().getString("userId");
        status = getIntent().getExtras().getInt("status");

        // Setup SharedPreferences
        sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        oneSessionId = sharedPreferences.getString("oneSessionId", "");

        // Bind views
        topAppBar = findViewById(R.id.user_top_app_bar);
        typeTv = findViewById(R.id.user_type_tv);
        loginTv = findViewById(R.id.user_login_tv);
        iconImg = findViewById(R.id.user_profile_picture_img);

        // Setup app bar
        topAppBar.setNavigationOnClickListener(v -> finish());

        if (status == MainActivity.STATUS_OK) {
            // Get data
            new Thread(() -> {

                try {
                    parseToUser(Objects.requireNonNull(JsonUtility.getJsonObject("https://mon.lyceeconnecte.fr/userbook/api/person?id=" + userId + "&type=undefined", oneSessionId))); } catch (JSONException | IOException e) { e.printStackTrace(); }

                // Set data to views
                runOnUiThread(this::applyData);
            }).start();
        } else {
            String userStr = sharedPreferences.getString(userId, "");
            if (!userStr.trim().isEmpty()) {
                try {
                    parseToUser(Objects.requireNonNull(ParseStringToJson.parseStringToJsonObject(userStr)));

                    // Apply user preferences
                    applyData();
                } catch (JSONException e) { e.printStackTrace(); }
            }
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private void parseToUser(JSONObject data) throws JSONException {

        JSONObject thisData = data.getJSONArray("result").getJSONObject(0);

        Log.e("TAG", "parseToUser: " + thisData);

        Gson gson = new Gson();
        user = gson.fromJson(thisData.toString(), PrivateUser.class);

        editor.putString(userId, data.toString());
        editor.commit();
    }

    private void applyData() {
        typeTv.setText(user.getType().get(0));
        loginTv.setText(user.getLogin());
        ProfilePicture.getUserProfilePicture(userId, this, iconImg);
        topAppBar.setTitle(user.getDisplayName());
    }
}