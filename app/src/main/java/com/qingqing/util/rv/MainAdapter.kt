package com.qingqing.util.rv

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.qingqing.util.R

internal class MainAdapter(private val context: Context) :
    RecyclerView.Adapter<MainAdapter.MainHolder>() {
    private val titles = arrayOf("Log", "Doing")

    private var onItemClickListener: OnItemClickListener? = null

    public interface OnItemClickListener {
        fun onItemClickListener(index: Int)
    }

    public fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val inflate = LayoutInflater.from(context).inflate(R.layout.item_main, parent, false)
        return MainHolder(inflate)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.tv.text = titles[position]
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener { onItemClickListener!!.onItemClickListener(position) }
        }
    }

    override fun getItemCount(): Int {
        return titles?.size ?: 0
    }

    internal class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        public val tv: TextView

        init {
            tv = itemView.findViewById(R.id.tv_content)
        }
    }
}
