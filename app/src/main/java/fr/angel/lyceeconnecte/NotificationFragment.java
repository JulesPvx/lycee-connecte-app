package fr.angel.lyceeconnecte;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import fr.angel.lyceeconnecte.Adapters.NotificationAdapter;
import fr.angel.lyceeconnecte.Models.Notification;
import fr.angel.lyceeconnecte.Utility.JsonUtility;
import fr.angel.lyceeconnecte.Utility.ParseStringToJson;

public class NotificationFragment extends Fragment {

    private String oneSessionId;
    private Integer status;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private RecyclerView notificationRv;

    private final ArrayList<Notification> notifications = new ArrayList<>();
    private NotificationAdapter notificationAdapter;

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
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Bind views
        notificationRv = view.findViewById(R.id.notification_rv);

        // Setup notifications recycler view
        LinearLayoutManager notificationsLayoutManager = new LinearLayoutManager(requireActivity());
        notificationRv.setLayoutManager(notificationsLayoutManager);
        notificationAdapter = new NotificationAdapter(notifications, requireActivity(), status);
        notificationRv.setAdapter(notificationAdapter);

        // Try to put previously retrieved data into views
        String notifications = sharedPreferences.getString("notifications", "");
        if (!notifications.trim().isEmpty()) {
            try {
                parseToNotifications(Objects.requireNonNull(ParseStringToJson.parseStringToJsonObject(notifications)));
                notificationAdapter.notifyDataSetChanged();
            } catch (JSONException e) { e.printStackTrace(); }
        }

        if (status == MainActivity.STATUS_OK) {
            // Handler to avoid recycler view errors
            Handler handler = new Handler();
            handler.postDelayed(this::getData, 1000);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getData() {
        // Get data
        new Thread(() -> {

            try {
                parseToNotifications(Objects.requireNonNull(JsonUtility.getJsonObject("https://mon.lyceeconnecte.fr/timeline/lastNotifications?type=ARCHIVE&type=BLOG&type=CALENDAR&type=COLLABORATIVEWALL&type=COMMUNITY&type=EXERCIZER&type=FORMULAIRE&type=FORUM&type=MESSAGERIE&type=MINDMAP&type=MOODLE&type=NEWS&type=PAGES&type=RACK&type=RBS&type=SCRAPBOOK&type=SHAREBIGFILES&type=SUPPORT&type=TIMELINE&type=TIMELINEGENERATOR&type=USERBOOK&type=USERBOOK_MOOD&type=USERBOOK_MOTTO&type=WIKI&type=WORKSPACE&page=0", oneSessionId))); } catch (JSONException | IOException e) { e.printStackTrace(); }

            requireActivity().runOnUiThread(() -> notificationAdapter.notifyDataSetChanged());
        }).start();
    }

    private void parseToNotifications(JSONObject data) throws JSONException {
        notifications.clear();

        JSONArray array = data.getJSONArray("results");

        for (int i = 0; i < array.length(); i++) {
            JSONObject notification = array.getJSONObject(i);
            long date = (long) notification.getJSONObject("date").get("$date");

            Gson gson = new Gson();
            Notification object = gson.fromJson(notification.toString(), Notification.class);
            object.setPublicationDate(date);

            notifications.add(object);
        }

        editor.putString("notifications", data.toString());
        editor.commit();
    }
}