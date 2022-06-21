package com.raz.crud_mobile

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.json.JSONObject
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class CreateEditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_edit)

        val edtNama = findViewById<EditText>(R.id.edtNama)
        val edtJenis = findViewById<EditText>(R.id.edtJenis)
        val edtHarga = findViewById<EditText>(R.id.edtHarga)
        val edtDeskripsi = findViewById<EditText>(R.id.edtDeskripsi)

        val btnSimpan = findViewById<Button>(R.id.btnSimpan)

        val isEdit = intent.getBooleanExtra("isEdit", false)

        if (isEdit) {
            with (intent) {
                btnSimpan.setText("Edit")
                edtNama.setText(getStringExtra("nama"));
                edtJenis.setText(getStringExtra("jenis"))
                edtHarga.setText(getIntExtra("harga", 0).toString())
                edtDeskripsi.setText(getStringExtra("deskripsi"))
            }
        }

        btnSimpan.setOnClickListener {
            val nama = edtNama.text.toString()
            val jenis = edtJenis.text.toString()
            val harga = edtHarga.text.toString().toInt()
            val deskripsi = edtDeskripsi.text.toString()

            thread {
                try {
                    val status = if (isEdit) {
                        Network.editMakanan(intent.getIntExtra("id", 0), nama, jenis, harga, deskripsi)
                    } else {
                        Network.storeMakanan(nama, jenis, harga, deskripsi)
                    }

                    runOnUiThread {
                        if (status) {
                            Toast.makeText(
                                this@CreateEditActivity,
                                "Berhasil simpan makanan",
                                Toast.LENGTH_SHORT
                            ).show()

                            setResult(RESULT_OK)
                            finish()
                        } else {
                            Toast.makeText(
                                this@CreateEditActivity,
                                "Gagal simpan makanan",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch (ex: Exception) {
                    Log.d("error-app", "Error : ${ex.toString()}")
                    runOnUiThread {
                        Toast.makeText(this, "Terjadi masalah", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}