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

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.inventory.data.Item
import com.example.inventory.databinding.FragmentAddItemBinding

/**
 * Fragment to add or update an item in the Inventory database.
 */
class AddItemFragment : Fragment() {

    private val navigationArgs: ItemDetailFragmentArgs by navArgs()
    //buat lateinit var yang disebut item dari jenis Item.
    lateinit var item: Item

    //buat private val yang disebut viewModel dari jenis InventoryViewModel. Gunakan delegasi properti Kotlin by activityViewModels() untuk membagikan ViewModel di seluruh fragmen.
    private val viewModel: InventoryViewModel by activityViewModels {
        //panggil konstruktor InventoryViewModelFactory() lalu teruskan instance ItemDao.
        InventoryViewModelFactory(
            (activity?.application as InventoryApplication).database.itemDao()
        )
    }

    // Binding object instance corresponding to the fragment_add_item.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment
    private var _binding: FragmentAddItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    //buat fungsi private yang disebut isEntryValid() yang menampilkan Boolean.
    //implementasikan fungsi isEntryValid(). Panggil fungsi isEntryValid() pada instance viewModel, yang meneruskan teks dari tampilan teks. Tampilkan nilai fungsi viewModel.isEntryValid().
    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.itemName.text.toString(),
            binding.itemPrice.text.toString(),
            binding.itemCount.text.toString(),
        )
    }

    //tambahkan fungsi private lain yang disebut addNewItem() tanpa parameter serta tidak menampilkan apa pun. Di dalam fungsi, panggil isEntryValid() di dalam kondisi if.
    private fun addNewItem() {
        if (isEntryValid()) {
            //panggil metode addNewItem() pada instance viewModel. Teruskan detail item yang dimasukkan oleh pengguna, gunakan instance binding untuk membacanya.
            viewModel.addNewItem(
                binding.itemName.text.toString(),
                binding.itemPrice.text.toString(),
                binding.itemCount.text.toString(),
            )
            //buat val action untuk kembali ke ItemListFragment. Panggil findNavController().navigate(), dengan meneruskan action.
            val action = AddItemFragmentDirections.actionAddItemFragmentToItemListFragment()
            findNavController().navigate(action)
        }
    }

   //Untuk menggabungkan semuanya, tambahkan pengendali klik ke tombol Save. Di class AddItemFragment, di atas fungsi onDestroyView(), ganti fungsi onViewCreated().
   //Di dalam fungsi onViewCreated(), tambahkan pengendali klik ke tombol save, dan panggil addNewItem() dari fungsi tersebut.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveAction.setOnClickListener {
            addNewItem()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }
}
