package com.example.catattoko.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Update
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.catattoko.model.GoodsEntity
import java.util.ArrayList

@Dao
interface GoodsDao {

    @Query("SELECT * FROM goods")
    fun getGoodsHistory():List<GoodsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecord(goods: GoodsEntity)

    @Delete
    fun deleteRecord(goods: GoodsEntity)

    @RawQuery
    fun rawQuery(theQuery: SimpleSQLiteQuery): List<String>
    fun getDateByOrder(): List<String> {
        return rawQuery(SimpleSQLiteQuery("SELECT tanggal FROM goods ORDER BY tanggal DESC"))
    }
}

