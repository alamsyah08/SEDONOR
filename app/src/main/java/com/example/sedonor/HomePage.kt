package com.example.sedonor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import java.security.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.concurrent.timer


class HomePage : AppCompatActivity() {
    private val cameraPermission = android.Manifest.permission.CAMERA

    private lateinit var sessionManager: SessionManager
    val db = FirebaseFirestore.getInstance()

    private val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    private lateinit var handler: Handler
    private lateinit var tvDate: TextView
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var adapterHome: AdapaterHome
    private lateinit var recyclerViewHome: RecyclerView
    private lateinit var rvLokasi: RecyclerView
    private lateinit var myAdapterLokasi: AdapterLokasiHome

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if(isGranted){
            startScanner()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        sessionManager = SessionManager(this)

        handler = Handler(Looper.getMainLooper())
        tvDate = findViewById(R.id.tvDate)
        hitungSisaWaktu { sisaWaktu ->
            // nilai sisa waktu
            var x = 0;
            if(x < sisaWaktu.toInt()){
                tvDate.text = sisaWaktu.toString() +" "+"hari menuju donor \ndarah berikutnya!"
            }else{
                tvDate.text = "0 hari menuju donor berikutnya!"
            }
        }

        //Zahwa
//        val db = FirebaseFirestore.getInstance()

        bottomNavigationView = findViewById(R.id.bottom_navbar)
        setupBottomNavigationBar()

        recyclerViewHome = findViewById(R.id.rvArtikelHome)
        recyclerViewHome.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Mengambil data artikel dari Firestore
        db.collection("artikels")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result.documents
                    // Konversi data Firestore ke list Artikel
                    val artikelList = convertQuerySnapshotToList(task.result)

                    // Inisialisasi dan atur adapter
                    adapterHome = AdapaterHome(this, artikelList as ArrayList<Artikel>)

                    // Set listener untuk perpindahan halaman
                    adapterHome.setOnItemClickListener(object : AdapaterHome.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            // Dapatkan data artikel pada posisi yang diklik
                            val clickedArtikel = artikelList[position]

                            // Persiapkan intent untuk perpindahan halaman
                            val intent = Intent(this@HomePage, DetailArtikel::class.java)
                            intent.putExtra("judul", clickedArtikel.judul)
                            intent.putExtra("konten", clickedArtikel.konten)
                            intent.putExtra("imageUrl", clickedArtikel.imageUrl)

                            // Mulai aktivitas DetailArtikel
                            startActivity(intent)
                        }
                    })
                    recyclerViewHome.adapter = adapterHome
                } else {
                    // Handle kegagalan
                    Log.e("Firestore", "Error getting documents.", task.exception)
                }
            }

        rvLokasi = findViewById(R.id.rvLokasiHome)
        rvLokasi.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        // Mengambil data lokasi donor dari Firestore
        db.collection("tempat")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result.documents
                    Log.e("data", document.toString())
                    // Konversi data Firestore ke list Artikel
                    val lokasiList = convertQuerySnapshotToListArtikel(task.result)

                    // Inisialisasi dan atur adapter
                    myAdapterLokasi = AdapterLokasiHome(this, lokasiList as ArrayList<LokasiDonor>)

                    // Set listener untuk perpindahan halaman
                    myAdapterLokasi.setOnItemClickListener(object : AdapterLokasiHome.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            // Dapatkan data artikel pada posisi yang diklik
                            val clickedArtikel = lokasiList[position]

                            // Persiapkan intent untuk perpindahan halaman
                            val intent = Intent(this@HomePage, DetailLokasiActivity::class.java)
                            intent.putExtra("nama", clickedArtikel.nama)
                            intent.putExtra("lokasi", clickedArtikel.lokasi)
                            intent.putExtra("deskripsi", clickedArtikel.deskripsi)
                            intent.putExtra("foto", clickedArtikel.foto)

                            //Mulai aktivitas DetailArtikel
                            startActivity(intent)
                        }
                    })
                    // Set adapter ke RecyclerView
                    rvLokasi.adapter = myAdapterLokasi
                } else {
                    // Handle kegagalan
                    Log.e("Firestore", "Error getting documents.", task.exception)
                }
            }

    }

    //Scanner
    fun btnCamera(view: View){
        requestCameraAndStartScanner()
    }

    private fun requestCameraAndStartScanner(){
        if(isPermissionGranted(cameraPermission)){
            startScanner()
        }else{
            requestCameraPermission()
        }
    }

    private fun startScanner(){
        CheckIn1.startScanner(this){
        }
    }

    private fun requestCameraPermission(){
        when {
            shouldShowRequestPermissionRationale(cameraPermission) -> {
                cameraPermissionRequest {
                    openPermissionSetting()
                }
            }
            else ->{
                requestPermissionLauncher.launch(cameraPermission)
            }
        }
    }

    //sisa waktu donor
    private fun hitungSisaWaktu(callback: (Long) -> Unit) {
        getTanggal { A ->
            // Waktu A
            val waktuA = Calendar.getInstance()
            waktuA.time = dateFormat.parse(A) as Date

            // Waktu B (3 bulan setelah Waktu A)
            val waktuB = Calendar.getInstance()
            waktuB.time = waktuA.time
            waktuB.add(Calendar.MONTH, 3)

            // Waktu C (saat ini)
            val waktuC = Calendar.getInstance()

            // Menghitung sisa waktu antara Waktu C dan Waktu B dalam hari
            val sisaWaktu = (waktuB.timeInMillis - waktuC.timeInMillis) / (24 * 60 * 60 * 1000)
            callback(sisaWaktu)
        }
    }

    private fun getTanggal(callback: (String?) -> Unit) {
        var retrievedUserId: String = sessionManager.getUserId().toString()
        var tanggal: String? = "ba"
        db.collection("users").document(retrievedUserId)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = convertDocumentSnapshotToUsers(task.result)
                    tanggal = user?.checkin
                    Log.e("tanggal", tanggal.toString())
                    callback(tanggal)
                }else {
                    // Handle the error case if needed
                    callback(null)
                }

            }
    }

    private fun convertDocumentSnapshotToUsers(documentSnapshot: DocumentSnapshot): Users? {
        return documentSnapshot.toObject(Users::class.java)
    }

    //Zahwa
    private fun setupBottomNavigationBar() {
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            handleBottomNavigationItemClick(item)
        }
    }

    private fun handleBottomNavigationItemClick(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_1 -> {
                item.setIcon(R.drawable.ic_home_selected)
                showToastAndNavigate("Home Clicked", HomePage::class.java)
            }
            R.id.menu_item_2 -> {
                item.setIcon(R.drawable.ic_tanyapmi_selected)
                showToastAndNavigate("Tanya PMI Clicked", ChatbotPMI::class.java)
            }
            R.id.menu_item_3 -> {
                item.setIcon(R.drawable.ic_akun_selected)
                // Lakukan aksi atau pindah ke halaman Account
//                showToastAndNavigate("Account Clicked", Account::class.java)
            }
        }
        return true
    }

    private fun convertQuerySnapshotToList(querySnapshot: QuerySnapshot?): List<Artikel> {
        val artikelList = mutableListOf<Artikel>()
        querySnapshot?.forEach { document: QueryDocumentSnapshot ->
            // Retrieve data from Firestore document
            val judul = document.getString("judul")
            val konten = document.getString("konten")
            val imageUrl = document.getString("gambar")
            val artikel = Artikel(judul, konten, imageUrl)
            artikelList.add(artikel)
        }
        return artikelList
    }

    private fun convertQuerySnapshotToListArtikel(querySnapshot: QuerySnapshot?): List<LokasiDonor> {
        val riwayatList = mutableListOf<LokasiDonor>()
        querySnapshot?.forEach { document: QueryDocumentSnapshot ->
            // Retrieve data from Firestore document
            val deskripsi = document.getString("deskripsi")
            val foto = document.getString("foto")
            val lokasi = document.getString("lokasi")
            val nama = document.getString("nama")
            val riwayat = LokasiDonor(deskripsi.toString(), foto.toString(),lokasi.toString(), nama.toString())
            riwayatList.add(riwayat)
            Log.w("data",riwayat.toString())
        }
        Log.w("data",riwayatList.toString())
        return riwayatList
    }

    private fun showToastAndNavigate(message: String, destination: Class<*>) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, destination))
    }

    fun intentKeArtikel (view: View) {
        // Ini akan dipanggil ketika tombol "Lihat semua" ditekan
        val intent = Intent(this, ArtikelPage::class.java)
        startActivity(intent)
    }

    fun intentKeTanyaPMI (view: View) {
        // Ini akan dipanggil ketika tombol "Lihat semua" ditekan
        val intent = Intent(this, ChatbotPMI::class.java)
        startActivity(intent)
    }

    fun btnRiwayat(view: View){
        val intent = Intent(this, RiwayatDonorActivity::class.java)
        startActivity(intent)
    }

    fun btnLokasi(view: View){
        val intent = Intent(this, LokasiDonorActivity::class.java)
        startActivity(intent)
    }

    fun btnReminder(view: View){
        hitungSisaWaktu { sisaWaktu ->
            val intent = Intent(this, ReminderActivity::class.java)
            intent.putExtra("SISAWAKTU", sisaWaktu.toString())
            startActivity(intent)
        }
    }

    fun btnCal(view: View){
        val intent = Intent(this, CalenderActivity::class.java)
        startActivity(intent)
    }
}