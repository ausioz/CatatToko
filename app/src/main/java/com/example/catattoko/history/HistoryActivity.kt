package com.example.catattoko.history

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catattoko.MainActivity
import com.example.catattoko.R
import com.example.catattoko.data.local.room.GoodsDatabase
import com.example.catattoko.databinding.ActivityHistoryBinding
import com.example.catattoko.model.GoodsEntity
import com.example.catattoko.recordgoods.RecordGoodsActivity
import java.time.LocalDate

class HistoryActivity : ComponentActivity(), ListView {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var goodsDatabase: GoodsDatabase
    private lateinit var listPresenter: ListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createGoodsDatabase()
        createPresenter()
        setupUserAdapter()
        listPresenter.getHistory()
    }

    override fun onResume() {
        super.onResume()
        listPresenter.getHistory()
    }


    private fun createPresenter() {
        listPresenter = ListPresenter(goodsDatabase, this)
    }

    private fun setupUserAdapter() {
        historyAdapter = HistoryAdapter(
            onClickUpdate = ::updateHistory,
            onClickDelete = listPresenter::deleteHistory
        )
        binding.recycleView.adapter = historyAdapter
        binding.recycleView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )
    }

    private fun createGoodsDatabase() {
        goodsDatabase = GoodsDatabase.getInstance(this)

    }

    override fun onReceiveHistory(goods: List<GoodsEntity>) {
        historyAdapter.setData(goods)
    }

    override fun onDeleteHistorySuccess() {
        listPresenter.getHistory()
        Toast.makeText(this, "Catatan dihapus", Toast.LENGTH_SHORT).show()
    }

    private fun updateHistory(goods: GoodsEntity) {
        val nik = intent.getIntExtra(MainActivity.NIK, 99999999)
        val nama = intent.getStringExtra(MainActivity.NAMA)


        val intent = Intent(applicationContext, RecordGoodsActivity::class.java)
        intent.putExtra(MainActivity.NAMA, nama)
        intent.putExtra(MainActivity.NIK, nik)

        if (nik == 6 || nik == 7) Toast.makeText(
            applicationContext,
            "User tidak memiliki akses",
            Toast.LENGTH_SHORT
        ).show()
        else startActivity(intent)
    }

}