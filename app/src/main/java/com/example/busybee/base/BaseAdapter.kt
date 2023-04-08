package com.example.busybee.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T, VB : ViewBinding>(
    private  var items: List<T>
): RecyclerView.Adapter<BaseAdapter.BaseViewHolder<VB>>(){

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = bindingInflater(inflater, parent, false)
        return BaseViewHolder(binding)
    }


    class BaseViewHolder<VB : ViewBinding>(val binding: VB):RecyclerView.ViewHolder(binding.root)



open fun areItemSame(oldItem: T, newItem: T) = oldItem?.equals(newItem) == true
}