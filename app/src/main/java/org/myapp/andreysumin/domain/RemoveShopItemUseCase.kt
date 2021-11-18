package org.myapp.andreysumin.domain

class RemoveShopItemUseCase(private val repository: ShopListRepository) {
    suspend fun removeShopList(shopItem: ShopItem){
        repository.removeShopList(shopItem)
    }
}