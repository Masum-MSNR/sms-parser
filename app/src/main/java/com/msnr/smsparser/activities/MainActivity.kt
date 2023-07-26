package com.msnr.smsparser.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.msnr.smsparser.R

class MainActivity : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var text: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.btnAllowPermission)
        text = findViewById(R.id.tvPermissionStatus)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.RECEIVE_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            button.visibility = Button.VISIBLE
            text.visibility = TextView.GONE
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    button.visibility = Button.VISIBLE
                    text.visibility = TextView.GONE
                } else {
                    button.visibility = Button.GONE
                    text.visibility = TextView.VISIBLE
                }
            } else {
                button.visibility = Button.GONE
                text.visibility = TextView.VISIBLE
            }
        }

        button.setOnClickListener {
            val permissions: Array<String> = arrayOf(Manifest.permission.RECEIVE_SMS)
            // check os version
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                permissions.plus(Manifest.permission.POST_NOTIFICATIONS)
            }
            ActivityCompat.requestPermissions(this, permissions, 100)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                button.visibility = Button.GONE
                text.visibility = TextView.VISIBLE
            }
        }
    }
}