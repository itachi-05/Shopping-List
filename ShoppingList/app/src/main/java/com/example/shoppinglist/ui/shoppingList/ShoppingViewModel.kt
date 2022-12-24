package com.example.shoppinglist.ui.shoppingList

import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.db.entities.ShoppingItem
import com.example.shoppinglist.data.repository.ShoppingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShoppingViewModel(private val repo: ShoppingRepository): ViewModel() {
    fun upsert(item: ShoppingItem) = CoroutineScope(Dispatchers.Main).launch {
        repo.upsert(item)
    }

    fun delete(item: ShoppingItem) = CoroutineScope(Dispatchers.Main).launch {
        repo.delete(item)
    }

    // no coroutines required for read operations
    fun getAllShoppingItems() = repo.getShoppingItems()
}