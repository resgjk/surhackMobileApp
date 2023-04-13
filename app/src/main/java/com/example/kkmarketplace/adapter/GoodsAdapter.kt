package com.example.kkmarketplace.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kkmarketplace.R
import com.example.kkmarketplace.databinding.ListItemBinding
import com.example.kkmarketplace.goods.Goods
import com.squareup.picasso.Picasso

class GoodsAdapter : ListAdapter<Goods, GoodsAdapter.Holder>(Comparator()) {
    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemBinding.bind(view)

        fun bind(goods: Goods) = with(binding) {
            Picasso.get().load("https://juicy-succinct-battery.glitch.me/api/photo/${goods.id.toString()}")
                .into(goodsImg)
            title.setText(goods.title)
            description.setText(goods.short_description)
            price.setText(goods.price + " рублей")
            category.setText("Категория: ${goods.category}")
            social.setText(
                "Социальная сеть: ${goods.social_salesman}\nНомер телефона: ${goods.phonenumber_salesman}"
            )
        }

    }

    class Comparator : DiffUtil.ItemCallback<Goods>() {
        override fun areItemsTheSame(oldItem: Goods, newItem: Goods): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Goods, newItem: Goods): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }
}