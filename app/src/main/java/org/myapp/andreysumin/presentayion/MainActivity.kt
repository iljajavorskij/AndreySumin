package org.myapp.andreysumin.presentayion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import org.myapp.andreysumin.R
import org.myapp.andreysumin.domain.ShopItem

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    lateinit var llList:LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        llList = findViewById(R.id.llList)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this){
            showList(it)
        }



    }
    fun showList(list:List<ShopItem>){
        llList.removeAllViews()
        for (shopItem in list){
            val layout = if (shopItem.enable){
                R.layout.item_card_unenable
            }else{
                R.layout.item_card_enabled
            }
            val view = LayoutInflater.from(this).inflate(layout,llList,false)
            val tvName = findViewById<TextView>(R.id.name)
            val tvCount = findViewById<TextView>(R.id.size)
            tvName.text = shopItem.name
            tvCount.text = shopItem.count.toString()
            view.setOnLongClickListener {
                viewModel.changeEnableState(shopItem)
                true
            }
            llList.addView(view)
        }
    }
}