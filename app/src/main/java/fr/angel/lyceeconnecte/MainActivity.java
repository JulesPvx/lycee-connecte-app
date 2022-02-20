package fr.angel.lyceeconnecte;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public static int STATUS_OK = 0;
    public static int STATUS_NO_INTERNET = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = Objects.requireNonNull(navHostFragment).getNavController();
        Bundle bundle = new Bundle();
        bundle.putInt("status", getIntent().getIntExtra("status", 0));
        navController.setGraph(R.navigation.main_nav, bundle);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            navController.navigate(item.getItemId(), bundle);
            return true;
        });
    }
}