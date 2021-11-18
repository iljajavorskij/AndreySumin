package org.myapp.andreysumin.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import org.myapp.andreysumin.domain.ShopItem
import org.myapp.andreysumin.domain.ShopListRepository
import kotlin.random.Random

class ShopListRepositoryImpl(
    application: Application
):ShopListRepository {

    private val shopItemDao = AppDataBase.getInstanse(application).shopListDao()
    private val mapper = ShopItemMapper()

     override suspend fun addShopListItem(shopItem: ShopItem) {
        shopItemDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun removeShopList(shopItem: ShopItem) {
       shopItemDao.deleteShopItem(shopItem.id)
    }

    override suspend fun editShopItem(shopItem: ShopItem) {
        shopItemDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun getShopItem(shopItemId: Int): ShopItem {
        val dbModel = shopItemDao.getShopItem(shopItemId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getShopList(): LiveData<List<ShopItem>> =  MediatorLiveData<List<ShopItem>>().apply {
    //с помощью класса медиатор мы переватываем лайфдату и преобразовываем ее в лайфдату другого типа
        addSource(shopItemDao.getShopList()){
            value = mapper.mapListDbModelToListEntity(it)
        }
        //Метод из класса транформйшн.мап сделает тоже самое под капотом так же вызывается
    // Медиатор лайф дата ему передается ресурс для считывания и когда он получает значеие его можно преобразовать с помощью мапера
       // override fun getShopList(): LiveData<List<ShopItem>> = Transformations.map(shopItemDao.getShopList()){
         //   mapper.mapListDbModelToListEntity(it)
        }

    }





