/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.inventory

import android.app.Application
import com.example.inventory.data.ItemRoomDatabase

//buat val bernama database dari jenis ItemRoomDatabase. Buat instance database dengan memanggil getDatabase() saat ItemRoomDatabase meneruskan dalam konteks. Gunakan delegasi lazy sehingga instance database akan lambat dibuat saat pertama kali memerlukan/mengakses referensi (bukan saat aplikasi dimulai). Tindakan ini akan membuat database (database fisik pada disk) pada akses pertama.
class InventoryApplication : Application(){
    val database: ItemRoomDatabase by lazy { ItemRoomDatabase.getDatabase(this) }
}
