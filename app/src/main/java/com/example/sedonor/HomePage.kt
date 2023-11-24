package com.example.sedonor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
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

//        var x = hitungSisaHari()

        tvDate = findViewById(R.id.tvDate)
        hitungSisaWaktu { sisaWaktu ->
            // Lakukan sesuatu dengan nilai sisa waktu di sini
            var x = 0;
            if(x < sisaWaktu.toInt()){
                tvDate.text = sisaWaktu.toString()
            }else{
                tvDate.text = "0"
            }
        }

//        updateSisaWaktuDaily()


//        tvDate.text = x.toString()
    }

    fun btnCamera(view: View){
//        val calender = Calendar.getInstance().time
//        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(calender)
//        Log.e("date", dateFormat.toString())
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

            // Memanggil callback dengan nilai sisa waktu
            callback(sisaWaktu)
        }
    }

//    private fun updateSisaWaktuDaily() {
//        // Timer untuk memperbarui setiap hari
//        timer(period = 86400000) {
//            // Menyinkronkan dengan thread utama
//            handler.post {
//                val sisaWaktu = hitungSisaWaktu()
//                tvDate.text = sisaWaktu.toString()
////                println("Sisa waktu antara Waktu C dan Waktu B: $sisaWaktu hari")
//                // Update UI atau lakukan tindakan lain sesuai kebutuhan
//            }
//        }
//    }

//    fun hitungSisaWaktu(): Period {
//        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
//
//        // Waktu A
//        val waktuA = LocalDate.parse("01-01-2023", formatter)
//
//        // Waktu B (3 bulan setelah Waktu A)
//        val waktuB = waktuA.plusMonths(3)
//
//        // Waktu C (saat ini)
//        val waktuC = LocalDate.now()
//
//        // Menghitung sisa waktu antara Waktu C dan Waktu B
//        return Period.between(waktuC, waktuB)
//    }

//    fun hitungSisaHari(): Long {
//        val targetDate = "20-02-2023"
//        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
//        val sekarang = Calendar.getInstance()
//        sekarang.time = dateFormat.parse(targetDate) as Date
//
//        val calendarTigaBulanKeDepan = Calendar.getInstance()
//        calendarTigaBulanKeDepan.add(Calendar.MONTH, 3)
//
//        return (calendarTigaBulanKeDepan.timeInMillis - sekarang.timeInMillis) / (24 * 60 * 60 * 1000)
//    }

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
                }
//                } else {
//                    // Handle the error case if needed
//                    callback(null)
//                }

            }
    }

    private fun convertDocumentSnapshotToUsers(documentSnapshot: DocumentSnapshot): Users? {
        return documentSnapshot.toObject(Users::class.java)
    }

    fun btnRiwayat(view: View){
        val intent = Intent(this, RiwayatDonorActivity::class.java)
        startActivity(intent)

    }
}