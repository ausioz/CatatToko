package com.example.catattoko.history

import com.example.catattoko.model.GoodsEntity

interface ListView {

    fun onReceiveHistory(goods: List<GoodsEntity>)
    fun onDeleteHistorySuccess()



}