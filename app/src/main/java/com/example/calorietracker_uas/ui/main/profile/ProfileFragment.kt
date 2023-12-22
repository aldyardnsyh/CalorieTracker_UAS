package com.example.calorietracker_uas.ui.main.profile

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.view.isNotEmpty
import androidx.fragment.app.Fragment
import com.example.calorietracker_uas.R
import com.example.calorietracker_uas.databinding.FragmentProfileBinding
import com.example.calorietracker_uas.ui.login.LoginActivity
import com.example.calorietracker_uas.ui.main.MainActivity
import com.example.calorietracker_uas.ui.main.main.MainViewModel
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mikhaellopez.circularprogressbar.CircularProgressBar

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth
    private val userViewModel by lazy { UserViewModel() }
    private val mainViewModel by lazy { MainViewModel(requireContext()) }
    private lateinit var updateHashMap: HashMap<String, Any>

    // Inside your ProfileFragment class
    private val CHANNEL_ID = "ProfileUpdateChannel" // Unique channel ID

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        observer()

        binding.imageLogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            activity?.finish()
        }
    }

    private fun observer() {
        userViewModel.userInfo.observe(viewLifecycleOwner) { user ->

            binding.profileName.text = user.name
            binding.profileWeight.text = user.weight
            binding.profileHeight.text = user.height
            binding.profileCalorieProgress.text = user.calorieGoal.toString()

            mainViewModel.mainList.observe(viewLifecycleOwner) {

                var totalCalorie = 0

                for (index in it.indices) {
                    totalCalorie += Integer.parseInt(it[index].calorie) * Integer.parseInt(it[index].many)
                }

                binding.circularProgressBar.apply {
                    setProgressWithAnimation(totalCalorie.toFloat(), 1000)

                    progressMax = user.calorieGoal.toString().toFloat()
                    progressBarColorStart = Color.GREEN
                    progressBarColorEnd = Color.RED
                    progressBarColorDirection = CircularProgressBar.GradientDirection.RIGHT_TO_LEFT

                    progressBarWidth = 7f
                    backgroundProgressBarWidth = 3f

                    roundBorder = true
                    progressDirection = CircularProgressBar.ProgressDirection.TO_RIGHT
                }

                binding.profileCurrentCalorie.text = totalCalorie.toString()
            }


            binding.btnEditProfile.setOnClickListener {
                val dialog =
                    LayoutInflater.from(context).inflate(R.layout.edit_profile_dialog, null)
                val builder = AlertDialog.Builder(context).setView(dialog).show()
                builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                builder.setCancelable(true)

                val etName = dialog.findViewById<TextInputLayout>(R.id.etEditName)
                etName.editText?.setText(user.name)
                val etSurname = dialog.findViewById<TextInputLayout>(R.id.etEditSurname)
                etSurname.editText?.setText(user.surname)
                val etWeight = dialog.findViewById<TextInputLayout>(R.id.etEditWeight)
                etWeight.editText?.setText(user.weight)
                val etHeight = dialog.findViewById<TextInputLayout>(R.id.etEditHeight)
                etHeight.editText?.setText(user.height)
                val etGoal = dialog.findViewById<TextInputLayout>(R.id.etEditGoal)
                etGoal.editText?.setText(user.calorieGoal.toString())

                dialog.findViewById<Button>(R.id.btnEditProfile).setOnClickListener {
                    if (etSurname.isNotEmpty() && etHeight.isNotEmpty() && etWeight.isNotEmpty() && etGoal.isNotEmpty()) {
                        updateHashMap = hashMapOf(
                            "name" to etName.editText?.text.toString(),
                            "surname" to etSurname.editText?.text.toString(),
                            "height" to etHeight.editText?.text.toString(),
                            "weight" to etWeight.editText?.text.toString(),
                            "calorieGoal" to Integer.parseInt(etGoal.editText?.text.toString())
                        )
                        userViewModel.updateAllData(updateHashMap)
                        builder.dismiss()
                        Toast.makeText(
                            requireContext(), "Profile Updated!!!", Toast.LENGTH_SHORT
                        ).show()
                        // Show the notification
                        showNotification("Profile Berhasil Diupdate!")
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "You have to fill all blanks",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
    private fun showNotification(message: String) {
        // Create an explicit intent for an activity in your app
        val intent = Intent(requireContext(), MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            requireContext(),
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        // Create a notification and set the notification content
        val notification = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.broccoli_png)
            .setContentTitle("Profile Updated!")
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true) // Close the notification when clicked
            .build()

        // Show the notification
        val notificationManager =
            requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create the notification channel for Android Oreo and higher
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Profile Update Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)

        notificationManager.notify(0, notification)
    }
}