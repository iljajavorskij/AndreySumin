package org.myapp.andreysumin.presentayion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import org.myapp.andreysumin.R
import org.myapp.andreysumin.domain.ShopItem

class ShopItemAdapter :RecyclerView.Adapter<ShopItemAdapter.ShopItemViewHolder>(){

    var shopList = listOf<ShopItem>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null


    class ShopItemViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val nameCard = itemView.findViewById<TextView>(R.id.name)
        val sizeCard = itemView.findViewById<TextView>(R.id.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when(viewType){
            VIEW_TYPE_ENABLED -> R.layout.item_card_enabled
            VIEW_TYPE_DISABLED -> R.layout.item_card_unenable
            else -> throw RuntimeException("Unknown view type $viewType")

        }
        val view = LayoutInflater.from(parent.context).
        inflate(layout,parent,false)
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shopList[position]
        holder.itemView.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
        holder.itemView.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }
        holder.nameCard.text = shopItem.name
        holder.sizeCard.text = shopItem.count.toString()
    }

    override fun onViewRecycled(holder: ShopItemViewHolder) {
        super.onViewRecycled(holder)
        holder.nameCard.text = ""
        holder.sizeCard.text = ""
        holder.nameCard.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.white))
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    override fun getItemViewType(position: Int): Int {

        val item = shopList[position]
        return if (item.enable){
            VIEW_TYPE_ENABLED
        }else{
            VIEW_TYPE_DISABLED
        }
    }




    companion object {
        const val VIEW_TYPE_ENABLED = 100
        const val VIEW_TYPE_DISABLED = 101

        const val MAX_POOL_ENABLE = 15

    }
}