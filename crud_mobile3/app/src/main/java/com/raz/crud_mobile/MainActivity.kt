package com.raz.crud_mobile

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edtUsername = findViewById<EditText>(R.id.edtUsername)
        val edtPassword = findViewById<EditText>(R.id.edtPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val username = edtUsername.text.toString()
            val password = edtPassword.text.toString()

            thread {
                try {
                    val status = Network.login(username, password)

                    runOnUiThread {
                        if (status) {
                            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                "Username atau password salah",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch (ex: Exception) {
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}