package fr.angel.lyceeconnecte.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import fr.angel.lyceeconnecte.Models.MyThread;
import fr.angel.lyceeconnecte.R;
import fr.angel.lyceeconnecte.Utility.TimeAgo;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {

    private final ArrayList<MyThread> threadList;

    public NewsAdapter(ArrayList<MyThread> threadList) {
        this.threadList = threadList;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.news_item_layout, parent, false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        final MyThread thread = threadList.get(position);

        // Set the data to the views here
        holder.titleTv.setText(thread.getTitle());

        // format date
        @SuppressWarnings("SpellCheckingInspection") SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault());

        Calendar calendar = Calendar.getInstance();
        try { calendar.setTime(Objects.requireNonNull(inputFormat.parse(thread.getDate()))); } catch (ParseException e) { e.printStackTrace(); }
        Date date = calendar.getTime();

        String finalTime = TimeAgo.getTimeAgo(date);

        holder.timeTv.setText(finalTime);

        // TODO: More details activity
    }

    @Override
    public int getItemCount() {
        return threadList == null? 0: threadList.size();
    }

    public static class NewsHolder extends RecyclerView.ViewHolder {

        private final TextView titleTv, timeTv;

        public NewsHolder(View itemView) {
            super(itemView);

            titleTv = itemView.findViewById(R.id.news_item_title_tv);
            timeTv = itemView.findViewById(R.id.news_item_time_ago_tv);
        }
    }
}
