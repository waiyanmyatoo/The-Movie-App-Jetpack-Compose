package com.example.animecompose.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CountViewModel : ViewModel() {

    private var _count: MutableLiveData<Int> = MutableLiveData<Int>(1)
    var count: LiveData<Int> = _count;

    private val _mutableCount = mutableStateOf<Int>(1)
    val mutableCount: State<Int> = _mutableCount


    fun increase() {
//        print("clicking")
//        _count.value = _count.value?.plus(1);
        _mutableCount.value++
    }
}