package fr.angel.lyceeconnecte.Utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.imageview.ShapeableImageView;

import fr.angel.lyceeconnecte.R;

public class ProfilePicture {

    public static void getUserProfilePicture(String id, Context context, ShapeableImageView img) {

        Glide.with(context)
                .asBitmap()
                .load("https://mon.lyceeconnecte.fr/userbook/avatar/" + id)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                        Bitmap image = resource;

                        Glide.with(context)
                                .asBitmap()
                                .load("https://mon.lyceeconnecte.fr/userbook/avatar/517cae4f-0c43-4ae4-b83f-6332536bb749")
                                .into(new CustomTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                        if (!image.sameAs(resource)) {
                                            img.setImageBitmap(image);
                                        } else {
                                            img.setImageResource(R.drawable.ic_launcher_foreground);
                                        }
                                    }

                                    @Override
                                    public void onLoadCleared(@Nullable Drawable placeholder) {

                                    }
                                });
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
    }
}
