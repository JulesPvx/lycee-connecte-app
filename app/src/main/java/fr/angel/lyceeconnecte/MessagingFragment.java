package fr.angel.lyceeconnecte;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
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
import com.google.android.material.navigationrail.NavigationRailView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import fr.angel.lyceeconnecte.Adapters.MailAdapter;
import fr.angel.lyceeconnecte.Models.Mail;

public class MessagingFragment extends Fragment {

    private String oneSessionId;
    private Integer status, page = 0;
    private boolean makeRequest = true;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private RequestQueue requestQueue;

    private NavigationRailView navigationRailView;
    private RecyclerView recyclerView;

    private ArrayList<Mail> mails = new ArrayList<>();
    private MailAdapter mailAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setup SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
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
        return inflater.inflate(R.layout.fragment_messaging, container, false);
    }

    @SuppressLint({"NotifyDataSetChanged", "NonConstantResourceId"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Bind views
        navigationRailView = view.findViewById(R.id.messaging_navigation_rail);
        recyclerView = view.findViewById(R.id.messaging_rv);

        // Setup novelties recycler view
        LinearLayoutManager mailsLayoutManager = new LinearLayoutManager(requireActivity());
        recyclerView.setLayoutManager(mailsLayoutManager);
        mailAdapter = new MailAdapter(mails, requireContext(), status);
        recyclerView.setAdapter(mailAdapter);

        navigationRailView.setOnItemSelectedListener(item -> {

            mails.clear();
            mailAdapter.notifyDataSetChanged();
            page = 0;

            switch (item.getItemId()) {
                case R.id.messages:
                    fetchMessages("Inbox");
                    break;
                case R.id.sent:
                    fetchMessages("Sent");
                    break;
                case R.id.drafts:
                    fetchMessages("Drafts");
                    break;
                case R.id.bin:
                    fetchMessages("Trash");
                    break;
            }
            Log.e("TAG", "onViewCreated: " + item);

            return true;
        });

        // Set policy
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        if (status == MainActivity.STATUS_OK) {

            // Instantiate the cache
            Cache cache = new DiskBasedCache(requireContext().getCacheDir(), 1024 * 1024); // 1MB cap

            // Set up the network to use HttpURLConnection as the HTTP client.
            Network network = new BasicNetwork(new HurlStack());

            // Instantiate the RequestQueue with the cache and network.
            requestQueue = new RequestQueue(cache, network);

            // Start the queue
            requestQueue.start();

            fetchMessages("Inbox");
        }
    }

    private void fetchMessages(String folder) {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "https://mon.lyceeconnecte.fr/zimbra/list?folder=/" + folder + "&page=" + page + "&unread=false", null, response -> {

                    if (response.length() <= 0) { makeRequest = false; }
                    try {
                        parseToMails(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> makeRequest = false) {
                    @Override
                    public Map<String, String> getHeaders() {
                        HashMap<String, String> headers = new HashMap<>();
                        headers.put("cookie", "oneSessionId=" + oneSessionId + ";");
                        return headers;
                    }
                };

                requestQueue.add(jsonArrayRequest);
                if (makeRequest) {
                    page += 1;
                    Log.d("TAG", "fetchMessages: " + page);
                    handler.postDelayed(this, 1000);
                }
            }
        }, 1000);
    }

    @SuppressLint("NotifyDataSetChanged")
    private boolean parseToMails(JSONArray data) throws JSONException {

        for (int i = 0; i < data.length(); i++) {
            JSONObject mail = data.getJSONObject(i);

            Gson gson = new Gson();
            Mail object = gson.fromJson(mail.toString(), Mail.class);

            mails.add(object);
        }

        mailAdapter.notifyDataSetChanged();

        // TODO: offline mode
        //editor.putString("mails", data.toString());
        //editor.commit();
        return data.length() > 0;
    }
}