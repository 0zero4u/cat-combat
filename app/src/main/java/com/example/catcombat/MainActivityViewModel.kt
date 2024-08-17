package com.example.catcombat

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.parcelize.Parcelize

class MainActivityViewModel : ViewModel() {

    val state: LiveData<State> get() = stateLiveData
    private val stateLiveData = MutableLiveData<State>()

    fun initState (state: State) {
        stateLiveData.value = state
    }

    fun increasePoints() {
        val oldState = stateLiveData.value
        stateLiveData.value = oldState?.copy(pointCounter = oldState.pointCounter + 1)
    }

    @Parcelize
    data class State (
        val pointCounter : Int
    ) : Parcelable

}