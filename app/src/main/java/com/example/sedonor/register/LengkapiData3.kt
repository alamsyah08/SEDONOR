package com.example.sedonor.register

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.sedonor.R
import com.example.sedonor.SessionManager
import com.example.sedonor.dbFirebase
import java.util.Calendar


class LengkapiData3 : AppCompatActivity() {
    lateinit var tanggal: Button
    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var dateButton: Button

    private lateinit var sessionManager: SessionManager
    private lateinit var dbFirebase: dbFirebase
    lateinit var retrievedUserId : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lengkapi_data3)

        dbFirebase = dbFirebase(this)
        sessionManager = SessionManager(this)
        retrievedUserId = sessionManager.getUserId().toString()

//        tanggal = findViewById<Button>(R.id.goldar)
//        dateButton.text = getTodaysDate()

        initDatePicker();
        dateButton = findViewById(R.id.goldar);
        dateButton.setText(getTodaysDate());
    }

    fun keLD4(view : View){
        var vDate = dateButton.getText()
        dbFirebase.updateData(retrievedUserId, "tanggalLahir", vDate, LengkapiData4::class.java)
    }
    fun back(view : View){
        val intentKeLD2 = Intent(this, LengkapiData2::class.java)
        startActivity(intentKeLD2)
    }


    private fun getTodaysDate(): String? {
        val cal: Calendar = Calendar.getInstance()
        val year: Int = cal.get(Calendar.YEAR)
        var month: Int = cal.get(Calendar.MONTH)
        month = month + 1
        val day: Int = cal.get(Calendar.DAY_OF_MONTH)
        return makeDateString(day, month, year)
    }

    private fun initDatePicker() {
        val dateSetListener =
            OnDateSetListener { datePicker, year, month, day ->
                var month = month
                month = month + 1
                val date = makeDateString(day, month, year)
                dateButton.setText(date)
            }
        val cal: Calendar = Calendar.getInstance()
        val year: Int = cal.get(Calendar.YEAR)
        val month: Int = cal.get(Calendar.MONTH)
        val day: Int = cal.get(Calendar.DAY_OF_MONTH)
        val style: Int = AlertDialog.THEME_HOLO_LIGHT
        datePickerDialog = DatePickerDialog(this, style, dateSetListener, year, month, day)
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    private fun makeDateString(day: Int, month: Int, year: Int): String? {
        return getMonthFormat(month) + " " + day + " " + year
    }

    private fun getMonthFormat(month: Int): String {
        if (month == 1) return "JAN"
        if (month == 2) return "FEB"
        if (month == 3) return "MAR"
        if (month == 4) return "APR"
        if (month == 5) return "MAY"
        if (month == 6) return "JUN"
        if (month == 7) return "JUL"
        if (month == 8) return "AUG"
        if (month == 9) return "SEP"
        if (month == 10) return "OCT"
        if (month == 11) return "NOV"
        return if (month == 12) "DEC" else "JAN"

        //default should never happen
    }

    fun openDatePicker(view: View?) {
        datePickerDialog.show()
    }
}