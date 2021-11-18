package org.myapp.andreysumin.domain

class AddShopItemUseCase(private val shopListRepository: ShopListRepository) {
    suspend fun addShopListItem(shopItem:ShopItem){
        shopListRepository.addShopListItem(shopItem)
    }
}