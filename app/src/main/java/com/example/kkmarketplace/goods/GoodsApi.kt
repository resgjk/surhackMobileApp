package com.example.kkmarketplace.goods

import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface GoodsApi {
    @GET("goods/search/{search}")
    suspend fun getGoodsBySearch(@Path("search") search: String): GoodsList

    @GET("goods/{id}")
    suspend fun getOneGoodsById(@Path("id") id: Int): Goods

    @GET("goods/catrgorieres/{cat}")
    suspend fun getGoodsByCat(@Path("cat") cat: String): GoodsList

    @GET("goods/salesman_id/{salesman_id}")
    suspend fun getGoodsListBySalId(@Path("salesman_id") salesman_id: Int): GoodsList

    @GET("goods")
    suspend fun getAllGoods(): GoodsList

    @DELETE("goods/{goods_id}")
    suspend fun delGoodsById(@Path("goods_id") goods_id: Int): Goods
}