package fr.angel.lyceeconnecte;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import fr.angel.lyceeconnecte.Adapters.NewsAdapter;
import fr.angel.lyceeconnecte.Adapters.NoveltyAdapter;
import fr.angel.lyceeconnecte.Models.MyThread;
import fr.angel.lyceeconnecte.Models.Novelty;
import fr.angel.lyceeconnecte.Utility.JsonFromUrl;
import fr.angel.lyceeconnecte.Utility.ParseStringToJson;

public class DashboardFragment extends Fragment {

    private String oneSessionId;
    private Integer status;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private MaterialCardView newsCv, newsFeedCv;
    private ShapeableImageView profilePictureImg;
    private MaterialTextView displayNameTv, schoolNameTv;

    private final ArrayList<MyThread> threads = new ArrayList<>();
    private NewsAdapter newsAdapter;
    private final ArrayList<Novelty> novelties = new ArrayList<>();
    private NoveltyAdapter noveltyAdapter;

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
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Bind views
        newsCv = view.findViewById(R.id.dashboard_news_cv);
        newsFeedCv = view.findViewById(R.id.dashboard_news_feed_cv);
        profilePictureImg = view.findViewById(R.id.dashboard_user_profile_picture_img);
        displayNameTv = view.findViewById(R.id.dashboard_display_name_tv);
        schoolNameTv = view.findViewById(R.id.news_item_time_ago_tv);
        RecyclerView newsRv = view.findViewById(R.id.dashboard_news_rv);
        RecyclerView newsFeedRv = view.findViewById(R.id.dashboard_news_feed_rv);

        // Setup news recycler view
        LinearLayoutManager newsLayoutManager = new LinearLayoutManager(requireActivity());
        newsRv.setLayoutManager(newsLayoutManager);
        newsAdapter = new NewsAdapter(threads);
        newsRv.setAdapter(newsAdapter);

        // Setup novelties recycler view
        LinearLayoutManager noveltiesLayoutManager = new LinearLayoutManager(requireActivity());
        newsFeedRv.setLayoutManager(noveltiesLayoutManager);
        noveltyAdapter = new NoveltyAdapter(novelties);
        newsFeedRv.setAdapter(noveltyAdapter);

        // Try to put previously retrieved data into views
        String threadFeed = sharedPreferences.getString("threadFeed", "");
        String thread = sharedPreferences.getString("threads", "");
        if (!threadFeed.trim().isEmpty()) {
            try {
                parseToThreadFeed(Objects.requireNonNull(ParseStringToJson.parseStringToJsonArray(threadFeed)));
                noveltyAdapter.notifyDataSetChanged();
                if (novelties.size() > 0) { newsFeedCv.setVisibility(View.VISIBLE); }
            } catch (JSONException e) { e.printStackTrace(); }
        }
        if (!thread.trim().isEmpty()) {
            try {
                parseToThreads(Objects.requireNonNull(ParseStringToJson.parseStringToJsonArray(thread)));
                newsAdapter.notifyDataSetChanged();
                if (threads.size() > 0) { newsCv.setVisibility(View.VISIBLE); }
            } catch (JSONException e) { e.printStackTrace(); }
        }

        if (status == MainActivity.STATUS_OK) {
            // Get data
            new Thread(() -> {

                try {
                    parseToThreads(Objects.requireNonNull(JsonFromUrl.getJsonArray("https://mon.lyceeconnecte.fr/actualites/infos/last/4", oneSessionId))); } catch (JSONException | IOException e) { e.printStackTrace(); }
                try {
                    parseToThreadFeed(Objects.requireNonNull(JsonFromUrl.getJsonArray("https://mon.lyceeconnecte.fr/timeline/flashmsg/listuser", oneSessionId))); } catch (JSONException | IOException e) { e.printStackTrace(); }

                requireActivity().runOnUiThread(() -> {

                    noveltyAdapter.notifyDataSetChanged();
                    newsAdapter.notifyDataSetChanged();

                    if (threads.size() > 0) { newsCv.setVisibility(View.VISIBLE); }
                    if (novelties.size() > 0) { newsFeedCv.setVisibility(View.VISIBLE); }

                });
            }).start();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void parseToThreadFeed(JSONArray data) throws JSONException {

        novelties.clear();

        for (int i = 0; i < data.length(); i++) {
            JSONObject novelty = data.getJSONObject(i);
            String content = (String) novelty.getJSONObject("contents").get("fr");

            Gson gson = new Gson();
            Novelty object = gson.fromJson(novelty.toString(), Novelty.class);
            object.setContent(content);

            novelties.add(object);
        }

        editor.putString("threadFeed", data.toString());
        editor.commit();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void parseToThreads(JSONArray data) throws JSONException {

        threads.clear();

        for (int i = 0; i < data.length(); i++) {
            JSONObject thread = data.getJSONObject(i);

            Gson gson = new Gson();
            MyThread object = gson.fromJson(thread.toString(), MyThread.class);

            threads.add(object);
        }

        editor.putString("threads", data.toString());
        editor.commit();
    }
}