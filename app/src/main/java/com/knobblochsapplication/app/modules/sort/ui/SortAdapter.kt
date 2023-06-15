package com.knobblochsapplication.app.modules.sort.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.databinding.RowSortBinding
import com.knobblochsapplication.app.modules.sort.`data`.model.SortRowModel
import kotlin.Int
import kotlin.collections.List

class SortAdapter(
  var list: List<SortRowModel>
) : RecyclerView.Adapter<SortAdapter.RowSortVH>() {
  private var clickListener: OnItemClickListener? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowSortVH {
    val view=LayoutInflater.from(parent.context).inflate(R.layout.row_sort,parent,false)
    return RowSortVH(view)
  }

  override fun onBindViewHolder(holder: RowSortVH, position: Int) {
    val sortRowModel = SortRowModel()
    // TODO uncomment following line after integration with data source
    // val sortRowModel = list[position]
    holder.binding.sortRowModel = sortRowModel
  }

  override fun getItemCount(): Int = 3
  // TODO uncomment following line after integration with data source
  // return list.size

  fun updateData(newData: List<SortRowModel>) {
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
      item: SortRowModel
    ) {
    }
  }

  inner class RowSortVH(
    view: View
  ) : RecyclerView.ViewHolder(view) {
    val binding: RowSortBinding = RowSortBinding.bind(itemView)
  }
}
