package com.example.catattoko.recordgoods

import android.util.Log
import com.example.catattoko.data.local.room.GoodsDao
import com.example.catattoko.data.local.room.GoodsDatabase
import com.example.catattoko.model.GoodsEntity

class RecordGoodsPresenter(
    private val goodsDatabase: GoodsDatabase,
    private val recordGoodsView: RecordGoodsView,
) {
    fun saveData(goods: GoodsEntity) {
        goodsDatabase.goodsDao().insertRecord(goods)
        recordGoodsView.onSuccessRecord()
    }
}