package org.myapp.andreysumin.domain

class RemoveShopItemUseCase(private val repository: ShopListRepository) {
    fun removeShopList(shopItem: ShopItem){
        repository.removeShopList(shopItem)
    }
}