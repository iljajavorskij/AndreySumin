package org.myapp.andreysumin.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.myapp.andreysumin.domain.ShopItem
@Entity(tableName = "shop_items")
data class ShopItemDbModel(
    @PrimaryKey(autoGenerate = true)//генерирует атоматически айди
    val id:Int,
    val name:String,
    val count:Int,
    val enable:Boolean
)
