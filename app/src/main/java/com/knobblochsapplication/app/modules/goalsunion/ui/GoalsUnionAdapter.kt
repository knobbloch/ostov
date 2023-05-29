package com.knobblochsapplication.app.modules.goalsunion.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.databinding.RowGoalsUnionBinding
import com.knobblochsapplication.app.modules.goalsunion.`data`.model.GoalsUnionRowModel
import kotlin.Int
import kotlin.collections.List

class GoalsUnionAdapter(
  var list: List<GoalsUnionRowModel>
) : RecyclerView.Adapter<GoalsUnionAdapter.RowGoalsUnionVH>() {
  private var clickListener: OnItemClickListener? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowGoalsUnionVH {
    val view=LayoutInflater.from(parent.context).inflate(R.layout.row_goals_union,parent,false)
    return RowGoalsUnionVH(view)
  }

  override fun onBindViewHolder(holder: RowGoalsUnionVH, position: Int) {
    val goalsUnionRowModel = GoalsUnionRowModel()
    // TODO uncomment following line after integration with data source
    // val goalsUnionRowModel = list[position]
    holder.binding.goalsUnionRowModel = goalsUnionRowModel
  }

  override fun getItemCount(): Int = 2
  // TODO uncomment following line after integration with data source
  // return list.size

  public fun updateData(newData: List<GoalsUnionRowModel>) {
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
      item: GoalsUnionRowModel
    ) {
    }
  }

  inner class RowGoalsUnionVH(
    view: View
  ) : RecyclerView.ViewHolder(view) {
    val binding: RowGoalsUnionBinding = RowGoalsUnionBinding.bind(itemView)
  }
}
