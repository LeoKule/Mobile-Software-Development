package com.kulev.myapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class FirstFragment : Fragment() {
    // Инициализация адаптера
    private val myAdapter = PhonesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_first, container, false)

        // Находим RecyclerView
        val recyclerView: RecyclerView = root.findViewById(R.id.recyclerView)

        // Загружаем данные
        loadData()

        // Настраиваем RecyclerView: LayoutManager и Adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = myAdapter

        return root
    }

    private fun loadData() {
        // Передаем данные из PhonesData в адаптер
        myAdapter.setupPhones(PhonesData.phonesArr)
    }
}