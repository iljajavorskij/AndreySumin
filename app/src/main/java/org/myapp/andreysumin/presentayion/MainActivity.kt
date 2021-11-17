package org.myapp.andreysumin.presentayion


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.myapp.andreysumin.R
import org.myapp.andreysumin.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(),ShopItemFragment.Companion.onEditingFinishedListener{

    private lateinit var viewModel: MainViewModel
    lateinit var shopItemAdapter: ShopItemAdapter
    lateinit var mBinding:ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        recycler()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this){
            shopItemAdapter.submitList(it)
        }

        mBinding.floatingActionButton.setOnClickListener {

            if(isOnePaneMode()){
                val intent = ShopItemActivity.intentForAdd(this)
                startActivity(intent)
            }else{
                launchFragment(ShopItemFragment.newInstansAddItem())
            }
        }
    }

    fun recycler(){
        with(mBinding.recyclerview){
            shopItemAdapter = ShopItemAdapter()
            adapter = shopItemAdapter
            recycledViewPool.setMaxRecycledViews(ShopItemAdapter.VIEW_TYPE_ENABLED,
                ShopItemAdapter.MAX_POOL_ENABLE)
            recycledViewPool.setMaxRecycledViews(ShopItemAdapter.VIEW_TYPE_DISABLED,
                ShopItemAdapter.MAX_POOL_ENABLE)
        }
        setupLongClick()
        setupClick()
        setupSwipe(mBinding.recyclerview )
    }

    private fun isOnePaneMode() :Boolean{
        return mBinding.shopItemContainer == null
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

    override fun onEditingFinished() {
        Toast.makeText(this,"Success", Toast.LENGTH_LONG).show()
        supportFragmentManager.popBackStack()
    }


}