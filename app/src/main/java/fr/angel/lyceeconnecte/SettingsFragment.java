package fr.angel.lyceeconnecte;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import android.os.StrictMode;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import fr.angel.lyceeconnecte.Utility.JsonUtility;

public class SettingsFragment extends PreferenceFragmentCompat {

    private String oneSessionId;
    private Integer status;

    private SharedPreferences dataSharedPreferences;
    private SharedPreferences prefsSharedPreferences;
    private SharedPreferences.Editor dataEditor;
    private SharedPreferences.Editor prefsEditor;

    private SwitchPreference expertModeSwitch;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setup SharedPreferences
        dataSharedPreferences = requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        dataEditor = dataSharedPreferences.edit();
        prefsSharedPreferences = requireActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        prefsEditor = prefsSharedPreferences.edit();

        oneSessionId = dataSharedPreferences.getString("oneSessionId", "");

        if (getArguments() != null) {
            status = getArguments().getInt("status");
        }
    }

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.perferences_screen, rootKey);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        expertModeSwitch = findPreference("zimbra_expert_mode");

        assert expertModeSwitch != null;
        expertModeSwitch.setOnPreferenceChangeListener((preference, newValue) -> {
            try { JsonUtility.putJsonObject("https://mon.lyceeconnecte.fr/userbook/preference/zimbra", oneSessionId, "{\"modeExpert\":" + newValue + ",\"viewMode\":\"list\"}");
            } catch (IOException | JSONException e) { e.printStackTrace(); }
            return true;
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AtomicReference<JSONObject> zimbraPreferences = new AtomicReference<>(new JSONObject());

        if (status == MainActivity.STATUS_OK) {
            // Get data
            new Thread(() -> {
                try {
                    zimbraPreferences.set(Objects.requireNonNull(JsonUtility.getJsonObject("https://mon.lyceeconnecte.fr/userbook/preference/zimbra", oneSessionId)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                requireActivity().runOnUiThread(() -> {

                    try {
                        expertModeSwitch.setChecked(zimbraPreferences.get().getString("preference").contains("true"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    expertModeSwitch.setEnabled(true);
                });
            }).start();
        } else {
            expertModeSwitch.setChecked(prefsSharedPreferences.getBoolean("expertMode", false));
        }
    }
}