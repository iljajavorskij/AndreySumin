package org.myapp.andreysumin.presentayion


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.myapp.andreysumin.R



class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    lateinit var shopItemAdapter: ShopItemAdapter

    private var shopItemContainer:FragmentContainerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        shopItemContainer = findViewById(R.id.shop_item_container)
        recycler()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this){
            shopItemAdapter.submitList(it)
        }

        val button_add = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        button_add.setOnClickListener {

            if(isOnePaneMode()){
                val intent = ShopItemActivity.intentForAdd(this)
                startActivity(intent)
            }else{
                launchFragment(ShopItemFragment.newInstansAddItem())
            }
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

    private fun isOnePaneMode() :Boolean{
        return shopItemContainer == null
    }

    private fun launchFragment(fragment:Fragment){
        supportFragmentManager.popBackStack()//удаляет из стека предыдущий фрагмент ,а если его нет ничего не делает
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.shop_item_container,fragment)
            .addToBackStack(null)
            .commit()
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
            if (isOnePaneMode()){
                val intent = ShopItemActivity.intentForEdit(this, it.id)
                startActivity(intent)
            }else{
                launchFragment(ShopItemFragment.newInstansEditItem(it.id))
            }
        }
    }

    private fun setupLongClick() {
        shopItemAdapter.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }


}