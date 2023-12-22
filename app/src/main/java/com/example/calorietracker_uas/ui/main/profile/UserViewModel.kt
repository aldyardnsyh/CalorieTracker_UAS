package com.example.calorietracker_uas.ui.main.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calorietracker_uas.data.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.parcelize.RawValue

class UserViewModel : ViewModel() {

    private var auth = Firebase.auth
    private val db = Firebase.firestore

    private var _userInfo = MutableLiveData<User>()
    val userInfo: MutableLiveData<User>
        get() = _userInfo

    init {
        getUserInfo()
    }

    private fun getUserInfo() {
        db.collection("users").document(auth.currentUser?.email.toString())
            .get()
            .addOnSuccessListener {
                it.toObject<User>()?.let { user ->
                    _userInfo.value = user
                }
            }
    }

    fun updateAllData(hashMap: HashMap<String, Any>) {
        db.collection("users").document(auth.currentUser?.email.toString())
            .update(hashMap)
    }

    fun updateSingleData(data: String, path: String) {
        db.collection("users").document(auth.currentUser?.email.toString())
            .update(
                mapOf(
                    (path to Integer.parseInt(data))
                )
            )
    }

    fun eatFood(date: String, foodName: @RawValue Any, hashMap: HashMap<String, Any>) {
        db.collection("users").document(auth.currentUser?.email.toString())
            .collection(date).document(foodName.toString())
            .set(hashMap)
    }

    fun vomitFood(date: String, foodName: @RawValue Any) {
        db.collection("users").document(auth.currentUser?.email.toString())
            .collection(date).document(foodName.toString()).delete()
    }
}