package com.example.sedonor;

import android.view.LayoutInflater;
import android.view.View;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.example.sedonor.artikel.Artikel;

public class AdapaterHome extends RecyclerView.Adapter<AdapaterHome.MyViewHolder> {
    Context context;
    ArrayList<Artikel> list;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public AdapaterHome(Context context, ArrayList<Artikel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_artikel_home, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Artikel artikel = list.get(position);
        holder.judul.setText(artikel.getJudul());

//        Glide.with(context)
//                .load(artikel.getImageUrl())
//                .into(holder.gambar);
        Glide.with(context)
                .load(artikel.getImageUrl())
                .placeholder(R.drawable.loading) // You can set a placeholder image
                .into(holder.gambar);
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView judul;
        ImageView gambar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            judul = itemView.findViewById(R.id.tvJudulHome);
            gambar = itemView.findViewById(R.id.ivGambarHome);

            // Set listener pada itemView (CardView)
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }
    }
}
