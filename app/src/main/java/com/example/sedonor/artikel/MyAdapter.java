package com.example.sedonor.artikel;

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
import com.example.sedonor.R;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Artikel> list;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public MyAdapter(Context context, ArrayList<Artikel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_artikel, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Artikel artikel = list.get(position);
        holder.judul.setText(artikel.getJudul());
        holder.konten.setText(artikel.getKonten());

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

        TextView judul, konten;
        ImageView gambar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            judul = itemView.findViewById(R.id.tvJudulArtikel);
            konten = itemView.findViewById(R.id.tvKonten);
            gambar = itemView.findViewById(R.id.ivGambar);

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
