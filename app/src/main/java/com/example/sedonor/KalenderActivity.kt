package com.example.sedonor

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.sedonor.artikel.Artikel
import com.example.sedonor.artikel.DetailArtikel
import com.example.sedonor.artikel.MyAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class KalenderActivity : AppCompatActivity() {
    private lateinit var myAdapterGejala: AdapterGejala
    private lateinit var recyclerViewGejala: RecyclerView

    private lateinit var myAdapterArtikel: MyAdapter
    private lateinit var recyclerViewArtikel: RecyclerView

    lateinit var retrievedUserId : String
    private lateinit var sisaWaktu: String
    private lateinit var tvSisaWaktu: TextView

    private lateinit var sessionManager: SessionManager
    val db = FirebaseFirestore.getInstance()

    private lateinit var recyclerView: RecyclerView
    private lateinit var tvDateMonth: TextView
    private lateinit var ivCalendarNext: ImageView
    private lateinit var ivCalendarPrevious: ImageView

    private val sdf = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
    private val cal = Calendar.getInstance(Locale.ENGLISH)
    private val currentDate = Calendar.getInstance(Locale.ENGLISH)
    private val dates = ArrayList<Date>()
    private lateinit var adapter: CalendarAdapter
    private val calendarList2 = ArrayList<CalendarDateModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kalender)

        val sisa = intent.getStringExtra("SISAWAKTU")
        sisaWaktu = sisa.toString()
        tvSisaWaktu = findViewById(R.id.tvDate)
        tvSisaWaktu.text = sisa + " "+"hari menuju donor \ndarah berikutnya!"

        sessionManager = SessionManager(this)
        retrievedUserId = sessionManager.getUserId().toString()
        recyclerViewGejala = findViewById(R.id.rvGejala)
        recyclerViewGejala.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


        // Mengambil data gejala
        val collectionReference = db.collection("users").document(retrievedUserId).collection("gejala")
        collectionReference.whereEqualTo("status", true)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val gejalaList = convertQuerySnapshotToListGejala(task.result)
                    myAdapterGejala = AdapterGejala(this, gejalaList as ArrayList<Gejala>)
                    myAdapterGejala.setOnItemClickListener(object : AdapterGejala.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            // Dapatkan data artikel pada posisi yang diklik
                            val clickedGejala = gejalaList[position]
                            val docRef = collectionReference.document(clickedGejala.id)

                            // Update field "status" menjadi false
                            docRef.update("status", false)
                                .addOnSuccessListener {
                                    refreshDataFromFirestore()
                                }
                                .addOnFailureListener { e ->
                                    Log.e("Firestore", "Error getting documents.", e)
                                }
                        }
                    })
                    recyclerViewGejala.adapter = myAdapterGejala
                } else {
                    Log.e("Firestore", "Error getting documents.", task.exception)
                }
            }

        //mengambil data artikel
        recyclerViewArtikel = findViewById(R.id.rvArtikel)
        recyclerViewArtikel.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        db.collection("artikels")

            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result.documents
                    // Konversi data Firestore ke list Artikel
                    val artikelList = convertQuerySnapshotToListArtikel(task.result)

                    // Inisialisasi dan atur adapter
                    myAdapterArtikel = MyAdapter(this, artikelList as ArrayList<Artikel>)

                    // Set listener untuk perpindahan halaman
                    myAdapterArtikel.setOnItemClickListener(object : MyAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            val clickedArtikel = artikelList[position]

                            val intent = Intent(this@KalenderActivity, DetailArtikel::class.java)
                            intent.putExtra("judul", clickedArtikel.judul)
                            intent.putExtra("konten", clickedArtikel.konten)
                            intent.putExtra("imageUrl", clickedArtikel.imageUrl)
                            startActivity(intent)
                        }
                    })

                    // Set adapter ke RecyclerView
                    recyclerViewArtikel.adapter = myAdapterArtikel
                } else {
                    // Handle kegagalan
                    Log.e("Firestore", "Error getting documents.", task.exception)
                }
            }

        tvDateMonth = findViewById(R.id.text_date_month)
        recyclerView = findViewById(R.id.recyclerViewKalender)
        ivCalendarNext = findViewById(R.id.iv_calendar_next)
        ivCalendarPrevious = findViewById(R.id.iv_calendar_previous)
        setUpAdapter()
        setUpClickListener()
        setUpCalendar()
    }

    private fun convertQuerySnapshotToListGejala(querySnapshot: QuerySnapshot?): List<Gejala> {
        val gejalaList = mutableListOf<Gejala>()
        querySnapshot?.forEach { document: QueryDocumentSnapshot ->
            // Retrieve data from Firestore document
            val id = document.id
            val nama = document.getString("nama")
            val status = document.getBoolean("status")
            val warna = document.getString("warna")

            if(status != null){
                val gejala = Gejala(id, nama.toString(), status,warna.toString())
                gejalaList.add(gejala)
            }
        }
        return gejalaList
    }

    private fun convertQuerySnapshotToListArtikel(querySnapshot: QuerySnapshot?): List<Artikel> {
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

    private fun refreshDataFromFirestore() {
        val collectionReference = db.collection("users").document(retrievedUserId).collection("gejala")

        collectionReference.whereEqualTo("status", true)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val gejalaList = convertQuerySnapshotToListGejala(task.result)
                    myAdapterGejala.updateData(gejalaList)
                } else {
                    // Handle kesalahan jika diperlukan
                }
            }
    }

    fun showAddDialog(context: Context) {
        val dialog = Dialog(context)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.add_gejala)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val btnDemam = dialog.findViewById<ImageView>(R.id.btnDemam)
        val DemamColor = Color.parseColor("#EDE420")
        btnDemam.setColorFilter(DemamColor, PorterDuff.Mode.SRC_IN)
        btnDemam.setOnClickListener {
            val docRef = db.collection("users").document(retrievedUserId).collection("gejala").document("Demam")
            docRef.update("status", true)
                .addOnSuccessListener {
                    refreshDataFromFirestore()
                    dialog.dismiss()
                }
                .addOnFailureListener { e ->
                    Log.e("Firestore", "Error getting documents.", e)
                    dialog.dismiss()
                }
        }

        val btnMual = dialog.findViewById<ImageView>(R.id.btnMual)
        val MualColor = Color.parseColor("#03B454")
        btnMual.setColorFilter(MualColor, PorterDuff.Mode.SRC_IN)
        btnMual.setOnClickListener {
            val docRef = db.collection("users").document(retrievedUserId).collection("gejala").document("Mual")
            docRef.update("status", true)
                .addOnSuccessListener {
                    refreshDataFromFirestore()
                    dialog.dismiss()
                }
                .addOnFailureListener { e ->
                    Log.e("Firestore", "Error getting documents.", e)
                    dialog.dismiss()
                }
        }

        val btnMaag = dialog.findViewById<ImageView>(R.id.btnMaag)
        val MaagColor = Color.parseColor("#DEB41E")
        btnMaag.setColorFilter(MaagColor, PorterDuff.Mode.SRC_IN)
        btnMaag .setOnClickListener {
            val docRef = db.collection("users").document(retrievedUserId).collection("gejala").document("Maag")
            docRef.update("status", true)
                .addOnSuccessListener {
                    refreshDataFromFirestore()
                    dialog.dismiss()
                }
                .addOnFailureListener { e ->
                    Log.e("Firestore", "Error getting documents.", e)
                    dialog.dismiss()
                }
        }

        val btnDarahTinggi = dialog.findViewById<ImageView>(R.id.btnDarahTinggi)
        val DtColor = Color.parseColor("#D4095E")
        btnDarahTinggi.setColorFilter(DtColor, PorterDuff.Mode.SRC_IN)
        btnDarahTinggi .setOnClickListener {
            val docRef = db.collection("users").document(retrievedUserId).collection("gejala").document("DarahTinggi")
            docRef.update("status", true)
                .addOnSuccessListener {
                    refreshDataFromFirestore()
                    dialog.dismiss()
                }
                .addOnFailureListener { e ->
                    Log.e("Firestore", "Error getting documents.", e)
                    dialog.dismiss()
                }
        }

        val bEditCancel = dialog.findViewById<ImageView>(R.id.ivCancel)
        bEditCancel.setOnClickListener { dialog.dismiss() }

        dialog.show()
    }

    private fun setUpClickListener() {
        ivCalendarNext.setOnClickListener {
            cal.add(Calendar.MONTH, 1)
            setUpCalendar()
        }
        ivCalendarPrevious.setOnClickListener {
            cal.add(Calendar.MONTH, -1)
            if (cal == currentDate)
                setUpCalendar()
            else
                setUpCalendar()
        }
    }

    /**
     * Setting up adapter for recyclerview
     */
    private fun setUpAdapter() {
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)
        adapter = CalendarAdapter { calendarDateModel: CalendarDateModel, position: Int ->
            calendarList2.forEachIndexed { index, calendarModel ->
                calendarModel.isSelected = index == position
            }
            adapter.setData(calendarList2)
        }
        recyclerView.adapter = adapter
    }

    /**
     * Function to setup calendar for every month
     */
    private fun setUpCalendar() {
        val calendarList = ArrayList<CalendarDateModel>()
        tvDateMonth.text = sdf.format(cal.time)
        val monthCalendar = cal.clone() as Calendar
        val maxDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        dates.clear()
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
        while (dates.size < maxDaysInMonth) {
            dates.add(monthCalendar.time)
            calendarList.add(CalendarDateModel(monthCalendar.time))
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        calendarList2.clear()
        calendarList2.addAll(calendarList)
        adapter.setData(calendarList)
    }
    fun btnAdd(view: View){
        showAddDialog(this)
    }

    fun btnReminder(view: View){
        val intent = Intent(this, ReminderActivity::class.java)
        intent.putExtra("SISAWAKTU", sisaWaktu)
        startActivity(intent)
    }

}