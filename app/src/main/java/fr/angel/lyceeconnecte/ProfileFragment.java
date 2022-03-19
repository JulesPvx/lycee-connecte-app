package fr.angel.lyceeconnecte;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import fr.angel.lyceeconnecte.Models.User;
import fr.angel.lyceeconnecte.Utility.ProfilePicture;

public class ProfileFragment extends Fragment {

    private String oneSessionId;
    private Integer status;

    private RequestQueue requestQueue;

    private ShapeableImageView profileImg;
    private SwitchMaterial mainInfoSwitch;
    private ExpandableLayout mainInfoEl;
    private TextInputLayout usernameTil, loginTil, passwordTil, emailTil, phoneTil, mobileTil, birthDateTil, homeInstTil;

    private SharedPreferences.Editor editor;

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
        passwordTil = view.findViewById(R.id.profile_password);
        emailTil = view.findViewById(R.id.profile_email);
        phoneTil = view.findViewById(R.id.profile_phone);
        mobileTil = view.findViewById(R.id.profile_mobile);
        birthDateTil = view.findViewById(R.id.profile_birth_date);
        homeInstTil = view.findViewById(R.id.profile_home_institutution);
        profileImg = view.findViewById(R.id.profile_img);
        mainInfoSwitch = view.findViewById(R.id.profile_main_info_switch);
        mainInfoEl = view.findViewById(R.id.profile_main_info_el);

        mainInfoSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> mainInfoEl.setExpanded(isChecked) );

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

            requestUserId();
        }
    }

    private void requestUserId() {
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
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://mon.lyceeconnecte.fr/directory/user/" + id, null, this::parseToUserObjectAndViews, error -> { }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("cookie", "oneSessionId=" + oneSessionId + ";");
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

    private void parseToUserObjectAndViews(JSONObject data) {

        Gson gson = new Gson();
        User user = gson.fromJson(data.toString(), User.class);

        editor.putString("user", data.toString());
        editor.commit();

        ProfilePicture.getUserProfilePicture(oneSessionId, getActivity(), profileImg);

        usernameTil.getEditText().setText(user.getDisplayName());
        loginTil.getEditText().setText(user.getLogin());
        //passwordTil.getEditText().setText(user.getPass());
        emailTil.getEditText().setText(user.getEmail());
        phoneTil.getEditText().setText(user.getHomePhone());
        mobileTil.getEditText().setText(user.getMobile());
        birthDateTil.getEditText().setText(user.getBirthDate());
        //homeInstTil.getEditText().setText(user.getHomeInst());
    }
}