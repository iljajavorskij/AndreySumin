package org.myapp.andreysumin.presentayion

import androidx.recyclerview.widget.DiffUtil
import org.myapp.andreysumin.domain.ShopItem

class ShopItemDiffCallback(val oldList: List<ShopItem>,val newList: List<ShopItem>):DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItemId = oldList[oldItemPosition].id
        val newItemId = newList[newItemPosition].id
        return oldItemId == newItemId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }
}