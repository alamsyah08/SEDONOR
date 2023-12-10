package com.example.sedonor.riwayat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sedonor.R

class MyAdapterRiwayat(var context: Context, var list: ArrayList<RiwayatDonor>) : RecyclerView.Adapter<MyAdapterRiwayat.MyViewHolder>() {
    private var mListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_riwayatdonor, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val riwayatDonor = list[position]
        holder.nama.setText(riwayatDonor.tempatDonor)
        holder.tanggal.setText(riwayatDonor.tanggalDonor)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var nama: TextView
        var tanggal: TextView

        init {
            nama = itemView.findViewById<TextView>(R.id.tvJudulDonor)
            tanggal = itemView.findViewById<TextView>(R.id.tvTanggal)

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