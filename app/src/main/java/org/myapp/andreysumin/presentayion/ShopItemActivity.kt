package org.myapp.andreysumin.presentayion

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import org.myapp.andreysumin.R
import org.myapp.andreysumin.domain.ShopItem

class ShopItemActivity : AppCompatActivity() {

    private lateinit var viewModel: ShopItemViewModel

    private lateinit var tilName:TextInputLayout
    private lateinit var tilSize:TextInputLayout
    private lateinit var editName:EditText
    private lateinit var editSize:EditText
    private lateinit var buttonSave:Button

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parsIntent()
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        initViews()
        addChangeTextListeners()
        addRightMode()
        observeViewModel()
    }



    private fun launchEditMode(){
        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(this){
            editName.setText(it.name)
            editSize.setText(it.count.toString())
        }
        buttonSave.setOnClickListener {
            viewModel.editShopItem(editName.text?.toString(),editSize.text?.toString())
        }
    }
    private fun launchAddMode(){
        buttonSave.setOnClickListener {
            viewModel.addShopItem(editName.text?.toString(),editSize.text?.toString())
        }
    }

    private fun observeViewModel(){
        viewModel.errorInputName.observe(this){
            var message =  if (it) {
                getString(R.string.error_input_name)
            }else{
                null
            }
            tilName.error = message
        }


        viewModel.errorInputSize.observe(this){
            var message =  if (it) {
                getString(R.string.error_input_size)
            }else{
                null
            }
            tilSize.error = message
        }

        viewModel.closeScreen.observe(this){
            finish()
        }
    }

    private fun parsIntent(){
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)){
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT ){
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT ){
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)){
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    private fun initViews(){
        tilName = findViewById(R.id.till_name)
        tilSize = findViewById(R.id.till_size)
        editName = findViewById(R.id.et_name)
        editSize = findViewById(R.id.et_size)
        buttonSave = findViewById(R.id.save_button)
    }

    private fun addRightMode(){
        when(screenMode){
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun addChangeTextListeners(){
        editName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        editSize.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputSize()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val MODE_ADD = "mode add"
        private const val MODE_EDIT = "mode edit"
        private const val EXTRA_SHOP_ITEM_ID = "mode edit"
        private const val MODE_UNKNOWN = ""

        fun intentForAdd(context: Context): Intent{
            val intent = Intent(context,ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun intentForEdit(context: Context,shopItemId: Int): Intent{
            val intent = Intent(context,ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            return intent
        }




    }
}