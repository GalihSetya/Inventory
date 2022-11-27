package com.example.inventory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//uat class ItemRoomDatabase sebagai class abstract yang memperluas RoomDatabase. Anotasikan class dengan @Database.
//Anotasi @Database memerlukan beberapa argumen, sehingga Room dapat membuat database.
//Tentukan Item sebagai satu-satunya class dengan daftar entities.
//Setel version sebagai 1. Setiap kali mengubah skema tabel database.
//Setel exportSchema ke false agar tidak menyimpan cadangan histori versi skema.
@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class ItemRoomDatabase : RoomDatabase() {

    //Database harus mengetahui DAO. Di dalam isi class, deklarasikan fungsi abstrak yang menampilkan ItemDao.
    abstract fun itemDao(): ItemDao

    //tentukan objek companion. Objek pendamping memungkinkan akses ke metode untuk membuat atau mendapatkan database dengan menggunakan nama class sebagai penentu.
    companion object {

        //Anotasikan INSTANCE dengan @Volatile. Nilai variabel yang tidak stabil tidak akan disimpan dalam cache, dan semua penulisan dan pembacaan akan dilakukan ke dan dari memori utama. Hal tersebut akan membantu memastikan agar nilai INSTANCE selalu terbaru dan sama untuk semua thread eksekusi.
        @Volatile
        private var INSTANCE: ItemRoomDatabase? = null

        //tentukan metode getDatabase() dengan parameter Context yang diperlukan builder database. Tampilkan jenis ItemRoomDatabase.
        fun getDatabase(context: Context): ItemRoomDatabase {

            //jika INSTANCE adalah null, lakukan inisialisasi di dalam blok synchronized{}. Gunakan operator elvis (?:) untuk melakukannya. Teruskan this objek pendamping yang ingin dikunci di dalam blok fungsi.
            return INSTANCE ?: synchronized(this) {
                //buat variabel instance val, dan gunakan builder database untuk mendapatkan database.
               //Teruskan konteks aplikasi, class database, serta nama untuk database, item_database ke Room.databaseBuilder().
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ItemRoomDatabase::class.java,
                    "item_database"
                )
                    //Tambahkan strategi migrasi yang diperlukan ke builder.
                    .fallbackToDestructiveMigration()
                     //Untuk membuat instance database, panggil .build().
                    .build()
                //tetapkan INSTANCE = instance.
                INSTANCE = instance
                //tampilkan instance.
                instance
            }
        }
    }
}