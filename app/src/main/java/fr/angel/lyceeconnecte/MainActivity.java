package fr.angel.lyceeconnecte;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import fr.angel.lyceeconnecte.Utility.JsonUtility;
import fr.angel.lyceeconnecte.Utility.ParseStringToJson;

public class MainActivity extends AppCompatActivity {

    public static int STATUS_OK = 0;
    public static int STATUS_NO_INTERNET = 1;

    private String oneSessionId;

    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Container
        View baseView = findViewById(R.id.nav_host_fragment);

        // Bind views
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        // Get extras
        int status = getIntent().getIntExtra("status", 0);

        // Setup SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        oneSessionId = sharedPreferences.getString("oneSessionId", "");

        // Setup fragment
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = Objects.requireNonNull(navHostFragment).getNavController();
        Bundle bundle = new Bundle();
        bundle.putInt("status", status);
        navController.setGraph(R.navigation.main_nav, bundle);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            navController.navigate(item.getItemId(), bundle);
            return true;
        });

        AtomicReference<Integer> count = new AtomicReference<>(0);
        if (status == MainActivity.STATUS_OK) {
            // Get data
            new Thread(() -> {
                try {
                    count.set(Objects.requireNonNull(JsonUtility.getJsonObject("https://mon.lyceeconnecte.fr/zimbra/count/INBOX?unread=true", oneSessionId)).getInt("count")); } catch (IOException | JSONException e) { e.printStackTrace(); }

                // Set data to views
                runOnUiThread(() -> {
                    BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(R.id.messagingFragment);
                    badge.setVisible(true);
                    badge.setNumber(count.get());
                });
            }).start();
        } else {
            String notificationCount = sharedPreferences.getString("notificationCount", "");
            if (!notificationCount.trim().isEmpty()) {
                Objects.requireNonNull(ParseStringToJson.parseStringToJsonObject(notificationCount));
            }

            Snackbar snackbar = Snackbar
                    .make(baseView, "Cannot connect to Lycée Connecté", BaseTransientBottomBar.LENGTH_INDEFINITE)
                    .setAction("RETRY", v -> {
                        // Retry to connect
                        editor.remove("oneSessionId");
                        editor.apply();
                        startActivity(new Intent(this, LoginActivity.class));
                        finish();
                    })
                    .setDuration(7500)
                    .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                    .setAnchorView(bottomNavigationView);

            snackbar.show();
        }
    }

    @Override
    public void onBackPressed() { }
}