package fr.angel.lyceeconnecte;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import fr.angel.lyceeconnecte.Utility.JsonUtility;
import fr.angel.lyceeconnecte.Utility.ParseStringToJson;
import fr.angel.lyceeconnecte.Utility.ProfilePicture;
import fr.angel.lyceeconnecte.Utility.TimeAgo;

public class MailActivity extends AppCompatActivity {

    private String oneSessionId, mailId, folder;
    private int menu;
    private Mail mail;

    private MaterialTextView fromTv, toTv, timeAgoTv;
    private WebView bodyWv;
    private ShapeableImageView iconImg;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private Intent data = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);

        int status = getIntent().getExtras().getInt("status");
        mailId = getIntent().getExtras().getString("mailId");
        menu = getIntent().getExtras().getInt("menu");

        // Init data
        data.putExtra("mailId", mailId);

        // Setup SharedPreferences
        sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        oneSessionId = sharedPreferences.getString("oneSessionId", "");

        // Bind views
        MaterialToolbar topAppBar = findViewById(R.id.mail_top_app_bar);
        fromTv = findViewById(R.id.mail_from_tv);
        fromTv = findViewById(R.id.mail_from_tv);
        toTv = findViewById(R.id.mail_to_tv);
        bodyWv = findViewById(R.id.mail_body_wv);
        timeAgoTv = findViewById(R.id.mail_time_ago_tv);
        iconImg = findViewById(R.id.mail_icon_img);

        // Setup app bar
        topAppBar.setNavigationOnClickListener(v -> finish());
        topAppBar.inflateMenu(menu);

        // Setup web view
        bodyWv.setWebViewClient(new WebViewClient());
        bodyWv.setWebChromeClient(new WebChromeClient());

        if (status == MainActivity.STATUS_OK) {
            // Get data
            new Thread(() -> {
                
                try {
                    parseToMail(Objects.requireNonNull(JsonUtility.getJsonObject("https://mon.lyceeconnecte.fr/zimbra/message/" + mailId, oneSessionId))); } catch (JSONException | IOException e) { e.printStackTrace(); }

                // Set data to views
                runOnUiThread(() -> {
                    try {
                        applyData();
                        // Setup app bar
                        topAppBar.setOnMenuItemClickListener(item -> {
                            try {
                                switch(item.getItemId()) {
                                    case R.id.archive:
                                        archive();
                                        return true;
                                    case R.id.delete:
                                        delete();
                                        return true;
                                    case R.id.mark_as_read:
                                        markAsRead();
                                        return true;
                                }
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }

                            return false;
                        });
                    } catch (JSONException | IOException e) { e.printStackTrace(); }
                });
            }).start();
        } else {
            String userStr = sharedPreferences.getString("mail_" + mailId, "");
            if (!userStr.trim().isEmpty()) {
                try {
                    parseToMail(Objects.requireNonNull(ParseStringToJson.parseStringToJsonObject(userStr)));

                    // Apply user preferences
                    applyData();
                } catch (JSONException | IOException e) { e.printStackTrace(); }
            }
        }
    }

    private void markAsRead() throws JSONException, IOException {
        JsonUtility.postJsonObject("https://mon.lyceeconnecte.fr/zimbra/toggleUnread?id=" + mail.getId() + "&unread=true", oneSessionId, "");
        Log.e("TAG", "markAsRead: " + mail.getId() + ": oneSessionId:" + oneSessionId);
        finish();
    }

    private void delete() throws JSONException, IOException {
        JsonUtility.postJsonObject("https://mon.lyceeconnecte.fr/zimbra/delete?id=" + mail.getId(), oneSessionId, "");
        finish();
    }

    private void archive() throws JSONException, IOException {
        JsonUtility.putJsonObject("https://mon.lyceeconnecte.fr/zimbra/trash?id=" + mail.getId(), oneSessionId, "");
        finish();
    }

    private void parseToMail(JSONObject data) throws JSONException {

        Gson gson = new Gson();
        mail = gson.fromJson(data.toString(), Mail.class);

        editor.putString("mail_" + mailId, data.toString());
        editor.commit();

    }

    private void applyData() throws JSONException, IOException {

        // Apply views
        bodyWv.loadDataWithBaseURL(null, mail.getBody(), "text/html", "UTF-8", null);

        timeAgoTv.setText(TimeAgo.getTimeAgo(mail.getDate()));

        ProfilePicture.getUserProfilePicture(mail.getFrom(), this, iconImg);

        // Apply logic
        data.putExtra("folder", mail.getSystemFolder().substring(0, 1).toUpperCase() + mail.getSystemFolder().substring(1).toLowerCase());

        if (mail.isUnread()) {
            mail.setWebUnread(false, oneSessionId);
            data.putExtra("unread", false);
        }
    }
}