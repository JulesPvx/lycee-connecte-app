package fr.angel.lyceeconnecte;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import org.apache.commons.lang3.StringUtils;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences.Editor editor;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Setup SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Bind views
        WebView webView = findViewById(R.id.login_web_view);
        MaterialButton startWithoutLoginBtn = findViewById(R.id.start_without_login_btn);

        startWithoutLoginBtn.setOnClickListener(v -> {
            // Start MainActivity
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            i.putExtra("status", MainActivity.STATUS_NO_INTERNET);
            startActivity(i);
            finish();
        });

        // Setup web view
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                if (url.equals("https://mon.lyceeconnecte.fr/timeline/timeline")) {
                    // Get cookies
                    String cookies = CookieManager.getInstance().getCookie(url);

                    String oneSessionId = StringUtils.substringBetween(cookies, "oneSessionId=", ";");

                    // Save oneSessionId for later use
                    editor.putString("oneSessionId", oneSessionId);
                    editor.apply();

                    // Start MainActivity
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    i.putExtra("oneSessionId", oneSessionId);
                    i.putExtra("status", MainActivity.STATUS_OK);
                    startActivity(i);
                    finish();
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);

                // Start MainActivity
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                i.putExtra("status", MainActivity.STATUS_NO_INTERNET);
                startActivity(i);
                finish();
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("https://educonnect.education.gouv.fr/idp/profile/SAML2/Unsolicited/SSO?providerId=https%3A%2F%2Fmon.lyceeconnecte.fr%2Fauth%2Fsaml%2Fmetadata%2Fidp.xml");

    }
}