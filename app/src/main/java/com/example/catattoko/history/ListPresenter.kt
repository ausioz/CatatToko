package com.example.catattoko.history

import com.example.catattoko.data.local.room.GoodsDao
import com.example.catattoko.data.local.room.GoodsDatabase
import com.example.catattoko.model.GoodsEntity

class ListPresenter(
    private val goodsDatabase: GoodsDatabase,
    private val listView: ListView,
) {

    fun getHistory() {
        listView.onReceiveHistory(
            goods = goodsDatabase.goodsDao().getGoodsHistory()
        )
    }

    fun deleteHistory(goods: GoodsEntity) {
        goodsDatabase.goodsDao().deleteRecord(goods)
        listView.onDeleteHistorySuccess()

    }

}