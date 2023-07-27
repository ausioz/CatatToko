package com.example.catattoko

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.ui.res.colorResource
import com.example.catattoko.databinding.ActivityMainBinding
import com.example.catattoko.history.HistoryActivity
import com.example.catattoko.recordgoods.RecordGoodsActivity

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val arrayListUser = arrayListOf("Adi","Budi","Candra","Dila", "Erni","Fani", "Gina"
        )
        val arrayAdapter = ArrayAdapter(applicationContext,R.layout.textview_teal,arrayListUser)
        binding.usernameSp.adapter =arrayAdapter

        binding.usernameSp.onItemSelectedListener= object :AdapterView.OnItemSelectedListener{
            @SuppressLint("ResourceType")
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {


                if(position==5 || position==6){
                    binding.recordGoods.setBackgroundColor(getColor(R.color.white))
                    binding.recordGoods.setOnClickListener {
                        Toast.makeText(applicationContext, "User tidak memiliki akses", Toast.LENGTH_SHORT).show()
                    }

                }
                else{
                    binding.recordGoods.setBackgroundColor(getColor(R.color.teal_200))
                    binding.recordGoods.setOnClickListener {
                        val intent = Intent(applicationContext,RecordGoodsActivity::class.java)
                        intent.putExtra(NAMA,arrayListUser[position])
                        intent.putExtra(NIK,position+1)
                        startActivity(intent)
                    }
                    binding.history.setOnClickListener {
                        val intent = Intent(applicationContext,HistoryActivity::class.java)
                        intent.putExtra(NAMA,arrayListUser[position])
                        intent.putExtra(NIK,position+1)
                        startActivity(intent)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
        binding.logout.setOnClickListener { finish() }
    }
    companion object {
        const val NAMA = "nama"
        const val NIK = "nik"
    }
}
