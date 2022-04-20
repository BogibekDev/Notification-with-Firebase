package dev.matyaqubov.androidnotification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import android.content.ComponentName

import android.content.Intent
import android.os.Build
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        FirebaseAnalytics.getInstance(this)
        FirebaseCrashlytics.getInstance()
        loadFCMToken()
        Firebase.messaging.subscribeToTopic("all").addOnCompleteListener { task ->
            Log.e("AAA", "subscribe:${task.isSuccessful} ")
        }

        Firebase.messaging.subscribeToTopic("user").addOnCompleteListener { task ->
            Log.e("AAA", "subscribe:${task.isSuccessful} ")
        }
        findViewById<TextView>(R.id.tv_add).setOnClickListener {
            Toast.makeText(this, "${5/0}", Toast.LENGTH_SHORT).show()
        }
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

    private fun navigate(intent: Intent?) {
        if (intent != null) {
            val text = intent.getStringExtra("type")
            when (text) {
                "parcel" -> {
                    //navigate to fragment
                    Toast.makeText(this, "Navigate to parcel Fragment", Toast.LENGTH_SHORT).show()
                }
                "simple" -> {
                    //navigate to fragment
                    Toast.makeText(this, "Navigate to parcel Fragment", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navigate(intent)
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