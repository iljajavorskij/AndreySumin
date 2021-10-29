package org.myapp.andreysumin.presentayion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.myapp.andreysumin.data.ShopListRepositoryImpl
import org.myapp.andreysumin.domain.EditShopItemUseCase
import org.myapp.andreysumin.domain.GetShopListUseCase
import org.myapp.andreysumin.domain.RemoveShopItemUseCase
import org.myapp.andreysumin.domain.ShopItem

class MainViewModel:ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val removeShopItemUseCase = RemoveShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()



    fun removeListItem(shopItem: ShopItem){
        removeShopItemUseCase.removeShopList(shopItem)
    }

    fun editListItem(shopItem: ShopItem){
        val newItem = shopItem.copy(enable = !shopItem.enable)
        editShopItemUseCase.editShopItem(newItem)
    }


}