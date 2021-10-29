package org.myapp.andreysumin.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {

    fun addShopListItem(shopItem:ShopItem)

    fun editShopItem(shopItem: ShopItem)

    fun getShopItem(shopItemId:Int):ShopItem

    fun getShopList():LiveData<List<ShopItem>>

    fun removeShopList(shopItem: ShopItem)

}