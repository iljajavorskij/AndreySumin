package org.myapp.andreysumin.presentayion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.myapp.andreysumin.data.ShopListRepositoryImpl
import org.myapp.andreysumin.domain.AddShopItemUseCase
import org.myapp.andreysumin.domain.EditShopItemUseCase
import org.myapp.andreysumin.domain.GetShopItemUseCase
import org.myapp.andreysumin.domain.ShopItem
import java.lang.Exception

class ShopItemViewModel:ViewModel() {
    val repository = ShopListRepositoryImpl

    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName


    private val _errorInputSize = MutableLiveData<Boolean>()
    val errorInputSize: LiveData<Boolean>
        get() = _errorInputSize

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _closeScreen = MutableLiveData<Unit>()
    val  closeScreen: LiveData<Unit>
        get() = _closeScreen




    fun getShopItem(shopItemId: Int){
        val item = getShopItemUseCase.getShopItem(shopItemId)
        _shopItem.value = item
    }

    fun addShopItem(inputName: String?,inputSize: String?){
        val name = parsName(inputName)
        val size = parsSize(inputSize)
        val fieldsValid = validateInput(name,size)
        if (fieldsValid) {
            val shopItem = ShopItem(name,size,true)
            addShopItemUseCase.addShopListItem(shopItem)
            finishWork()
        }

    }

    fun editShopItem(inputName: String?,inputSize: String?){
        val name = parsName(inputName)
        val size = parsSize(inputSize)
        val fieldsValid = validateInput(name,size)
        if (fieldsValid) {
            _shopItem.value?.let {
                val item = it.copy(name = name,count = size)
                editShopItemUseCase.editShopItem(item)
                finishWork()
            }

        }
    }

    private fun parsName (inputName:String?): String{
        return inputName?.trim() ?: ""
    }

    private fun parsSize(inputSize: String?) :Int{
        return try {
            inputSize?.trim()?.toInt() ?: 0
        }catch (e :Exception){
            0
        }
    }

    private fun validateInput(name: String,size: Int): Boolean {
        var result = true
        if (name.isBlank()){
           _errorInputName.value = true
            result = false
        }
        if (size <= 0){
            _errorInputSize.value = true
            result = false
        }
        return result
    }




    fun resetErrorInputName(){
        _errorInputName.value = false
    }

    fun resetErrorInputSize(){
        _errorInputSize.value = false
    }

    private fun finishWork(){
        _closeScreen.value = Unit
    }


}