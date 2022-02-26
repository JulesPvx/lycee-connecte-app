package fr.angel.lyceeconnecte;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import fr.angel.lyceeconnecte.Models.Mail;
import fr.angel.lyceeconnecte.Models.PrivateUser;
import fr.angel.lyceeconnecte.Utility.JsonUtility;
import fr.angel.lyceeconnecte.Utility.ParseStringToJson;
import fr.angel.lyceeconnecte.Utility.ProfilePicture;
import fr.angel.lyceeconnecte.Utility.TimeAgo;

public class MailActivity extends AppCompatActivity {

    private String oneSessionId, mailId;
    private Integer status;
    private Mail mail;

    private MaterialToolbar topAppBar;
    private MaterialTextView fromTv, toTv, timeAgoTv;
    private WebView bodyWv;
    private ShapeableImageView iconImg;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);

        status = getIntent().getExtras().getInt("status");
        mailId = getIntent().getExtras().getString("mailId");

        // Setup SharedPreferences
        sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        oneSessionId = sharedPreferences.getString("oneSessionId", "");

        // Bind views
        topAppBar = findViewById(R.id.mail_top_app_bar);
        fromTv = findViewById(R.id.mail_from_tv);
        fromTv = findViewById(R.id.mail_from_tv);
        toTv = findViewById(R.id.mail_to_tv);
        bodyWv = findViewById(R.id.mail_body_wv);
        timeAgoTv = findViewById(R.id.mail_time_ago_tv);
        iconImg = findViewById(R.id.mail_icon_img);

        // Setup app bar
        topAppBar.setNavigationOnClickListener(v -> finish());

        // Setup web view
        bodyWv.setWebViewClient(new WebViewClient());
        bodyWv.setWebChromeClient(new WebChromeClient());

        if (status == MainActivity.STATUS_OK) {
            // Get data
            new Thread(() -> {
                
                try {
                    parseToMail(Objects.requireNonNull(JsonUtility.getJsonObject("https://mon.lyceeconnecte.fr/zimbra/message/" + mailId, oneSessionId))); } catch (JSONException | IOException e) { e.printStackTrace(); }

                // Set data to views
                runOnUiThread(this::applyData);
            }).start();
        } else {
            String userStr = sharedPreferences.getString("mail_" + mailId, "");
            if (!userStr.trim().isEmpty()) {
                try {
                    parseToMail(Objects.requireNonNull(ParseStringToJson.parseStringToJsonObject(userStr)));

                    // Apply user preferences
                    applyData();
                } catch (JSONException e) { e.printStackTrace(); }
            }
        }
    }

    private void parseToMail(JSONObject data) throws JSONException {

        Gson gson = new Gson();
        mail = gson.fromJson(data.toString(), Mail.class);

        editor.putString("mail_" + mailId, data.toString());
        editor.commit();

    }

    private void applyData() {
        bodyWv.loadDataWithBaseURL(null, mail.getBody(), "text/html", "UTF-8", null);

        timeAgoTv.setText(TimeAgo.getTimeAgo(mail.getDate()));

        ProfilePicture.getUserProfilePicture(mail.getFrom(), this, iconImg);
    }
}