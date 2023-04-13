package com.example.kkmarketplace

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kkmarketplace.databinding.ActivityCategoriesBinding

class CategoriesActivity : AppCompatActivity() {

    lateinit var viewBinding: ActivityCategoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityCategoriesBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.igrushki.setOnClickListener { viewCategoryOpen("Игрушки") }
        viewBinding.ukrasheniya.setOnClickListener { viewCategoryOpen("Украшения") }
        viewBinding.krasota.setOnClickListener { viewCategoryOpen("Красота") }
        viewBinding.interier.setOnClickListener { viewCategoryOpen("Интерьер") }
        viewBinding.sladosti.setOnClickListener { viewCategoryOpen("Сладости") }
        viewBinding.stil.setOnClickListener { viewCategoryOpen("Стиль") }
        viewBinding.tvorchestvo.setOnClickListener { viewCategoryOpen("Творчество") }
    }

    fun viewCategoryOpen(cat: String) {
        val newIntent = Intent(this, CategoryView::class.java)
        newIntent.putExtra("type", cat)
        startActivity(newIntent)
    }
}