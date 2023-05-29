package com.knobblochsapplication.app.modules.downloadlist.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.databinding.RowDownloadListBinding
import com.knobblochsapplication.app.modules.downloadlist.`data`.model.DownloadListRowModel
import kotlin.Int
import kotlin.collections.List

class DownloadListAdapter(
  var list: List<DownloadListRowModel>
) : RecyclerView.Adapter<DownloadListAdapter.RowDownloadListVH>() {
  private var clickListener: OnItemClickListener? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowDownloadListVH {
    val view=LayoutInflater.from(parent.context).inflate(R.layout.row_download_list,parent,false)
    return RowDownloadListVH(view)
  }

  override fun onBindViewHolder(holder: RowDownloadListVH, position: Int) {
    val downloadListRowModel = DownloadListRowModel()
    // TODO uncomment following line after integration with data source
    // val downloadListRowModel = list[position]
    holder.binding.downloadListRowModel = downloadListRowModel
  }

  override fun getItemCount(): Int = 3
  // TODO uncomment following line after integration with data source
  // return list.size

  public fun updateData(newData: List<DownloadListRowModel>) {
    list = newData
    notifyDataSetChanged()
  }

  fun setOnItemClickListener(clickListener: OnItemClickListener) {
    this.clickListener = clickListener
  }

  interface OnItemClickListener {
    fun onItemClick(
      view: View,
      position: Int,
      item: DownloadListRowModel
    ) {
    }
  }

  inner class RowDownloadListVH(
    view: View
  ) : RecyclerView.ViewHolder(view) {
    val binding: RowDownloadListBinding = RowDownloadListBinding.bind(itemView)
  }
}
