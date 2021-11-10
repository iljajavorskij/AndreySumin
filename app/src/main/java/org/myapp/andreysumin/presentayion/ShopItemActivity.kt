package org.myapp.andreysumin.presentayion

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.myapp.andreysumin.R
import org.myapp.andreysumin.domain.ShopItem


class ShopItemActivity : AppCompatActivity() {

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parsIntent()
        if (savedInstanceState == null){
            addRightMode()
        }


    }


private fun parsIntent(){
    if (!intent.hasExtra(EXTRA_SCREEN_MODE)){
        throw RuntimeException("Param screen mode is absent")
    }
    val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
    if (mode != MODE_ADD && mode != MODE_EDIT){
        throw RuntimeException("Param screen mode")
    }
    screenMode = mode

    if (screenMode == MODE_EDIT){
        if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)){
            throw RuntimeException("Param screen")
        }
        shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID,ShopItem.UNDEFINED_ID)
    }
}

    private fun addRightMode(){
      val fragment = when(screenMode){
            MODE_EDIT -> ShopItemFragment.newInstansEditItem(shopItemId)
            MODE_ADD -> ShopItemFragment.newInstansAddItem()
          else -> throw RuntimeException("unkown screen mode ")
        }
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.shop_item_container,fragment)
            .commit()
    }


    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val MODE_ADD = "mode add"
        private const val MODE_EDIT = "mode edit"
        private const val EXTRA_SHOP_ITEM_ID = "mode edit"
        private const val MODE_UNKNOWN = ""



        fun intentForAdd(context: Context): Intent {
            val intent = Intent(context,ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun intentForEdit(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context,ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            return intent
        }

    }


    }
