package com.example.catattoko.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.catattoko.databinding.ItemHistoryBinding
import com.example.catattoko.model.GoodsEntity
import kotlin.reflect.KSuspendFunction1

class HistoryAdapter(
    private val onClickDelete: (GoodsEntity)->Unit,
    private val onClickUpdate: (GoodsEntity)->Unit
):RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private val goods = arrayListOf<GoodsEntity>()

    inner class ViewHolder(
        private  val binding: ItemHistoryBinding,)
        :RecyclerView.ViewHolder(binding.root){
        fun bind(goodsEntity: GoodsEntity){
            binding.id.text = "Id Catatan: "+ goodsEntity.id.toString()
            binding.nik.text = "NIK: "+ goodsEntity.nik
            binding.nama.text = "Nama: "+goodsEntity.nama
            binding.jumlahBarang.text = "Jumlah Barang: "+goodsEntity.jumlahBarang
            binding.pemasok.text = "Pemasok: "+goodsEntity.pemasok
            binding.tanggal.text = "Tanggal: "+goodsEntity.tanggal
            binding.edit.setOnClickListener { onClickUpdate(goodsEntity) }
            binding.delete.setOnClickListener { onClickDelete(goodsEntity) }
        }

        }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun getItemCount(): Int {
        return goods.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(goods[position])
    }


    fun setData(users:List<GoodsEntity>){
        this.goods.clear()
        this.goods.addAll(users)
        notifyDataSetChanged()
    }

}