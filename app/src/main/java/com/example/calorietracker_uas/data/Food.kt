package com.example.calorietracker_uas.data


import android.os.Parcelable
import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Food(
    val id: @RawValue Any = "",
    val name: @RawValue Any = "",
    val calorie: String = "",
    val url: String = "",
    val protein: String = "",
    val fat: String = "",
    val carbohydrate: String = "",
    var many: String = "0"
) : Parcelable, java.io.Serializable {
    companion object {
        fun DocumentSnapshot.toFood(): Food? {
            return try {
                val id = id
                val name = getString("name").toString()
                val calorie = getString("calorie").toString()
                val url = getString("url").toString()
                val protein = getString("protein").toString()
                val fat = getString("fat").toString()
                val carbohydrate = getString("carbohydrate").toString()
                val many = getString("many").toString()
                Food(id, name, calorie, url, protein, fat, carbohydrate, many)
            } catch (e: java.lang.Exception) {
                Log.e(TAG, "Error converting food", e)
                FirebaseCrashlytics.getInstance().log("Error converting food")
                FirebaseCrashlytics.getInstance().setCustomKey("foodId", id)
                FirebaseCrashlytics.getInstance().recordException(e)
                null
            }
        }

        private const val TAG = "Food"
    }
}