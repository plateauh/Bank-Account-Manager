/*
* Developed by Najed Alseghair
* as a requirement of Android belt exam
* 30-9-2021
* */

package com.example.najed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.najed.databinding.ItemRowBinding

class RecyclerViewAdapter(private val historyList: ArrayList<String>): RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>() {

    class ItemViewHolder(val binding: ItemRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = historyList[position]
        holder.binding.apply {
            tvItem.text = item
        }
    }

    override fun getItemCount() = historyList.size
}