package org.myapp.andreysumin.presentayion

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import org.myapp.andreysumin.R
import org.myapp.andreysumin.domain.ShopItem


class ShopItemFragment() : Fragment() {

    private lateinit var viewModel: ShopItemViewModel
    lateinit var onEditingFinishedListener: onEditingFinishedListener

    private lateinit var tilName: TextInputLayout
    private lateinit var tilSize: TextInputLayout
    private lateinit var editName: EditText
    private lateinit var editSize: EditText
    private lateinit var buttonSave: Button

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is onEditingFinishedListener){
            onEditingFinishedListener = context
        }else{
            throw java.lang.RuntimeException("Activity must implement onEditingFinishedListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parsParams()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        initViews(view)
        addChangeTextListeners()
        addRightMode()
        observeViewModel()
    }

    private fun launchEditMode(){
        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(viewLifecycleOwner){
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
        viewModel.errorInputName.observe(viewLifecycleOwner){
            var message =  if (it) {
                getString(R.string.error_input_name)
            }else{
                null
            }
            tilName.error = message
        }


        viewModel.errorInputSize.observe(viewLifecycleOwner){
            var message =  if (it) {
                getString(R.string.error_input_size)
            }else{
                null
            }
            tilSize.error = message
        }

        viewModel.closeScreen.observe(viewLifecycleOwner){
            onEditingFinishedListener.onEditingFinished()// не проверяет под капотом на ноль и нужно обрабатывать в ручную
           // requireActivity().onBackPressed() проверяет под капотом на ноль и приложения крашится
        }
    }



    private fun parsParams(){
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)){
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = args.getString(SCREEN_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT){
            throw RuntimeException("Param screen mode $mode")
        }
        screenMode = mode

        if (screenMode == MODE_EDIT){
            if (!args.containsKey(SHOP_ITEM_ID)){
                throw RuntimeException("Param screen")
            }
            shopItemId = args.getInt(SHOP_ITEM_ID,ShopItem.UNDEFINED_ID)
        }
    }

    private fun initViews(view: View){
        tilName = view.findViewById(R.id.till_name)
        tilSize = view.findViewById(R.id.till_size)
        editName = view.findViewById(R.id.et_name)
        editSize = view.findViewById(R.id.et_size)
        buttonSave = view.findViewById(R.id.save_button)
    }

    private fun addRightMode(){
        when(screenMode){
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun addChangeTextListeners(){
        editName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        editSize.addTextChangedListener(object : TextWatcher {
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
        private const val SCREEN_MODE = "extra_mode"
        private const val MODE_ADD = "mode add"
        private const val MODE_EDIT = "mode edit"
        private const val SHOP_ITEM_ID = "mode edit"
        private const val MODE_UNKNOWN = ""


        fun newInstansEditItem(shopItemId: Int):ShopItemFragment{
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(SHOP_ITEM_ID,shopItemId)
                }
            }
        }

        fun newInstansAddItem():ShopItemFragment{
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        interface onEditingFinishedListener{
            fun onEditingFinished()
        }




}

}