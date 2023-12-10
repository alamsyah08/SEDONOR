package com.example.sedonor

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class AdapterGejala(var context: Context, var list: ArrayList<Gejala>) : RecyclerView.Adapter<AdapterGejala.MyViewHolder>() {
    private var mListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_gejala, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val gejala = list[position]
        val newColor = Color.parseColor(gejala.warna)
        holder.nama.setText(gejala.nama)
        holder.bgGejala.setColorFilter(newColor, PorterDuff.Mode.SRC_IN)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var bgGejala: ImageView
        var nama: TextView

        init {
            bgGejala = itemView.findViewById<ImageView>(R.id.bgGejala)
            nama = itemView.findViewById<TextView>(R.id.namaGejala)

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

    fun updateData(newData: List<Gejala>) {
        list.clear()
        list.addAll(newData)
        notifyDataSetChanged()
    }
}