package org.myapp.andreysumin.presentayion

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.myapp.andreysumin.data.ShopListRepositoryImpl
import org.myapp.andreysumin.domain.EditShopItemUseCase
import org.myapp.andreysumin.domain.GetShopListUseCase
import org.myapp.andreysumin.domain.RemoveShopItemUseCase
import org.myapp.andreysumin.domain.ShopItem

class MainViewModel(application: Application):AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val removeShopItemUseCase = RemoveShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun removeListItem(shopItem: ShopItem){
        viewModelScope.launch {
            removeShopItemUseCase.removeShopList(shopItem)
        }
    }

    fun changeEnableState(shopItem: ShopItem){
        viewModelScope.launch {
            val newItem = shopItem.copy(enable = !shopItem.enable)
            editShopItemUseCase.editShopItem(newItem)
        }
    }
}