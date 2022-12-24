package com.example.shoppinglist.ui.shoppingList

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import com.example.shoppinglist.R
import com.example.shoppinglist.data.db.entities.ShoppingItem

class AddShoppingItemDialog(context: ShoppingActivity, var addDialogListener: AddDialogListener): AppCompatDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add_shopping_item)

        findViewById<TextView>(R.id.tvAdd)?.setOnClickListener {
            val name = findViewById<TextView>(R.id.etName)?.text.toString()
            val amount = findViewById<TextView>(R.id.etAmount)?.text.toString()
            if(name.isEmpty() || amount.isEmpty()){
                Toast.makeText(context,"Name or Amount can not be empty",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val item = ShoppingItem(name,amount.toInt())
            addDialogListener.onAddButtonClick(item)
            dismiss()
        }

        findViewById<TextView>(R.id.tvCancel)?.setOnClickListener {
            cancel()
        }
    }
}