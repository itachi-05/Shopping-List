package com.example.shoppinglist.ui.shoppingList

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.data.db.ShoppingDatabase
import com.example.shoppinglist.data.db.entities.ShoppingItem
import com.example.shoppinglist.data.repository.ShoppingRepository
import com.example.shoppinglist.databinding.ActivityShoppingBinding
import com.example.shoppinglist.other.ShoppingItemAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider

class ShoppingActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()
    private val factory: ShoppingViewModelFactory by instance()

    private val viewModel: SplashScreenVM by viewModels()

    private lateinit var binding: ActivityShoppingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition{
                viewModel.isLoading.value
            }
        }
        binding = ActivityShoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val viewModel = ViewModelProvider(this, factory)[ShoppingViewModel::class.java]


        val adapter = ShoppingItemAdapter(listOf(), viewModel)

        val recyclerView = findViewById<RecyclerView>(R.id.rvShoppingItems)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        viewModel.getAllShoppingItems().observe(this, Observer {
            adapter.items = it
            adapter.notifyDataSetChanged()
        })

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener{
            AddShoppingItemDialog(this,
            object: AddDialogListener{
                override fun onAddButtonClick(item: ShoppingItem) {
                    viewModel.upsert(item)
                }
            }).show()
        }
    }
}