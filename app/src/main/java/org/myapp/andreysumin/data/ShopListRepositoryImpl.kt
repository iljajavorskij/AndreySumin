package org.myapp.andreysumin.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.myapp.andreysumin.domain.ShopItem
import org.myapp.andreysumin.domain.ShopListRepository

object ShopListRepositoryImpl:ShopListRepository {

    private val shopListLD = MutableLiveData<List<ShopItem>>()

    private val shopList = mutableListOf<ShopItem>()

    private var autoIncrementId = 0

    init {
        for (i in 0 until 10){
            val item = ShopItem("Name$i",i,true)
            addShopListItem(item)
        }
    }

    override fun addShopListItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID){
            shopItem.id = autoIncrementId
            autoIncrementId++
        }
        shopList.add(shopItem)
        updateShopList()
    }

    override fun removeShopList(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateShopList()
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldListItem = getShopItem(shopItem.id)
        shopList.remove(oldListItem)
        addShopListItem(shopItem)
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find { it.id == shopItemId } ?: throw RuntimeException("element with id not found")
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLD //метод тулст вызывается ля того чтобы возвращать копию коллекии а не оригинал что бы нельзя было менять оригинал
    }



    private fun updateShopList(){
        shopListLD.value = shopList.toList()
    }
}