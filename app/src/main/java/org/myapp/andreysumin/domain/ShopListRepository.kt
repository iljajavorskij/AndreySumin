package org.myapp.andreysumin.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {

    suspend fun addShopListItem(shopItem:ShopItem)

    suspend fun editShopItem(shopItem: ShopItem)

    suspend fun getShopItem(shopItemId:Int):ShopItem

    fun getShopList():LiveData<List<ShopItem>>

    suspend fun removeShopList(shopItem: ShopItem)

}