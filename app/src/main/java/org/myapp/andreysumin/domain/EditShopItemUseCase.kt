package org.myapp.andreysumin.domain

class EditShopItemUseCase(private  val repository: ShopListRepository) {
    fun editShopItem(shopItem: ShopItem){
        repository.editShopItem(shopItem)
    }
}