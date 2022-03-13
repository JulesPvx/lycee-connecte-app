package fr.angel.lyceeconnecte;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import fr.angel.lyceeconnecte.HiddenFeatures.HiddenFeaturesActivity;
import fr.angel.lyceeconnecte.Models.User;
import fr.angel.lyceeconnecte.Utility.JsonUtility;
import fr.angel.lyceeconnecte.Utility.ParseStringToJson;

public class SettingsFragment extends PreferenceFragmentCompat {

    private String oneSessionId;
    private Integer status;
    private User currentUser;

    private SharedPreferences dataSharedPreferences;
    private SharedPreferences prefsSharedPreferences;
    private SharedPreferences.Editor dataEditor;

    private Preference hiddenFeatures;
    private SwitchPreference expertModeSwitch;
    private EditTextPreference displayName, birthDate, address;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setup SharedPreferences
        dataSharedPreferences = requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        dataEditor = dataSharedPreferences.edit();
        prefsSharedPreferences = requireActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor prefsEditor = prefsSharedPreferences.edit();

        oneSessionId = dataSharedPreferences.getString("oneSessionId", "");

        if (getArguments() != null) {
            status = getArguments().getInt("status");
        }
    }

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.perferences_screen, rootKey);

        // Set policy
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Bind preferences
        expertModeSwitch = findPreference("zimbra_expert_mode");
        displayName = findPreference("display_name");
        address = findPreference("address");
        birthDate = findPreference("birth_date");
        hiddenFeatures = findPreference("hidden_features");

        expertModeSwitch.setOnPreferenceChangeListener((preference, newValue) -> {
            try { JsonUtility.putJsonObject("https://mon.lyceeconnecte.fr/userbook/preference/zimbra", oneSessionId, "{\"modeExpert\":" + newValue + ",\"viewMode\":\"list\"}");
            } catch (IOException | JSONException e) { e.printStackTrace(); }
            return true;
        });
        displayName.setOnPreferenceChangeListener((preference, newValue) -> {
            try { JsonUtility.putJsonObject("https://mon.lyceeconnecte.fr/directory/user/" + currentUser.getId(), oneSessionId, "{\"displayName\":\"" + newValue + "\"}");
            } catch (IOException | JSONException e) { e.printStackTrace(); }
            return true;
        });
        address.setOnPreferenceChangeListener((preference, newValue) -> {
            try { JsonUtility.putJsonObject("https://mon.lyceeconnecte.fr/directory/user/" + currentUser.getId(), oneSessionId, "{\"address\":\"" + newValue + "\"}");
            } catch (IOException | JSONException e) { e.printStackTrace(); }
            return true;
        });
        birthDate.setOnPreferenceChangeListener((preference, newValue) -> {
            try { JsonUtility.putJsonObject("https://mon.lyceeconnecte.fr/directory/user/" + currentUser.getId(), oneSessionId, "{\"birthDate\":\"" + newValue + "\"}");
            } catch (IOException | JSONException e) { e.printStackTrace(); }
            return true;
        });
        hiddenFeatures.setOnPreferenceClickListener(preference -> {
            Intent i = new Intent(getContext(), HiddenFeaturesActivity.class);
            startActivity(i);
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
                // Get zimbra preferences
                try {
                    zimbraPreferences.set(Objects.requireNonNull(JsonUtility.getJsonObject("https://mon.lyceeconnecte.fr/userbook/preference/zimbra", oneSessionId))); } catch (IOException e) { e.printStackTrace(); }
                try {
                    parseToUser(Objects.requireNonNull(JsonUtility.getJsonObject("https://mon.lyceeconnecte.fr/directory/user/" + Objects.requireNonNull(JsonUtility.getJsonObject("https://mon.lyceeconnecte.fr/auth/oauth2/userinfo", oneSessionId)).getString("userId"), oneSessionId))); } catch (IOException | JSONException e) { e.printStackTrace(); }
                requireActivity().runOnUiThread(() -> {

                    // Apply zimbra preferences
                    try {
                        expertModeSwitch.setChecked(zimbraPreferences.get().getString("preference").contains("true"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // Apply user preferences
                    displayName.setText(currentUser.getDisplayName());
                    birthDate.setText(currentUser.getBirthDate());
                    address.setText(currentUser.getAddress());

                    expertModeSwitch.setEnabled(true);
                    displayName.setEnabled(true);
                    birthDate.setEnabled(true);
                    address .setEnabled(true);

                    FirebaseDatabase database = FirebaseDatabase.getInstance("https://lycee-connecte-default-rtdb.europe-west1.firebasedatabase.app");
                    DatabaseReference verifiedUsers = database.getReference("verified-users");

                    // Check if user is verified
                    verifiedUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot s: snapshot.getChildren()) {
                                if (Objects.equals(s.getValue(), currentUser.getId())) { hiddenFeatures.setEnabled(true); }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) { }
                    });
                });
            }).start();
        } else {
            // Zimbra
            expertModeSwitch.setChecked(prefsSharedPreferences.getBoolean("expertMode", false));

            // User
            String user = dataSharedPreferences.getString("user", "");
            if (!user.trim().isEmpty()) {
                try {
                    parseToUser(Objects.requireNonNull(ParseStringToJson.parseStringToJsonObject(user)));

                    // Apply user preferences
                    displayName.setText(currentUser.getDisplayName());
                    birthDate.setText(currentUser.getBirthDate());
                    address.setText(currentUser.getAddress());
                } catch (JSONException e) { e.printStackTrace(); }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void parseToUser(JSONObject data) throws JSONException {

        Gson gson = new Gson();
        currentUser = gson.fromJson(data.toString(), User.class);

        dataEditor.putString("user", data.toString());
        dataEditor.commit();
    }
}