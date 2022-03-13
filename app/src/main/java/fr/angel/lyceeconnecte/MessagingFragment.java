package fr.angel.lyceeconnecte;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.material.navigationrail.NavigationRailView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import fr.angel.lyceeconnecte.Adapters.MailAdapter;
import fr.angel.lyceeconnecte.Models.Mail;

public class MessagingFragment extends Fragment {

    private String oneSessionId;
    private Integer status;

    private RequestQueue requestQueue;

    private final ArrayList<Mail> mails = new ArrayList<>();
    private final HashMap<String, ArrayList<Mail>> mailsMap = new HashMap<>();
    private final HashMap<String, Integer> pages = new HashMap<>();
    private MailAdapter mailAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setup SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE);

        oneSessionId = sharedPreferences.getString("oneSessionId", "");

        if (getArguments() != null) {
            status = getArguments().getInt("status");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_messaging, container, false);
    }

    @SuppressLint({"NotifyDataSetChanged", "NonConstantResourceId"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Bind views
        NavigationRailView navigationRailView = view.findViewById(R.id.messaging_navigation_rail);
        RecyclerView recyclerView = view.findViewById(R.id.messaging_rv);

        // Setup mails recycler view
        LinearLayoutManager mailsLayoutManager = new LinearLayoutManager(requireActivity());
        recyclerView.setLayoutManager(mailsLayoutManager);
        mailAdapter = new MailAdapter(mails, requireContext(), status);
        recyclerView.setAdapter(mailAdapter);

        // Setup rail view
        navigationRailView.setOnItemSelectedListener(item -> {

            String category = "Inbox";

            switch (item.getItemId()) {
                case R.id.messages:
                    category = "Inbox";
                    break;
                case R.id.sent:
                    category = "Sent";
                    break;
                case R.id.drafts:
                    category = "Drafts";
                    break;
                case R.id.bin:
                    category = "Trash";
                    break;
            }

            getData(category);

            return true;
        });

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

            getData("Inbox");
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getData(String folder) {

        // Get previous data if existing
        if (mailsMap.get(folder) != null) {
            mails.clear();
            mails.addAll(Objects.requireNonNull(mailsMap.get(folder)));
            mailAdapter.notifyDataSetChanged();
        }

        // Get latest data
        pages.put(folder, 0);
        mailsMap.put(folder, new ArrayList<>());
        request(folder);
    }

    private void request(String folder) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "https://mon.lyceeconnecte.fr/zimbra/list?folder=/" + folder + "&page=" + pages.get(folder) + "&unread=false", null, response -> {

            if (response.length() <= 0) {
                endRequest(folder);
                requestQueue.cancelAll("Inbox");
            } else {
                pages.put(folder, Objects.requireNonNull(pages.get(folder)) + 1);
                try {
                    parseToMails(response, folder); } catch (JSONException e) { e.printStackTrace(); }
                request(folder);
            }
        }, error -> { }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("cookie", "oneSessionId=" + oneSessionId + ";");
                return headers;
            }
        };
        jsonArrayRequest.setTag(folder);

        requestQueue.add(jsonArrayRequest);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void endRequest(String folder) {
        mails.clear();
        mails.addAll(Objects.requireNonNull(mailsMap.get(folder)));
        mailAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void parseToMails(JSONArray data, String folder) throws JSONException {

        for (int i = 0; i < data.length(); i++) {
            JSONObject mail = data.getJSONObject(i);

            Gson gson = new Gson();
            Mail object = gson.fromJson(mail.toString(), Mail.class);

            Objects.requireNonNull(mailsMap.get(folder)).add(object);
        }
    }
}