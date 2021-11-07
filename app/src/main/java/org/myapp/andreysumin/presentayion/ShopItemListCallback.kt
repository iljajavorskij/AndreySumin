package org.myapp.andreysumin.presentayion

import androidx.recyclerview.widget.DiffUtil
import org.myapp.andreysumin.domain.ShopItem

class ShopItemListCallback():DiffUtil.ItemCallback<ShopItem>() {
    override fun areItemsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem == newItem
    }
}