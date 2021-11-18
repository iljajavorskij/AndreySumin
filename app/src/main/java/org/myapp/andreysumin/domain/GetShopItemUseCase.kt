package org.myapp.andreysumin.domain

class GetShopItemUseCase(private val repository: ShopListRepository) {

    suspend fun getShopItem(shopItemId:Int):ShopItem{
        return repository.getShopItem(shopItemId)
    }
}