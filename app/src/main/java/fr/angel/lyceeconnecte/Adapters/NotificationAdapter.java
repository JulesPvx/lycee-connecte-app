package fr.angel.lyceeconnecte.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.text.Spannable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

import fr.angel.lyceeconnecte.Models.Notification;
import fr.angel.lyceeconnecte.R;
import fr.angel.lyceeconnecte.UserActivity;
import fr.angel.lyceeconnecte.Utility.ProfilePicture;
import fr.angel.lyceeconnecte.Utility.TimeAgo;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationHolder> {

    private final ArrayList<Notification> notificationList;
    private final Context context;
    private final Integer status;

    public NotificationAdapter(ArrayList<Notification> notificationList, Context context, Integer status) {
        this.notificationList = notificationList;
        this.context = context;
        this.status = status;
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.notification_item_layout, parent, false);
        return new NotificationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
        if (position < notificationList.size()) {
            Notification notification = notificationList.get(position);

            // Set the data to the views here
            holder.typeTv.setText(notification.getType());

            //String finalTime = TimeAgo.getTimeAgo(notification.getDate());
            holder.timeTv.setText(TimeAgo.getTimeAgo(notification.getPublicationDate()));

            ProfilePicture.getUserProfilePicture(notification.getSender(), context, holder.iconImg);

            // Create spannable
            Spannable spannable;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                spannable = (Spannable) Html.fromHtml(notification.getMessage(), Html.FROM_HTML_MODE_COMPACT);
            } else {
                spannable = (Spannable) Html.fromHtml(notification.getMessage());
            }
            holder.contentTV.setText(spannable);

            holder.iconImg.setOnClickListener(v -> {
                Intent i = new Intent(context, UserActivity.class);
                i.putExtra("userId", notification.getSender());
                i.putExtra("status", status);
                context.startActivity(i);
            });
        }
    }

    @Override
    public int getItemCount() {
        return notificationList == null? 0: notificationList.size();
    }

    public static class NotificationHolder extends RecyclerView.ViewHolder {

        private final MaterialTextView typeTv, timeTv, contentTV;
        private final ShapeableImageView iconImg;

        public NotificationHolder(View itemView) {
            super(itemView);

            typeTv = itemView.findViewById(R.id.notification_item_type_tv);
            timeTv = itemView.findViewById(R.id.notification_item_time_ago_tv);
            contentTV = itemView.findViewById(R.id.notification_item_content_tv);
            iconImg = itemView.findViewById(R.id.notification_item_icon_img);
        }
    }
}
