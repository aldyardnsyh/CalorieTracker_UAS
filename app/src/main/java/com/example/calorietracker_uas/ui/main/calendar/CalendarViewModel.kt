package com.example.calorietracker_uas.ui.main.calendar

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calorietracker_uas.data.Food
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class CalendarViewModel(context: Context, current: String) : ViewModel() {

    private var auth = Firebase.auth
    private var dbUsers = Firebase.firestore.collection("users")

    private val _calendarList = MutableLiveData<List<Food>>()
    val calendarList: MutableLiveData<List<Food>>
        get() = _calendarList

    var totalCalorie = 0

    init {
        getCalendar(context, current)
    }

    private fun getCalendar(context: Context, current: String) {
        dbUsers.document(auth.currentUser?.email.toString())
            .collection(current)
            .addSnapshotListener { querySnapshot, firestoreException ->
                firestoreException?.let {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    return@addSnapshotListener
                }
                querySnapshot?.let {
                    val calendarList: ArrayList<Food> = ArrayList()
                    for (document in it) {
                        val addedMeal = document.toObject<Food>()
                        calendarList.add(addedMeal)
                        totalCalorie += totalCalorie + Integer.parseInt(addedMeal.calorie)
                        _calendarList.postValue(calendarList)
                    }
                }
            }
    }
}