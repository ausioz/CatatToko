package com.example.catattoko.recordgoods

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Build
import android.os.Build.VERSION
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.material3.AlertDialog
import com.example.catattoko.MainActivity
import com.example.catattoko.data.local.room.GoodsDao
import com.example.catattoko.data.local.room.GoodsDatabase
import com.example.catattoko.databinding.ActivityRecordBinding
import com.example.catattoko.model.GoodsEntity
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date


class RecordGoodsActivity : ComponentActivity(), RecordGoodsView {
    private lateinit var binding: ActivityRecordBinding
    private lateinit var goodsDatabase: GoodsDatabase

    private lateinit var recordGoodsPresenter: RecordGoodsPresenter

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nik = intent.getIntExtra(MainActivity.NIK, 99999999)
        val nama = intent.getStringExtra(MainActivity.NAMA)
        val tanggal = LocalDate.now().toString()

        binding.nik.setText(nik.toString())
        binding.nama.setText(nama)
        binding.tanggal.setText(tanggal)

        val previousData = getDataFromIntent()
        if (previousData != null) {
            setPreviousData(previousData)
        }

        binding.simpan.setOnClickListener {
            saveData(
                nik = binding.nik.text.toString().toInt(),
                nama = binding.nama.text.toString(),
                jumlahBarang = binding.jumlahBarang.text.toString(),
                pemasok = binding.pemasok.text.toString(),
                tanggal = binding.tanggal.text.toString()
            )
        }
        createAppDatabase()
        createPresenter()
    }

    private fun createAppDatabase() {
        goodsDatabase = GoodsDatabase.getInstance(this)
    }

    private fun createPresenter() {
        recordGoodsPresenter = RecordGoodsPresenter(goodsDatabase, this)
    }

    private fun getDataFromIntent(): GoodsEntity? {
        val userData = if (VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("goods", GoodsEntity::class.java)
        } else {
            intent.getParcelableExtra<GoodsEntity>("goods")
        }
        return userData
    }

    private fun setPreviousData(goods: GoodsEntity) {
        binding.nik.hint = goods.nik.toString()
        binding.nama.hint = goods.nama
        binding.jumlahBarang.setText(goods.jumlahBarang)
        binding.pemasok.setText(goods.pemasok)
        binding.tanggal.hint = goods.tanggal
    }

    private fun saveData(
        nik: Int,
        nama: String,
        jumlahBarang: String,
        pemasok: String,
        tanggal: String
    ) {

        val df: DateFormat = SimpleDateFormat(DATE_FORMAT)
        df.isLenient = false
        var lastDateRecord= goodsDatabase.goodsDao().getDateByOrder().toList().toString()
        Log.d("wkw",lastDateRecord)
        if (lastDateRecord == "[]"){
            val calendar = Calendar.getInstance()
            calendar.time = df.parse(tanggal)
            calendar.add(Calendar.DAY_OF_MONTH,-14)
            lastDateRecord = df.format(calendar.time)
        } else {var lastDateRecordList = goodsDatabase.goodsDao().getDateByOrder().toList()
        lastDateRecord =lastDateRecordList.first()}
        val parsedLastDate= df.parse(lastDateRecord)
        val parsedInputDate = df.parse(tanggal)

        val goods = GoodsEntity(
            nik = nik,
            nama = nama,
            jumlahBarang = jumlahBarang,
            pemasok = pemasok,
            tanggal = tanggal
        )

        if (nik != intent.getIntExtra(MainActivity.NIK, 99999999) ||
            nama != intent.getStringExtra(MainActivity.NAMA)
        ) {
            Toast.makeText(this, "NIK / Nama anda tidak sesuai dengan user", Toast.LENGTH_SHORT)
                .show()
        } else if (nik.toString() == "" || nama == "" || jumlahBarang.isEmpty() ||
            pemasok.isEmpty() || tanggal.isEmpty()
        ) {
            Toast.makeText(this, "Catatan harus semua terisi", Toast.LENGTH_SHORT)
                .show()
        } else if (isDateNotValid(tanggal)) {
            Toast.makeText(this, "Format tanggal salah", Toast.LENGTH_SHORT)
                .show()
        } else if (daysBetween(parsedLastDate, parsedInputDate) < 14) {
            AlertDialog.Builder(this)
                .setTitle("Permasalahan Tanggal")
                .setMessage("Tanggal input dengan catatan terakhir kurang dari 2 minggu")
                .setCancelable(false)
                .setPositiveButton("Lanjut Simpan Catatan") { _, _ -> recordGoodsPresenter.saveData(goods) }
                .setNegativeButton("Kembali") { _, _ -> }
                .show()
        } else {
            recordGoodsPresenter.saveData(goods)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun isDateNotValid(date: String): Boolean {
        return try {
            val df: DateFormat = SimpleDateFormat(DATE_FORMAT)
            df.isLenient = false
            df.parse(date)
            false
        } catch (e: ParseException) {
            true
        }
    }

    fun daysBetween(d1: Date, d2: Date): Int {
        return ((d2.time - d1.time) / (1000 * 60 * 60 * 24)).toInt()
    }

    override fun onSuccessRecord() {
        Toast.makeText(this, "Barang telah tercatat", Toast.LENGTH_SHORT).show()
        finish()
    }

    companion object {
        const val DATE_FORMAT = "yyyy-MM-dd"
    }
}