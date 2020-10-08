package com.example.myaudioplayer;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AlbumDetailsAdapter extends RecyclerView.Adapter<AlbumDetailsAdapter.MyHolderAlbumDetails> {

    Context context;
    static  ArrayList<MusicFiles> albumFiles;
    View view;

    public AlbumDetailsAdapter(Context context, ArrayList<MusicFiles> albumFiles) {
        this.context = context;
        this.albumFiles = albumFiles;
    }

    @NonNull
    @Override
    public MyHolderAlbumDetails onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.music_items, parent, false);
        return new MyHolderAlbumDetails(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolderAlbumDetails holder, final int position) {
        holder.album_name.setText(albumFiles.get(position).getTitle());
        byte[] image = getAlbumArt(albumFiles.get(position).getPath());
        if (image != null)
        {
            Glide.with(context).asBitmap()
                    .load(image)
                    .into(holder.album_image);
        }else
        {
            Glide.with(context)
                    .load(R.drawable.ic_launcher_background)
                    .into(holder.album_image);
        }

       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent intent = new Intent(context, PlayerActivity.class);
               intent.putExtra("sender", "albumDetails");
               intent.putExtra("position", position);
               context.startActivity(intent);

           }
       });


    }

    @Override
    public int getItemCount() {
        return albumFiles.size();
    }

    private byte[] getAlbumArt(String uri){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        return art;
    }



    public class MyHolderAlbumDetails extends RecyclerView.ViewHolder {
        ImageView album_image;
        TextView album_name;

        public MyHolderAlbumDetails(@NonNull View itemView) {
            super(itemView);
            album_image = itemView.findViewById(R.id.music_img);
            album_name = itemView.findViewById(R.id.music_file_name);

        }
    }
}
