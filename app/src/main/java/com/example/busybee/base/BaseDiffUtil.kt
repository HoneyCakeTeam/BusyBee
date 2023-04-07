package com.example.busybee.base

import androidx.recyclerview.widget.DiffUtil

class BaseDiffUtil<T>(
    private val oldList : List <T> ,
    private val newList : List <T> ,
    private val checkIfSameItem : (oldItem:T , newItem :T) -> Boolean ,
    private val checkIfSameContent : (oldItem: T , newItem : T ) -> Boolean
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        TODO("Not yet implemented")
    }

    override fun getNewListSize(): Int {
        TODO("Not yet implemented")
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        TODO("Not yet implemented")
    }
}