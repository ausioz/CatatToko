package com.example.catattoko.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.catattoko.model.GoodsEntity

@Database(
    entities =[GoodsEntity::class],
    version = 1
)

abstract class GoodsDatabase: RoomDatabase() {

    abstract fun goodsDao():GoodsDao

    companion object{
        private var INSTANCE:GoodsDatabase? = null
        fun getInstance (context: Context) : GoodsDatabase{
            if (INSTANCE==null){
                synchronized(GoodsDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                        context,
                        GoodsDatabase::class.java,"goods.db",
                    )
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE!!
        }

    }

}