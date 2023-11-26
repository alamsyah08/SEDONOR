package com.example.sedonor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import java.util.Collections

class RiwayatDonorActivity : AppCompatActivity() {
    private lateinit var myAdapter: MyAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvTotal: TextView

    private lateinit var sessionManager: SessionManager
    lateinit var retrievedUserId : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat_donor)

        val db = FirebaseFirestore.getInstance()
        sessionManager = SessionManager(this)
        retrievedUserId = sessionManager.getUserId().toString()

        recyclerView = findViewById(R.id.rvArtikel)
        tvTotal =  findViewById(R.id.tvTotal)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // Mengambil data dari Firestore
        val collectionReference = db.collection("users").document(retrievedUserId).collection("riwayat")
        collectionReference.whereEqualTo("status", true)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result.documents
                    val total = document.size.toString()
                    tvTotal.text = "Total Donor: $total"

                    // Konversi data Firestore ke list Artikel
                    val riwayatList = convertQuerySnapshotToList(task.result)

                    // Inisialisasi dan atur adapter
                    myAdapter = MyAdapter(this, riwayatList as ArrayList<RiwayatDonor>)

                    // Set listener untuk perpindahan halaman
                    myAdapter.setOnItemClickListener(object : MyAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            // Dapatkan data artikel pada posisi yang diklik
                            val clickedArtikel = riwayatList[position]

                            // Persiapkan intent untuk perpindahan halaman
                            val intent = Intent(this@RiwayatDonorActivity, DetailRiwayatDonorActivity::class.java)
                            intent.putExtra("judul", clickedArtikel.tempatDonor)
                            intent.putExtra("tanggal", clickedArtikel.tanggalDonor)
                            intent.putExtra("KH", clickedArtikel.kadarHemoglobin)
                            intent.putExtra("ST", clickedArtikel.suhuTubuh)
                            intent.putExtra("TDD", clickedArtikel.tekananDarahDiastolik)
                            intent.putExtra("TDS", clickedArtikel.tekananDarahSistolik)
                            intent.putExtra("DN", clickedArtikel.denyutNadi)
                            intent.putExtra("BB", clickedArtikel.beratBadan)

                            // Mulai aktivitas DetailArtikel
                            startActivity(intent)
                        }
                    })

                    // Set adapter ke RecyclerView
                    recyclerView.adapter = myAdapter
                } else {
                    // Handle kegagalan
                    Log.e("Firestore", "Error getting documents.", task.exception)
                }
            }
    }

    private fun convertQuerySnapshotToList(querySnapshot: QuerySnapshot?): List<RiwayatDonor> {
        val riwayatList = mutableListOf<RiwayatDonor>()
        querySnapshot?.forEach { document: QueryDocumentSnapshot ->
            // Retrieve data from Firestore document
            val beratBadan = document.getString("beratBadan")
            val denyutNadi = document.getString("denyutNadi")
            val kadarHemoglobin = document.getString("kadarHemoglobin")
            val status = document.getBoolean("status")
            val suhuTubuh = document.getString("suhuTubuh")
            val tanggalDonor = document.getString("tanggalDonor")
            val tekananDarahDiastolik = document.getString("tekananDarahDiastolik")
            val tekananDarahSistolik = document.getString("tekananDarahSistolik")
            val tempatDonor = document.getString("tempatDonor")

            val riwayat = RiwayatDonor(beratBadan.toString(), denyutNadi.toString(),kadarHemoglobin.toString(), status, suhuTubuh.toString(), tanggalDonor.toString(), tekananDarahDiastolik.toString(), tekananDarahSistolik.toString(), tempatDonor.toString())
            riwayatList.add(riwayat)
            Log.w("data",riwayat.toString())
        }
        Log.w("data",riwayatList.toString())
        return riwayatList
    }

    fun intentKeHome (view: View) {
        // Ini akan dipanggil ketika tombol "Lihat semua" ditekan
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)
    }
}