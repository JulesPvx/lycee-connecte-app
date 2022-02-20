package fr.angel.lyceeconnecte.Adapters;

import android.os.Build;
import android.text.Html;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.angel.lyceeconnecte.Models.Novelty;
import fr.angel.lyceeconnecte.R;

public class NoveltyAdapter extends RecyclerView.Adapter<NoveltyAdapter.NoveltyHolder> {

    // List to store all the contact details
    private final ArrayList<Novelty> noveltiesList;

    // Constructor for the Class
    public NoveltyAdapter(ArrayList<Novelty> noveltiesList) {
        this.noveltiesList = noveltiesList;
    }

    @NonNull
    @Override
    public NoveltyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.novelty_item_layout, parent, false);
        return new NoveltyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoveltyHolder holder, int position) {
        final Novelty novelty = noveltiesList.get(position);

        // Set the data to the views here
        Spannable spannable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            spannable = (Spannable) Html.fromHtml(novelty.getContent(), Html.FROM_HTML_MODE_COMPACT);
        } else {
            spannable = (Spannable) Html.fromHtml(novelty.getContent());
        }
        holder.contentTv.setText(spannable);
        holder.contentTv.setMovementMethod(LinkMovementMethod.getInstance());

        holder.signatureTv.setText(novelty.getSignature());
    }

    @Override
    public int getItemCount() {
        return noveltiesList == null? 0: noveltiesList.size();
    }

    public static class NoveltyHolder extends RecyclerView.ViewHolder {

        private final TextView contentTv, signatureTv;

        public NoveltyHolder(View itemView) {
            super(itemView);

            contentTv = itemView.findViewById(R.id.novelty_item_content_tv);
            signatureTv = itemView.findViewById(R.id.novelty_item_signature_tv);
        }
    }
}
