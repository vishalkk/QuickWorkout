package com.example.quickworkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quickworkout.databinding.ItemExcerciseStatusBinding
import kotlinx.coroutines.NonDisposableHandle
import kotlinx.coroutines.NonDisposableHandle.parent

class ExcerciseStatusAdapter(val items:ArrayList<ExcerciseModel>):RecyclerView.Adapter<ExcerciseStatusAdapter.ViewHolder>() {
class ViewHolder(val itemExcerciseStatusBinding: ItemExcerciseStatusBinding)
    :RecyclerView.ViewHolder(itemExcerciseStatusBinding.root){
    val tvItem = itemExcerciseStatusBinding.tvItem
}

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val model: ExcerciseModel = items[position]
            holder.tvItem.text = model.getId().toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(ItemExcerciseStatusBinding.inflate(LayoutInflater.from(
            parent.context),
            parent,false))
    }

    override fun getItemCount(): Int {
           return  items.size
            }
}