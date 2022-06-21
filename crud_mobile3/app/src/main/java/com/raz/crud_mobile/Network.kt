package com.raz.crud_mobile

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class Network {
    companion object {
        var token: String? = ""

        fun login(username: String, password: String): Boolean {
            val url = URL("http://192.168.0.241:8000/api/login")

            with(url.openConnection() as HttpURLConnection) {
                requestMethod = "POST"

                setRequestProperty("Content-Type", "application/x-www-form-urlencoded")

                with(outputStream.bufferedWriter()) {
                    write("username=${Uri.encode(username)}&password=${Uri.encode(password)}")
                    flush()
                }

                return when (responseCode) {
                    200 -> {
                        val result = inputStream.bufferedReader().readText()
                        val data = JSONObject(result)

                        token = "Bearer " + data["token"].toString()

                        true
                    }
                    else -> false
                }
            }
        }

        fun getListMakanan(): JSONArray? {
            val url = URL("http://192.168.0.241:8000/api/makanan")

            with(url.openConnection() as HttpURLConnection) {
                requestMethod = "GET"
                setRequestProperty("Authorization", token)

                return when (responseCode) {
                    200 -> {
                        val result = inputStream.bufferedReader().readText()

                        val data = JSONObject(result)

                        data.getJSONArray("data")
                    }
                    else -> null
                }
            }
        }

        fun editMakanan(id: Int, nama: String, jenis: String, harga: Int, deskripsi: String) : Boolean {
            val url = URL("http://192.168.0.241:8000/api/makanan/${id}")

            with (url.openConnection() as HttpURLConnection) {
                requestMethod = "PUT"

                setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
                setRequestProperty("Authorization", token)

                with(outputStream.bufferedWriter()) {
                    write("nama=${Uri.encode(nama)}&jenis=${Uri.encode(jenis)}&deskripsi=${Uri.encode(deskripsi)}&harga=${harga}")
                    flush()
                }

                return when (responseCode) {
                    200 -> {
                        val result = inputStream.bufferedReader().readText()

                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        }

        fun storeMakanan(nama: String, jenis: String, harga: Int, deskripsi: String) : Boolean {
            val url = URL("http://192.168.0.241:8000/api/makanan")

            with (url.openConnection() as HttpURLConnection) {
                requestMethod = "POST"

                setRequestProperty("Authorization", token)
                setRequestProperty("Content-Type", "application/x-www-form-urlencoded")

                with(outputStream.bufferedWriter()) {
                    write("nama=${Uri.encode(nama)}&jenis=${Uri.encode(jenis)}&deskripsi=${Uri.encode(deskripsi)}&harga=${harga}")
                    flush()
                }

                return when (responseCode) {
                    200 -> {
                        val result = inputStream.bufferedReader().readText()

                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        }

        fun deleteMakanan(id: Int) : Boolean {
            val url = URL("http://192.168.0.241:8000/api/makanan/${id}")

            with (url.openConnection() as HttpURLConnection) {
                requestMethod = "DELETE"

                setRequestProperty("Authorization", token)

                return when (responseCode) {
                    200 -> {
                        val result = inputStream.bufferedReader().readText()

                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        }
    }
}