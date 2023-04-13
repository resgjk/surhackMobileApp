package com.example.kkmarketplace

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kkmarketplace.adapter.GoodsAdapter
import com.example.kkmarketplace.databinding.ActivitySearchViewBinding
import com.example.kkmarketplace.goods.GoodsApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchView : AppCompatActivity() {
    lateinit var viewBinding: ActivitySearchViewBinding
    private lateinit var adapter: GoodsAdapter

    val connect = Retrofit.Builder().baseUrl("https://juicy-succinct-battery.glitch.me/api/")
        .addConverterFactory(GsonConverterFactory.create()).build()
    val goodsAPI = connect.create(GoodsApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySearchViewBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        adapter = GoodsAdapter()
        viewBinding.rcView.layoutManager = LinearLayoutManager(this)
        viewBinding.rcView.adapter = adapter

        val search = intent.getStringExtra("search").toString()

        CoroutineScope(Dispatchers.IO).launch {
            val list = goodsAPI.getGoodsBySearch(search)
            runOnUiThread {
                viewBinding.apply {
                    adapter.submitList(list.goods)
                }
            }
        }
    }
}