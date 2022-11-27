package com.example.inventory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.Item
import com.example.inventory.data.ItemDao
import kotlinx.coroutines.launch

//Perluas class InventoryViewModel dari class ViewModel. Teruskan objek ItemDao
//
class InventoryViewModel(private val itemDao: ItemDao) : ViewModel() {

    //tambahkan fungsi publik bernama addNewItem() yang menggunakan tiga string untuk detail item. Teruskan string detail item ke fungsi getNewItemEntry() dan tetapkan nilai yang ditampilkan ke val yang bernama newItem. Lakukan panggilan ke insertItem() dengan meneruskan newItem untuk menambahkan entity baru ke database. Fungsi ini akan dipanggil dari fragmen UI untuk menambahkan detail Item ke database.
    fun addNewItem(itemName: String, itemPrice: String, itemCount: String) {
        val newItem = getNewItemEntry(itemName, itemPrice, itemCount)
        insertItem(newItem)
    }

    //tambahkan fungsi private yang disebut insertItem() yang mengambil objek Item dan menambahkan data ke database dengan cara yang tidak memblokir.
    private fun insertItem(item: Item) {
        //gunakan viewModelScope.launch untuk memulai coroutine dalam ViewModelScope. Di dalam fungsi peluncuran, panggil fungsi penangguhan insert() pada itemDao yang meneruskan item. ViewModelScope adalah properti ekstensi ke class ViewModel yang otomatis membatalkan coroutine turunannya ketika ViewModel dihancurkan.
        viewModelScope.launch {
            itemDao.insert(item)
        }
    }

    /**
     * Returns true if the EditTexts are not empty
     */
    fun isEntryValid(itemName: String, itemPrice: String, itemCount: String): Boolean {
        if (itemName.isBlank() || itemPrice.isBlank() || itemCount.isBlank()) {
            return false
        }
        return true
    }

  //tambahkan fungsi pribadi lainnya yang menggunakan tiga string dan menampilkan instance Item.
    private fun getNewItemEntry(itemName: String, itemPrice: String, itemCount: String): Item {
        return Item(
            itemName = itemName,
            itemPrice = itemPrice.toDouble(),
            quantityInStock = itemCount.toInt()
        )
    }
}


//tambahkan class InventoryViewModelFactory untuk membuat instance InventoryViewModel. Teruskan parameter konstruktor yang sama sebagai InventoryViewModel yang merupakan instance ItemDao. Perluas class dari class ViewModelProvider.Factory.
class InventoryViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {

    //mengganti metode create() di dalam class ViewModelProvider.Factory sebagai berikut, yang mengambil jenis class apa pun sebagai argumen dan menampilkan objek ViewModel.
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
       //Implementasikan metode create(). Periksa apakah modelClass sama dengan class InventoryViewModel dan tampilkan instance-nya. Jika tidak, berikan pengecualian.
        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InventoryViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
