package org.myapp.andreysumin.presentayion

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import org.myapp.andreysumin.R
import org.myapp.andreysumin.databinding.FragmentShopItemBinding
import org.myapp.andreysumin.domain.ShopItem


class ShopItemFragment() : Fragment() {

    private lateinit var viewModel: ShopItemViewModel
    lateinit var onEditingFinishedListener: onEditingFinishedListener

    private var _mBinding:FragmentShopItemBinding? = null
    private val mBinding:FragmentShopItemBinding
    get() = _mBinding ?: throw RuntimeException("FragmentShopItemBinding == null")


    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onAttach(context: Context) {
        Log.d("ShopItemFragment","onAttach")
        super.onAttach(context)
        if (context is onEditingFinishedListener){
            onEditingFinishedListener = context
        }else{
            throw java.lang.RuntimeException("Activity must implement onEditingFinishedListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ShopItemFragment","onCreate")
        parsParams()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d("ShopItemFragment","onCreateView")
        _mBinding = FragmentShopItemBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("ShopItemFragment","onViewCreated")
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        addChangeTextListeners()
        addRightMode()
        observeViewModel()
    }

    override fun onStart() {
        super.onStart()
        Log.d("ShopItemFragment","onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("ShopItemFragment","onResume")

    }

    override fun onPause() {
        super.onPause()
        Log.d("ShopItemFragment","onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("ShopItemFragment","onStop")

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _mBinding = null

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ShopItemFragment","onDestroy")

    }

    override fun onDetach() {
        super.onDetach()
        Log.d("ShopItemFragment","onDetach")

    }

    private fun launchEditMode(){
        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(viewLifecycleOwner){
            mBinding.etName.setText(it.name)
            mBinding.etSize.setText(it.count.toString())
        }
        mBinding.saveButton.setOnClickListener {
            viewModel.editShopItem(mBinding.etName.text?.toString(),mBinding.etSize.text?.toString())
        }
    }
    private fun launchAddMode(){
        mBinding.saveButton.setOnClickListener {
            viewModel.addShopItem(mBinding.etName.text?.toString(),mBinding.etSize.text?.toString())
        }
    }

    private fun observeViewModel(){
        viewModel.errorInputName.observe(viewLifecycleOwner){
            var message =  if (it) {
                getString(R.string.error_input_name)
            }else{
                null
            }
            mBinding.tillName.error = message
        }


        viewModel.errorInputSize.observe(viewLifecycleOwner){
            var message =  if (it) {
                getString(R.string.error_input_size)
            }else{
                null
            }
            mBinding.tillSize.error = message
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

    private fun addRightMode(){
        when(screenMode){
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun addChangeTextListeners(){
        mBinding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        mBinding.etSize.addTextChangedListener(object : TextWatcher {
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