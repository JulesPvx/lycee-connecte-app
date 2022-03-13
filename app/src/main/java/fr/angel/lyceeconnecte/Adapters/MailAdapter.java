package fr.angel.lyceeconnecte.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Calendar;

import fr.angel.lyceeconnecte.MailActivity;
import fr.angel.lyceeconnecte.Models.Mail;
import fr.angel.lyceeconnecte.R;
import fr.angel.lyceeconnecte.Utility.ProfilePicture;

public class MailAdapter extends RecyclerView.Adapter<MailAdapter.MailHolder> {

    // List to store all the contact details
    private final ArrayList<Mail> mailList;
    private final Context context;
    private final Integer status;

    // Constructor for the Class
    public MailAdapter(ArrayList<Mail> mailList, Context context, Integer status) {
        this.mailList = mailList;
        this.context = context;
        this.status = status;
    }

    @NonNull
    @Override
    public MailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.mail_item_layout, parent, false);
        return new MailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MailHolder holder, int position) {
        final Mail mail = mailList.get(position);

        // Set the data to the views here
        if (mail.getState().equals("SENT")) {
            for (ArrayList<String> name : mail.getDisplayNames()) {
                if (name.get(0).equals(mail.getFrom())) {
                    holder.titleTv.setText(name.get(1));
                    ProfilePicture.getUserProfilePicture(mail.getFrom(), context, holder.iconImg);
                }
            }
        }
        else if (mail.getState().equals("DRAFT")) {
            StringBuilder finalName = new StringBuilder();
            if (!mail.getDisplayNames().isEmpty()) {
                for (ArrayList<String> name : mail.getDisplayNames()) {
                    finalName.append(", ").append(name);
                }
            } else {
                finalName.append("(Vide)");
            }
            holder.titleTv.setText(finalName);
            if (!mail.getTo().isEmpty()) {
                ProfilePicture.getUserProfilePicture(mail.getTo().get(0), context, holder.iconImg);
            }
        }

        // Make text bold if unread
        if (mail.isUnread()) {
            holder.titleTv.setAlpha(1f);
            holder.dateTv.setAlpha(1f);
            holder.titleTv.setTypeface(null, Typeface.BOLD);
            holder.dateTv.setTypeface(null, Typeface.BOLD);
        } else {
            holder.titleTv.setAlpha(0.5f);
            holder.dateTv.setAlpha(0.5f);
            holder.titleTv.setTypeface(null, Typeface.NORMAL);
            holder.dateTv.setTypeface(null, Typeface.NORMAL);
        }

        if (!mail.getSubject().trim().isEmpty()) { holder.bodyTv.setText(mail.getSubject()); } else { holder.bodyTv.setText(R.string.empty_subject); }

        holder.dateTv.setText(getDate(mail.getDate()));

        holder.parent.setOnClickListener(v -> {
            Intent i = new Intent(context, MailActivity.class);
            i.putExtra("status", status);
            i.putExtra("mailId", mail.getId());
            int res = R.menu.mail_actions_menu_default;
            switch (mail.getSystemFolder()) {
                case "INBOX":
                    res = R.menu.mail_actions_menu_inbox;
                    Log.e("TAG", "onBindViewHolder: " + mail.getSystemFolder() + ":INBOX");
                    break;
                case "SENT":
                    res = R.menu.mail_actions_menu_sent;
                    Log.e("TAG", "onBindViewHolder: " + mail.getSystemFolder() + ":SENT");
                    break;
                case "DRAFTS":
                    res = R.menu.mail_actions_menu_drafts;
                    Log.e("TAG", "onBindViewHolder: " + mail.getSystemFolder() + ":DRAFTS");
                    break;
                case "TRASH":
                    res = R.menu.mail_actions_menu_trash;
                    Log.e("TAG", "onBindViewHolder: " + mail.getSystemFolder() + ":TRASH");
                    break;
            }
            Log.e("TAG", "onBindViewHolder: " + res);
            i.putExtra("menu", res);
            context.startActivity(i);

            // Set as read
            holder.titleTv.setAlpha(0.5f);
            holder.dateTv.setAlpha(0.5f);
            holder.titleTv.setTypeface(null, Typeface.NORMAL);
            holder.dateTv.setTypeface(null, Typeface.NORMAL);
            mail.setUnread(false);
        });
    }

    @Override
    public int getItemCount() {
        return mailList == null? 0: mailList.size();
    }

    public static class MailHolder extends RecyclerView.ViewHolder {

        private final MaterialTextView bodyTv, titleTv, dateTv;
        private final ShapeableImageView iconImg;
        private final MaterialCardView parent;

        public MailHolder(View itemView) {
            super(itemView);

            titleTv = itemView.findViewById(R.id.mail_item_title_tv);
            bodyTv = itemView.findViewById(R.id.mail_item_body_tv);
            dateTv = itemView.findViewById(R.id.mail_item_date_tv);
            iconImg = itemView.findViewById(R.id.mail_item_icon_img);
            parent = itemView.findViewById(R.id.mail_item_parent);
        }
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        return DateFormat.format("d MMM", cal).toString();
    }
}
