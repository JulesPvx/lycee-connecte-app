package fr.angel.lyceeconnecte.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.text.Html;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import net.cachapa.expandablelayout.ExpandableLayout;

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
    private final Context context;

    public NewsAdapter(ArrayList<MyThread> threadList, Context context) {
        this.threadList = threadList;
        this.context = context;
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
        try { calendar.setTime(Objects.requireNonNull(inputFormat.parse(thread.getModified()))); } catch (ParseException e) { e.printStackTrace(); }
        Date date = calendar.getTime();

        String finalTime = TimeAgo.getTimeAgo(date);

        holder.timeTv.setText(finalTime);

        // Create spannable
        Spannable spannable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            spannable = (Spannable) Html.fromHtml(thread.getContent(), Html.FROM_HTML_MODE_COMPACT);
        } else {
            spannable = (Spannable) Html.fromHtml(thread.getContent());
        }
        // Get color
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(com.google.android.material.R.attr.colorOnSurface, typedValue, true);
        @ColorInt int color = typedValue.data;
        // Change spannable text color
        spannable.setSpan(new ForegroundColorSpan(color), 0, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // Set text content
        holder.contentTv.setText(spannable);
        holder.contentTv.setMovementMethod(LinkMovementMethod.getInstance());

        holder.expandBtn.setOnClickListener(v -> {
            holder.expandableLayout.toggle();
            if (holder.expandableLayout.isExpanded()) {
                holder.expandBtn.setText("Hide");
            } else {
                holder.expandBtn.setText("Show more");
            }
        });

        // TODO: More details activity
    }

    @Override
    public int getItemCount() {
        return threadList == null? 0: threadList.size();
    }

    public static class NewsHolder extends RecyclerView.ViewHolder {

        private final TextView titleTv, timeTv, contentTv;
        private final MaterialButton expandBtn;
        private final ExpandableLayout expandableLayout;

        public NewsHolder(View itemView) {
            super(itemView);

            titleTv = itemView.findViewById(R.id.news_item_title_tv);
            timeTv = itemView.findViewById(R.id.news_item_time_ago_tv);
            contentTv = itemView.findViewById(R.id.news_item_content_tv);
            expandBtn = itemView.findViewById(R.id.news_item_show_more_btn);
            expandableLayout = itemView.findViewById(R.id.news_item_expandable_layout);
        }
    }
}
