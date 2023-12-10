package com.example.sedonor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sedonor.lokasi.LokasiDonor

class AdapterLokasiHome(var context: Context, var list: ArrayList<LokasiDonor>) : RecyclerView.Adapter<AdapterLokasiHome.MyViewHolder>() {
    private var mListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_lokasi_home, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val lokasiDonor = list[position]
        holder.nama.setText(lokasiDonor.nama)
        holder.lokasi.setText(lokasiDonor.lokasi)
        Glide.with(context)
            .load(lokasiDonor.foto)
            .placeholder(R.drawable.background_image) // You can set a placeholder image
            .into(holder.gambar);
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var nama: TextView
        var tanggal: TextView
        var lokasi: TextView
        var gambar: ImageView

        init {
            nama = itemView.findViewById<TextView>(R.id.tvNamaLokasiHome)
            lokasi = itemView.findViewById<TextView>(R.id.tvLokasiHome)
            gambar = itemView.findViewById<ImageView>(R.id.ivGambarArtikelHome)
            tanggal = itemView.findViewById<TextView>(R.id.tvTanggalHome)

            // Set listener pada itemView (CardView)
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            if (mListener != null) {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    mListener!!.onItemClick(position)
                }
            }
        }
    }
}