package dev.matyaqubov.androidnotification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import android.content.ComponentName

import android.content.Intent
import android.os.Build


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFCMToken()
        forxiaomi()
    }

    private fun forxiaomi() {
        val manufacturer = "xiaomi"
        if (manufacturer.equals(Build.MANUFACTURER, ignoreCase = true)) {
            //this will open auto start screen where user can enable permission for your app
            val intent = Intent()
            intent.component = ComponentName(
                "com.miui.securitycenter",
                "com.miui.permcenter.autostart.AutoStartManagementActivity"
            )
            startActivity(intent)
        }
    }

    private fun loadFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.d("@@@@", "Fetching FCM registration token failed")
                return@OnCompleteListener
            }
            // Get new FCM registration token
            // Save it in locally to use later
            val token = task.result
            Log.d("@@@@", token.toString())
            //PrefsManager(this).storeDeviceToken(token.toString())
        })
    }
}