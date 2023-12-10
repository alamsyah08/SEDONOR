package com.example.sedonor

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.sedonor.register.LengkapiData1
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore


class dbFirebase(private val context: Context){

    private var sessionManager: SessionManager = SessionManager(context)
    private var auth: FirebaseAuth = Firebase.auth
    private val firestore = Firebase.firestore
    private var navIntent: NavIntent = NavIntent(context)

     fun LoginFirebase(email: String, password: String){

        var salah ="Email atau Password salah"
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(TaskExecutors.MAIN_THREAD, OnCompleteListener  { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Login Berhasil", Toast.LENGTH_SHORT).show()
                    val user: FirebaseUser? = auth.currentUser
                    val userId = user?.uid
                    if (userId != null) {
                        sessionManager.saveUserId(userId)
                    }
                    navIntent.pindahPage(HomePage::class.java)
                } else {
                    Toast.makeText(context, salah,
                        Toast.LENGTH_SHORT).show()
                }
            })
    }

     fun RegisterFirebase(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(TaskExecutors.MAIN_THREAD, OnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = Firebase.auth.currentUser
                    val userId = user?.uid.toString()
                    if (userId != null) {
                        sessionManager.saveUserId(userId)
                    }
                    val userData = hashMapOf<String, Any?>(
                        "nama" to null,
                        "gender" to null,
                        "tanggalLahir" to null,
                        "domisili" to null,
                        "tinggiBadan" to 0,
                        "beratBadan" to 0,
                        "goldar" to null,
                        "riwayatPenyakit" to null,
                        "kodeDonor" to 0,
                        "status" to false,
                        "checkin" to "01-01-2001"
                    )

                    saveData(userId, userData)

                    Toast.makeText(context, "Register Berhasil", Toast.LENGTH_SHORT).show()
                    navIntent.pindahPage(LengkapiData1::class.java)
                } else {
                    Toast.makeText(context, "Register Gagal.", Toast.LENGTH_SHORT).show()
                }
            })
    }

    fun saveData(uID: String, nama: HashMap<String, Any?>){
        firestore.collection("users").document(uID.toString()).set(nama)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot added")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

     fun updateData(uID : String, judul: String, isi: Any?, movePage: Class<*>){
        val db = firestore.collection("users").document(uID)
        db.update(judul, isi)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot update")
                navIntent.pindahPage(movePage)
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error update document", e) }
    }

//    fun getData(judul: String, uID: String){
//        val db = firestore.collection(judul).document(uID)
//        db.get()
//            .addOnSuccessListener { result ->
//
//            }
//            .addOnFailureListener { exception ->
//                Log.w(TAG, "Error getting documents.", exception)
//            }
//    }
}