package fr.angel.lyceeconnecte;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import fr.angel.lyceeconnecte.Models.Related;
import fr.angel.lyceeconnecte.Models.User;
import fr.angel.lyceeconnecte.Utility.JsonUtility;
import fr.angel.lyceeconnecte.Utility.ParseStringToJson;
import fr.angel.lyceeconnecte.Utility.ProfilePicture;

public class ProfileFragment extends Fragment {

    private String oneSessionId;
    private Integer status;

    private RequestQueue requestQueue;
    private Bitmap newUserPic = null;

    private MaterialTextView displayNameTv;
    private ShapeableImageView profileImg, related1Img,  related2Img;
    private ExpandableLayout mainInfoEl, relatedEl;
    private MaterialTextView related1Name, related2Name;
    private TextInputLayout usernameTil, loginTil, emailTil, phoneTil, mobileTil, birthDateTil, homeInstTil;
    private FloatingActionButton sendFab, profilePicFab;

    private SharedPreferences.Editor editor;

    private final ActivityResultLauncher<Intent> pickImageResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data0 = result.getData();
                    Uri selectedImage = Objects.requireNonNull(data0).getData();
                    InputStream imageStream = null;
                    try {
                        imageStream = requireActivity().getContentResolver().openInputStream(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap bm = BitmapFactory.decodeStream(imageStream);

                    // Display image
                    profileImg.setImageBitmap(bm);

                    newUserPic = bm;
                }
            });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setup SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        oneSessionId = sharedPreferences.getString("oneSessionId", "");

        if (getArguments() != null) {
            status = getArguments().getInt("status");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Bind views
        usernameTil = view.findViewById(R.id.profile_username);
        loginTil = view.findViewById(R.id.profile_login);
        emailTil = view.findViewById(R.id.profile_email);
        phoneTil = view.findViewById(R.id.profile_phone);
        mobileTil = view.findViewById(R.id.profile_mobile);
        birthDateTil = view.findViewById(R.id.profile_birth_date);
        homeInstTil = view.findViewById(R.id.profile_home_institution);
        profileImg = view.findViewById(R.id.profile_img);
        SwitchMaterial mainInfoSwitch = view.findViewById(R.id.profile_main_info_switch);
        SwitchMaterial relatedSwitch = view.findViewById(R.id.profile_related_info_switch);
        mainInfoEl = view.findViewById(R.id.profile_main_info_el);
        relatedEl = view.findViewById(R.id.profile_related_info_el);
        related1Img = view.findViewById(R.id.related_1_img);
        related2Img = view.findViewById(R.id.related_2_img);
        related1Name = view.findViewById(R.id.related_1_name);
        related2Name = view.findViewById(R.id.related_2_name);
        displayNameTv = view.findViewById(R.id.profile_displayname);
        sendFab = view.findViewById(R.id.profile_send_changes_fab);
        profilePicFab = view.findViewById(R.id.profile_modify_profile_pic_fab);

        mainInfoSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> mainInfoEl.setExpanded(isChecked) );
        relatedSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> relatedEl.setExpanded(isChecked) );

        // Set policy
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Get data
        if (status == MainActivity.STATUS_OK) {

            // Instantiate the cache
            Cache cache = new DiskBasedCache(requireContext().getCacheDir(), 1024 * 1024); // 1MB cap
            // Set up the network to use HttpURLConnection as the HTTP client.
            Network network = new BasicNetwork(new HurlStack());
            // Instantiate the RequestQueue with the cache and network.
            requestQueue = new RequestQueue(cache, network);
            // Start the queue
            requestQueue.start();

            getData();

        } else {
            // Disable editing when offline
            sendFab.setEnabled(false);
            Objects.requireNonNull(emailTil.getEditText()).setFocusable(false);
            Objects.requireNonNull(phoneTil.getEditText()).setFocusable(false);
            Objects.requireNonNull(mobileTil.getEditText()).setFocusable(false);
        }
    }

    private void getData() {
        // Get user id
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://mon.lyceeconnecte.fr/auth/oauth2/userinfo", null, response -> {
            try {
                requestUserInfo(response.getString("userId"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> { }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("cookie", "oneSessionId=" + oneSessionId + ";");
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

    private void requestUserInfo(String id) {
        // Get user basic info
        JsonObjectRequest basicInfoRequest = new JsonObjectRequest(Request.Method.GET, "https://mon.lyceeconnecte.fr/directory/user/" + id, null, this::parseToUserObjectAndViewsMainInfo, error -> { }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("cookie", "oneSessionId=" + oneSessionId + ";");
                return headers;
            }
        };

        // Get user basic info
        JsonObjectRequest relatedRequest = new JsonObjectRequest(Request.Method.GET, "https://mon.lyceeconnecte.fr/userbook/api/person?id=" + id, null, response -> {
            try { parseToRelatedViews(response); } catch (JSONException e) { e.printStackTrace(); }
        }, error -> { }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("cookie", "oneSessionId=" + oneSessionId + ";");
                return headers;
            }
        };

        requestQueue.add(basicInfoRequest);
        requestQueue.add(relatedRequest);
    }

    private void parseToUserObjectAndViewsMainInfo(JSONObject data) {

        Gson gson = new Gson();
        User user = gson.fromJson(data.toString(), User.class);

        // Save info
        editor.putString("user", data.toString());
        editor.commit();

        // Apply to views
        ProfilePicture.getUserProfilePicture(user.getId(), getActivity(), profileImg);

        Objects.requireNonNull(usernameTil.getEditText()).setText(user.getDisplayName());
        Objects.requireNonNull(loginTil.getEditText()).setText(user.getLogin());
        Objects.requireNonNull(emailTil.getEditText()).setText(user.getEmail());
        Objects.requireNonNull(phoneTil.getEditText()).setText(user.getHomePhone());
        Objects.requireNonNull(mobileTil.getEditText()).setText(user.getMobile());
        Objects.requireNonNull(birthDateTil.getEditText()).setText(user.getBirthDate());
        Objects.requireNonNull(homeInstTil.getEditText()).setText(user.getStructureNodes().get(0).getName());
        Objects.requireNonNull(displayNameTv).setText(user.getDisplayName());

        // Handle pick image from gallery fab
        profilePicFab.setOnClickListener(v -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");

            pickImageResultLauncher.launch(photoPickerIntent);
        });

        // Handle fab send changes
        sendFab.setOnClickListener(v -> {
            // Push simple information
            try {
                String response = JsonUtility.putJsonObject("https://mon.lyceeconnecte.fr/directory/user/" + user.getId(), oneSessionId, "{\"displayName\":\"" + usernameTil.getEditText().getText() + "\"," /*TODO: Implement address edit text*/ + "\"email\":\"" + emailTil.getEditText().getText() + "\",\"homePhone\":\"" + phoneTil.getEditText().getText() + "\",\"mobile\":\"" + mobileTil.getEditText().getText() + "\",\"birthDate\":\"" + birthDateTil.getEditText().getText() + "\"}");
                JSONObject object = ParseStringToJson.parseStringToJsonObject(response);
                assert object != null;
                if (object.has("error")) {
                    Log.w("Error", "parseToUserObjectAndViewsMainInfo: " + response);
                    String error = object.getString("error");
                    if (error.contains("email")) { emailTil.setError(error); }
                    if (error.contains("mobile")) { mobileTil.setError(error); }
                    if (error.contains("homePhone")) { phoneTil.setError(error); }
                    if (error.contains("birthDate")) { birthDateTil.setError(error); }
                    if (error.contains("displayName")) { usernameTil.setError(error); }
                } else {
                    emailTil.setErrorEnabled(false);
                    mobileTil.setErrorEnabled(false);
                    phoneTil.setErrorEnabled(false);
                    birthDateTil.setErrorEnabled(false);
                    usernameTil.setErrorEnabled(false);
                }
            } catch (IOException | JSONException e) { e.printStackTrace(); }

            /* TODO: Push user new profile picture
            if (newUserPic != null) {

            }*/
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://lycee-connecte-default-rtdb.europe-west1.firebasedatabase.app");
        DatabaseReference verifiedUsers = database.getReference("verified-users");

        // Check if user is verified
        verifiedUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot s: snapshot.getChildren()) {
                    if (!Objects.equals(s.getValue(), user.getId())) {
                        // Disable editing for non-verified users
                        usernameTil.setFocusable(false);
                        birthDateTil.setFocusable(false);
                    } else {
                        // Enable for verified users
                        usernameTil.getEditText().setCompoundDrawablesWithIntrinsicBounds(null, null, null ,null);
                        birthDateTil.getEditText().setCompoundDrawablesWithIntrinsicBounds(null, null, null ,null);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        // Show user id when login til clicked 7 times
        AtomicInteger clickCounter = new AtomicInteger(7);
        displayNameTv.setOnClickListener(v -> {
            if (clickCounter.get() == 0) {
                displayNameTv.setText(user.getId());
                ClipboardManager clipboard = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("user id", user.getId());
                clipboard.setPrimaryClip(clip);
            } else {
                clickCounter.addAndGet(-1);
            }
        });
    }

    private void parseToRelatedViews(JSONObject data) throws JSONException {

        Gson gson = new Gson();
        for (int i = 0; i < data.getJSONArray("result").length(); i++) {
            Related related = gson.fromJson(data.getJSONArray("result").get(i).toString(), Related.class);
            switch (i) {
                case 0:
                    ProfilePicture.getUserProfilePicture(related.getRelatedId(), getActivity(), related1Img);
                    related1Name.setText(related.getRelatedName());
                    break;
                case 1:
                    ProfilePicture.getUserProfilePicture(related.getRelatedId(), getActivity(), related2Img);
                    related2Name.setText(related.getRelatedName());
                    break;
            }
        }

        editor.putString("user", data.toString());
        editor.commit();
    }
}