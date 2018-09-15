package com.example.rasel.musicplayer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MediaCursorAdapter extends RecyclerView.Adapter<MediaCursorAdapter.ViewHolder> {

    private ArrayList<SongDetails> songList;
    private Context context;

    MediaCursorAdapter(Context context, ArrayList<SongDetails> songList) {
        this.songList = songList;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvSongTitle.setText(songList.get(position).getTitle());
        holder.tvSongArtist.setText(songList.get(position).getArtist());

        String data = songList.get(position).getUri();
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(data);
        Bitmap bitmap = BitmapFactory.decodeByteArray(mmr.getEmbeddedPicture(), 0, mmr.getEmbeddedPicture().length);
        mmr.release();
        holder.imSongPoster.setImageBitmap(bitmap);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NowPlaying.class);
                intent.putParcelableArrayListExtra("songList",songList);
                intent.putExtra("position",String.valueOf(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSongTitle, tvSongArtist;
        private ImageView imSongPoster;
        private CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvSongTitle = itemView.findViewById(R.id.tvTitle);
            tvSongArtist = itemView.findViewById(R.id.tvArtist);
            imSongPoster = itemView.findViewById(R.id.imSongPoster);
            cardView = itemView.findViewById(R.id.cvAllSongs);
        }
    }
}