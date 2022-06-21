package com.raz.crud_mobile

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray
import org.json.JSONObject
import org.w3c.dom.Text
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class HomeActivity : AppCompatActivity() {
    lateinit var listMakanan: RecyclerView

    val activityResultLoad = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            load()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        listMakanan = findViewById<RecyclerView>(R.id.listMakanan)
        val fab = findViewById<FloatingActionButton>(R.id.fab)

        load()

        fab.setOnClickListener {
            activityResultLoad.launch(Intent(this@HomeActivity, CreateEditActivity::class.java))
        }
    }

    fun load() {
        thread {
            try {
                val data = Network.getListMakanan()

                runOnUiThread {
                    if (data != null) {
                        listMakanan.adapter = ListMakananAdapter(this@HomeActivity, data)
                    } else {
                        Toast.makeText(
                            this@HomeActivity,
                            "Tidak ada daftar makanan",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (ex: Exception) {
                Log.d("Error", ex.message ?: "")
                runOnUiThread {
                    Toast.makeText(this@HomeActivity, "Terjadi masalah", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

class ListMakananAdapter(val activity: HomeActivity, val list: JSONArray) : RecyclerView.Adapter<ListMakananHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListMakananHolder {
        return ListMakananHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.makanan_menu_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListMakananHolder, position: Int) {
        val item = list.getJSONObject(position)

        with(holder) {
            txtNama.text = item["nama"].toString()
            txtJenis.text = item["jenis"].toString()
            txtHarga.text = item["harga"].toString()
            txtDeskripsi.text = item["deskripsi"].toString()

            btnEdit.setOnClickListener {
                with (Intent(activity, CreateEditActivity::class.java)) {
                    putExtra("id", item["id"].toString().toInt())
                    putExtra("nama", item["nama"].toString())
                    putExtra("jenis", item["jenis"].toString())
                    putExtra("harga", item["harga"].toString().toInt())
                    putExtra("deskripsi", item["deskripsi"].toString())
                    putExtra("isEdit", true)

                    activity.activityResultLoad.launch(this)
                }
            }

            btnHapus.setOnClickListener {
                AlertDialog.Builder(activity)
                    .setMessage("Yakin ingin dihapus ?")
                    .setPositiveButton("Ya", DialogInterface.OnClickListener { dialogInterface, i ->
                        thread {
                            try {
                                val status = Network.deleteMakanan(item["id"] as Int)

                                activity.runOnUiThread {
                                    if (status) {
                                        Toast.makeText(
                                            activity,
                                            "Berhasil hapus makanan",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        Toast.makeText(
                                            activity,
                                            "Gagal hapus makanan",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }

                                activity.load()
                            } catch (ex: Exception) {
                                Toast.makeText(activity, "Terjadi Masalah", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    })
                    .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialogInterface, i ->
                        dialogInterface.cancel()
                    })
                    .show()
            }
        }
    }

    override fun getItemCount(): Int = list.length()
}

class ListMakananHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val txtNama = itemView.findViewById<TextView>(R.id.txtNama)
    val txtHarga = itemView.findViewById<TextView>(R.id.txtHarga)
    val txtJenis = itemView.findViewById<TextView>(R.id.txtJenis)
    val txtDeskripsi = itemView.findViewById<TextView>(R.id.txtDeskripsi)
    val btnEdit = itemView.findViewById<Button>(R.id.btnEdit)
    val btnHapus = itemView.findViewById<Button>(R.id.btnHapus)
}