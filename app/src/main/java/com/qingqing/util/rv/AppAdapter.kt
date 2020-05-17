package com.qingqing.util.rv

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.qingqing.util.R
import com.qingqing.util.dto.AppInfo

internal class AppAdapter(private val context: Context, private var data: List<AppInfo>) :
    RecyclerView.Adapter<AppAdapter.AppHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppHolder {
        val inflate = LayoutInflater.from(context).inflate(R.layout.item_app, parent, false)
        return AppHolder(inflate)
    }

    private var onItemClickListener: OnItemClickListener? = null

    public interface OnItemClickListener {
        fun onItemClickListener(index: Int,dto:AppInfo)
    }

    public fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }
    override fun onBindViewHolder(holder: AppHolder, position: Int) {
        holder.tv.text = data[position].appName
        holder.iv.setImageBitmap(data[position].appIcon)
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener { onItemClickListener!!.onItemClickListener(position,data[position]) }
        }
    }

    fun setData(list: List<AppInfo>) {
        this.data = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    internal class AppHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        public val tv: TextView
        public var iv: ImageView

        init {
            tv = itemView.findViewById(R.id.tv)
            iv = itemView.findViewById(R.id.iv)
        }
    }
}
