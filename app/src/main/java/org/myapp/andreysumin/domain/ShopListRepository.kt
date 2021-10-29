package org.myapp.andreysumin.domain

interface ShopListRepository {

    fun addShopListItem(shopItem:ShopItem)

    fun editShopItem(shopItem: ShopItem)

    fun getShopItem(shopItemId:Int):ShopItem

    fun getShopList():List<ShopItem>

    fun removeShopList(shopItem: ShopItem)

}