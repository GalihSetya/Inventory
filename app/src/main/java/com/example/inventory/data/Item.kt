package com.example.inventory.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Deklarasi id dari jenis Int, itemName dari jenis String, itemPrice dari jenis Double, dan quantityInStock dari jenis Int sebagai parameter untuk konstruktor utama.
//class Item

//Konversikan class Item ke class data dengan mengawali definisi class-nya dengan kata kunci data.
//Di atas deklarasi class Item, beri anotasi pada class data dengan @Entity. Gunakan argumen tableName untuk memberikan item sebagai nama tabel SQLite.
@Entity(tableName = "item")
data class Item(
    //Untuk mengidentifikasi id sebagai kunci utama, beri anotasi pada properti id dengan @PrimaryKey. Setel parameter autoGenerate ke true sehingga Room menghasilkan ID untuk setiap entity. Hal ini menjamin bahwa ID untuk setiap item bersifat unik.
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    //Anotasi ColumnInfo digunakan untuk menyesuaikan kolom yang terkait dengan bidang tertentu.
    @ColumnInfo(name = "name")
    val itemName: String,
    @ColumnInfo(name = "price")
    val itemPrice: Double,
    @ColumnInfo(name = "quantity")
    val quantityInStock: Int

)