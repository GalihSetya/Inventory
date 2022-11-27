package com.example.inventory.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

//Ubah definisi class menjadi interface dan beri anotasi dengan @Dao.
@Dao
interface ItemDao {

    //Tambahkan @Query dengan fungsi getItems():
    //Minta kueri SQLite untuk menampilkan semua kolom dari tabel item yang diurutkan dalam urutan menaik.
    //Minta getItems() menampilkan daftar entity Item sebagai Flow.
    @Query("SELECT * from item ORDER BY name ASC")
    fun getItems(): Flow<List<Item>>

    //Tambahkan anotasi @Query, sediakan kueri sebagai parameter string ke anotasi @Query.
    //Pilih semua kolom dari item
    //WHERE id cocok dengan argumen id. Perhatikan :id.
    @Query("SELECT * from item WHERE id = :id")
    //Di bawah anotasi @Query, tambahkan fungsi getItem() yang menggunakan argumen Int serta tampilkan Flow<Item>.
    fun getItem(id: Int): Flow<Item>

    //tambahkan fungsi insert() yang menggunakan instance class Entity item sebagai argumennya.
    //Argumen OnConflict memberi tahu Room apa yang harus dilakukan jika terjadi konflik. Strategi OnConflictStrategy.IGNORE mengabaikan item baru jika kunci utama tersebut sudah ada dalam database.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    //Entity yang diperbarui memiliki kunci yang sama dengan entity yang diteruskan.
    //Serupa dengan metode insert(), jadikan metode update() berikut sebagai suspend.
    @Update
    suspend fun update(item: Item)

    //Anotasi @Delete menghapus satu item, atau sebuah daftar item.
    @Delete
    suspend fun delete(item: Item)





}