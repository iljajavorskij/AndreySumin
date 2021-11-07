package org.myapp.andreysumin.domain

class AddShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun addShopListItem(shopItem:ShopItem){
        shopListRepository.addShopListItem(shopItem)
    }
}