package com.example.kkmarketplace

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kkmarketplace.adapter.GoodsAdapter
import com.example.kkmarketplace.databinding.ActivityMainBinding
import com.example.kkmarketplace.goods.GoodsApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: GoodsAdapter
    private lateinit var viewBinding: ActivityMainBinding

    val connect = Retrofit.Builder().baseUrl("https://juicy-succinct-battery.glitch.me/api/")
        .addConverterFactory(GsonConverterFactory.create()).build()
    val goodsAPI = connect.create(GoodsApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        adapter = GoodsAdapter()
        viewBinding.rcView.layoutManager = LinearLayoutManager(this)
        viewBinding.rcView.adapter = adapter

        val userEmail = intent.getStringExtra("userEmail")
        val userPassword = intent.getStringExtra("userPassword")
        val userId = intent.getStringExtra("userId")

        viewBinding.categoriesBtn.setOnClickListener {
            startActivity(Intent(this, CategoriesActivity::class.java))
        }

        viewBinding.searchBtn.setOnClickListener {
            val newIntent = Intent(this, SearchView::class.java)
            newIntent.putExtra("search", viewBinding.searchEdit.text.toString())
            startActivity(newIntent)
        }

        CoroutineScope(Dispatchers.IO).launch {
            val list = goodsAPI.getAllGoods()
            runOnUiThread {
                viewBinding.apply {
                    adapter.submitList(list.goods)
                }
            }
        }
    }
}