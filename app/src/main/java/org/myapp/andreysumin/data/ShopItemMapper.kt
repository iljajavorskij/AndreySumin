package org.myapp.andreysumin.data

import org.myapp.andreysumin.domain.ShopItem

class ShopItemMapper {
    fun mapEntityToDbModel(shopItem :ShopItem) = ShopItemDbModel(
        id = shopItem.id,
        name = shopItem.name,
        count = shopItem.count,
        enable = shopItem.enable
    )

    fun mapDbModelToEntity(shopItemDbModel: ShopItemDbModel) = ShopItem(
        id = shopItemDbModel.id,
        name = shopItemDbModel.name,
        count = shopItemDbModel.count,
        enable = shopItemDbModel .enable
    )

    fun mapListDbModelToListEntity(list:List<ShopItemDbModel>) = list.map {
        mapDbModelToEntity(it)
    }
}