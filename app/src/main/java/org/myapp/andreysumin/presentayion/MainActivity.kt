package org.myapp.andreysumin.presentayion


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.myapp.andreysumin.R



class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    lateinit var shopItemAdapter: ShopItemAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this){
            shopItemAdapter.submitList(it)
        }

        val button_add = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        button_add.setOnClickListener {
            val intent = ShopItemActivity.intentForAdd(this)
            startActivity(intent)
        }
    }

    fun recycler(){
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        with(recyclerView){
            shopItemAdapter = ShopItemAdapter()
            adapter = shopItemAdapter
            recycledViewPool.setMaxRecycledViews(ShopItemAdapter.VIEW_TYPE_ENABLED,
                ShopItemAdapter.MAX_POOL_ENABLE)
            recycledViewPool.setMaxRecycledViews(ShopItemAdapter.VIEW_TYPE_DISABLED,
                ShopItemAdapter.MAX_POOL_ENABLE)
        }
        setupLongClick()
        setupClick()
        setupSwipe(recyclerView)
    }

    private fun setupSwipe(recyclerView: RecyclerView?) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = shopItemAdapter.currentList[viewHolder.adapterPosition]
                viewModel.removeListItem(item)
            }
        }
        var itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun setupClick() {
        shopItemAdapter.onShopItemClickListener = {
            Log.d("MainActivity", it.toString())
            val intent = ShopItemActivity.intentForEdit(this, it.id)
            startActivity(intent)
        }
    }

    private fun setupLongClick() {
        shopItemAdapter.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }


}