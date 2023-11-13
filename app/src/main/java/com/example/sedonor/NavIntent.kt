package com.example.sedonor

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity

class NavIntent(private val context: Context) : AppCompatActivity() {
    fun pindahPage(page: Class<*>){
        var movePage = Intent(context, page)
        context.startActivity(movePage)
    }
}