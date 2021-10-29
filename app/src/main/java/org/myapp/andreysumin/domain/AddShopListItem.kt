package org.myapp.andreysumin.domain

class AddShopListItem(private val shopListRepository: ShopListRepository) {
    fun addShopListItem(shopItem:ShopItem){
        shopListRepository.addShopListItem(shopItem)
    }
}