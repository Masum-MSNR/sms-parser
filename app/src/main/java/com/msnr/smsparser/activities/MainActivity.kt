package com.msnr.smsparser.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.msnr.smsparser.R
import com.msnr.smsparser.databinding.ActivityMainBinding
import com.msnr.smsparser.network.WebService
import com.msnr.smsparser.network.pojo.ResponsePojo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.RECEIVE_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            binding.btnAllowPermission.visibility = Button.VISIBLE
            binding.tvPermissionStatus.visibility = TextView.GONE
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    binding.btnAllowPermission.visibility = Button.VISIBLE
                    binding.tvPermissionStatus.visibility = TextView.GONE
                } else {
                    binding.btnAllowPermission.visibility = Button.GONE
                    binding.tvPermissionStatus.visibility = TextView.VISIBLE
                }
            } else {
                binding.btnAllowPermission.visibility = Button.GONE
                binding.tvPermissionStatus.visibility = TextView.VISIBLE
            }
        }

        binding.btnAllowPermission.setOnClickListener {
            val permissions: Array<String> = arrayOf(Manifest.permission.RECEIVE_SMS)
            // check os version
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                permissions.plus(Manifest.permission.POST_NOTIFICATIONS)
            }
            ActivityCompat.requestPermissions(this, permissions, 100)
        }

        binding.btnCheck.setOnClickListener {
            val url = binding.etUrl.text.toString()
            binding.tvResponse.text = "Checking..."
            WebService.client?.checkUrl(url, "json")?.enqueue(object :
                Callback<ResponsePojo> {
                override fun onResponse(
                    call: Call<ResponsePojo>,
                    response: Response<ResponsePojo>
                ) {
                    val responsePojo = response.body()
                    if (responsePojo != null) {
                        if (!responsePojo.results.verified) {
                            binding.tvResponse.text = "$url \nThis is a phishing url"
                        } else {
                            binding.tvResponse.text = "$url \nThis is not a phishing url"
                        }
                    } else {
                        binding.tvResponse.text = "Something went wrong"
                    }

                }

                override fun onFailure(call: Call<ResponsePojo>, t: Throwable) {
                    binding.tvResponse.text = "Something went wrong"
                    t.stackTrace
                }
            })
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
                binding.btnAllowPermission.visibility = Button.GONE
                binding.tvPermissionStatus.visibility = TextView.VISIBLE
            }
        }
    }
}